package com.usercenter.common.enums;

import java.util.Arrays;

public enum UserManagerLevelEnum {
    SUPER_ADMINI(0, "超级管理员"),
    GLOBAL_ADMINI(1, "全局管理员"),
    GROUP_ADMINI(2, "集团管理员"),
    USER(3, "用户");;
    private Integer code;

    private String name;


    UserManagerLevelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserManagerLevelEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
