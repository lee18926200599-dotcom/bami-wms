package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformLogisticsRelationshipDTO {

    @ApiModelProperty(value = "电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;
}
