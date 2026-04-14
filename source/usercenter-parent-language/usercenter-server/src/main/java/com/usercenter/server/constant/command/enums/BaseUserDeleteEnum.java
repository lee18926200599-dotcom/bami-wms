package com.usercenter.server.constant.command.enums;

/**
 * 删除状态
 */
public enum BaseUserDeleteEnum  {
    UNDELETE(0,"未删除"),
    DELETE(1,"已删除");

    private Integer id;

    private String desc;

    BaseUserDeleteEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

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
}
