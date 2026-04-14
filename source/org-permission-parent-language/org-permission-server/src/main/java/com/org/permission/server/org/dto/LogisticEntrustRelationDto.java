package com.org.permission.server.org.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 物流委托关系数据实体
 */
@ApiModel(description = "物流委托关系数据实体")
@Data
public class LogisticEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 委托关系ID
	 */
	private Integer id;
	/**
	 * 默认（true是;false否）
	 */
	private Integer defaultFlag;
	/**
	 * 来源合同号
	 */
	private String oriAccCode;
	/**
	 * 物流服务商ID
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织ID
	 */
	private Long logisticsOrgId;
	/**
	 * 物流组织名
	 */
	private String logisticsOrgName;
	/**
	 * 关联物流服务商ID
	 */
	private Long relevanceLogisticsProviderId;
	/**
	 * 关联物流组织ID
	 */
	private Long relevanceLogisticsOrgId;
	/**
	 * 关联物流组织名
	 */
	private String relevanceLogisticsOrgName;
}
