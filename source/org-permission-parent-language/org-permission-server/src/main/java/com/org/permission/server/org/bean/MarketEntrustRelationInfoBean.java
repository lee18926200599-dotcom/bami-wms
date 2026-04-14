package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 采销委托关系实体
 */
@Data
public class MarketEntrustRelationInfoBean extends MarketEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 采购/销售组织名
	 */
	private String marketOrgName;
	/**
	 * 库存组织名
	 */
	private String stockOrgName;

	public MarketEntrustRelationInfoBean() {
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MarketEntrustRelationInfoBean{");
		sb.append(super.toString());
		sb.append(", marketOrgName='").append(marketOrgName).append('\'');
		sb.append(", stockOrgName='").append(stockOrgName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
