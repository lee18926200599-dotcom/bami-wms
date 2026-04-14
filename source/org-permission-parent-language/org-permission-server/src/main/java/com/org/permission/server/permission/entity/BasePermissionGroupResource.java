package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_group_resource实体类  集团的功能权限权限表管理
*/ 
@Data
@TableName(value = "base_permission_group_resource")
public class BasePermissionGroupResource extends BaseEntity implements Serializable {
	public String comment = "集团的功能权限权限表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//集团id(base_organization.id)
	@TableField(value = "group_id")
	private Long groupId;
	//功能权限id(base_permission_resource.id)
	@TableField(value = "permission_id")
	private Long permissionId;

}

