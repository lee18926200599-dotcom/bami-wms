package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 物流委托关系查询请求参数
 */
@ApiModel(description = "物流委托关系查询请求参数")
@Data
public class LogisticEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认
	 */
	private Integer defaultFlag;
	/**
	 * 来源合同号
	 */
	private String oriAccCode;
	/**
	 * 物流服务商ID(CRM 客商ID)
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织ID(ORG 业务单元ID)
	 */
	private Long logisticsOrgId;
}
