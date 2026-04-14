package com.common.base.enums;

import java.util.Arrays;

/**
 * 状态枚举
 */
public enum StateEnum {

    CREATE(0, "创建"),
    ENABLE(1, "启用"),
    DISABLE(2,"停用");

    private Integer code;

    private String name;


    StateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StateEnum getEnum(Integer code) {
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
