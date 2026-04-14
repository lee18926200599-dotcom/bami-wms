package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系数据
 */
@ApiModel
@Data
public class StoragePlatformEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("默认（1是，0否）")
	private Integer defaultFlag;

	@ApiModelProperty("状态（1未启用，2启用，3停用，4删除）")
	private Integer state;

	@ApiModelProperty("仓储服务商")
	private Long warehouseProviderId;

	@ApiModelProperty("库存组织")
	private Long stockOrgId;

	@ApiModelProperty("库存组织")
	private String stockOrgName;

	@ApiModelProperty("仓库")
	private Long warehouseId;

	@ApiModelProperty("物流服务商")
	private Long logisticsProviderId;

	@ApiModelProperty("物流服务商名称")
	private String logisticsProviderName;

	@ApiModelProperty("物流组织")
	private Long logisticsOrgId;

	@ApiModelProperty("物流组织")
	private String logisticsOrgName;

	@ApiModelProperty("来源合同号")
	private String oriAccCode;

	@ApiModelProperty("备注")
	private String remark;
}
