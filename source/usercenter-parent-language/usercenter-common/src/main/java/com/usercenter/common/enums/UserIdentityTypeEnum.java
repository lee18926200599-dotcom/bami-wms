package com.usercenter.common.enums;

import java.util.Arrays;

/**
 * 用户身份类型
 */
public enum UserIdentityTypeEnum {
    STAFF(0, "员工"),
    CUSTOMER(1, "客户"),
    SUPPLIER(2, "供应商"),
    ;
    private Integer code;

    private String name;


    UserIdentityTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserIdentityTypeEnum getEnum(String code) {
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
