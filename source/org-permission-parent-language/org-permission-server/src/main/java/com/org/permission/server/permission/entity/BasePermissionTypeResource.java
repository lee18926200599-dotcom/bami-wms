package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_type_resource实体类  业务类型域名功能权限资源表(tree)管理
*/ 
@Data
@TableName(value = "base_permission_type_resource")
public class BasePermissionTypeResource extends BaseEntity implements Serializable {
	public String comment = "业务类型域名功能权限资源表(tree)";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();

	//域名id(base_domain_config.id)
	@TableField(value = "domian_id")
	private Integer domianId;
	//客户业务类型id(字典)
	@TableField(value = "business_type_id")
	private Integer businessTypeId;
	//资源id(base_permission_resource.id)
	@TableField(value = "permission_id")
	private Long permissionId;
}

