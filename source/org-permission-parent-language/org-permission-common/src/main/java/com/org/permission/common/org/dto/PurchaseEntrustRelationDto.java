package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购业务委托关系数据实体
 */
@ApiModel
@Data
public class PurchaseEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托关系主键
	 */
	private Integer id;

	@ApiModelProperty("默认（1是，0否）")
	private Integer defaultFlag;

	@ApiModelProperty("状态（1未启用，2启用，3停用，4删除）")
	private Integer state;

	@ApiModelProperty("采购组织ID")
	private Long purchaseOrgId;

	@ApiModelProperty("采购组织名")
	private String purchaseOrgName;

	@ApiModelProperty("库存组织ID")
	private Long stockOrgId;

	@ApiModelProperty("库存组织名")
	private String stockOrgName;

	@ApiModelProperty("应付组织ID")
	private Long payOrgId;

	@ApiModelProperty("应付组织名")
	private String payOrgName;

	@ApiModelProperty("结算组织ID")
	private Long settleOrgId;

	@ApiModelProperty("结算组织名")
	private String settleOrgName;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("是否与普罗格签约：false否")
	private Integer signFlag;

}

