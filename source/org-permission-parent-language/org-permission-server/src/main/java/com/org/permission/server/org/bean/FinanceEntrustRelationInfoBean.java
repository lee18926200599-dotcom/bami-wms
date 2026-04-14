package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 财务业务委托关系数据实体
 */
@Data
public class FinanceEntrustRelationInfoBean extends FinanceEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务组织名
	 */
	private String bizOrgName;
	/**
	 * 核算组织名
	 */
	private String accountOrgName;
	/**
	 * 结算组织名
	 */
	private String settleOrgName;

	public FinanceEntrustRelationInfoBean() {
	}
}

