package com.usercenter.common.enums;

import java.util.Arrays;

/**
 * 用户状态
 */
public enum UserStateEnum {

    UNENABLE(0, "未启用"),
    ENABLE(1, "启用"),
    DISABLE(2, "停用"),
    ;

    private Integer code;

    private String name;


    UserStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserStateEnum getEnum(String code) {
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
