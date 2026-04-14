package com.org.permission.server.org.enums;

import java.util.Arrays;

/**
 * 对应客户上下游关系状态枚举
 */
public enum CustTypeEnum {
    CUSTOM(1, " 客户（下游）"),
    SUPPLIER(2, "供应商（上游）");

    private Integer code;

    private String name;


    CustTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CustTypeEnum getEnum(Integer code) {
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
