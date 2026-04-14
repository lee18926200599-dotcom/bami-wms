package com.org.permission.server.permission.enums;

public enum PermissionTypeEnum {
    ORG("org", "组织权限"), FUNC("func", "功能权限"), DATA("data", "数据权限");
    public String code;
    public String desc;

    PermissionTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
