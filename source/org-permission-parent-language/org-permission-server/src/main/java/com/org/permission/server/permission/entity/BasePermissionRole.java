package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_role实体类  用户角色关联表管理
*/ 
@Data
@TableName(value = "base_permission_role")
public class BasePermissionRole extends BaseEntity implements Serializable {
	public String comment = "用户角色关联表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//角色编码
	@TableField(value = "role_code")
	private String roleCode;
	//
	@TableField(value = "role_name")
	private String roleName;
	//
	@TableField(value = "remark")
	private String remark;
	//所属组织id
	@TableField(value = "org_id")
	private Long orgId;
	//所属集团id
	@TableField(value = "group_id")
	private Long groupId;
}

