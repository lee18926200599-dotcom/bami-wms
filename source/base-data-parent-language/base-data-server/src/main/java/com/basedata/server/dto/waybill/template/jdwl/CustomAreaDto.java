
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

/**
 *  对应承运商面单模板的自定义区资源
 */
@Data
public class CustomAreaDto {

    /**
     * 自定义区模板编码，长度2-20
     */
    private String customAreaCode;
    /**
     * 自定义区模板名称，长度50
     */
    private String customAreaName;
    /**
     * 自定义区模板URL，长度255
     */
    private String customAreaUrl;


    /**
     * 标准区域模板编码，长度2-20
     */
    private String standardTemplateCode;
    /**
     * 标准区域模板名称，长度50
     */
    private String standardTemplateName;
    /**
     * 标准区域模板URL，长度255
     */
    private String standardTemplateUrl;
}
