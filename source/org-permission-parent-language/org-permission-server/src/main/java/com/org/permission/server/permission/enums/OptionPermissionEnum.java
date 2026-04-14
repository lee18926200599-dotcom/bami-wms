package com.org.permission.server.permission.enums;

/**
 * 操作权限枚举
 */
public enum OptionPermissionEnum {

    NO(0,"没有操作权限"),
    QUERY(1,"查询"),
    OPERATE(2,"操作"),
    ;
    private int type;
    private String desc;
    OptionPermissionEnum(int type,String desc){
        this.type=type;
        this.desc=desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
