package com.usercenter.common.enums;

public enum BusinessSystemMapTypeEnum {
    IDENTITY_TYPE(1, "身份类型"),
    USER_SYSTEM(2, "用户体系");
    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BusinessSystemMapTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
