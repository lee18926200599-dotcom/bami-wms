package com.usercenter.server.constant.command.enums;

/**
 * 管理员等级
 */
public enum BaseUserManagerLevelEnum  {
    SUPER_ADMINISTRATORS(0,"超级管理员"),
    GLOBAL_ADMINISTRATORS(1,"全局管理员"),
    GROUP_ADMINISTRATORS(2,"集团管理员"),
    USER(3,"用户");


    private Integer code;

    private String name;

    BaseUserManagerLevelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

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
}
