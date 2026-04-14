package com.usercenter.common.enums;

import com.common.language.util.I18nUtils;

public enum ReturnCodesEnum {
    GENERATE_DETAIL_SUCCESS(1000, "user.generate.success"),
    /**
     * 参数校验 不通过
     */
    PARAM_PHONE_ERROR(1001,"user.param.phone.error"),
    PARAM_PHONE_NOT_EXIST(1002,"user.param.phone.notexist"),
    PARAM_BODY_NULL(1003,"user.param.body.null"),
    PARAM_PHONE_NULL(1004,"user.param.phone.null"),
    PARAM_SOURCE_TYPE_NULL(1005,"user.param.sourcetype.null"),
    PARAM_IDENTITY_TYPE_NULL(1006,"user.param.identitytype.null"),
    PARAM_USERNAME_ERROR(1007,"user.param.username.error"),
    PARAM_MANAGERLEVEL_ERROR(1008,"user.param.userlevel.null"),
    PARAM_SOURCE_ERROR(1009,"user.param.usersource.null"),
    PARAM_USER_ID_NOT_EXIST(1010,"user.param.userid.notexist"),

    /**
     * 用户已存在
     */
    USER_EXISTS(2001,"user.user.exist"),
    /**
     * 登录次数过多
     */
    MANY_LOGINS(2002,"user.login.many"),
    /**
     * 用户名或密码错误
     */
    ACCOUNT_WRONG(2003,"user.account.error"),
    /**
     *token过期
     */
    TOKEN_OVERDUE(2004,"user.token.overdue"),
    /**
     * 用户不存在
     */
    USER_NOTEXISTS(2005,"user.user.notexist"),

    /**
     * 用户名不存在
     */
    USERNAME_NOTEXISTS(2006,"user.username.notexist"),

    /**
     * 用户帐号、邮箱至少有一个非空
     */
    USERNAME_EMAIL_EMPTY(2007,"user.username.email.notempty"),

    /**
     * 手机号已被注册
     */
    PHONE_EXISTS(2008,"user.phone.exist"),

    /**
     * 用户编码已存在
     */
    USERNUMBER_EXISTS(2009,"user.usercode.exist"),
    /**
     * 用户未被停用
     */
    NOT_DISABLED(2010,"user.user.notdisabled"),

    /**
     * 请重置密码
     */
    PASSWORD_RESET(2011,"user.pwd.reset"),

    /**
     * 用户未启用
     */
    NOT_ENABLE(2013,"user.user.enabled"),
    CODE_WRONG(2014,"user.vcode.error"),
    CODE_NOTEXISTS(2015,"user.vcode.notexist"),

    PASSWORD_REUSE(2016,"user.pwd.reuse"),
    USERNAME_EXISTS(2017,"user.username.exist"),
    EMAIL_EXISTS(2018,"user.email.exist"),
    USER_UNIQUE_ERROR(2019,"user.group.user.unique"),
    INVALID_TOKEN(2020, "user.login.invalid"),
    LOGININ_OTHER(2021, "user.logining.other"),
    ACCOUNT_LOCK(2022, "user.accout.locked"),
    CURRENTID_NOTNULL(2023, "user.current.login.notnull"),
    CODE_EMPTY(2024, "user.vcode.empty"),
    USER_LOCK(2025,"user.user.locked"),
    GLOBAL_MANAGER_ONLY_ONE(2026,"user.user.global.exist"),
    GROUP_MANAGER_EXISTS(2027,"user.user.group.exist"),
    NO_DATA(2028,"user.data.null"),
    DELETE_USER_ERROR(2029,"user.user.cannot.delete"),

    PHONE_EMAIL_EMPTY(2030,"user.phone.email.notnull"),
    GET_CODE_ERROR(2030,"user.getvode.fail"),
    ERROR_LOGIN_OPERATE(2031,"user.current.user.nopermission"),
    USER_UPDATE_FAIL(2032,"user.admin.update.fail"),
    OLDPASSWORD_CODE_EMPTY(2033,"user.oldpwd.vcode.cannot.null"),
    OLDPASSWORD_WRONG(2034, "user.oldpwd.error"),
    PASSWORD_WRONG(2035, "user.pwd.error"),
    DOMAIN_NOT_EXIST(2036, "user.domain.notexist"),
    USER_NO_GROUP(2037,"user.user.group.data.null"),
    PASSWORD_SAME(2038,"user.pwd.same"),
    // 格式验证失败
    REGEX_FAILD_EMAIL(2100,"user.regex.email.error"),
    REGEX_FAILD_PHONE(2101,"user.regex.phone.error"),
    REGEX_FAILD_PASSWORD(2102,"user.regex.pwd.error"),
    REGEX_FAILD_NEWPASSWORD(2103,"user.regex.newpwd.error"),
    REGEX_FAILD_USERNAME(2104,"user.regex.username.error"),

    ;

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return I18nUtils.getMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ReturnCodesEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
