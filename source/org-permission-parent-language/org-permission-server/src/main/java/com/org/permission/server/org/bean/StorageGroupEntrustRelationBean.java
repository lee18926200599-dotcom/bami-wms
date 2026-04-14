package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团内仓储业务委托关系数据实体
 */
@Data
public class StorageGroupEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 物流组织ID
	 */
	private Long logisticsOrgId;
	/**
	 * 核算组织ID
	 */
	private Long accountOrgId;
	/**
	 * 结算组织ID
	 */
	private Long settleOrgId;

}

