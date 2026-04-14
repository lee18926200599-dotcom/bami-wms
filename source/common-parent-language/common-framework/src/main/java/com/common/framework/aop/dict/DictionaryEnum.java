package com.common.framework.aop.dict;

/**
 * @Description 字典枚举
 */
public enum DictionaryEnum {

    STRUCT_TYPE("STRUCT_TYPE","结构类型"),
    ORDER_STATE("ORDER_STATE","订单状态"),
    OPERATE_TYPE("OPERATE_TYPE", "运营类型"),

    DELIVERY_TYPE("DELIVERY_TYPE","配送方式"),

    WAREHOUSE_PROPERTIES("WAREHOUSE_PROPERTIES", "仓库性质"),

    LOCATION_RULE("LOCATION_RULE", "货位规则"),

    AREA_TYPE("AREA_TYPE", "库区类型"),

    PURPOSE_ATTR("PURPOSE_ATTR", "用途属性"),

    REPLENISH_ARRT("REPLENISH_ARRT", "补货属性"),

    RETURN_ATTR("RETURN_ATTR", "退货属性"),

    LOCATION_OMNER_ATTR("LOCATION_OMNER_ATTR", "货位货主属性"),

    ITEM_STORAGE_ATTR("ITEM_STORAGE_ATTR", "商品存放属性"),

    ITEM_ABC_CATEGORY("ITEM_ABC_CATEGORY", "商品ABC分类"),

    LOCATION_STORAGE_ATTR("LOCATION_STORAGE_ATTR", "货位存放属性"),

    GOODS_ATTR("GOODS_ATTR", "货物属性"),

    CIRCULATION_ATTR("CIRCULATION_ATTR", "流转属性"),

    TEMPERATURE_ATTR("TEMPERATURE_ATTR", "温控属性"),

    STORAGE_SPEC("STORAGE_SPEC", "储存规格"),

    ARAE_CODE_RULE("ARAE_CODE_RULE", "获取编码规则"),

    CONTAINER_CATEGORY("CONTAINER_CATEGORY", "容器类别"),

    WORKBENCH_TYPE("WORKBENCH_TYPE", "工作台类型"),

    DISASSEMBLYTEAM_TYPE("DISASSEMBLYTEAM_TYPE", "装卸队类型"),

    LOCATION_TYPE("LOCATION_TYPE", "货架类型"),

    CONTAINER_RULE_TYPE("CONTAINER_RULE_TYPE", "容器顺序规则"),

    PLATEFORM_BUSI_TYPE("PLATEFORM_BUSI_TYPE", "月台业务类型"),

    PLATEFORM_UNIT("PLATEFORM_UNIT", "月台吞吐单位"),

    EMPLOYMENT_TYPE("EMPLOYMENT_TYPE", "用工类型"),

    PICKUP_ORDER_TYPE("PICKUP_ORDER_TYPE", "拣选单类型"),

    /**********************************************/

    INBOUND_TYPE("INBOUND_TYPE", "入库单类型"),

    INBOUND_NOTIFY_STATE("INBOUND_NOTIFY_STATE", "入库通知单状态"),

    ITEM_VALIDITY_STATE("ITEM_VALIDITY_STATE", "期效状态"),


    REJECTION_STATE("REJECTION_STATE", "拒收单状态"),

    RECEIVING_INBOUND_STATE("RECEIVING_INBOUND_STATE", "收货入库状态"),

    /*开始--------------------- 库内 ---------------------------*/
    PUTAWAY_STATE("PUTAWAY_STATE", "上架单状态"),
    PUTAWAY_TYPE("PUTAWAY_TYPE", "上架单类型"),
    PUTAWAY_SOURCE_TYPE("PUTAWAY_SOURCE_TYPE", "上架单来源单据类型"),
    PUTAWAY_BUSINESS_TYPE("PUTAWAY_BUSINESS_TYPE", "上架单业务单据类型"),
    TAKE_DOWN_STATE("TAKE_DOWN_STATE", "下架单状态"),
    TAKE_DOWN_TYPE("TAKE_DOWN_TYPE", "下架单类型"),
    TAKE_DOWN_SOURCE("TAKE_DOWN_SOURCE", "下架单来源单据类型"),
    TAKE_DOWN_BUSINESS_TYPE("TAKE_DOWN_BUSINESS_TYPE", "下架单业务类型"),
    INSIDE_DIFFERENCE_STATE("INSIDE_DIFFERENCE_STATE", "差异单状态"),
    INSIDE_DIFFERENCE_TYPE("INSIDE_DIFFERENCE_TYPE", "差异单类型/损益单来源单据类型"),
    INSIDE_DIFFERENCE_SOURCE_TYPE("INSIDE_DIFFERENCE_SOURCE_TYPE", "差异单来源类型"),
    PROFIT_LOSS_ORDER_STATE("PROFIT_LOSS_ORDER_STATE", "损益单状态"),
    PROFIT_LOSS_ORDER_TYPE("PROFIT_LOSS_ORDER_TYPE", "损益单类型"),
    IN_OUT_TYPE("IN_OUT_TYPE", "出入位类型"),
    IN_OUT_SOURCE_TYPE("IN_OUT_SOURCE_TYPE", "出入位业务类型"),
    IN_OUT_JOB_TYPE("IN_OUT_JOB_TYPE", "出入位作业类型"),
    UN_GROUP_TYPE("UN_GROUP_TYPE", "拆组类型"),
    MOVE_AREA_TYPE("MOVE_AREA_TYPE", "移库管理-移库类型"),
    MOVE_AREA_STATE("MOVE_AREA_STATE", "移库管理-移库单状态"),
    MOVE_AREA_SOURCE_ORDER_TYPE("MOVE_AREA_SOURCE_ORDER_TYPE", "移库管理-来源单据类型"),
    /*结束--------------------- 库内 ---------------------------*/

    /*开始--------------------- 出库 ---------------------------*/
    WAVE_STATE("WAVE_STATE", "波次类型"),
    /*结束--------------------- 出库 ---------------------------*/

    BMS_STATE("BMS_STATE", "状态"),
    BMS_EXPENSE_TYPE("BMS_EXPENSE_TYPE", "费用类型"),
    BMS_PURCHASE_SALES_TYPE("BMS_PURCHASE_SALES_TYPE", "采销类型"),
    BMS_DATA_TYPE("BMS_DATA_TYPE", "数据类型"),
    BMS_EXPENSE_BILL_STATE("BMS_EXPENSE_BILL_STATE", "费用单状态"),


    ;
    private String code;

    private String name;

    DictionaryEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


}
