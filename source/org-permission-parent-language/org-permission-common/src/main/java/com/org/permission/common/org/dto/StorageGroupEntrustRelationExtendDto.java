package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系数据
 */
@Data
public class StorageGroupEntrustRelationExtendDto extends StorageGroupEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流组织对应全局客商ID
	 */
	private Long logisticsOrgCustId;

	/**
	 * 物流组织对应全局客商名
	 */
	private String logisticsOrgCustName;
}
