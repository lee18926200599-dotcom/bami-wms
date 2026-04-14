package com.basedata.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * :查询面单模板列表（标准、自定义）-请求对象
 */
@Data
public class BaseStorePrintTemplateReqVo {

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商ID列表（来源于系统客商体系）")
    private List<Long> logisticsIdList;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "面单模板类型（0标准、1自定义）")
    private Integer templateType;
}
