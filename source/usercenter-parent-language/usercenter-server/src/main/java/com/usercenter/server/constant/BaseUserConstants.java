package com.usercenter.server.constant;

public interface BaseUserConstants {

    /**
     * 用户编码前缀：管理员
     */
    String USER_NUMBER_PREFIX_MANAGER = "AP";

    /**
     * 用户编码前缀：管理员 细表
     */
    String USER_DETAIL_NUMBER_PREFIX_MANAGER = "D_AP";

    /**
     * 用户编码前缀：普通用户
     */
    String USER_NUMBER_PREFIX_USER = "U";

    /**
     * 用户编码前缀：普通用户 细表
     */
    String USER_DETAIL_NUMBER_PREFIX_USER = "D_U";

    /**
     * 用户密码前缀
     */
    String USER_PASSWORD_PREFIX = "Plat";


    /**
     * 重置密码redis缓存
     */
    String RESET_PASSWORD_PREFIX = "RESET_PASSWORD";


    /**
     * 用户账户锁定redis 缓存目录【子表缓存】
     */
    String USER_ACCOUNT_LOCK_DETAIL = "LOCK_STATE:DETAIL:";


    /**
     * 用户账户锁定redis,缓存目录【主表目录】
     */
    String USER_ACCOUNT_LOCK_ID = "LOCK_STATE:ID:";
    /**
     * 用户账户锁定账户 格式id+_lock
     */
    String USER_LOCK_SUFFIX = "_lock";

    /**
     * 用户菜单权限缓存后缀
     */
    String CACHE_MENU_PERMISSIONS_SUFFIX = "_menuPermissons";

    /**
     * 失败5次锁定
     */
    Integer LOGIN_FAIL_COUNT = 5;

    /**
     * 登陆失败次数redis后缀
     */
    String LOGIN_FAIL_COUNT_SUFFIX = "_loginCount";

    /**
     * 修改密码验证码效期 s
     */
    Integer PASSWORD_CODE_TIME = 300;
    /**
     * web token验证成功后延期时间（s）
     */
    Integer WEB_ACTIVE_TIME = 3600;

    Long Plat_GROUP_ID = 2L;
    /**
     * 登录失败次数 redis后缀
     */
    String LOGIN_FAIL_COUNT_ATTACHMENT = "failedLoginCount";

    /**
     * 验证码redis后缀
     */
    String LOGIN_CAPTEXT_SUFFIX = "_capText";

    /**
     * 禁止调用，修改用户手机号
     */
    String BASE_USER_UPDATE_PHONE_LOCK="BASE_USER:UPDATE:PHONE:LOCK:";
    /**
     * 用户手机/ID ：手机验证码错误次数统计前缀
     */
    String BASE_USER_CODE_ERROR = "BASE_USER:CODE:ERROR:";

    /**
     * redis的失效时长，5分钟
     */
    Integer TTL_5_MIN=300;

    /**
     * REDIS的失效时长，12小时
     */
    Integer TTL_12_HOUR=432000;

    /**
     * 禁止调用 修改秘密的用户
     */
    String BASE_USER_UPDATE_PASSWORD_LOCK="BASE_USER:UPDATE:PASSWORD:LOCK:";
    /**
     * 图片验证码缓存【token+"_capText"】
     */
    String CAPTCH_TOKEN_SUFFIX="_capText";
    /**
     * 短信或者邮箱验验证码缓存【格式 id+_passwordCode】
     */
    String PASSWORD_CODE_SUFFIX = "_passwordCode";
    /**
     * 校验验证码结果缓存 前缀
     */
    String BASE_USER_CODE_RESULT = "BASE_USER:CODE:RESULT:";
    /**
     * 接口尝试次数
     */
    Integer COUNT = 5;
}
