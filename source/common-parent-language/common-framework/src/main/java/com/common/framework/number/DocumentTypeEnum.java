package com.common.framework.number;

import java.util.Arrays;

/**
 * 业务类型状态枚举
 */
public enum DocumentTypeEnum {
    INBOUNDNOTIFY("RKTZ", 6, "入库通知单"),
    OUTBOUNDNOTIFY("CKTZ", 7, "出库通知单"),
    OUTORDERNO("CK", 6, "出库单"),
    INBOUNDNOTIFY_POOL("RKTZC", 6, "入库通知单池"),
    RECEIVINGINBOUND("SH", 6, "收货入库单"),
    INBOUNDREJECTION("JS", 6, "收货拒收单"),
    RECEIVINGWORK("SHZY", 6, "收货作业单"),
    LACK_ORDER("BQ", 6, "报缺作业单"),
    PUTAWAY("SJ", 6, "上架单"),
    TAKEDOWN("XJ", 6, "下架单"),
    PICK("JX", 6, "拣选单"),
    WMS_ORDER("D", 7, "wms系统订单号"),
    WMS_SHIP("YD", 7, "wms运单测试"),
    WAREHOUSE_CATEGORY("CCFL", 6, "仓储分类"),
    IETM("IC", 6, "商品编码"),
    SALE_RETURN_ORDER("XT", 6, "销退单号"),
    SALE_RETURN_BATCH("XTB", 6, "销退批次号"),
    SALE_RETURN_RECEIVE("XTSH", 6, "销退收货单号"),
    MOVE_LOCATION("YW", 6, "移位单"),
    MOVE_AREA_TASK_NO("YK", 6, "移库单号"),
    INVENTORY_CHECK("PD", 4, "盘点单号"),
    REPLENISH_PLAN_ORDER("BH", 6, "补货计划单号"),
    WAVE_NO("BC", 6, "波次号"),
    BATCH_NO("BN", 6, "批次号"),
    RF_TASK_NO("TSK", 6, "rf任务号"),
    RF_WORK_NO("WK", 6, "rf作业单号"),
    PRICE_TABLE_NO("JGB", 6, "价格表编号"),
    BILLING_SOURCE_NO("JFY", 6, "计费源编号"),
    EXPENSE_BILL_NO("FYD", 6, "费用单编号"),
    SETTLEMENT_BILL_NO("JSD", 5, "结算单编号"),
    ADJUST_BILL_NO("TZD", 4, "费用调整单号"),
    ACCOUNT_STRATEGY_NO("JZCL", 3, "记账策略编码"),
    ACCOUNT_BILL_NO("DZD", 3, "对账单编号"),
    ARTIFICIAL_ACCOUNT_CONFIG_NO("RGZBD", 3, "人工账单号"),
    ARTIFICIAL_ACCOUNT_NO("RGZD", 5, "人工账单号"),
    ;

    private String code;

    private int size;

    private String name;


    DocumentTypeEnum(String code, int size, String name) {
        this.code = code;
        this.size = size;
        this.name = name;
    }

    public static DocumentTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }


}
