package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class EntrustRelationOrgInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("组织ID")
	private Long orgId;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("组织类别（2集团;3业务单元;4部门）")
	private Integer orgType;

	@ApiModelProperty("组织编码")
	private String orgCode;

	@ApiModelProperty("组织名称")
	private String orgName;

	@ApiModelProperty("全局客户id")
	private Long custId;

	@ApiModelProperty("物流服务商")
	private Long logisticsProviderId;

	@ApiModelProperty("物流组织")
	private Long logisticsOrgId;

	@ApiModelProperty("仓储服务商")
	private Long warehouseProviderId;
}
