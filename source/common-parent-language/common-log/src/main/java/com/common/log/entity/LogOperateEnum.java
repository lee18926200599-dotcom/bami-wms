package com.common.log.entity;

/**
 * 业务类型状态枚举
 */
public enum LogOperateEnum {
    LOG_SAVE("新增"),
    LOG_UPDATE("修改");

    private String name;


    LogOperateEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



}
