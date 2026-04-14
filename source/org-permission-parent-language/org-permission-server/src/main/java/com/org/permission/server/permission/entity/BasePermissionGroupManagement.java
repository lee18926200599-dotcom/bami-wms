package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_group_management实体类  集团-管理维度表管理
*/ 
@Data
@TableName(value = "base_permission_group_management")
public class BasePermissionGroupManagement extends BaseEntity implements Serializable {
	public String comment = "集团-管理维度表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//集团id(base_organization.id)
	@TableField(value = "group_id")
	private Long groupId;
	//管理维度id(base_permission_management.id)
	@TableField(value = "management_id")
	private Integer managementId;
	//备注
	@TableField(value = "remark")
	private String remark;

}

