package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlatformLogisticsRelationshipReqDTO {

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商编码列表")
    private List<String> logisticsCodeList;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台编码列表")
    private List<String> platformCodeList;

}
