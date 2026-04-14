
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

/**
 *对应类型自定义模板资源
 */
@Data
public class CustomTemplateDto {
    /**
     * 模板编码，长度2-20
     */
    private String customTemplateCode;
    /**
     * 模板名称，长度50
     */
    private String customTemplateName;
    /**
     * 模板URL，长度255
     */
    private String customTemplateUrl;
}
