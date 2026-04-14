package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购业务委托关系数据实体
 */
@ApiModel
@Data
public class PurchaseEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("默认（1是，0否）")
	private Integer defaultFlag;

	@ApiModelProperty("采购组织ID")
	private Long purchaseOrgId;

	@ApiModelProperty("库存组织ID")
	private Long stockOrgId;

	@ApiModelProperty("应收应付组织ID")
	private Long payReceiveOrgId;

	@ApiModelProperty("结算组织ID")
	private Long settmentOrgId;
}

