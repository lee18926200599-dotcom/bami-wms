package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 仓储委托关系数据实体
 */
@Data
public class StoragePlatformEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 来源合同号
	 */
	private String oriAccCode;
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
	 * 物流服务商
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织
	 */
	private Long logisticsOrgId;
}
