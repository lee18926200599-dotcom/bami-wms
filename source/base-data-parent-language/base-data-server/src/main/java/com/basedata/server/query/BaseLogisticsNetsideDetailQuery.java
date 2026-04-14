package com.basedata.server.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseLogisticsNetsideDetailQuery{

    @ApiModelProperty(value = "服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

}
