package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* 角色上的组织权限
 */
@Data
public class RoleOrgPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String orgCode;
	private String orgName;
	private String orgType;
	private Long groupId;
	private Long parentId;
	private boolean check;
	private List<RoleOrgPermission> childOrgs;

}
