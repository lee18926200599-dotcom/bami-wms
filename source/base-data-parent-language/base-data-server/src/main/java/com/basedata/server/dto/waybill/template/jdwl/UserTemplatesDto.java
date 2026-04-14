
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 *  订阅ISV服务的普通商家绘制的模板资源列表
 */
@Data
public class UserTemplatesDto {
    /**
     *自定义区资源列表
     */
    private List<CustomAreaListDto> customAreaList;
    /**
     *自定义模板资源列表
     */
    private List<CustomTemplateListDto> customTemplateList;
}
