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
*base_permission_management实体类  管理维度表管理
*/ 
@Data
@TableName(value = "base_permission_management")
public class BasePermissionManagement  implements Serializable {
	public String comment = "管理维度表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	
	@TableId(value = "id")
	private Integer id;
	//管理维度名称
	@TableField(value = "name")
	private String name;
	//描述
	@TableField(value = "des")
	private String des;
	//备注
	@TableField(value = "remark")
	private String remark;
	//状态
	@TableField(value = "state")
	private Integer state;
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

