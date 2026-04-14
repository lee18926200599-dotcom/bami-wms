package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售业务委托关系查询参数
 */
@ApiModel
@Data
public class SaleEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("默认（true是，false否）")
	private Integer defaultFlag;

	@ApiModelProperty("状态（1未启用，2启用，3停用，4删除）")
	private Integer state;

	@ApiModelProperty("销售组织ID")
	private Long saleOrgId;

	@ApiModelProperty("库存组织ID")
	private Long stockOrgId;

	@ApiModelProperty("应收应付组织ID")
	private Long payReceiveOrgId;

	@ApiModelProperty("结算组织ID")
	private Long settmentOrgId;

	@ApiModelProperty("备注")
	private String remark;

	public SaleEntrustRelationQueryParam() {
	}
}

