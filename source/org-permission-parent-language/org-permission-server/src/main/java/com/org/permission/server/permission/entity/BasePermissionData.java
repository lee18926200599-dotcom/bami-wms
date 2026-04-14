package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_data实体类  数据权限资源表管理
*/ 
@Data
@TableName(value = "base_permission_data")
public class BasePermissionData extends BaseEntity implements Serializable {
	public String comment = "数据权限资源表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//管理维度id(base_permission_management.id)
	@TableField(value = "management_id")
	private Integer managementId;
	//分配方式
	@TableField(value = "distribution_type")
	private String distributionType;
	//分类依据(分类依据不为空，分配方式就是按类别，为空，就是按个体)
	@TableField(value = "based")
	private String based;
	//所属组织id（冗余仓库字段）
	@TableField(value = "org_id")
	private Long orgId;
	//集团id
	@TableField(value = "group_id")
	private Long groupId;
	//
	@TableField(value = "gb_code")
	private String gbCode;
	//各个业务线数据权限id
	@TableField(value = "data_resource_id")
	private String dataResourceId;
	//各个业务线数据权限名称（具体的 类别/个体）
	@TableField(value = "data_resource")
	private String dataResource;
	@TableField(value = "data_resource_code")
	private String dataResourceCode;
	//父级id
	@TableField(value = "parent_id")
	private Long parentId;
	//操作权限(查询、维护)
	@TableField(value = "option_permission")
	private Integer optionPermission;

}

