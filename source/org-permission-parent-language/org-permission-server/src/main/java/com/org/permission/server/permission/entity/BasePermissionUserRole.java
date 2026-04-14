package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_user_role实体类  用户角色关联表管理
*/ 
@Data
@TableName(value = "base_permission_user_role")
public class BasePermissionUserRole extends BaseEntity implements Serializable {
	public String comment = "用户角色关联表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();

	//用户id(base_user.id)
	@TableField(value = "user_id")
	private Long userId;
	//角色id(base_role.id)
	@TableField(value = "role_id")
	private Long roleId;
	//所属集团id
	@TableField(value = "group_id")
	private Long groupId;
	//所属组织id
	@TableField(value = "org_id")
	private Long orgId;
	//关联生效时间
	@TableField(value = "effective_time")
	private Date effectiveTime;
	//关联失效时间
	@TableField(value = "expire_time")
	private Date expireTime;
	//授权人
	@TableField(value = "author_user")
	private Long authorUser;
	//授权时间
	@TableField(value = "author_time")
	private Date authorTime;
}

