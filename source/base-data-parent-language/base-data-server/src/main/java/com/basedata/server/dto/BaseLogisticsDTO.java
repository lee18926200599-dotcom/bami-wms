package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class BaseLogisticsDTO {
    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;
}
