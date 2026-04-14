package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 采销委托关系数据实体
 */
@ApiModel(description = "采销委托关系数据实体")
@Data
public class MarketEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托关系ID
	 */
	private Integer id;
	/**
	 * 默认（true是;false否)
	 */
	private Boolean defaultFlag;
	/**
	 * 来源合同号（选填）
	 */
	private String oriAccCode;
	/**
	 * 货主(CRM 客商ID)
	 */
	private Long ownerId;
	/**
	 * 采销组织ID
	 */
	private Long marketOrgId;
	/**
	 * 采销组织名
	 */
	private String marketOrgName;
	/**
	 * 仓储服务商ID
	 */
	private Long warehouseProviderId;
	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 库存组织名
	 */
	private String stockOrgName;
	/**
	 * 仓库ID
	 */
	private Long warehouseId;

	/**
	 * 是否平台签约 false未签约
	 */
	private Boolean signFlag;

	public MarketEntrustRelationDto() {
	}

	@Override
	public String toString() {
		return "MarketEntrustRelationDto{" +
				"id=" + id +
				", defaultFlag=" + defaultFlag +
				", oriAccCode='" + oriAccCode + '\'' +
				", ownerId=" + ownerId +
				", marketOrgId=" + marketOrgId +
				", marketOrgName='" + marketOrgName + '\'' +
				", warehouseProviderId=" + warehouseProviderId +
				", stockOrgId=" + stockOrgId +
				", stockOrgName='" + stockOrgName + '\'' +
				", warehouseId=" + warehouseId +
				", signFlag=" + signFlag +
				'}';
	}
}
