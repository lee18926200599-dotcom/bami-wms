package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 财务委托关系查询请求参数
 */
@ApiModel(description = "财务委托关系查询请求参数")
@Data
public class QueryFinanceEntrustRelationParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 至少一个有值，不可同时为空
	 */
	@ApiModelProperty("业务组织ID（选填，三选一）")
	private Long bizOrgId;

	@ApiModelProperty("核算组织ID（选填，三选一）")
	private Long accountOrgId;

	@ApiModelProperty("结算组织ID（选填，三选一）")
	private Long settleOrgId;
}
