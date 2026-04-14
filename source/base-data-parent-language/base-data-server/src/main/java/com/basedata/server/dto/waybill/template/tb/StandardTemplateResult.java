package com.basedata.server.dto.waybill.template.tb;

import lombok.Data;

/**
 *  所有cp的标准模板
 */
@Data
public class StandardTemplateResult {
    /**
     * cp编码
     */
    private String cp_code;
    /**
     * 该cp的所有标准模板
     */
    private StandardTemplates standard_templates;
}
