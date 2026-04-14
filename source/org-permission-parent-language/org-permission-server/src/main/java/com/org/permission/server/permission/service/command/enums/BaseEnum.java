package com.org.permission.server.permission.service.command.enums;

public interface BaseEnum {

    /**
     * 获取枚举ID
     * @return
     */
    Integer getId();

    /**
     * 获取枚举名称
     * @return
     */
    String getName();
    /**
     * 获取枚举描述
     * @return
     */
    String getDesc();

}