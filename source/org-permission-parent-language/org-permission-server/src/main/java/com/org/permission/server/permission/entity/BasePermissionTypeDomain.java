package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_type_domain实体类  业务类型和域名关系表（初始化表）管理
*/ 
@Data
@TableName(value = "base_permission_type_domain")
public class BasePermissionTypeDomain implements Serializable {
	public String comment = "业务类型和域名关系表（初始化表）";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();

	
	@TableId(value = "id")
	private Long id;
	//业务类型id(字典)
	@TableField(value = "type_id")
	private Integer typeId;
	//业务类型名称（查看使用）
	@TableField(value = "type_name")
	private String typeName;
	//域名id(base_domain_config.id)
	@TableField(value = "domain_id")
	private Integer domainId;
	//域名名称（查看使用）
	@TableField(value = "domain_name")
	private String domainName;
	//创建人id
	@TableField(value = "created_by")
	private Long createdBy;
	//创建人
	@TableField(value = "created_name")
	private String createdName;
	//创建日期
	@TableField(value = "created_date")
	private Date createdDate;
	//修改人id
	@TableField(value = "modified_by")
	private Long modifiedBy;
	//修改人
	@TableField(value = "modified_name")
	private String modifiedName;
	//修改时间
	@TableField(value = "modified_date")
	private Date modifiedDate;

}

