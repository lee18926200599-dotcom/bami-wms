package com.usercenter.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.basedata.common.dto.DictionaryItemDto;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.org.client.feign.StaffFeign;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.QueryBizByGroupIdAndUserIdParam;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.permission.dto.AdminGroupDto;
import com.org.permission.common.permission.dto.GetUserMenuPermissionsDto;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import com.org.permission.common.permission.param.UserMenuParam;
import com.org.permission.common.query.BatchQueryParam;
import com.usercenter.common.dto.*;
import com.usercenter.common.enums.*;
import com.usercenter.common.dto.request.GetLoginInfoListRequest;
import com.usercenter.common.dto.request.GetMenusByPhoneReq;
import com.usercenter.common.dto.request.LoginRequest;
import com.usercenter.common.dto.request.UserSaveRespDTO;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.constant.command.enums.OrgTypeEnum;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.entity.BaseUserPasswordHistory;
import com.usercenter.server.mapper.BaseUserDetailMapper;
import com.usercenter.server.mapper.BaseUserMapper;
import com.usercenter.server.common.exception.WarnException;
import com.usercenter.server.domain.condition.UserDetailCondition;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.domain.service.PermissionDomainService;
import com.usercenter.server.service.IBaseUserApiService;
import com.usercenter.server.service.IBaseUserCheckService;
import com.usercenter.server.utils.RegexUtils;
import com.usercenter.server.utils.UserRedisUtil;
import com.usercenter.server.utils.UserStaffUtil;
import com.usercenter.server.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Description: 用户服务，通用实现
 */
@Service
public class BaseUserApiServiceImpl extends BaseUserCommonServiceImpl implements IBaseUserApiService {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserApiServiceImpl.class);

    /**
     * 需要默认启用的业务结合
     */
    private static Set<BusinessSystemEnum> ENABLE_SET = Sets.newHashSet();

    /**
     * 不需要权限认证的系统
     */
    private static Set<BusinessSystemEnum> NO_PERMISSION_SYSTEM = Sets.newHashSet();


    /**
     * APP与注册来源映射关系
     */
    private static Map<String, String> APPNAMES = new HashMap<>();

    /**
     * 允许多端登陆的系统
     */
    private static Set<BusinessSystemEnum> MULTI_LOGIN_SYSTEM = Sets.newHashSet();

    /**
     * 初始化
     */
    static {
        NO_PERMISSION_SYSTEM.add(BusinessSystemEnum.PLATFORM);
        ENABLE_SET.add(BusinessSystemEnum.ORGANIZATION);
        ENABLE_SET.add(BusinessSystemEnum.PERMISSION);
        ENABLE_SET.add(BusinessSystemEnum.BOSS);

    }

    @Value("${multi.device.login:true}")
    protected Boolean multiDeviceLogin;

    @Value("${spring.profiles.active:dev}")
    protected String profile;

    @Autowired
    private BaseUserDetailMapper detailMapper;

    @Autowired
    private BaseUserMapper userMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    protected UserRedisUtil userRedisUtil;

    @Autowired
    private IBaseUserCheckService userCheckService;


    @Resource
    private AmqpTemplate amqpTemplate;

    @Autowired
    private PermissionDomainService permissionDomainService;
    @Autowired
    private StaffFeign staffFeign;
    @Autowired
    private DictionaryDomainService dictionaryDomainService;
    @Autowired
    private OrgDomainService orgDomainService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserSaveRespDTO register(UserSaveDto saveDTO) {
        //参数校验
        checkUser(saveDTO);
        //默认启用的业务
        saveDTO.setState(UserStateEnum.UNENABLE.getCode());
        if (ENABLE_SET.contains(BusinessSystemEnum.getEnum(saveDTO.getBusinessSystem()))) {
            saveDTO.setState(UserStateEnum.ENABLE.getCode());
        }
        //拷贝参数到实体
        save(saveDTO);
        //拷贝实体上的密码等属性
        UserSaveRespDTO respDTO = new UserSaveRespDTO();
        BeanUtils.copyProperties(saveDTO, respDTO);
        //生成token
        UserUtil.TokenContent tokenContent = userUtil.generateToken(SourceTypeEnum.getEnum(saveDTO.getSourceType()), BusinessSystemEnum.getEnum(saveDTO.getBusinessSystem()), saveDTO.getUserAuthId());
        respDTO.setToken(tokenContent.getToken());
        respDTO.setTokenExpireDate(tokenContent.getTokenExpireDate());
        respDTO.setTokenExpireTime(tokenContent.getTokenExpireTime());
        return respDTO;
    }


    @Override
    public UserAuthDto getUserAuthInfoById(Long id) {
        BaseUser baseUser = baseUserMapper.load(id);
        if (baseUser == null) {
            return null;
        }
        UserAuthDto userInfoDTO = new UserAuthDto();
        BeanUtils.copyProperties(baseUser, userInfoDTO);
        userInfoDTO.setUserAuthId(baseUser.getId());
        return userInfoDTO;
    }

    @Override
    public List<FplUser> getUserInfoList(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        UserDetailCondition condition = new UserDetailCondition();
        condition.setIds(ids);
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        List<BaseUserInfo> baseUserInfoList = detailMapper.selectDetailList(condition);
        if (CollectionUtils.isEmpty(baseUserInfoList)) {
            return Lists.newArrayList();
        }
        List<FplUser> fplUserInfoDTOList = UserUtil.convertToUserList(baseUserInfoList);
        return fplUserInfoDTOList;
    }

    @Override
    public Map<Long, FplUser> getUserInfoMap(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Maps.newHashMap();
        }
        UserDetailCondition condition = new UserDetailCondition();
        condition.setIds(ids);
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        List<BaseUserInfo> baseUserInfoList = detailMapper.selectDetailList(condition);
        UserStaffUtil.setStaffInfo(baseUserInfoList);
        if (CollectionUtils.isEmpty(baseUserInfoList)) {
            return Maps.newHashMap();
        }
        Map<Long, FplUser> userMap = Maps.newHashMap();
        List<FplUser> fplUserInfoDTOList = UserUtil.convertToUserList(baseUserInfoList);
        for (FplUser fplUserInfoDTO : fplUserInfoDTOList) {
            userMap.put(fplUserInfoDTO.getId(), fplUserInfoDTO);
        }
        return userMap;
    }

    @Override
    public void updateUser(FplUser updateDTO) {
        //不允许修改密码
        updateDTO.setPassword(null);
        BaseUser baseUser = new BaseUser();
        UserDetailCondition condition = new UserDetailCondition();
        condition.setId(updateDTO.getId());
        BaseUserInfo baseUserInfo = detailMapper.selectDetail(condition);
        //用户名校验
        AssertUtils.notNull(baseUserInfo, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        baseUser.setId(baseUserInfo.getUserId());
        if (StringUtil.isNotEmpty(updateDTO.getUserName()) && !updateDTO.getUserName().equals(baseUserInfo.getUserName())) {
            List<BaseUserInfo> existUsers = getUserListByUserName(updateDTO.getUserName(), null, null);
            AssertUtils.isEmpty(existUsers, ReturnCodesEnum.USERNAME_EXISTS.getMessage());
            baseUser.setUserName(updateDTO.getUserName());
        }
        //手机号校验
        if (StringUtils.isNotEmpty(updateDTO.getPhone()) && !updateDTO.getPhone().equals(baseUserInfo.getPhone())) {
            AssertUtils.isTrue(RegexUtils.isMobileSimple(updateDTO.getPhone()), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
            List<BaseUserInfo> existUsers = getUserListByPhone(updateDTO.getPhone(), null, null);
            AssertUtils.isEmpty(existUsers, ReturnCodesEnum.PHONE_EXISTS.getMessage());
            baseUser.setPhone(updateDTO.getPhone());
        }
        //邮箱校验
        if (StringUtils.isNotEmpty(updateDTO.getEmail()) && !updateDTO.getEmail().equals(baseUserInfo.getEmail())) {
            AssertUtils.isTrue(RegexUtils.isEmail(updateDTO.getEmail()), ReturnCodesEnum.REGEX_FAILD_EMAIL.getMessage());
            List<BaseUserInfo> existUsers = getUserListByEmail(updateDTO.getEmail(), null, null);
            AssertUtils.isEmpty(existUsers, ReturnCodesEnum.EMAIL_EXISTS.getMessage());
            baseUser.setEmail(updateDTO.getEmail());
        }
        baseUser.setModifiedBy(updateDTO.getModifiedBy());
        baseUser.setModifiedDate(new Date());
        baseUser.setUserCode(updateDTO.getUserCode());
        baseUser.setManagerLevel(updateDTO.getManagerLevel());
        baseUser.setFirstTimeLoginFlag(updateDTO.getFirstTimeLoginFlag());
        baseUser.setLockFlag(updateDTO.getLockFlag());
        baseUser.setPasswordFlag(updateDTO.getPasswordFlag());
        BaseUserDetail baseUserDetail = new BaseUserDetail();
        BeanUtils.copyProperties(updateDTO, baseUserDetail);
        baseUserDetail.setModifiedDate(new Date());
        baseUser.setDetailList(Arrays.asList(baseUserDetail));
        updateUser(baseUser);
    }


    @Override
    public UserAllPermissionDto getMenuPermissonsByToken(String token) {
        UserAllPermissionDto userAllPermissionDto = new UserAllPermissionDto();
        try {
            //从缓存中获取菜单权限
            userAllPermissionDto = (UserAllPermissionDto) userRedisUtil.get(token + BaseUserConstants.CACHE_MENU_PERMISSIONS_SUFFIX);
            if (userAllPermissionDto != null) {
                return userAllPermissionDto;
            }
            //如果缓存中没有则现查
            UserUtil.TokenContent tokenContent = userUtil.getTokenContent(token);
            String userId = tokenContent.getId();
            String sourceType = tokenContent.getSourceType().toString();
            if (StringUtils.isEmpty(userId)) {
                return userAllPermissionDto;
            }
            FplUser fplUser = getUserById(Long.valueOf(userId));
            if (fplUser == null) {
                return userAllPermissionDto;
            }
            FplUser puser = new FplUser();
            BeanUtils.copyProperties(fplUser, puser);
            userAllPermissionDto = permissionDomainService.getPermissonsByUid(fplUser.getId(), fplUser.getGroupId(), DomainEnum.BOSS.getValue(), sourceType, puser);
            return userAllPermissionDto;
        } catch (Exception e) {
            logger.error("access permission failed " + e.getMessage());
        }
        return userAllPermissionDto;
    }


    @Override
    public FplUser login(LoginRequest loginRequest) {
        //获取用户认证信息
        String loginName = loginRequest.getLoginName();
        BusinessSystemEnum businessSystem = loginRequest.getBusinessSystem();
        logger.info("-------------开始匹配用户，用户名{}，来源系统{}", loginName, businessSystem == null ? "" : businessSystem.toString());
        FplUser fplUser = getUserByLoginInfo(loginName, businessSystem, loginRequest.getSourceType(), (Long) null);
        if (fplUser != null) {
            fplUser.setBelongGroupId(fplUser.getGroupId());
        } else {
            String jsonString = JSON.toJSONString(loginRequest);
            jsonString = jsonString.replaceAll("\"password.*?,", "\"password\":\"和谐\",");
            logger.info("用户登陆异常：用户不存在：", "登陆信息：\n {}", jsonString);
        }
        logger.info("----最终匹配到用户：{}-------------", JSON.toJSONString(fplUser));
        //登陆校验
        userCheckService.loginCheck(loginRequest, fplUser);
        //登陆成功的逻辑
        loginSuccess(loginRequest, fplUser);
        //权限
        loginInitPermissions(loginRequest, fplUser);
        return fplUser;
    }

    @Override
    public List<LoginInfoDto> getLoginInfoList(GetLoginInfoListRequest request) {
        //参数获取和校验
        BusinessSystemEnum businessSystem = request.getBusinessSystem();
        String token = request.getToken();
        Assert.notNull(token, I18nUtils.getMessage("user.check.token.null"));
        Assert.notNull(businessSystem, I18nUtils.getMessage("user.check.business.system.null"));
        //获取token中的用户id
        UserUtil.TokenContent tokenContent = userUtil.getTokenContent(token);
        String userId = tokenContent.getId();
        //获取用户的key，如果传了业务系统返回指定业务系统的key，如果没有返回全部的
        TreeSet<String> userKeys = userRedisUtil.getUserKeys(userId);
        if (ObjectUtils.isEmpty(userKeys)) {
            return Lists.newArrayList();
        }
        List<LoginInfoDto> result = Lists.newArrayList();
        if (ObjectUtils.isEmpty(businessSystem)) {
            userKeys.forEach(key -> {
                String value = (String) userRedisUtil.get(key);
                if (token.equals(value)) {
                    return;
                }
                LoginInfoDto loginInfo = new LoginInfoDto(key, value);
                result.add(loginInfo);
            });
            return result;
        }
        userKeys.forEach(key -> {
            if (key.indexOf(businessSystem.toString()) > 0) {
                String value = (String) userRedisUtil.get(key);
                if (token.equals(value)) {
                    return;
                }
                LoginInfoDto loginInfo = new LoginInfoDto(key, value);
                result.add(loginInfo);
            }
        });

        return result;
    }

    @Override
    public void logoutUser(LogoutUserReq logoutUserReq) {
        Assert.notNull(logoutUserReq.getToken(), I18nUtils.getMessage("user.check.token.null"));
        UserUtil.TokenContent tokenContent = userUtil.getTokenContent(logoutUserReq.getToken());
        List<String> keys = logoutUserReq.getKeys();
        if (!ObjectUtils.isEmpty(keys)) {
            for (String key : keys) {
                if (key.indexOf(tokenContent.getId()) > 0) {
                    userRedisUtil.del(key);
                }
            }
        }
    }


    @Override
    public void logout(String token) {
        //获取token信息
        userRedisUtil.del(token);
    }

    @Override
    public FplUser getUserByToken(String token, Long groupId) {
        FplUser fplUser = new FplUser();
        UserUtil.TokenContent tokenContent = userUtil.getTokenContent(token);
        String userId = tokenContent.getId();
        BusinessSystemEnum businessSystem = tokenContent.getBusinessSystem();
        SourceTypeEnum sourceType = tokenContent.getSourceType();
        if (StringUtils.isNotEmpty(userId)) {
            fplUser = getUserByLoginInfo(Long.valueOf(userId), businessSystem, groupId);
            if (fplUser != null) {
                fplUser.setBelongGroupId(fplUser.getGroupId());
                if (groupId != null) {
                    fplUser.setGroupId(groupId);
                }
                fplUser.setToken(token);
                fplUser.setTokenExpireTime(tokenContent.getTokenExpireTime());
                LoginRequest loginRequest = new LoginRequest(businessSystem, sourceType);
                getUserByTokenInitPermissions(loginRequest, fplUser);
            }
        }
        return fplUser;
    }

    @Override
    public FplUser getTokenByUserId(Long userId, BusinessSystemEnum businessSystem, SourceTypeEnum sourceType) {
        FplUser fplUser = getUserById(userId);
        AssertUtils.notNull(fplUser, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        UserUtil.TokenContent tokenContent = userUtil.generateToken(sourceType, businessSystem, fplUser.getUserAuthId());
        fplUser.setToken(tokenContent.getToken());
        fplUser.setTokenExpireDate(tokenContent.getTokenExpireDate());
        fplUser.setTokenExpireTime(tokenContent.getTokenExpireTime());
        return fplUser;
    }

    @Override
    public void isTokenValid(Long userId, String token) {
        //获取token还能活多久
        String key;
        FplUser fplUser = getUserById(userId);
        AssertUtils.notNull(fplUser, ReturnCodesEnum.INVALID_TOKEN.getMessage());
        Map<String, String> userTokenMap = userUtil.getUserTokenMap(fplUser.getUserAuthId().toString());
        AssertUtils.isTrue(userTokenMap.get(token) != null, ReturnCodesEnum.INVALID_TOKEN.getMessage());
        long ttl = userRedisUtil.getExpire(userTokenMap.get(token));
        AssertUtils.isTrue(ttl > 0, ReturnCodesEnum.INVALID_TOKEN.getMessage());
        key = userTokenMap.get(token);


        AssertUtils.isTrue(ttl > 0, ReturnCodesEnum.INVALID_TOKEN.getMessage());
        try {
            UserUtil.TokenContent tokenContent = userUtil.getTokenContent(token);
            SourceTypeEnum sourceType = tokenContent.getSourceType();
            BusinessSystemEnum businessSystem = tokenContent.getBusinessSystem();

            if (SourceTypeEnum.PC.equals(sourceType)) {
                if (!multiDeviceLogin) {
                    //多设备登录
                    String userToken = userRedisUtil.getStr(tokenContent.getId() + "_" + sourceType.toString());
                    AssertUtils.isTrue(token.equals(userToken), ReturnCodesEnum.LOGININ_OTHER.getMessage());
                }
            }
            AssertUtils.isTrue(tokenContent.getId().equals(fplUser.getUserAuthId().toString()), ReturnCodesEnum.LOGININ_OTHER.getMessage());
            String idLock = userRedisUtil.getStr(fplUser.getUserAuthId() + BaseUserConstants.USER_LOCK_SUFFIX);
            AssertUtils.isTrue(StringUtils.isEmpty(idLock), ReturnCodesEnum.ACCOUNT_LOCK.getMessage());
            AssertUtils.isTrue(fplUser.getState() != null && UserStateEnum.ENABLE.getCode().intValue() == fplUser.getState(), ReturnCodesEnum.NOT_ENABLE.getMessage());
            //token延期操作
            if (SourceTypeEnum.PC.equals(sourceType)) {
                if (ttl < BaseUserConstants.WEB_ACTIVE_TIME) {
                    userRedisUtil.expire(key, BaseUserConstants.WEB_ACTIVE_TIME, TimeUnit.SECONDS);
                }
            } else {
                userRedisUtil.expire(key, userUtil.getAppTokenExpireTime(), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            handleException(ReturnCodesEnum.INVALID_TOKEN, e);
        }


    }

    @Override
    public String getPasswordCode(String phone, String email, ValidateTypeEnum validateType) {
        try {
            BaseUser user = null;
            AssertUtils.isTrue(StringUtils.isNotEmpty(phone) || StringUtils.isNotEmpty(email), ReturnCodesEnum.PHONE_EMAIL_EMPTY.getMessage());
            if (StringUtils.isNotEmpty(phone)) {
                AssertUtils.isTrue(RegexUtils.isMobileSimple(phone), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
                user = getUserByLoginName(phone);
            }
            if (StringUtils.isNotEmpty(email)) {
                AssertUtils.isTrue(RegexUtils.isEmail(email), ReturnCodesEnum.REGEX_FAILD_EMAIL.getMessage());
                user = getUserByLoginName(email);
            }
            AssertUtils.notNull(user, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
            String code = UserUtil.getCode();
            if (validateType.getCode() == ValidateTypeEnum.PHONE.getCode()) {
            } else if (validateType.getCode() == ValidateTypeEnum.EMAIL.getCode()) {
            } else if (validateType.getCode() == ValidateTypeEnum.CODE.getCode()) {

            }
        } catch (Exception e) {
            logger.info("发送验证码失败：", e);
            handleException(ReturnCodesEnum.GET_CODE_ERROR, e);
        }
        return null;
    }

    @Override
    public PageInfo<FplUser> findUserListPage(UserDto userDto, Integer pageNum, Integer pageSize) {
        if (userDto.getFilterByUserPermission()) {
            AssertUtils.notNull(userDto.getQueryUserId(), ReturnCodesEnum.CURRENTID_NOTNULL.getMessage());
            BaseUserDetail userDetail = getDetailById(userDto.getQueryUserId());
            AssertUtils.notNull(userDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
            //如果是普通用户，需要过滤用户组织权限
            if (userDetail.getManagerLevel() == UserManagerLevelEnum.USER.getCode()) {
                Set<Long> orgIds = permissionDomainService.getOrgsByUidAndGroupId(userDetail.getId(), userDto.getGroupId());
                if (CollectionUtils.isNotEmpty(orgIds)) {
                    userDto.setOrgIdList(new ArrayList<>(orgIds));
                }
            }
        }
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<BaseUserInfo> users = baseUserDetailMapper.findUserList(userDto);
        UserStaffUtil.setStaffInfo(users);
        List<FplUser> fplUserList = UserUtil.convertToUserList(users);
        translate(fplUserList);
        PageInfo<FplUser> result = new PageInfo(fplUserList);
        result.setPages(page.getPages());
        result.setPageSize(page.getPageSize());
        result.setPageNum(page.getPageNum());
        result.setTotal(page.getTotal());
        return result;
    }


    /**
     * 翻译人员真实姓名
     *
     * @param fplUserList
     */
    private void translate(List<FplUser> fplUserList) {
        if (CollectionUtils.isNotEmpty(fplUserList)) {
            List<Long> staffIdList = fplUserList.stream().map(user -> user.getArchivesId()).collect(Collectors.toList());
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(staffIdList);
            RestMessage<List<StaffInfoDto>> staffRestMessage = staffFeign.batchQueryStaffsInfo(batchQueryParam);
            if (staffRestMessage != null && CollectionUtils.isNotEmpty(staffRestMessage.getData())) {
                Map<Long, String> staffMap = staffRestMessage.getData().stream().collect(Collectors.toMap(StaffInfoDto::getId, StaffInfoDto::getRealname));
                for (FplUser fplUser : fplUserList) {
                    String realName = staffMap.get(fplUser.getArchivesId());
                    fplUser.setRealName(realName);
                }
            }
        }
    }

    @Override
    public List<FplUser> findUserList(UserDto userDto) {
        String source = userDto.getSource();

        //默认查询未删除的
        if (ObjectUtils.isEmpty(userDto.getDeletedFlag())) {
            userDto.setDeletedFlag(FlagEnum.FALSE.getCode());
        }
        //查询删除和未删除的
        if (userDto.getWithDeleted() != null && userDto.getWithDeleted() == true) {
            userDto.setDeletedFlag(null);
        }
        userDto.setSource(source);
        List<BaseUserInfo> users = baseUserDetailMapper.findUserList(userDto);
        List<FplUser> fplUserList = UserUtil.convertToUserList(users);
        return fplUserList;
    }


    @Override
    public List<DictionaryItemDto> registerSource() {
        return dictionaryDomainService.getItemList("4PL_USER_REGISTER_SOURCE");
    }

    /**
     * 异常处理（不捕获warnexception）
     */
    private void handleException(ReturnCodesEnum returnCodesEnum, Exception e) {
        if (!(e instanceof WarnException)) {
            throw new WarnException(returnCodesEnum.getMessage());
        } else {
            throw (WarnException) e;
        }
    }

    /**
     * 登陆成功
     *
     * @param loginReq 登陆参数
     * @return 用户详情
     */
    private FplUser loginSuccess(LoginRequest loginReq, FplUser fplUser) {
        SourceTypeEnum sourceType = loginReq.getSourceType();
        BusinessSystemEnum businessSystem = loginReq.getBusinessSystem();
        AssertUtils.notNull(fplUser, ReturnCodesEnum.USERNAME_NOTEXISTS.getMessage());
        //生成token
        UserUtil.TokenContent tokenContent = userUtil.generateToken(sourceType, businessSystem, fplUser.getUserAuthId());
        fplUser.setToken(tokenContent.getToken());
        fplUser.setTokenExpireDate(tokenContent.getTokenExpireDate());
        fplUser.setTokenExpireTime(tokenContent.getTokenExpireTime());
        //删除登录次数
        userRedisUtil.del(fplUser.getUserAuthId().toString() + BaseUserConstants.LOGIN_FAIL_COUNT_SUFFIX);
        fplUser.setPassword(null);
        if (fplUser.getFirstTimeLoginFlag() == FlagEnum.TRUE.getCode()) {
            BaseUser tempUser = new BaseUser();
            tempUser.setId(fplUser.getUserAuthId());
            tempUser.setFirstTimeLoginFlag(FlagEnum.FALSE.getCode());
            tempUser.setPasswordFlag(FlagEnum.FALSE.getCode());
            updateUser(tempUser);
        }
        processLoginMode(loginReq, tokenContent.getToken());
        return fplUser;
    }

    /**
     * 处理登陆模式
     *
     * @param request 登陆请求
     * @param token   登陆请求
     */
    @Async
    public void processLoginMode(LoginRequest request, String token) {
        LoginModeEnum loginMode = request.getLoginMode();
        List<LoginInfoDto> loginInfoList = Lists.newArrayList();
        if (loginMode == null || LoginModeEnum.NONE.equals(loginMode)) {
            return;
        } else if (LoginModeEnum.APP_SINGLE.equals(loginMode)) {
            Assert.notNull(request.getBusinessSystem(), I18nUtils.getMessage("user.check.login.business.system.null"));
            loginInfoList = getLoginInfoList(new GetLoginInfoListRequest(token, request.getBusinessSystem()));

        } else if (LoginModeEnum.GLOBAL_SINGLE.equals(loginMode)) {
            loginInfoList = getLoginInfoList(new GetLoginInfoListRequest(token, null));
        }
        for (LoginInfoDto loginInfoDTO : loginInfoList) {
            logoutUser(new LogoutUserReq(loginInfoDTO.getToken(), loginInfoDTO.getKey()));
        }
    }


    /**
     * 初始化权限信息
     *
     * @param loginRequest 登陆请求
     * @param fplUser      用户信息
     */
    private void loginInitPermissions(LoginRequest loginRequest, FplUser fplUser) {
        //参数陈列
        SourceTypeEnum sourceType = loginRequest.getSourceType();
        BusinessSystemEnum businessSystem = loginRequest.getBusinessSystem();
        //需要权限验证的系统初始化用户权限数据
        if (!NO_PERMISSION_SYSTEM.contains(loginRequest.getBusinessSystem())) {
            //多端登陆
            userRedisUtil.set(fplUser.getUserAuthId().toString() + "_" + sourceType.toString(), fplUser.getToken(), userUtil.getTtlMillis(sourceType));

            if (fplUser.getManagerLevel() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
                fplUser.setGroupId(BaseUserConstants.Plat_GROUP_ID);
            }
            //获取菜单权限
            UserAllPermissionDto userMenus = getUserMenus(fplUser, loginRequest, true);
            //获取集团权限
            Object userGroups = getUserGroups(fplUser.getId(), true);
            AssertUtils.isNotEmpty((List)userGroups,I18nUtils.getMessage("user.check.user.group.permission.null"));
            //获取组织权限
            Object userOrgs = getUserOrgs(fplUser, userGroups, true);
            AssertUtils.notNull(userOrgs,I18nUtils.getMessage("user.check.user.unit.permission.null"));
            fplUser.setMenus(userMenus);
            fplUser.setGroupInfo(userGroups);
            fplUser.setOrgInfo(userOrgs);

        }
        Long globalCustomerId = getGlobalCustomer(fplUser);
        fplUser.setGlobalCustomer(globalCustomerId);
        //权限存入Redis
        if (fplUser.getMenus() != null) {
            userRedisUtil.set(fplUser.getToken() + "_menuPermissons", fplUser.getMenus(), fplUser.getTokenExpireTime());
            fplUser.getMenus().setAllFunc(new ArrayList<>());
        }
    }

    /**
     * Description: 初始化权限信息
     *
     * @param loginRequest 登陆请求
     * @param fplUser      用户信息
     */
    private void getUserByTokenInitPermissions(LoginRequest loginRequest, FplUser fplUser) {
        //获取菜单权限
        UserAllPermissionDto userMenus = getUserMenus(fplUser, loginRequest, false);
        //获取集团权限
        Object userGroups = getUserGroups(fplUser.getId(), false);
        //获取组织权限
        Object userOrgs = getUserOrgs(fplUser, userGroups, false);
        fplUser.setMenus(userMenus);
        fplUser.setGroupInfo(userGroups);
        fplUser.setOrgInfo(userOrgs);
        Long globalCustomerId = getGlobalCustomer(fplUser);
        fplUser.setGlobalCustomer(globalCustomerId);
        //权限存入Redis
        if (!ObjectUtils.isEmpty(fplUser.getMenus())) {
            userRedisUtil.set(fplUser.getToken() + "_menuPermissons", fplUser.getMenus(), fplUser.getTokenExpireTime());
            fplUser.getMenus().setAllFunc(new ArrayList<>());
        }
    }

    /**
     * 获取用户集团列表
     *
     * @param userId 用户id
     * @return 用户集团列表
     */
    private Object getUserGroups(Long userId, Boolean isLogin) {
        List<Object> userGroups = Lists.newArrayList();
        List<FplUser> detailList = getDetailListByUserId(userId);
        Set<FplUser> globalFplUserSet = detailList.stream().filter(user -> user.getManagerLevel().equals(UserManagerLevelEnum.GLOBAL_ADMINI.getCode())).collect(Collectors.toSet());
        Set<Long> groupUserSet = detailList.stream().filter(user -> user.getManagerLevel().equals(UserManagerLevelEnum.GROUP_ADMINI.getCode())).map(user -> user.getId()).collect(Collectors.toSet());
        Set<Long> userSet = detailList.stream().filter(user -> user.getManagerLevel().equals(UserManagerLevelEnum.USER.getCode())).map(user -> user.getId()).collect(Collectors.toSet());
        //全局管理员
        if (CollectionUtils.isNotEmpty(globalFplUserSet)) {
            GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
            groupListQueryParam.setPageSize(10000);// 默认查1000
            PageInfo<OrgInfoDto> orgInfoDtoPageInfo = orgDomainService.queryAllGroupInfoList(groupListQueryParam);
            if (orgInfoDtoPageInfo != null && CollectionUtils.isNotEmpty(orgInfoDtoPageInfo.getList())) {
                userGroups.addAll(orgInfoDtoPageInfo.getList());
            }
        }
        //集团管理员
        if (CollectionUtils.isNotEmpty(groupUserSet)) {
            RestMessage<List<AdminGroupDto>> groupsRestMessage = permissionDomainService.getGroupsByIds(groupUserSet);
            if (isLogin) {
                AssertUtils.isTrue(groupsRestMessage.isSuccess() && CollectionUtils.isNotEmpty(groupsRestMessage.getData()), ReturnCodesEnum.USER_NO_GROUP.getMessage());
                userGroups.addAll(groupsRestMessage.getData());
            } else {
                if (groupsRestMessage.isSuccess()) {
                    userGroups.addAll(groupsRestMessage.getData());
                }
            }
        }
        //普通用户
        if (!ObjectUtils.isEmpty(userSet)) {
            RestMessage<Set<Long>> orgPermissions = permissionDomainService.batchGetOrgPermissions(userSet);
            if (orgPermissions.isSuccess() && CollectionUtils.isNotEmpty(orgPermissions.getData())) {
                List<Long> pesIds = Lists.newArrayList(orgPermissions.getData());
                BatchQueryParam batchQueryParam = new BatchQueryParam();
                batchQueryParam.setIds(pesIds);
                List<Integer> orgTypes = new ArrayList<>();
                orgTypes.add(OrgTypeEnum.BIZ_UNIT.getType());
                batchQueryParam.setOrgTypes(orgTypes);
                RestMessage<List<OrgInfoDto>> orgRestMessage = orgDomainService.queryGroupInfoByParam(batchQueryParam);
                if (orgRestMessage.isSuccess()) {
                    userGroups.addAll(orgRestMessage.getData());
                }
            }
        }
        userGroups = distinct(userGroups);
        return userGroups;
    }

    /**
     * 集团去重
     */
    private List<Object> distinct(List<Object> groupList) {
        List<Object> result = Lists.newArrayList();
        Set<Long> groupSet = Sets.newHashSet();
        for (Object o : groupList) {
            if (o instanceof AdminGroupDto) {
                AdminGroupDto adminGroupDto = (AdminGroupDto) o;
                boolean add = groupSet.add(adminGroupDto.getGroupId());
                if (add) {
                    result.add(adminGroupDto);
                }
            } else {
                OrgInfoDto orgInfoDto = (OrgInfoDto) o;
                boolean add = groupSet.add(orgInfoDto.getGroupId());
                if (add) {
                    result.add(orgInfoDto);
                }
            }
        }
        return result;
    }


    /**
     * 获取集团业务单元列表
     *
     * @param fplUser 用户信息
     * @param groups  集团信息
     * @return
     */
    private Object getUserOrgs(FplUser fplUser, Object groups, Boolean isLogin) {
        if (fplUser.getManagerLevel() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
            RestMessage<List<OrgInfoDto>> restMessage = orgDomainService.queryBUByGroupId(fplUser.getGroupId());
            if (restMessage.isSuccess()) {
                return restMessage.getData();
            }
            //集团管理员
        } else if (fplUser.getManagerLevel() == UserManagerLevelEnum.GROUP_ADMINI.getCode()) {
            boolean exists = false;
            List<AdminGroupDto> adminGroupDtos = (List<AdminGroupDto>) groups;
            Long groupId = 0L;
            if (isLogin) {
                //登陆时，用户时集团管理员，如果用户管理的集团时用户的所属集团，默认切换到所属集团，否则默认取第一条
                if (fplUser.getGroupId() != null) {
                    exists = adminGroupDtos.stream().anyMatch(adminGroupDto -> adminGroupDto.getGroupId().intValue() == fplUser.getGroupId().intValue());
                }
                if (exists) {
                    groupId = fplUser.getGroupId();
                } else {
                    AdminGroupDto adminGroupDto = adminGroupDtos.stream().min(Comparator.comparing(AdminGroupDto::getGroupId)).get();
                    groupId = adminGroupDto.getGroupId();
                    fplUser.setGroupId(adminGroupDto.getGroupId());
                }
            } else {
                groupId = fplUser.getGroupId();
            }
            RestMessage<List<OrgInfoDto>> restMessage = orgDomainService.queryBUByGroupId(groupId);
            if (restMessage.isSuccess()) {
                return restMessage.getData();
            }
            //普通用户
        } else if (fplUser.getManagerLevel() == UserManagerLevelEnum.USER.getCode()) {
            QueryBizByGroupIdAndUserIdParam queryBizByGroupIdAndUserIdParam = new QueryBizByGroupIdAndUserIdParam();
            queryBizByGroupIdAndUserIdParam.setGroupId(fplUser.getGroupId());
            queryBizByGroupIdAndUserIdParam.setUserId(fplUser.getId());
            RestMessage<List<OrgInfoDto>> restMessage = orgDomainService.queryBizByGroupIdAndUserId(queryBizByGroupIdAndUserIdParam);
            if (restMessage.isSuccess()) {
                return restMessage.getData();
            }
        }
        return Lists.newArrayList();
    }

    /**
     * 获取用户菜单
     *
     * @param loginRequest 登陆参数
     * @param fplUser      用户信息
     * @return 菜单
     */
    private UserAllPermissionDto getUserMenus(FplUser fplUser, LoginRequest loginRequest, Boolean isLogin) {
        String businessSystem = loginRequest.getBusinessSystem().toString();
        String sourceType = loginRequest.getSourceType().toString();
        Long userId = fplUser.getId();
        Long groupId = fplUser.getGroupId();
        Integer domain = DomainEnum.parser(businessSystem, sourceType).getValue();
        AssertUtils.isNotTrue(domain.equals(0), ReturnCodesEnum.DOMAIN_NOT_EXIST.getMessage());
        //查询权限
        FplUser puser = new FplUser();
        BeanUtil.copyProperties(fplUser, puser);
        UserAllPermissionDto userAllPermissionDto;
        if (isLogin) {
            userAllPermissionDto = permissionDomainService.getPermissonsByUid(userId, groupId, domain, sourceType, puser);
        } else {
            userAllPermissionDto = permissionDomainService.getPermissonsByToken(userId, groupId, domain, sourceType, puser);
        }
        return userAllPermissionDto;
    }

    /**
     * 获取全局客商信息
     *
     * @param fplUser 用户信息
     * @return 全局客商id
     */
    private Long getGlobalCustomer(FplUser fplUser) {
        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserNameAndPassword(BaseUser user, BaseUserPasswordHistory userPasswordHistory) {
        updateUser(user);
        userPasswordHistoryMapper.insert(userPasswordHistory);
    }

    @Override
    public List<FplUser> findUserList(String condition, Long orgId) {
        UserDto userDto = new UserDto();
        userDto.setOrgId(orgId);
        userDto.setCondition(condition);
        List<BaseUserInfo> userInfoList = baseUserDetailMapper.findUserList(userDto);
        AssertUtils.isNotEmpty(userInfoList, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        return UserUtil.convertToUserList(userInfoList);
    }


    @Override
    public List<FplUser> getDetailListByUserId(Long userId) {
        List<BaseUserDetail> baseUserDetails = baseUserDetailMapper.selectDetailListByUserId(userId);
        if (ObjectUtils.isEmpty(baseUserDetails)) {
            return Lists.newArrayList();
        }
        List<FplUser> fplUserList = new ArrayList<>();
        for (BaseUserDetail baseUserDetail : baseUserDetails) {
            FplUser fplUser = new FplUser();
            BeanUtils.copyProperties(baseUserDetail, fplUser);
            fplUserList.add(fplUser);
        }
        return fplUserList;
    }


    @Override
    public BaseUserInfo getUserByPhone(String phone, Integer source) {
        UserDetailCondition condition = new UserDetailCondition();
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        condition.setPhone(phone);
        condition.setSource(source);
        condition.setState(UserStateEnum.ENABLE.getCode());
        List<BaseUserInfo> userList = baseUserDetailMapper.selectDetailList(condition);
        return ObjectUtils.isEmpty(userList) ? null : userList.get(0);
    }

    public List<GetUserMenuPermissionsDto> getMenusByPhone(GetMenusByPhoneReq req) {
        UserDetailCondition condition = new UserDetailCondition();
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        condition.setPhone(req.getPhone());
        condition.setState(UserStateEnum.ENABLE.getCode());
        List<BaseUserInfo> userList = baseUserDetailMapper.selectDetailList(condition);
        if (ObjectUtils.isEmpty(userList)) {
            return Lists.newArrayList();
        }
        List<UserMenuParam> userMenuParamList = Lists.newArrayList();
        userList.forEach(baseUserInfo -> {
            if (baseUserInfo.getGroupId() != null) {
                UserMenuParam userMenuReqDto = new UserMenuParam();
                userMenuReqDto.setUserId(baseUserInfo.getId());
                userMenuReqDto.setGroupId(baseUserInfo.getGroupId());
                userMenuReqDto.setManagerLevel(baseUserInfo.getManagerLevel());
                userMenuReqDto.setSource(req.getSourceTypeEnum().name());
                userMenuParamList.add(userMenuReqDto);
            }
        });
        List<GetUserMenuPermissionsDto> userMenuPermissionsDtoList = permissionDomainService.getUserMenuPermissions(userMenuParamList);
        return userMenuPermissionsDtoList;
    }
}

