package com.common.log.entity;

/**
 * 业务流程
 */
public enum LogFlowEnum {
    PURCHASE_INBOUND(10, "采购入库"),
    PURCHASE_RETURN_INBOUND(20, "采退入库"),
    SALE_REFUND_INBOUND(30, "销退入库"),
    TRANSFER_INBOUND(40, "调拨入库"),
    TRANSFER_OUTBOUND(50, "调拨出库"),
    CONVERT_IN(80, "包装转换入库"),
    CONVERT_OUT(90, "包装转换出库"),
    SALE_OUT(100, "销售出库"),
    LACK_ORDER_CANCEL(110, "报缺取消"),
    REFUND_SUPPLY(120, "退供出库"),
    DIFFERENCE(121, "差异"),
    MOVE_TAKE_DOWN(130, "移库"),
    MOVE_LOCATION(170, "移位"),

    OTHER_INBOUND(140, "其它入库"),
    OTHER_OUTBOUND(150, "其它出库"),
    INV_CHECK(160, "盘点"),
    TASK_DOWN(135, "下架"),
    PUT_AWAY(136, "上架")
    ;
    private Integer code;
    private String name;


    LogFlowEnum(Integer code,String name) {
        this.code=code;
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
