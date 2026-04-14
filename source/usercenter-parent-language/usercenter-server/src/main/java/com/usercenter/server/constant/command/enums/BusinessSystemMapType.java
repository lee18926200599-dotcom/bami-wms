package com.usercenter.server.constant.command.enums;

/**
 * 身份类型枚举
 */
public enum BusinessSystemMapType {
    IDENTITY_TYPE(1,"身份类型"),
    USER_SYSTEM(2,"用户体系");
    private Integer id;
    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BusinessSystemMapType(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
