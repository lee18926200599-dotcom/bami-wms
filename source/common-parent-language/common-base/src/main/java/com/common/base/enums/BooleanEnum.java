package com.common.base.enums;

import java.util.Arrays;

/**
 * 业务类型状态枚举
 */
public enum BooleanEnum {
    TRUE(1, "是"),
    FALSE(0, "否");

    private Integer code;

    private String name;


    BooleanEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BooleanEnum getEnum(Integer code) {
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
