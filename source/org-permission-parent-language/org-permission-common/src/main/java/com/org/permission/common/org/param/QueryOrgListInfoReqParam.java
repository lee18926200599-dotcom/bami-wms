package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询组织列表请求参数
 */
@ApiModel(description = "查询组织列表请求参数", value = "QueryOrgListInfoReqParam")
@Data
public class QueryOrgListInfoReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty("状态")
	private Integer state;
}
