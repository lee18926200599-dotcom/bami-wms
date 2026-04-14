package com.org.permission.server.org.enums;

import java.util.Arrays;

/**
 * 用工性质枚举
 */
public enum EmploymentTypeEnum {

    /**
     * 1=正式工
     * 2=临时工
     */
    FORMAL(1,"正式工"),
    TEMP(2,"临时工");
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

    EmploymentTypeEnum(int code, String name){
        this.code=code;
        this.name=name;
    }
    public static EmploymentTypeEnum getEnum(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode()==code)
                .findFirst()
                .orElse(null);
    }
}
