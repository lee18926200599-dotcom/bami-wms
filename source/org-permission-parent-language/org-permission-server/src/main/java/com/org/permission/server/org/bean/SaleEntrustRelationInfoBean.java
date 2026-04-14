package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 销售业务委托关系数据实体
 */
@Data
public class SaleEntrustRelationInfoBean extends SaleEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 销售组织名
	 */
	private String saleOrgName;
	/**
	 * 库存组织名
	 */
	private String stockOrgName;
	/**
	 * 应收组织名
	 */
	private String receiveOrgName;
	/**
	 * 结算组织名
	 */
	private String settleOrgName;

	/**
	 * 是否签约
	 */
	private Integer signFlag;

}

