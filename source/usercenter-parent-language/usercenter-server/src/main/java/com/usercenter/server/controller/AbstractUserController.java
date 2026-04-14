package com.usercenter.server.controller;

import com.common.util.util.AssertUtils;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserPasswordHistory;
import com.usercenter.server.service.IBaseUserApiService;
import com.usercenter.server.service.IBaseUserCommonService;
import com.usercenter.server.utils.PasswordEncoder;
import com.usercenter.server.utils.RegexUtils;
import com.usercenter.server.utils.UserRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 提供 基于controller的公用方法
 */
public abstract class AbstractUserController {


    @Autowired
    protected IBaseUserApiService userApiService;

    @Autowired
    @Qualifier("baseUserCommonServiceImpl")
    protected IBaseUserCommonService iBaseUserCommonService;

    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${web.token.expire.time:86400}")
    protected int webTokenExpireTime;
    @Value("${app.token.expire.time:1296000}")
    protected int appTokenExpireTime;
    @Value("${user.token.secretkey:8c7d6436694217bce63e5abfbdfde75e}")
    protected String userTokenSecretkey;
    @Resource
    protected PasswordEncoder passwordEncoder;

    @Value("${user.id.generated.name:ucenter}")
    protected String idGeneratedName;

    @Value("${multi.device.login:true}")
    protected Boolean multiDeviceLogin;
    @Value("${spring.profiles.active:dev}")
    protected String profile;

    @Resource
    protected UserRedisUtil userRedisUtil;

    /**
     * 更新密码参数校验
     *
     * @param baseUser
     * @param code
     * @param oldPassword
     * @param newPassword
     * @return
     */
    protected ReturnCodesEnum updateUserPasswordCheck(BaseUser baseUser, String code, String oldPassword, String newPassword) {
        AssertUtils.isTrue(RegexUtils.isPassword(newPassword), ReturnCodesEnum.REGEX_FAILD_PASSWORD.getMessage());
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(oldPassword)) {
            return ReturnCodesEnum.OLDPASSWORD_CODE_EMPTY;
        }
        if (StringUtils.isNotEmpty(oldPassword)) {
            if (!passwordEncoder.encode(oldPassword).equals(baseUser.getPassword())) {
                return ReturnCodesEnum.OLDPASSWORD_WRONG;
            }
        }

        AssertUtils.isNotTrue(passwordEncoder.encode(newPassword).equals(baseUser.getPassword()), ReturnCodesEnum.PASSWORD_SAME.getMessage());
        return null;
    }


    /**
     * 数据更新
     *
     * @param baseUser
     * @param newPassword
     */
    protected void updateUserPassword(BaseUser baseUser, String newPassword) {
        if (baseUser.getFirstTimeLoginFlag() == FlagEnum.TRUE.getCode()) {
            baseUser.setFirstTimeLoginFlag(FlagEnum.FALSE.getCode());
        }
        if (baseUser.getPasswordFlag() == FlagEnum.TRUE.getCode()) {
            baseUser.setPasswordFlag(FlagEnum.FALSE.getCode());
        }
        baseUser.setPassword(passwordEncoder.encode(newPassword));
        BaseUserPasswordHistory userPasswordHistory = new BaseUserPasswordHistory();
        userPasswordHistory.setUserId(baseUser.getId());
        userPasswordHistory.setPassword(passwordEncoder.encode(newPassword));
        userPasswordHistory.setCreateDate(new Date());
        userApiService.updateUserNameAndPassword(baseUser, userPasswordHistory);
    }


    /**
     * 获取调用失败次数【绑定主键，用户ID】
     *
     * @param userId
     * @return
     */
    protected Integer errorInvoke(Long userId) {
        String sCount = userRedisUtil.getStr(BaseUserConstants.BASE_USER_UPDATE_PHONE_LOCK + userId);
        if (StringUtils.isNotEmpty(sCount) && StringUtils.isNumeric(sCount)) {
            return new Integer(sCount);
        }
        return 0;
    }


    /**
     * 调用接口不通过，次数+1
     *
     * @param userId
     * @param errorCount
     */
    protected void writeErrorToRedis(Long userId, Integer errorCount) {
        errorCount++;
        userRedisUtil.set(BaseUserConstants.BASE_USER_CODE_ERROR + userId, errorCount.toString(), BaseUserConstants.TTL_5_MIN);
    }


    /**
     * 禁用用户 调用修改密码接口
     * 禁用时长  12小时
     *
     * @param userId
     */
    protected void updatePasswordLock(Long userId) {
        userRedisUtil.set(BaseUserConstants.BASE_USER_UPDATE_PASSWORD_LOCK + userId, Boolean.TRUE.toString(), BaseUserConstants.TTL_12_HOUR);
    }

    /**
     * 判断用户是否可以调用修改密码接口
     *
     * @param userId
     * @return
     */
    protected Boolean getUpdatePasswordLock(Long userId) {
        String s = userRedisUtil.getStr(BaseUserConstants.BASE_USER_UPDATE_PASSWORD_LOCK + userId);
        if (Boolean.TRUE.toString().equals(s)) {
            return true;
        }
        return false;
    }


    protected void updateBindPhoneLock(Long userId) {
        userRedisUtil.set(BaseUserConstants.BASE_USER_UPDATE_PHONE_LOCK + userId, Boolean.TRUE.toString(), BaseUserConstants.TTL_12_HOUR);
    }


    protected Boolean getUpdateBindPhoneLock(Long userId) {
        String s = userRedisUtil.getStr(BaseUserConstants.BASE_USER_UPDATE_PHONE_LOCK + userId);
        if (Boolean.TRUE.toString().equals(s)) {
            return true;
        }
        return false;
    }


}
