package com.basedata.server.constant;

import org.springframework.stereotype.Component;

@Component
public class MqConstants {
    //交换机最大重试次数
    public static final Integer MAX_ATTEMPT_COUNT = 2;

    //重试间隔时间（毫秒值）
    public static final Long INTERVAL_TIME = 5L;

    //仓库启用停用生成默认规则
    public static final String WMS_4PL_WAREHOUSE_CREATE_DEFAULT_RULE_QUEUE = "wms_4pl_warehouse_create_default_rule_queue";
    public static final String WMS_4PL_WAREHOUSE_CREATE_DEFAULT_RULE_EXCHANGE = "wms_4pl_warehouse_create_default_rule_exchange";
    public static final String WMS_4PL_WAREHOUSE_CREATE_DEFAULT_RULE_RUTE_KEY = "wms_4pl_warehouse_create_default_rule_rute_key";

    // 快递面单模板配置更新通知（base_data服务）
    public static final String WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_QUEUE = "wms_4pl_basedata_store_print_template_update_notify_queue";
    public static final String WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_EXCHANGE = "wms_4pl_basedata_store_print_template_update_notify_exchange";
    public static final String WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_KEY = "wms_4pl_basedata_store_print_template_update_notify_key";


}
