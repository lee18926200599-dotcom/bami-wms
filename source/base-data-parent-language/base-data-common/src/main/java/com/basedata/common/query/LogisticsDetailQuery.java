package com.basedata.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * comments:承运商编码关系表
 */
@Data
public class LogisticsDetailQuery {
    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "owner_id")
    private Long ownerId;
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;
    @ApiModelProperty(value = "外部系统承运商编码")
    private String externalLogisticsCode;


}
