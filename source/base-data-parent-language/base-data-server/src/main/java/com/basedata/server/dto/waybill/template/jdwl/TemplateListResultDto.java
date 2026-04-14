
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 *  模板数据资源
 */
@Data
public class TemplateListResultDto {
    /**
     * 京东官方标准面单模板资源列表
     */
    private List<StandardTemplatesDto> standardTemplateList;
    /**
     * 自研商家绘制的模板资源列表
     */
    private DevUserTemplatesDto devUserTemplateList;
    /**
     * ISV绘制的模板资源列表
     */
    private IsvTemplatesDto isvTemplateList;
    /**
     * 订阅ISV服务的普通商家绘制的模板资源列表
     */
    private UserTemplatesDto userTemplateList;
}
