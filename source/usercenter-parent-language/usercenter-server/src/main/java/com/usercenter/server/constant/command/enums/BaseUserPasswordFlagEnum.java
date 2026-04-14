package com.usercenter.server.constant.command.enums;

/**
 * 重置密码枚举
 */
public enum BaseUserPasswordFlagEnum  {
    NO(0,"不需要重置"),
    YES(1,"需要重置密码");

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

    BaseUserPasswordFlagEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
