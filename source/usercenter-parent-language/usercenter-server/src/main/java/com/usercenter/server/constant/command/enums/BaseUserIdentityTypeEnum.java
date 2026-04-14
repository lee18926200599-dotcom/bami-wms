package com.usercenter.server.constant.command.enums;

/**
 * 身份类型枚举
 */
public enum BaseUserIdentityTypeEnum  {
    STAFF(0,"员工"),
    CUSTOMER(1,"客户"),
    SUPPLIER(2,"供应商"),
    MEMEBER(3,"会员")
    ;
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

    BaseUserIdentityTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
