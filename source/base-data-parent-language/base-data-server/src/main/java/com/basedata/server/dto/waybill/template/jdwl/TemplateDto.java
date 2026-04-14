
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

/**
 *  对应承运商面单模板资源
 */
@Data
public class TemplateDto {
    /**
     * 模板编码，长度2-20
     */
    private String standardTemplateCode;
    /**
     * 模板名称，长度50
     */
    private String standardTemplateName;
    /**
     * 模板URL，长度255
     */
    private String standardTemplateUrl;
}
