package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团内仓储业务委托关系数据实体
 */
@Data
public class StorageGroupEntrustRelationInfoBean extends StorageGroupEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存组织ID
	 */
	private String stockOrgName;
	/**
	 * 物流组织ID
	 */
	private String logisticsOrgName;
	/**
	 * 核算组织ID
	 */
	private String accountOrgName;
	/**
	 * 结算组织ID
	 */
	private String settleOrgName;
}

