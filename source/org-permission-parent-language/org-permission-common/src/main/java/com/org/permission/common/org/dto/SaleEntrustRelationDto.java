package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售业务委托关系数据实体
 */
@ApiModel
@Data
public class SaleEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托关系ID
	 */
	private Integer id;

	@ApiModelProperty("默认（true是，false否）")
	private Integer defaultFlag;

	@ApiModelProperty("状态（1未启用，2启用，3停用，4删除）")
	private Integer state;

	@ApiModelProperty("销售组织ID")
	private Long saleOrgId;

	@ApiModelProperty("销售组织名")
	private String saleOrgName;

	@ApiModelProperty("库存组织ID")
	private Long stockOrgId;

	@ApiModelProperty("库存组织名")
	private String stockOrgName;

	@ApiModelProperty("应收应付组织ID")
	private Long receiveOrgId;

	@ApiModelProperty("应收应付组织名")
	private String receiveOrgName;

	@ApiModelProperty("结算组织ID")
	private Long settleOrgId;

	@ApiModelProperty("结算组织名")
	private String settleOrgName;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("是否普罗格签约：false")
	private Integer signFlag;
}

