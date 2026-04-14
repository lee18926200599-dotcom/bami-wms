package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePlatformLogisticsDetailDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;

    @ApiModelProperty(value = "电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "外部系统承运商编码")
    private String externalLogisticsCode;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    //仓库编码
    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;
    //仓库id
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

}
