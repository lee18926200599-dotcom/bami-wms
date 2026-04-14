package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_group_param实体类  集团参数配置管理
*/ 
@Data
@TableName(value = "base_permission_group_param")
public class BasePermissionGroupParam extends BaseEntity implements Serializable {
	public String comment = "集团参数配置";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//所属集团id(base_organization.id)
	@TableField(value = "group_id")
	private Long groupId;
	//参数代码
	@TableField(value = "param_code")
	private String paramCode;
	//参数名称
	@TableField(value = "param_name")
	private String paramName;
	//备注
	@TableField(value = "remark")
	private String remark;
	//参数值
	@TableField(value = "param_value")
	private String paramValue;
}

