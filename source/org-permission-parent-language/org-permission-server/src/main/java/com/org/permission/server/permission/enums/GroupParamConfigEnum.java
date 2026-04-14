package com.org.permission.server.permission.enums;

/**
 * 集团参数设置
 */
public enum GroupParamConfigEnum {
    USER_PARAM("RBAC001", "允许为用户授权", "是否允许为用户直接授权"),
    FUNC_PARAM("RBAC002", "功能授权-用户角色冲突", "当用户功能权限和角色功能权限冲突时，策略"),
    ORG_PARAM("RBAC003", "组织授权-用户角色冲突", "当用户组织权限和角色功能权限冲突时，策略"),
    DATA_PARAM("RBAC004", "数据授权-用户角色冲突", "当用户数据权限和角色功能权限冲突时，策略"),
    ;
    public String value;
    public String name;
    public String remark;

    GroupParamConfigEnum(String value, String name, String remark) {
        this.value = value;
        this.name = name;
        this.remark = remark;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static GroupParamConfigEnum getEnumByValue(String value) {
        for (GroupParamConfigEnum config : GroupParamConfigEnum.values()) {
            if (config.getValue().equals(value)) {
                return config;
            }
        }
        return null;
    }

}
