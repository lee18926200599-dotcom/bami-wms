package com.usercenter.common.enums;

/**
 * 验证方式
 */
public enum ValidateTypeEnum {
    /**
     * 手机
     */
    PHONE(0),
    /**
     * 邮箱
     */
    EMAIL(1),
    /**
     * 修改密码CODE
     */
    CODE(2),
    ;

    private int code;

    ValidateTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
