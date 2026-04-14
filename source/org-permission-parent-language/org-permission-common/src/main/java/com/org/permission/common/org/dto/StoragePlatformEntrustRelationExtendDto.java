package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系数据
 */
@ApiModel
@Data
public class StoragePlatformEntrustRelationExtendDto extends StoragePlatformEntrustRelationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流组织对应全局客商ID
	 */
	@ApiModelProperty(value = "物流组织对应全局客商ID")
	private Long logisticsOrgCustId;

	/**
	 * 物流组织对应全局客商名
	 */
	@ApiModelProperty(value = "物流组织对应全局客商名")
	private String logisticsOrgCustName;
}
