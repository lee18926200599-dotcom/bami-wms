package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 物流委托关系展示数据
 */
@Data
public class LogisticsEntrustRelationInfoBean extends LogisticsEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流组织名
	 */
	private String logisticsOrgName;
	/**
	 * 关联物流组织名
	 */
	private String relevanceLogisticsOrgName;

	public LogisticsEntrustRelationInfoBean() {
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LogisticsEntrustRelationInfoBean{");
		sb.append(super.toString());
		sb.append(", logisticsOrgName='").append(logisticsOrgName).append('\'');
		sb.append(", relevanceLogisticsOrgName='").append(relevanceLogisticsOrgName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
