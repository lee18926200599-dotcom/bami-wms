package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系查询参数
 */
@Data
public class StoragePlatformEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认（1是，0否）
	 */
	private Integer defaultFlag;
	/**
	 * 仓储服务商(CRM 客商ID)
	 */
	private Long warehouseProviderId;
	/**
	 * 库存组织(ORG 业务单元ID)
	 */
	private Long stockOrgId;
	/**
	 * 仓库ID（WMS 仓库ID）
	 */
	private Long warehouseId;
	/**
	 * 物流服务商(CRM 客商ID)
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织(ORG 业务单元ID)
	 */
	private Long logisticsOrgId;
	/**
	 * 来源合同号
	 */
	private String oriAccCode;
}
