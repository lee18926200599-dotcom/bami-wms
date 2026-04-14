package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 采购业务委托关系数据实体
 */
@Data
public class PurchaseEntrustRelationInfoBean extends PurchaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 采购组织名
	 */
	private String purchaseOrgName;
	/**
	 * 库存组织名
	 */
	private String stockOrgName;
	/**
	 * 应付组织名
	 */
	private String payOrgName;

	/**
	 * 结算组织名
	 */
	private String settleOrgName;

	/**
	 * 是否与普罗格签约：false否
	 */
	private Integer signFlag;
}

