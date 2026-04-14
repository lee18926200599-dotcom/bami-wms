package com.org.permission.server.org.enums;

/**
 * 注册来源枚举类
 */
public enum RegistSourceEnum {
    BOSS(1000),
    OMS(2000),
    SCM(3000),
    TMS(4000),
    WMS(5000),

    ;

    private int value;

    private RegistSourceEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
