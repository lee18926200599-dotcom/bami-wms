package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组织权限列表查询参数
 */
@ApiModel(description = "组织权限列表查询参数")
@Data
public class QueryOrgPermissionListReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID（必填）")
	private Long userId;

	@ApiModelProperty("集团ID（必填）")
	private Long groupId;

	@ApiModelProperty("职能类别(选填)（1法人公司；2财务；3采购；4销售；5仓储；6物流;7金融）")
	private List<Integer> orgFuncs;
}
