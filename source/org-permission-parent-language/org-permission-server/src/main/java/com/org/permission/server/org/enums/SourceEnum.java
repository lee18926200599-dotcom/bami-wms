package com.org.permission.server.org.enums;

import java.util.Arrays;

/**
 * 业务类型状态枚举
 */
public enum SourceEnum {
    BOSS(1, "BOSS");

    private Integer code;

    private String name;


    SourceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SourceEnum getEnum(Integer code) {
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
