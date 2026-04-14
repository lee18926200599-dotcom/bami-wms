package com.usercenter.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.common.base.enums.StateEnum;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.BatchUpdateReq;
import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.common.enums.*;
import com.usercenter.server.common.factory.BeanEnumFactory;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.constant.command.enums.UpdateUserCommandBeanEnum;
import com.usercenter.server.constant.command.enums.UpdateUserStrategyBeanEnum;
import com.usercenter.server.domain.condition.UserDetailCondition;
import com.usercenter.server.domain.dto.UpdateUserDTO;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.domain.dto.UserNamesDto;
import com.usercenter.server.domain.service.CodeDomainService;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.entity.*;
import com.usercenter.server.mapper.BaseUserBusinessSystemMapMapper;
import com.usercenter.server.mapper.BaseUserDetailMapper;
import com.usercenter.server.mapper.BaseUserMapper;
import com.usercenter.server.mapper.BaseUserPasswordHistoryMapper;
import com.usercenter.server.service.IBaseUserBusinessSystemMapService;
import com.usercenter.server.service.IBaseUserCommonService;
import com.usercenter.server.service.IBaseUserStaffMapService;
import com.usercenter.server.service.command.UpdateUserCommand;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.server.utils.PasswordEncoder;
import com.usercenter.server.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 用户服务，通用实现
 */
@Service("baseUserCommonServiceImpl")
public class BaseUserCommonServiceImpl implements IBaseUserCommonService {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserCommonServiceImpl.class);

    public BaseUserCommonServiceImpl() {
        CleanThread cleanThread = new CleanThread();
        cleanThread.start();
    }

    /**
     * 发送邮件统计信息
     */
    private static Map<String, Integer> WARN_MAIL_MESSAGE = Maps.newHashMap();

    @Value("${spring.profiles.active:dev}")
    protected String profile;

    /**
     * 域名和业务类型映射
     * 域名来自于 业务系统
     */
    private static final Map<Integer, List<Integer>> businessTypesCache = new HashMap<>(8);


    /**
     * 业务系统匹配注册来源
     */
    private static final Map<String, List<String>> businessSystemSourceCache = new HashMap<>(3);


    static {

        /**
         * 业务系统匹配  域名，业务类型
         */
        //匹配scm业务
        businessTypesCache.put(DomainEnum.SCM.getValue(), Arrays.asList(1,4, 5, 30, 31, 32));
        //匹配TMS APP/PC端业务
        businessTypesCache.put(DomainEnum.TMS.getValue(), Arrays.asList(3));
        //匹配CRM APP/业务
        businessTypesCache.put(DomainEnum.CRM.getValue(), Arrays.asList(12, 18));
        //匹配rf(wms)/业务
        businessTypesCache.put(DomainEnum.WMS.getValue(), Arrays.asList(2));
        /**
         * 业务系统匹配注册来源
         */
        //注册来源
        businessSystemSourceCache.put(BusinessSystemEnum.BOSS.name(), Arrays.asList("1000"));
// // TODO: 2024/8/6 暂时不按照业务系统过滤 
//        businessSystemSourceCache.put(BusinessSystemEnum.SCM.name(), Arrays.asList("1000", "3000"));
//
//        businessSystemSourceCache.put(BusinessSystemEnum.TMS.name(), Arrays.asList("1000", "4000"));
//
//        businessSystemSourceCache.put(BusinessSystemEnum.OMS.name(), Arrays.asList("1000", "2000"));

    }

    /**
     * 用户主表
     */
    @Autowired
    protected BaseUserMapper baseUserMapper;

    /**
     * 用户检查服务
     */
    @Autowired
    protected BaseUserCheckServiceImpl userCheckService;

    /**
     * 用户子表
     */
    @Autowired
    protected BaseUserDetailMapper baseUserDetailMapper;


    /**
     * 历史密码
     */
    @Autowired
    protected BaseUserPasswordHistoryMapper userPasswordHistoryMapper;

    /**
     * 非对称加密服务
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * user 主表分布式id
     */
    @Value("${user.id.generated.name:ucenter}")
    private String idGeneratedName;

    @Autowired
    private BeanEnumFactory beanEnumFactory;

    @Autowired
    private IBaseUserBusinessSystemMapService businessSystemMapService;

    @Autowired
    private IBaseUserStaffMapService userStaffMapService;
    /**
     * 用户系统映射
     */
    @Autowired
    protected BaseUserBusinessSystemMapMapper baseUserBusinessSystemMapMapper;

    @Resource
    protected CodeDomainService codeDomainService;
    @Resource
    protected OrgDomainService orgDomainService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(UserSaveDto saveUserDTO) {
        //查询是否存在主表信息
        BaseUser query = new BaseUser();
        query.setPhone(saveUserDTO.getPhone());
        query.setDeletedFlag(FlagEnum.FALSE.getCode());
        logger.info("用户注册保存数据：{}", saveUserDTO.toString());
        BaseUser userInfo = baseUserMapper.loadByParam(query);
        logger.info("查询用户主表信息是否存在");
        //不存在记录，新增主表，新增字表
        if (userInfo == null) {
            logger.info("查询用户主表信息不存在");
            //----新增主表
            savePrimary(saveUserDTO);
            //----新增细表
            saveDetail(saveUserDTO);
            //存在记录
        } else {
            logger.info("查询用户主表信息存在,用户信息：{}", userInfo.toString());
            //更新主表
            updatePrimary(saveUserDTO, userInfo);
            saveUserDTO.setUserAuthId(userInfo.getId());
            //----保存细表
            saveDetail(saveUserDTO);
        }
        return 1;
    }


    /**
     * 更新主表
     *
     * @param saveUserDTO 新增用户的信息
     * @param userInfo    数据库中的信息
     */
    public void updatePrimary(UserSaveDto saveUserDTO, BaseUser userInfo) {
        //----如果新增用户的管理员等级比当前高，则更新它
        if (saveUserDTO.getManagerLevel() < userInfo.getManagerLevel()) {
            userInfo.setManagerLevel(saveUserDTO.getManagerLevel());
            userInfo.setModifiedDate(new Date());
            baseUserMapper.update(userInfo);
        }
    }

    /**
     * 保存主表，回填主表id
     *
     * @param saveUserDTO 保存信息
     */
    private void savePrimary(UserSaveDto saveUserDTO) {
        BaseUser baseUser = buildUser(saveUserDTO);
        logger.info("保存主表:{}", baseUser.toString());
        baseUserMapper.insert(baseUser);
        saveUserDTO.setUserAuthId(baseUser.getId());
    }

    /**
     * 保存细表
     *
     * @param saveUserDTO 保存信息
     */
    private void saveDetail(UserSaveDto saveUserDTO) {
        //新增子表
        BaseUserDetail detail = buildDetail(saveUserDTO);
        //绝不保存密码
        detail.setPassword(null);
        detail.setContactEmail(saveUserDTO.getEmail());
        // // TODO: 2024/4/30 暂时默认不启用
        detail.setState(StateEnum.CREATE.getCode());
        logger.info("保存细表:{}", detail.toString());
        baseUserDetailMapper.insert(detail);
        saveUserDTO.setId(detail.getId());
        //如果存在人员id,则保存对应关系到用户人员映射表
        if (!ObjectUtils.isEmpty(saveUserDTO.getArchivesId())) {
            userStaffMapService.save(new BaseUserStaffMap(detail.getId(), saveUserDTO.getArchivesId(), saveUserDTO.getCreatedBy(), null));
        }
    }

    /**
     * 构建主表信息
     *
     * @param saveDTO 保存请求
     * @return
     */
    private BaseUser buildUser(UserSaveDto saveDTO) {
        logger.info("构建主表信息开始");
        //设置主表缺省信息
        if (UserUtil.getUserId() != null) {
            logger.info("CurrentUserId:{}", UserUtil.getUserId());
            saveDTO.setCreatedBy(UserUtil.getUserId());
        }
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(saveDTO, baseUser, "detailList");
        baseUser.setCreatedDate(new Date());
        baseUser.setLockFlag(FlagEnum.FALSE.getCode());
        String password = generatePassword(baseUser);
        baseUser.setPassword(passwordEncoder.encode(password));
        baseUser.setPasswordFlag(FlagEnum.FALSE.getCode());
        baseUser.setDeletedFlag(FlagEnum.FALSE.getCode());
        baseUser.setFirstTimeLoginFlag(FlagEnum.TRUE.getCode());
        String userNumber = generateUserNumber(baseUser);
        baseUser.setUserCode(userNumber);
        saveDTO.setPassword(password);
        return baseUser;
    }

    /**
     * 构建细表信息
     *
     * @param saveDTO
     * @return
     */
    private BaseUserDetail buildDetail(UserSaveDto saveDTO) {
        //设置用户体系
        initUserSystem(saveDTO);
        //设置细表信息
        BaseUserDetail detail = new BaseUserDetail();
        BeanUtils.copyProperties(saveDTO, detail);
        detail.setCreatedDate(new Date());
        if (UserUtil.getUserId() != null) {
            detail.setCreatedBy(UserUtil.getUserId());
        }
        detail.setUserId(saveDTO.getUserAuthId());
        detail.setPasswordFlag(FlagEnum.FALSE.getCode());
        detail.setLockFlag(FlagEnum.FALSE.getCode());
        detail.setDeletedFlag(FlagEnum.FALSE.getCode());
        detail.setFirstTimeLoginFlag(FlagEnum.TRUE.getCode());
        detail.setContactEmail(saveDTO.getContactEmail());
        detail.setUserCode(generateDetailUserNumber(saveDTO.getManagerLevel()));
        return detail;
    }

    /**
     * 初始化用户体系
     */
    private void initUserSystem(UserSaveDto saveDTO) {
        BaseUserBusinessSystemMap query = new BaseUserBusinessSystemMap();
        query.setMapType(BusinessSystemMapTypeEnum.USER_SYSTEM.getCode());
        query.setBusinessSystemId(saveDTO.getBusinessSystem());
        query.setState(UserStateEnum.ENABLE.getCode().toString());
        List<BaseUserBusinessSystemMap> baseUserBusinessSystemMaps = baseUserBusinessSystemMapMapper.selectByMap(BeanUtil.beanToMap(query, true, true));
        if (CollectionUtils.isNotEmpty(baseUserBusinessSystemMaps)) {
            String userSystem = baseUserBusinessSystemMaps.get(0).getMapName();

        }
    }

    /**
     * 生成密码
     *
     * @param user
     * @return
     */
    private String generatePassword(BaseUser user) {
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            if (StringUtils.isNotEmpty(user.getPhone())) {
                // 正常手机号不用处理，上游放开了手机号规则验证，此处处理
                if (user.getPhone().length()>=6){
                    return BaseUserConstants.USER_PASSWORD_PREFIX + user.getPhone().substring(user.getPhone().length()-6);
                }else{
                    return BaseUserConstants.USER_PASSWORD_PREFIX + UserUtil.getCode();
                }

            } else {
                return BaseUserConstants.USER_PASSWORD_PREFIX + UserUtil.getCode();
            }
        }
        return password;
    }

    /**
     * 生成用户编码
     *
     * @param user
     * @return
     */
    private String generateUserNumber(BaseUser user) {
        return codeDomainService.getUserCode();
    }

    /**
     * 生成细表用户编码
     *
     * @param managerLevel
     * @return
     */
    private String generateDetailUserNumber(Integer managerLevel) {
        return codeDomainService.getUserDetailCode();
    }

    @Override
    public FplUser getUserById(Long id) {
        UserDetailCondition condition = new UserDetailCondition();
        condition.setId(id);
        BaseUserInfo baseUserInfo = baseUserDetailMapper.selectDetail(condition);
        if (baseUserInfo == null) {
            return null;
        }
        FplUser fplUser = new FplUser();
        BeanUtils.copyProperties(baseUserInfo, fplUser);
        fplUser.setUserAuthId(baseUserInfo.getUserId());
        return fplUser;
    }

    @Override
    public BaseUser getOnePrimary(BaseUser baseUser) {
        return baseUserMapper.loadByParam(baseUser);
    }

    @Override
    public BaseUser getPrimaryById(Long id) {
        return baseUserMapper.load(id);
    }

    @Override
    public List<BaseUser> getPrimaryList(BaseUser baseUser) {
        return baseUserMapper.find(baseUser);
    }


    @Override
    public BaseUserDetail getOneDetail(BaseUserDetail detail) {
        return baseUserDetailMapper.loadByParam(detail);
    }

    @Override
    public BaseUserDetail getDetailById(Long id) {
        return baseUserDetailMapper.load(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(BaseUser baseUser) {
        AssertUtils.isNotNull(baseUser.getId(), I18nUtils.getMessage("user.check.id.notnull"));
        baseUserMapper.update(baseUser);
        if (!CollectionUtils.isEmpty(baseUser.getDetailList())) {
            baseUserDetailMapper.updateList(baseUser.getDetailList());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDetail(BaseUserDetail baseUserDetail) {
        AssertUtils.isNotNull(baseUserDetail.getId(), I18nUtils.getMessage("user.check.id.notnull"));
        baseUserDetailMapper.update(baseUserDetail);
    }


    @Override
    public UpdateUserCommandResp batchUpdate(BatchUpdateReq req) {
        //获取命令
        UpdateUserCommand command = beanEnumFactory.getBean(UpdateUserCommandBeanEnum.getBeanEnum(req.getOperate()), UpdateUserCommand.class);
        //获取更新子表策略
        UpdateUserStrategyService strategy = beanEnumFactory.getBean(UpdateUserStrategyBeanEnum.getBeanEnum(req), UpdateUserStrategyService.class);
        //获取
        UpdateUserStrategyDTO updateUserStrategyDTO = new UpdateUserStrategyDTO();
        updateUserStrategyDTO.setDetailIds(req.getIds());
        updateUserStrategyDTO.setGroupId(req.getGroupId());
        updateUserStrategyDTO.setUserId(req.getUserId());
        UpdateUserCommandResp execute = command.execute(strategy, updateUserStrategyDTO);
        return execute;
    }

    /**
     * 前置校验
     *
     * @param user 用户信息
     */
    @Override
    public void checkUser(UserSaveDto user) {
        //这里特殊处理是因为boss用户管理新增用户可以不填用户名和密码
        if (!user.getBusinessSystem().equals(BusinessSystemEnum.ORGANIZATION.getCode())) {
            AssertUtils.isTrue(StringUtils.isNotEmpty(user.getEmail()) || StringUtils.isNotEmpty(user.getUserName()), ReturnCodesEnum.USERNAME_EMAIL_EMPTY.getMessage());
        }
        AssertUtils.notNull(user.getIdentityType(), ReturnCodesEnum.PARAM_IDENTITY_TYPE_NULL.getMessage());
        //用户名校验(主表)
        userCheckService.checkPrimaryUserInfo(user.getUserName(), user.getEmail(), user.getPhone());
        //管理员类型
        AssertUtils.notNull(user.getManagerLevel(), ReturnCodesEnum.PARAM_MANAGERLEVEL_ERROR.getMessage());
        //判断注册来源
        userCheckService.checkSource(user.getSource());
        //唯一性校验
        userCheckService.uniqueCheck(user, false);
    }


    @Override
    public void updateCheck(UpdateUserDTO updateUserDTO) {
        //这里特殊处理是因为boss用户管理新增用户可以不填用户名和密码
        if (!updateUserDTO.getBusinessSystem().equals(BusinessSystemEnum.ORGANIZATION.getCode())) {
            AssertUtils.isTrue(StringUtils.isNotEmpty(updateUserDTO.getEmail()) || StringUtils.isNotEmpty(updateUserDTO.getUserName()), ReturnCodesEnum.USERNAME_EMAIL_EMPTY.getMessage());
        }
        //身份类型不为空
        AssertUtils.notNull(updateUserDTO.getIdentityType(), ReturnCodesEnum.PARAM_IDENTITY_TYPE_NULL.getMessage());
        //校验主表信息
        userCheckService.checkPrimaryUserInfo(updateUserDTO.getUserName(), updateUserDTO.getEmail(), updateUserDTO.getPhone(), updateUserDTO.getUserAuthId());
        //管理员类型
        AssertUtils.notNull(updateUserDTO.getManagerLevel(), ReturnCodesEnum.PARAM_MANAGERLEVEL_ERROR.getMessage());
        //唯一性校验
        UserSaveDto saveDTO = new UserSaveDto();
        BeanUtil.copyProperties(updateUserDTO, saveDTO);
        userCheckService.uniqueCheck(saveDTO, true);
    }


    @Override
    public void insertUserPasswordHistory(BaseUserPasswordHistory userPasswordHistory) {
        userPasswordHistoryMapper.insert(userPasswordHistory);
    }

    @Override
    public BaseUser getUserByLoginName(String loginName) {
        return baseUserMapper.selectByLoginName(loginName);
    }

    @Override
    public FplUser getUserByLoginInfo(String loginName, BusinessSystemEnum businessSystem, SourceTypeEnum sourceTypeEnum, Long groupId) {
        logger.info("getUserByLoginInfo:loginName{}  进入入户匹配逻辑", loginName);
        //登陆用户选择逻辑
        FplUser fplUser = new FplUser();
        //获取该用户名对应的所有子表记录
        List<BaseUserInfo> baseUserInfoList = baseUserDetailMapper.selectUserListByLoginName(loginName);
        logger.info("getUserByLoginInfo:loginName{}  查询到的所有用户:{}", loginName, (ObjectUtils.isEmpty(baseUserInfoList) ? "未匹配到" : JSON.toJSONString(baseUserInfoList)));
        if (CollectionUtils.isEmpty(baseUserInfoList)) {
            logger.error("登录用户不存在,loginName:{}", loginName);
            return null;
        }

        //只匹配启用的记录
        List<BaseUserInfo> enabledUserInfoList = baseUserInfoList.stream().filter(baseUserInfo -> UserStateEnum.ENABLE.getCode().equals(baseUserInfo.getState())).collect(Collectors.toList());
        logger.info("getUserByLoginInfo：loginName{}  过滤出启用的用户:{}", loginName, (ObjectUtils.isEmpty(enabledUserInfoList) ? "空" : JSON.toJSONString(enabledUserInfoList)));
        //如果登陆源是pc则不过滤掉集团为0的用户id,然后根据id升序排序
        if (SourceTypeEnum.PC.equals(sourceTypeEnum) && !ObjectUtils.isEmpty(enabledUserInfoList)) {
            enabledUserInfoList = enabledUserInfoList.stream().filter(baseUserInfo -> !ObjectUtils.isEmpty(baseUserInfo.getGroupId()) && !baseUserInfo.getGroupId().equals(0)).collect(Collectors.toList());
            Collections.sort(enabledUserInfoList, Comparator.comparing(BaseUserInfo::getGroupId));
            logger.info("getUserByLoginInfo：loginName{}  登陆端是PC，并且有多个子账号，过滤掉集团id为0的账号，并且按集团排序，排序后的记录:{}", loginName, ObjectUtils.isEmpty(enabledUserInfoList) ? "空" : JSON.toJSONString(enabledUserInfoList));
        }
        //如果没有启用的，返回第一条
        if (ObjectUtils.isEmpty(enabledUserInfoList)) {
            logger.error("getUserByLoginInfo：loginName{}  无启用状态用户！", loginName);
            AssertUtils.isNotEmpty(enabledUserInfoList, ReturnCodesEnum.NOT_ENABLE.getMessage());
        }
        logger.info("----匹配到用户列表：{}", JSON.toJSONString(enabledUserInfoList));
        //根据登陆系统获取到对应的身份
        Set<Integer> identitySet = businessSystemMapService.getMapIdsBySystem(businessSystem, BusinessSystemMapTypeEnum.IDENTITY_TYPE);
        logger.info("getUserByLoginInfo：loginName{}  根据登陆系统获取到匹配的身份类型：{}", loginName, (ObjectUtils.isEmpty(identitySet) ? "无" : JSON.toJSON(identitySet)));
        //身份匹配的记录
        List<BaseUserInfo> matchedIdentityList = enabledUserInfoList.stream().filter(baseUserInfo -> identitySet.contains(baseUserInfo.getIdentityType())).collect(Collectors.toList());
        logger.info("getUserByLoginInfo：loginName{}  根据身份类型进行过滤后：{}", loginName, (ObjectUtils.isEmpty(matchedIdentityList) ? "无" : JSON.toJSON(matchedIdentityList)));
        //PC端切换集团
        if (groupId != null) {
            //集团匹配的记录
            List<BaseUserInfo> matchedGroupList = enabledUserInfoList.stream().filter(baseUserInfo -> groupId.equals(baseUserInfo.getGroupId())).collect(Collectors.toList());
            logger.info("getUserByLoginInfo：loginName{}  集团id（当前为切换集团）：{}  根据集团过滤后：{}", loginName, groupId, (ObjectUtils.isEmpty(matchedGroupList) ? "无" : JSON.toJSON(matchedGroupList)));
            //交集前备份
            List<BaseUserInfo> matchedIdentityCopyList = Lists.newArrayList(matchedIdentityList);
            matchedIdentityList.retainAll(matchedGroupList);
            logger.info("getUserByLoginInfo：loginName{}  集团id（当前为切换集团）：{}  该集团记录和符合身份条件的记录的交集：{}", loginName, groupId, (ObjectUtils.isEmpty(matchedIdentityList) ? "无" : JSON.toJSON(matchedIdentityList)));
            if (CollectionUtils.isEmpty(matchedIdentityList)) {
                matchedIdentityList = matchedIdentityCopyList;
                logger.info("getUserByLoginInfo：loginName{}  集团id（当前为切换集团）：{}  该集团记录和符合身份条件的记录的交集为空，沿用身份匹配的记录：{}", loginName, groupId, (ObjectUtils.isEmpty(matchedIdentityList) ? "无" : JSON.toJSON(matchedIdentityList)));
            }
        }
        if (ObjectUtils.isEmpty(matchedIdentityList)) {
            logger.info("getUserByLoginInfo：loginName{}  根据身份过滤后无匹配数据", loginName);
            return null;
        }


        //匹配业务类型
        List<BaseUserInfo> userByBusinessType = getUserByBusinessType(businessSystem, sourceTypeEnum, matchedIdentityList);
        if (CollectionUtils.isEmpty(userByBusinessType)) {
            logger.info("根据业务类型无法匹配到用户,userName:{}", JSON.toJSONString(matchedIdentityList));
            userByBusinessType = getUserBySource(loginName, matchedIdentityList, businessSystem, sourceTypeEnum);
        } else {
            userByBusinessType = getUserBySource(loginName, userByBusinessType, businessSystem, sourceTypeEnum);
        }
        AssertUtils.isNotEmpty(userByBusinessType, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        //然后是默认取第一条
        UserUtil.convert(userByBusinessType.get(0), fplUser);
        logger.info("---------------------------进入入户匹配逻辑（getUserByLoginInfo）：------------------------------------------");
        return fplUser;
    }


    /**
     * 根据业务类型-》域名-》业务类型来匹配
     *
     * @param businessSystemEnum
     * @param sourceTypeEnum
     * @param baseUserInfos
     * @return
     */
    private List<BaseUserInfo> getUserByBusinessType(BusinessSystemEnum businessSystemEnum, SourceTypeEnum sourceTypeEnum, List<BaseUserInfo> baseUserInfos) {
        if (sourceTypeEnum == null) {
            return baseUserInfos;
        }
        //获取到域名ID
        Integer domainId = DomainEnum.parser(businessSystemEnum.toString(), sourceTypeEnum.toString()).getValue();
        List<Integer> businessTypes = businessTypesCache.get(domainId);
        logger.info("getUserByLoginInfo：根据域名id{}，匹配到的业务类型{}", domainId, ObjectUtils.isEmpty(businessTypes) ? "空" : JSON.toJSONString(businessTypes));
        if (businessTypes == null) {
            return baseUserInfos;
        }
        List<Long> groupIdList = baseUserInfos.stream().map(u -> u.getGroupId()).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(groupIdList)) {
            return baseUserInfos;
        }
        List<OrgInfoDto> orgInfoDtoList = orgDomainService.queryGroupByIdList(groupIdList);
        if (CollectionUtils.isEmpty(orgInfoDtoList)) {
            return baseUserInfos;
        }
        groupIdList.clear();
        orgInfoDtoList.forEach(orgInfo -> {
            String businessType = orgInfo.getBusinessType();
            if (StringUtils.isNotBlank(businessType)) {
                String[] split = StringUtils.split(businessType, ",");
                for (String business : split) {
                    if (StringUtils.isNumeric(business)) {
                        if (businessTypes.contains(new Integer(business))) {
                            groupIdList.add(orgInfo.getId());
                        }
                    }
                }
            }
        });
        logger.info("根据业务类型，配置到的集团ID:{}", groupIdList);
        return baseUserInfos.stream().filter(baseUserInfo -> groupIdList.contains(baseUserInfo.getGroupId())).collect(Collectors.toList());
    }


    /**
     * 根据注册来源来匹配用户
     *
     * @param baseUserInfos
     * @param businessSystem
     * @return
     */
    private List<BaseUserInfo> getUserBySource(String loginName, List<BaseUserInfo> baseUserInfos, BusinessSystemEnum businessSystem, SourceTypeEnum sourceType) {
        List<String> sources = businessSystemSourceCache.get(businessSystem.name());
        if (sources != null) {
            List<BaseUserInfo> collect = baseUserInfos.stream().filter(baseUserInfo -> baseUserInfo.getSource() != null)
                    .filter(baseUserInfo -> sources.contains(baseUserInfo.getSource().toString())).collect(Collectors.toList());
            if (ObjectUtils.isEmpty(collect)) {
                logger.error("根据注册来源不能匹配到数据集合:{}", baseUserInfos);
                return baseUserInfos;
            } else {
                logger.info("根据注册来源匹配到的用户:{}", collect);
                return collect;
            }
        }
        logger.info("登录，未配置业务系统的注册来源，不执行注册来源匹配规则");
        return baseUserInfos;
    }

    @Override
    public FplUser getUserByLoginInfo(Long userId, BusinessSystemEnum businessSystem, Long groupId) {
        BaseUser baseUser = getPrimaryById(userId);
        return getUserByLoginInfo(baseUser.getUserName(), businessSystem, null, groupId);
    }


    /**
     * 根据用户登录名获取用户列表
     *
     * @param userName 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @return 用户
     */
    private List<BaseUserInfo> getUserListByLoginName(String userName, String email, String phone, UserStateEnum userStateEnum, Integer lock) {
        UserDetailCondition condition = new UserDetailCondition();
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        condition.setUserName(userName);
        condition.setEmail(email);
        condition.setPhone(phone);
        if (userStateEnum != null) {
            condition.setState(userStateEnum.getCode());
        }
        condition.setLockFlag(lock);
        return baseUserDetailMapper.selectDetailList(condition);
    }

    @Override
    public List<BaseUserInfo> getUserListByUserName(String userName, UserStateEnum userStateEnum, Integer lock) {
        return getUserListByLoginName(userName, null, null, userStateEnum, lock);
    }

    @Override
    public List<BaseUserInfo> getUserListByEmail(String email, UserStateEnum userStateEnum, Integer lock) {
        return getUserListByLoginName(null, email, null, userStateEnum, lock);
    }

    @Override
    public List<BaseUserInfo> getUserListByPhone(String phone, UserStateEnum userStateEnum, Integer lock) {
        return getUserListByLoginName(null, null, phone, userStateEnum, lock);
    }

    /**
     * 根据用户登录名获取用户
     *
     * @param userName 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @return 用户
     */
    private BaseUserInfo getUserByLoginName(String userName, String email, String phone, UserStateEnum userStateEnum, Integer lock) {
        List<BaseUserInfo> existUsers = getUserListByLoginName(userName, email, phone, userStateEnum, lock);
        if (ObjectUtils.isEmpty(existUsers)) {
            return null;
        }
        return existUsers.get(0);
    }


    @Override
    public BaseUserInfo getUserByUserName(String userName, UserStateEnum userStateEnum, Integer lock) {
        List<BaseUserInfo> existUsers = getUserListByLoginName(userName, null, null, userStateEnum, lock);
        if (ObjectUtils.isEmpty(existUsers)) {
            return null;
        }
        return existUsers.get(0);
    }


    @Override
    public BaseUserInfo getUserByEmail(String email, UserStateEnum userStateEnum, Integer lock) {
        List<BaseUserInfo> existUsers = getUserListByLoginName(null, email, null, userStateEnum, lock);
        if (ObjectUtils.isEmpty(existUsers)) {
            return null;
        }
        return existUsers.get(0);
    }


    @Override
    public BaseUserInfo getUserByPhone(String phone, UserStateEnum userStateEnum, Integer lock) {
        List<BaseUserInfo> existUsers = getUserListByLoginName(null, null, phone, userStateEnum, lock);
        if (ObjectUtils.isEmpty(existUsers)) {
            return null;
        }
        return existUsers.get(0);
    }


    @Override
    public boolean isHistoryPassword(Long userId, String password) {
        BaseUserPasswordHistory baseUserPasswordHistory = new BaseUserPasswordHistory();
        baseUserPasswordHistory.setUserId(userId);
        baseUserPasswordHistory.setPassword(password);
        List<BaseUserPasswordHistory> passwordHistoryList = userPasswordHistoryMapper.selectByMap(BeanUtil.beanToMap(baseUserPasswordHistory));
        if (CollectionUtils.isEmpty(passwordHistoryList)) {
            return false;
        }
        return true;
    }

    @Override
    public List<BaseUserInfo> getListByUserNames(List<String> userNames) {
        UserNamesDto userNamesDto = new UserNamesDto();
        userNamesDto.setUserNames(userNames);
        return getListByUserNames(userNamesDto);
    }

    @Override
    public List<BaseUserInfo> getListByUserNames(UserNamesDto userNamesDto) {
        return baseUserDetailMapper.selectListByUserNames(userNamesDto);
    }

    /**
     * 清除缓存的线程
     */
    public class CleanThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    Calendar instance = Calendar.getInstance();
                    int hour = instance.get(Calendar.HOUR_OF_DAY);
                    if (hour == 1) {
                        WARN_MAIL_MESSAGE.clear();
                    }
                    TimeUnit.MINUTES.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

