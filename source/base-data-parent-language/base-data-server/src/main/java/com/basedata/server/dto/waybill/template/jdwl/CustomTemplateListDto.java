
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 自定义模板资源
 */
@Data
public class CustomTemplateListDto {
    /**
     * 模板类型名称，长度2-20
     */
    private String type;
    /**
     * 对应类型自定义模板资源列表
     */
    private List<CustomTemplateDto> templates;

}
