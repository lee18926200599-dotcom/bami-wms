package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FacesheetConfigDto {

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    @ApiModelProperty(value = "货主")
    private Long ownerId;

    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;

    @ApiModelProperty(value = "承运商编码")
    private String deliveryCode;

    @ApiModelProperty(value = "平台编码")
    private String extPlatformCode;

    @ApiModelProperty(value = "平台名称")
    private String extPlatformName;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺Code")
    private String storeCode;

}
