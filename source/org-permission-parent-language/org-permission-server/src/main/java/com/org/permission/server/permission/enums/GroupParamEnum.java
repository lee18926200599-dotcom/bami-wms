package com.org.permission.server.permission.enums;

/**
 *  集团参数设置参数值
 */
public enum GroupParamEnum {
    USER_PARAM("[{\"name\": \"是\",\"select\": \"0\"},{\"name\": \"否\",\"select\":\"1\"}]", "是否允许为用户直接授权(默认)"),
    FUNC_CLASH("[{\"name\": \"取用户权限\",\"select\": \"1\"},{\"name\": \"权限并集\",\"select\":\"0\"}]", "功能权限冲突默认"),
    ORG_CLASH("[{\"name\": \"取用户权限\",\"select\": \"1\"},{\"name\": \"权限交集\",\"select\":\"0\"}]", "组织权限冲突默认"),
    DATA_CLASH("[{\"name\": \"取用户权限\",\"select\": \"1\"},{\"name\": \"权限交集\",\"select\":\"0\"}]", "数据权限冲突默认"),
    USER_PARAM_YES("1", "是"),
    USER_PARAM_NO("0", "否"),
    CLASH_USER("1", "取用户权限"),
    SUM("1", "权限并集"),
    CROSS("1", "权限交集"),
    ROLE_STRATEGY("role", "取用户角色上的权限策略"),
    USER_STRATEGY("user", "取用户权限策略"),
    SUM_STRATEGY("sum", "取用户权限和角色权限并集策略"),
    CROSS_STRATEGY("cross", "取用户权限和角色权限交集策略"),
    PURCHASE_SALE_PRICE_BILLING_PARAM("[{\"name\": \"是\",\"select\": \"1\"},{\"name\": \"否\",\"select\":\"0\"}]", "销售(采购)价格表(计费策略)提交后自动审批通过(默认)"),
    NOT_ALLOW_DELETE_FEE_LOG_PARAM("[{\"name\": \"是\",\"select\": \"1\"},{\"name\": \"否\",\"select\":\"0\"}]", "不允许删除系统生成的费用流水;主要用于测试、试运行期间清除数据。删除规则：系统自动生成、已启用、未对账的费用流水。");
    public String value;
    public String desc;

    GroupParamEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
