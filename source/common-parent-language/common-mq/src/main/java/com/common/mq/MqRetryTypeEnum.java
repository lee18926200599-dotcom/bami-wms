package com.common.mq;


/**
 * 货位状态信息
 */

public enum MqRetryTypeEnum {
    NEW_ITEM(1, "新品"),
    ASSIGN_LOCATION(2, "货位推荐"),
    ORDER_HANDLER(3, "订单预处理"),
    WAVE_OCCUPY(4, "波次库存预占"),
    STOCK_MOVE(5, "库存转移"),
    TAKE_DOWN(6, "下架确认"),
    ORDER_SHIP(7, "面单获取"),
    ORDER_SHIP_V2(14, "面单获取版本2"),
    ORDER_BATCH_OCCUPY_STOCK(15, "批量占用货位库存"),
    ASSIGN_LOCATION_MOVE_AREA(8, "移库货位推荐"),
    ASSIGN_LOCATION_INTERCEPT_RETURN(9, "拦截归位货位推荐"),
    ASSIGN_LOCATION_SALE_RETURN(10, "销退入库货位推荐"),
    BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY(11, "快递面单模板更新通知"),
    ORDER_CALLBACK(12, "回传"),
    WIDE_ORDER_UPDATE(13, "宽表更新"),
    ORDER_RECEIVE(16, "接单"),
    ;

    private int type;
    private String name;

    MqRetryTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(int type) {
        for (MqRetryTypeEnum ele : MqRetryTypeEnum.values()) {
            if (ele.getType() == type)
                return ele.getName();
        }
        return null;
    }

    public static MqRetryTypeEnum getEnum(int type) {
        for (MqRetryTypeEnum ele : MqRetryTypeEnum.values()) {
            if (ele.getType() == type)
                return ele;
        }
        return null;
    }
}
