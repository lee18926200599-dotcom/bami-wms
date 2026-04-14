
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 *  模板数据资源
 */
@Data
public class IsvTemplatesDto {
    /**
     * 自定义区资源列表
     */
    private List<CustomAreaListDto> customAreaList;
    /**
     * 自定义模板资源列表
     */
    private List<CustomTemplateListDto> customTemplateList;
}
