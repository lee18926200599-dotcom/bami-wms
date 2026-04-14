package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 财务委托关系
 */
@Data
public class FinanceEntrustRelationInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托关系ID
	 */
	private Integer id;
	/**
	 * 业务组织ID
	 */
	private Long bizOrgId;
	/**
	 * 业务组织名
	 */
	private String bizOrgName;
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
	private Long settleOrgId;
	/**
	 * 结算组织名
	 */
	private String settleOrgName;

	/**
	 * 是否默认（1是，0否）
	 */
	private Integer defaultFlag;
}
