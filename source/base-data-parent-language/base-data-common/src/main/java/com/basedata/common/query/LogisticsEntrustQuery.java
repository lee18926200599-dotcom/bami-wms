package com.basedata.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * comments:承运商委托关系表实体类型
 */
@Data
public class LogisticsEntrustQuery {
    //集团id
    @ApiModelProperty(value = "集团id")
    private Long groupId;
    //id
    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;
    //仓库编码
    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;
    //配送方式
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;
    //货主
    @ApiModelProperty(value = "owner_id")
    private Long ownerId;
    //是否默认
    @ApiModelProperty(value = "是否默认")
    private Integer defaultFlag;
    //仓库id
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    //系统承运商编码
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "业务类型(2C 2B)")
    private String sourceType;

}
