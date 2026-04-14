package com.usercenter.server.service.impl;

import com.basedata.common.dto.DictionaryItemDto;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.LoginRequest;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.constant.DictionaryConstants;
import com.usercenter.server.constant.command.enums.BaseUserIdentityTypeEnum;
import com.usercenter.server.constant.command.enums.BaseUserManagerLevelEnum;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.common.exception.WarnException;
import com.usercenter.server.domain.condition.UserDetailCondition;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.service.IBaseUserCheckService;
import com.usercenter.server.utils.RegexUtils;
import com.usercenter.server.utils.UserRedisUtil;
import com.usercenter.server.utils.UserRequestContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 用户校验服务
 */
@Service
public class BaseUserCheckServiceImpl extends BaseUserCommonServiceImpl implements IBaseUserCheckService {

    @Resource
    private UserRedisUtil userRedisUtil;
    @Resource
    private DictionaryDomainService dictionaryDomainService;

    @Override
    public void checkPrimaryUserInfo(String userName, String email, String phone) {
        //校验同一用户，不能有不同的邮箱/用户名
        //----手机校验（从主表中查询）
        AssertUtils.isNotEmpty(phone, ReturnCodesEnum.PARAM_PHONE_NULL.getMessage());
        AssertUtils.isTrue(RegexUtils.isMobileSimple(phone), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
        //校验不同用户邮箱/用户名不能重复
        checkUserNameAndEmail(userName, email, null, phone);
    }


    @Override
    public void checkPrimaryUserInfo(String userName, String email, String phone, Long userAuthId) {
        //手机号校验
        if (StringUtil.isNotEmpty(phone)) {
            AssertUtils.isTrue(RegexUtils.isMobileSimple(phone), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
            List<BaseUserInfo> userListByPhone = getUserListByPhone(phone, null, null);
            for (BaseUserInfo user : userListByPhone) {
                AssertUtils.isTrue(user.getUserId().equals(userAuthId), ReturnCodesEnum.PHONE_EXISTS.getMessage());
            }
        }
        checkUserNameAndEmail(userName, email, userAuthId, null);
    }

    /**
     * 用户名/邮箱校验
     *
     * @param userName   用户名
     * @param email      邮箱
     * @param userAuthId 主表id
     * @param phone      手机号
     */
    private void checkUserNameAndEmail(String userName, String email, Long userAuthId, String phone) {
        if (StringUtil.isNotEmpty(userName)) {
            AssertUtils.isTrue(RegexUtils.isUsername(userName), ReturnCodesEnum.REGEX_FAILD_USERNAME.getMessage());
            //用户名校验
            List<BaseUserInfo> existUsers = getUserListByUserName(userName, null, null);
            if (!CollectionUtils.isEmpty(existUsers)) {
                for (BaseUserInfo user : existUsers) {
                    if (userAuthId != null) {
                        //不是同一主表id：用户名已存在
                        AssertUtils.isTrue(user.getUserId().equals(userAuthId), ReturnCodesEnum.USERNAME_EXISTS.getMessage());
                    } else if (StringUtil.isNotEmpty(phone)) {
                        //不是同一手机号：用户名已存在
                        AssertUtils.isTrue(user.getPhone().equals(phone), ReturnCodesEnum.USERNAME_EXISTS.getMessage());
                    }
                }
            }
        }
        if (StringUtil.isNotEmpty(email)) {
            AssertUtils.isTrue(RegexUtils.isEmail(email), ReturnCodesEnum.REGEX_FAILD_EMAIL.getMessage());
            //邮箱校验
            List<BaseUserInfo> existUsers = getUserListByEmail(email, null, null);
            if (!CollectionUtils.isEmpty(existUsers)) {
                for (BaseUserInfo user : existUsers) {
                    if (userAuthId != null) {
                        AssertUtils.isTrue(user.getUserId().equals(userAuthId), ReturnCodesEnum.EMAIL_EXISTS.getMessage());
                    } else if (StringUtil.isNotEmpty(phone)) {
                        AssertUtils.isTrue(user.getPhone().equals(phone), ReturnCodesEnum.EMAIL_EXISTS.getMessage());
                    }
                }
            }
        }
    }


    @Override
    public void checkSource(String source) {
        //判断注册来源
        AssertUtils.isNotEmpty(source, ReturnCodesEnum.PARAM_SOURCE_ERROR.getMessage());
        List<DictionaryItemDto> dictionaryItemDtoList = dictionaryDomainService.getItemList(DictionaryConstants.FPL_USER_REGISTER_SOURCE);
        AssertUtils.isNotTrue(CollectionUtils.isEmpty(dictionaryItemDtoList), I18nUtils.getMessage("user.check.register.source.select.fail"));
        DictionaryItemDto dictionaryItemDto = dictionaryItemDtoList.stream().filter(item -> item.getItemCode().equals(source)).findFirst().orElse(null);
        AssertUtils.isNotNull(dictionaryItemDto, I18nUtils.getMessage("user.check.register.source.error"));
    }

    @Override
    public void uniqueCheck(UserSaveDto saveDTO, Boolean isUpdate) {
        //和数据库中是否重复
        UserDetailCondition condition = new UserDetailCondition();
        condition.setPhone(saveDTO.getPhone());
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        List<BaseUserInfo> baseUserInfoList = baseUserDetailMapper.selectDetailList(condition);
        //如果是更新，排除当前的记录
        if (isUpdate) {
            baseUserInfoList = baseUserInfoList.stream().filter(baseUserInfo -> !baseUserInfo.getId().equals(saveDTO.getId())).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(baseUserInfoList)) {
            if (BaseUserIdentityTypeEnum.MEMEBER.getId().equals(saveDTO.getIdentityType())) {
                checkDB(saveDTO, baseUserInfoList.stream().filter(detail -> BaseUserIdentityTypeEnum.MEMEBER.getId().equals(detail.getIdentityType())).collect(Collectors.toList()));
            } else {
                checkDB(saveDTO, baseUserInfoList.stream().filter(detail -> !BaseUserIdentityTypeEnum.MEMEBER.getId().equals(detail.getIdentityType())).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public void loginCheck(LoginRequest loginRequest, FplUser fplUser) {
        Boolean useCode = loginRequest.getUseCode() == null ? false : loginRequest.getUseCode();
        String captchaToken = loginRequest.getCaptchaToken();
        String code = loginRequest.getCode();
        String password = loginRequest.getPassword();
        BusinessSystemEnum businessSystem = loginRequest.getBusinessSystem();
        AssertUtils.notNull(fplUser, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        //用户启用检查
        AssertUtils.isTrue(fplUser.getState().equals(UserStateEnum.ENABLE.getCode()), ReturnCodesEnum.NOT_ENABLE.getMessage());
        //用户锁定检查
        AssertUtils.isNotTrue(Objects.equals(fplUser.getLockFlag(), FlagEnum.TRUE.getCode()), ReturnCodesEnum.ACCOUNT_LOCK.getMessage());
        //失败次数校验
        String loginCount = (String) userRedisUtil.get(fplUser.getUserAuthId().toString() + BaseUserConstants.LOGIN_FAIL_COUNT_SUFFIX);
        //----附加属性（失败次数）放到请求中
        Map<String, Object> attachment = Maps.newHashMap();
        attachment.put(BaseUserConstants.LOGIN_FAIL_COUNT_ATTACHMENT, StringUtils.isEmpty(loginCount) == true ? 0 : Integer.valueOf(loginCount));
        UserRequestContextHolder.addRequestAttr(BaseUserConstants.LOGIN_FAIL_COUNT_ATTACHMENT, attachment);
        //----超过最大失败次数，锁定用户（主表）
        if (StringUtils.isNotEmpty(loginCount) && Integer.valueOf(loginCount) >= BaseUserConstants.LOGIN_FAIL_COUNT) {
            BaseUser userAuthInfo = new BaseUser();
            userAuthInfo.setLockFlag(FlagEnum.TRUE.getCode());
            userAuthInfo.setId(fplUser.getUserAuthId());
            updateUser(userAuthInfo);
            userRedisUtil.del(fplUser.getUserAuthId().toString() + BaseUserConstants.LOGIN_FAIL_COUNT_SUFFIX);
            throw new WarnException(ReturnCodesEnum.MANY_LOGINS.getMessage());
        }
        //验证码校验
        if (useCode) {
            String reqCode = userRedisUtil.getStr(captchaToken + BaseUserConstants.LOGIN_CAPTEXT_SUFFIX);
            AssertUtils.isNotEmpty(reqCode, ReturnCodesEnum.CODE_EMPTY.getMessage());
            AssertUtils.isTrue(reqCode.equalsIgnoreCase(code), ReturnCodesEnum.CODE_WRONG.getMessage());
        }
        //登陆失败次数叠加
        if (!passwordEncoder.isPasswordValid(fplUser.getPassword(), password)) {
            //返回的失败次数+1
            userRedisUtil.set(fplUser.getUserAuthId().toString() + BaseUserConstants.LOGIN_FAIL_COUNT_SUFFIX, (StringUtils.isEmpty(loginCount) == true ? 1 : Integer.valueOf(loginCount) + 1) + "", 3600);
            Map<String, Object> incrementAttachment = Maps.newHashMap();
            incrementAttachment.put(BaseUserConstants.LOGIN_FAIL_COUNT_ATTACHMENT, StringUtils.isEmpty(loginCount) == true ? 1 : Integer.valueOf(loginCount) + 1);
            throw new WarnException(ReturnCodesEnum.ACCOUNT_WRONG, incrementAttachment);
        }

    }


    /**
     * 新增用户和数据库已存在的用户是否重复
     *
     * @param userSaveDTO 当前用户账号
     */
    private void checkDB(UserSaveDto userSaveDTO, List<BaseUserInfo> dbList) {
        Set<Long> uniqueSet = Sets.newHashSet();
        for (BaseUserInfo detail : dbList) {
            //存在全局管理员子账号不允许再新增
            AssertUtils.isTrue(detail.getManagerLevel() > BaseUserManagerLevelEnum.GLOBAL_ADMINISTRATORS.getCode(), ReturnCodesEnum.GLOBAL_MANAGER_ONLY_ONE.getMessage());
            AssertUtils.isTrue(userSaveDTO.getManagerLevel() > BaseUserManagerLevelEnum.GLOBAL_ADMINISTRATORS.getCode(), ReturnCodesEnum.GLOBAL_MANAGER_ONLY_ONE.getMessage());
            Long key = detail.getGroupId();
            uniqueSet.add(key);
        }
        check(userSaveDTO, uniqueSet);
    }

    /**
     * 检查新增的用户是否和uniqueSet重复
     *
     * @param userSaveDTO 新增用户
     * @param uniqueSet   已经校验的用户
     */
    private void check(UserSaveDto userSaveDTO, Set<Long> uniqueSet) {
        Long key = userSaveDTO.getGroupId();
        AssertUtils.isNotTrue(uniqueSet.contains(key), ReturnCodesEnum.USER_UNIQUE_ERROR.getMessage());
        uniqueSet.add(key);
    }
}
