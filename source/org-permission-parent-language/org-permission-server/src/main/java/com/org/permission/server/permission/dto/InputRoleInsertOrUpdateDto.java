package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
* 增加或者修改角色入参
 */
@Data
@ApiModel(description="增加或者修改角色入参")
public class InputRoleInsertOrUpdateDto extends InputParentDto {

	private static final long serialVersionUID = -1846617515383814069L;
	@ApiModelProperty(value="角色id")
	private Long id;
	@ApiModelProperty(value="角色id")
	private Long roleId;
	@ApiModelProperty(value="角色编码")
	private String roleCode;
	@ApiModelProperty(value="角色名称 ")
	private String roleName;
	@ApiModelProperty(value="角色备注 ")
	private String remark;
	@ApiModelProperty(value="所属组织id")
	private Long orgId;
	@ApiModelProperty(value="所属集团id")
	private Long groupId;
}

