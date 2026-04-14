package com.usercenter.common.enums;

/**
 * 登录模式
 */
public enum LoginModeEnum {
    /**
     * 不控制多端登陆（默认）
     */
    NONE,
    /**
     * 同一个应用一个账号只能有一个登陆
     */
    APP_SINGLE,
    /**
     * 同一个账号只能在所有应用中只能有一个地方登陆
     */
    GLOBAL_SINGLE
}
