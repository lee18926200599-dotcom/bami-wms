package com.common.base.enums;

import java.util.Arrays;

public enum RuleTypeEnum {
    ABC_RULE("abcRule", "是否按商品ABC管理"),
    MANAGED_BY_CATEGROY_RULE("managedByCategroyRule", "是否按仓储分类管理"),
    MANAGE_EXPENSIV_ITEM_RULE("manageExpensivItemRule", "是否管理贵品"),
    RECORD_CARRIER_RULE("recordCarrierRule", "到货登记是否记录承运商"),
    STOCK_INBOUND_CHANGE_RULE("stockInboundChangeRule", "收货入库库存变化点"),
    ITEM_BARCODE_CHECK_RULE("itemBarcodeCheckRule", "商品条码校验规则"),
    ITEM_EFFECTIVE_DATE_RULE("itemEffectiveDateRule", "商品效期管控"),
    BATCH_NUMBER_RULE("batchNumberRule", "商品批号管理"),
    ITEM_AUXILIARY_RULE("itemAuxiliaryRule", "商品辅助信息维护规则"),
    RECEIVED_INSPEC_RULE("receivedInspecRule", "收货抽检"),
    OVER_RECEIVED_CONTROL_RULE("overReceivedControlRule", "超收控制"),
    CLOSE_ORDER_METHOD_RULE("closeOrderMethodRule", "收货完成关单方式"),
    RETURN_RECEIVING_RESULTS_RULE("returnReceivingResultsRule", "收货结果回传"),
    ALLOWD_EFECTIVE_ITEM_RULE("allowdEfectiveItemRule", "是否允许收残"),
    ALLOWD_MIXED_RECEIVE_RULE("allowdMixedReceiveRule", "同商品同容器是否允许混批收"),
    ENABLE_RECEIVE_RESULT_RULE("enableReceiveResultRule", "是否启用收货结果登记"),
    ITEM_MIXED_BATCHES_RULE("itemMixedBatchesRule", "正品是否混批次存放"),
    FLOW_CONFIG_RULE("flowConfigRule", "流程配置规则"),
    DELIVER_AND_FACENO_PRINT_RULE("deliverAndFaceNoPrintRule", "快递面单打印规则"),
    PICK_UP_BILL_CREATION_RULE("pickUpBillCreationRule", "拣选单生成规则"),
    PICK_UP_BY_BILL_RULE("pickUpByBillRule", "按单拣货判定规则"),
    STORAGE_AREA_PICK_UP_RULE("storageAreaPickUpRule", "备货区商品拣选规则"),
    STORAGE_DISTRIBUTION_RULE("storageDistributionRule", "库存分配规则配置规则"),
    WAREHOUSE_CUT_OFF_TIME_RULE("warehouseCutOffTimeRule", "仓库截单时间规则"),
    BOX_LIST_AND_BOX_LABEL_PRINTING_RULE("boxListAndBoxLabelPrintingRule", "装箱清单与箱签打印规则"),
    WEIGHT_HAND_OVER_RULE("weightHandOverRule", "称重规则"),
    WAVE_HANG_UP_RULE("waveHangUpRule", "波次挂起规则"),
    LACK_GOODS_SEND_RULE("lackGoodsSendRule", "缺货发货出库规则"),
    PULL_OFF_SHELVES_RULE("pullOffShelvesRule", "下架规则"),
    INVENTORY_ORDER_RULE("inventoryOrderRule", "盘点单生成规则"),
    MOVE_AREA_TASK_RULE("moveAreaTaskRule", "移库单生成规则"),
    MOVE_LOCATION_TASK_RULE("moveLocationTaskRule", "移位单生成规则"),
    ALLOW_RF_PUT_AWAY_RULE("allowRFPutAwayRule", "RF上架规则"),
    INVENTORY_ORDER_DIFF_RETURN_RULE("inventoryOrderDiffReturnRule", "盘点差异是否回传规则"),

    ;


    private String code;

    private String name;


    RuleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RuleTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static RuleTypeEnum getEnumByName(String name) {
        return Arrays.stream(values())
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
