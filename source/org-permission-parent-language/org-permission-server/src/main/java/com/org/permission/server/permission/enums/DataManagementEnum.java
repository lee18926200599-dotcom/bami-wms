package com.org.permission.server.permission.enums;

/**
 *  数据权限维度  1=仓库  2=客户 3=供应商 4=区域 5=业务平台 6=CRM客户查看权限 7=站点
 */
public enum DataManagementEnum {

    WAREHOUSE(1, "仓库"),
    CUSTOMER(2, "客户"),
    SUPPLIER(3, "供应商"),
    REGION(4, "区域"),
    BUSINESS_PLATFORM_NUMBER(5, "业务平台编号"),
    CRM_CUSTOMER(6, "CRM客户查看权限"),
    SITE(7, "站点"),
    BUSINESS_LINE(8, "业务线"),
    ;
    private Integer code;
    private String name;

    DataManagementEnum(Integer code, String name) {
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
