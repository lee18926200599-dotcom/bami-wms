package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 销售业务委托关系数据实体
 */
@Data
public class SaleEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 销售组织ID
	 */
	private Long saleOrgId;
	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 应收组织ID
	 */
	private Long receiveOrgId;
	/**
	 * 结算组织ID
	 */
	private Long settleOrgId;
}

