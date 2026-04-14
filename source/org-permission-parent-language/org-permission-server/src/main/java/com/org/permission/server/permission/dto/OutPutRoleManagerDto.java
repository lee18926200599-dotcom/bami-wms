package com.org.permission.server.permission.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 编辑角色时候 角色下展示的权限
 */
@Data
public class OutPutRoleManagerDto extends OutPutRoleDto {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色上的功能权限
	 */
	private Map<String, List<RolePermission>> funcPermissions;
	/*
	 * 角色上的组织权限
	 */
	private List<RoleOrgPermission> orgPermissions;

}
