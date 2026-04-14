package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 物流业务委托关系数据实体
 */
@Data
public class LogisticsEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 来源合同号
	 */
	private String oriAccCode;
	/**
	 * 物流服务商
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织
	 */
	private Long logisticsOrgId;
	/**
	 * 关联物流服务商
	 */
	private Long relevanceLogisticsProviderId;
	/**
	 * 关联物流组织
	 */
	private Long relevanceLogisticsOrgId;
}

