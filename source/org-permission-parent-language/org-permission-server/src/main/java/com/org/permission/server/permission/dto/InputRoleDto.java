package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色管理输入参数
 */
@Data
public class InputRoleDto extends InputParentDto {

	private static final long serialVersionUID = 4551603368055671640L;
	@ApiModelProperty(value="组织机构id 数组，类型int")
	private List<Long> list;
	@ApiModelProperty(value="角色编码")
	private String roleCode;
	@ApiModelProperty(value="角色名称")
	private String roleName;

}
