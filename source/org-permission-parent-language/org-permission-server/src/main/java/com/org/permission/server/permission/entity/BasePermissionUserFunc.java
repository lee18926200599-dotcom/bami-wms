package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_user_func实体类  用户权限关联表管理
*/ 
@Data
@TableName(value = "base_permission_user_func")
public class BasePermissionUserFunc extends BaseEntity implements Serializable {
	public String comment = "用户权限关联表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();

	//用户id(base_user.id)
	@TableField(value = "user_id")
	private Long userId;
	//权限类型(功能权限 ：func,组织权限： org，数据权限：data)
	@TableField(value = "permission_type")
	private String permissionType;
	//权限id(功能func：base_permission_resource.id、组织org：base_organization.id、数据data：base_permission_data.id)
	@TableField(value = "permission_id")
	private Long permissionId;
	//数据权限（1：查询，2：查询、维护）
	@TableField(value = "option_permission")
	private Integer optionPermission;
	//集团id
	@TableField(value = "group_id")
	private Long groupId;
	//授权人
	@TableField(value = "author_user")
	private Long authorUser;
	//授权时间
	@TableField(value = "author_time")
	private Date authorTime;


}

