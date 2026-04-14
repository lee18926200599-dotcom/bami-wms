package com.usercenter.server.constant.command.enums;

/**
 * 锁定状态
 */
public enum BaseUserLockEnum  {
    UNLOCK(0,"未锁定"),
    LOCK(1,"锁定");

    private Integer id;

    private String desc;

    BaseUserLockEnum(Integer id, String desc) {
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
