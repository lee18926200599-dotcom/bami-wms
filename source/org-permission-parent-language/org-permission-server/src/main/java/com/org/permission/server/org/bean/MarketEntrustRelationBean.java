package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 采销委托关系数据实体
 */
@Data
public class MarketEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 5934970981060204517L;

	/**
	 * 来源合同号
	 */
	private String oriAccCode;
	/**
	 * 货主
	 */
	private Long ownerId;
	/**
	 * 采购/销售组织
	 */
	private Long marketOrgId;
	/**
	 * 仓储服务商
	 */
	private Long warehouseProviderId;
	/**
	 * 库存组织
	 */
	private Long stockOrgId;
	/**
	 * 仓库
	 */
	private Long warehouseId;

	/**
	 * 是否签约
	 */
	private Integer signFlag;

	public MarketEntrustRelationBean() {
	}
}
