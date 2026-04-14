
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 *  京东官方标准面单模板资源
 */
@Data
public class StandardTemplatesDto {
    /**
     * 承运商编码，长度2-10
     */
    private String cpCode;
    /**
     * 对应承运商面单模板资源列表
     */
    private List<TemplateDto> templates;


}
