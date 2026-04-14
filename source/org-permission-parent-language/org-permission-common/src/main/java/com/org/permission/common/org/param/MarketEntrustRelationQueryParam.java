package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采销委托关系查询请求参数
 */
@ApiModel(description = "采销委托关系查询请求参数")
@Data
public class MarketEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 所有属性不可同时为空（七选一）
	 */
	@ApiModelProperty("货主ID(选填)(CRM 客商ID)")
	private Long ownerId;

	@ApiModelProperty("采销组织(选填)(ORG 业务单元ID)")
	private Long marketingOrgId;

	@ApiModelProperty("仓储服务商ID(选填)(CRM 客商ID)")
	private Long warehouseProviderId;

	@ApiModelProperty("库存组织(选填)(ORG 业务单元ID)")
	private Long stockOrgId;

	@ApiModelProperty("仓库ID(选填)（WMS 仓库ID）")
	private Long warehouseId;

	@ApiModelProperty("来源合同号(选填)")
	private String oriAccCode;

	@ApiModelProperty("是否默认(选填)（true是，false否，null 所有）")
	private Integer defaultFlag;

	@ApiModelProperty("委托范围（选填,默认3全部）=1，集团间；2，集团内；3，全部")
	private Integer entrustRange;
}
