package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 采购业务委托关系数据实体
 */
@Data
public class PurchaseEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 采购组织ID
	 */
	private Long purchaseOrgId;
	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 应付组织ID
	 */
	private Long payOrgId;
	/**
	 * 结算组织ID
	 */
	private Long settleOrgId;

	public PurchaseEntrustRelationBean() {
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PurchaseEntrustRelationBean{");
		sb.append(super.toString());
		sb.append("purchaseOrgId=").append(purchaseOrgId);
		sb.append(", stockOrgId=").append(stockOrgId);
		sb.append(", payOrgId=").append(payOrgId);
		sb.append(", settleOrgId=").append(settleOrgId);
		sb.append('}');
		return sb.toString();
	}
}

