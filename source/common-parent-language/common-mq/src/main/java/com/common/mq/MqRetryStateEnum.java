package com.common.mq;

public enum MqRetryStateEnum {

    CREATE(10, "创建"),
    PROCESS(20, "执行中"),
    ;

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MqRetryStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
