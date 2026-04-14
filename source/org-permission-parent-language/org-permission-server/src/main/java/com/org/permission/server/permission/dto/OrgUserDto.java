package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色批量返回用户，组织权限和用户关联查询返回对象
 */
@Data
public class OrgUserDto implements Serializable {


	private static final long serialVersionUID = -8641988231943927380L;
	private Long userId;
	private String orgCode;
	private String orgName;
	private Long parentId;
	private Long orgId;
	private String userCode;
	private String userName;
	private String realName;

}
