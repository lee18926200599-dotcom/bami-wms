package com.org.permission.common.org.dto.orgfunc;

import com.org.permission.common.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购组织职能数据实体
 */
@ApiModel("采购组织职能")
@Data
public class PurchaseOrgFuncInfoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	@ApiModelProperty("组织ID")
	private Long orgId;
	/**
	 * 组织名
	 */
	@ApiModelProperty("组织名")
	private String orgName;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	@ApiModelProperty("功能类别")
	private Integer funcType = 3;
	/**
	 * 默认应付组织ID
	 */
	@ApiModelProperty("默认应付组织ID")
	private Long payOrgId;
	/**
	 * 默认结算财务组织ID
	 */
	@ApiModelProperty("默认结算财务组织ID")
	private Long settleOrgId;
	/**
	 * 默认库存组织ID
	 */
	@ApiModelProperty("默认库存组织ID")
	private Long stockOrgId;
	/**
	 * 默认应付组织名
	 */
	@ApiModelProperty("默认应付组织名")
	private String payOrgName;
	/**
	 * 默认结算财务组织名
	 */
	@ApiModelProperty("默认结算财务组织名")
	private String settleOrgName;
	/**
	 * 默认库存组织名
	 */
	@ApiModelProperty("默认库存组织名")
	private String stockOrgName;
}

