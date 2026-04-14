package com.common.log.entity;

/**
 * 业务类型状态枚举
 */
public enum LogModuleEnum {
    WAREHOUSE("仓库"),
    BOX("箱型"),
    AREA("库区"),
    LOCATION("货位"),
    LOCATION_RULE("货位规则"),
    WMS_RULE("规则配置"),
    CONTAINER("容器"),
    CONTAINER_TYPE("容器类型"),
    PLATFORM("月台"),
    ITEM("商品"),
    ITEM_IMAGE("商品图片"),
    ITEM_KEEP_RULE("商品存放规则"),
    ITEM_PICK_RULE("商品拣选规则"),
    WORKBENCH("工作台"),
    SUPPLIER("供应商"),
    COMPANY("客户"),
    OWNER_AREA("货主库区配置"),
    DISASSEMBLY_TEAM("拆装队"),
    RECEIVED_EXPENSE_BILL("应收费用单"),
    ;

    private String name;


    LogModuleEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



}
