package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系数据
 */
@Data
public class StorageGroupEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 默认（1是，0否）
	 */
	private Integer defaultFlag;
	/**
	 * 状态（1未启用，2启用，3停用）
	 */
	private Integer state;
	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 库存组织名
	 */
	private String stockOrgName;
	/**
	 * 物流组织ID
	 */
	private Long logisticsOrgId;
	/**
	 * 物流组织名
	 */
	private String logisticsOrgName;
	/**
	 * 核算组织ID
	 */
	private Long accountOrgId;
	/**
	 * 核算组织名
	 */
	private String accountOrgName;
	/**
	 * 结算组织ID
	 */
	private Long settmentOrgId;
	/**
	 * 结算组织名
	 */
	private String settmentOrgName;
	/**
	 * 备注
	 */
	private String remark;
}
