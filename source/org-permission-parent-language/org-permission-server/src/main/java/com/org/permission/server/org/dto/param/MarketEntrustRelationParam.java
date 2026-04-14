package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增采销委托关系
 */
@ApiModel(description = "新增采销委托关系")
@Data
public class MarketEntrustRelationParam extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("默认（必填）")
    private Integer defaultFlag = 0;

    @ApiModelProperty("来源合同号（选填）")
    private String oriAccCode;

    @ApiModelProperty("货主（必填）")
    private Long ownerId;

    @ApiModelProperty("采销组织（必填）")
    private Long marketOrgId;

    @ApiModelProperty("仓储服务商（必填）")
    private Long warehouseProviderId;

    @ApiModelProperty("库存组织（必填）")
    private Long stockOrgId;

    @ApiModelProperty("仓库（必填）")
    private Long warehouseId;

    @ApiModelProperty("是否平台签约,默认false未签约")
    private Integer signFlag = 0;

    @ApiModelProperty("备注")
    private String remark;
}
