package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_group_data实体类  集团的数据权限关系表管理
*/ 
@Data
@TableName(value = "base_permission_group_data")
public class BasePermissionGroupData extends BaseEntity implements Serializable {
	public String comment = "集团的数据权限关系表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//集团id(base_organization.id)
	@TableField(value = "group_id")
	private Long groupId;
	//数据权限id(base_permission_data.id)
	@TableField(value = "data_id")
	private Long dataId;

}

