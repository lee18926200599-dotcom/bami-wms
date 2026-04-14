package com.basedata.server.dto.waybill.template.tb;

import lombok.Data;

@Data
public class CloudPrintBaseResult {
    /**
     * 所有cp的标准模板
     */
    private StandardTemplateResultDatas datas;

    private String error_code;
    /**
     * 获取面单失败	错误信息
     */
    private String error_message;
    /**
     * 是否成功
     */
    private Boolean success;
}
