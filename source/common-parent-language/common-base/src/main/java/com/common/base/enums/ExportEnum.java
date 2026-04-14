package com.common.base.enums;

/**
 * 业务类型状态枚举
 */
public enum ExportEnum {
    SELECTED("选中"),
    CURRENT("当前页"),
    FIXED("选定"),
    ALL("所有页"),
    ;

    private String name;


    ExportEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
