package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色管理出参
 */
@Data
@ApiModel(description="角色管理列表出参")
public class OutPutRoleDto implements Serializable{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="角色id")
	private Long id;
	@ApiModelProperty(value="部门id")
	private Long orgId;
	@ApiModelProperty(value="组织名称")
	private String orgName;
	@ApiModelProperty(value="角色编码")
	private String roleCode;
	@ApiModelProperty(value="角色名称")
	private String roleName;
	@ApiModelProperty(value="角色备注")
	private String remark;
    @ApiModelProperty(value = "是否存在启用用户 0：纯在 1：存在")
    private int hasEnableUser;
}
