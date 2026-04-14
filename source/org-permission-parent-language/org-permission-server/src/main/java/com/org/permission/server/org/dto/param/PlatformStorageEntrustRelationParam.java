package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓储委托关系数据实体(集团间)
 */
@ApiModel(description = "平台仓储委托关系")
@Data
public class PlatformStorageEntrustRelationParam extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("默认（1是;0否）（必填）")
    private Integer defaultFlag = 0;

    @ApiModelProperty("来源合同号（选填）")
    private String oriAccCode;

    @ApiModelProperty("仓储服务商ID")
    private Long warehouseProviderId;

    @ApiModelProperty("库存组织ID")
    private Long stockOrgId;

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("物流服务商ID")
    private Long logisticsProviderId;

    @ApiModelProperty("物流组织ID")
    private Long logisticsOrgId;

    @ApiModelProperty("备注")
    private String remark;
}
