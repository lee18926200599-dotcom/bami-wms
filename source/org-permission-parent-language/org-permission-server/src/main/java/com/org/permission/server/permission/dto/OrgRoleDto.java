package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织机构和角色关联出的列表返回对象
 */
@Data
public class OrgRoleDto implements Serializable {
	private static final long serialVersionUID = -4720643881537739960L;
	private Long roleId;
	private String orgCode;
	private String orgName;
	private Long parentId;
	private Long orgId;
	private String roleName;
	private String roleCode;

}
