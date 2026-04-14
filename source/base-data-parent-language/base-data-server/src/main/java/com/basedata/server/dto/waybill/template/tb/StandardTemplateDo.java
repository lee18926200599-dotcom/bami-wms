package com.basedata.server.dto.waybill.template.tb;

import lombok.Data;

/**
 *  该cp的所有标准模板
 */
@Data
public class StandardTemplateDo {
    /**
     * 模板id
     */
    private Integer standard_template_id;
    /**
     * 模板名称
     */
    private String standard_template_name;
    /**
     * 模板url
     */
    private String standard_template_url;
    /**
     * 1 快递标准面单 ,2 快递三联面单, 3 快递便携式三联单, 4 快运标准面单, 5 快运三联面单, 6 快递一联单
     */
    private Integer standard_waybill_type;
    /**
     * 如果没有 brandCode,则为 default
     */
    private String brand_code;
}
