package com.usercenter.common.enums;

public enum FlagEnum {
    TRUE(1),
    FALSE(0);
    private Integer code;

    FlagEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
