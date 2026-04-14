package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*权限体系配置表（域名首页url配置在这里）管理
*/ 

 @ApiModel(description = "权限体系配置表（域名首页url配置在这里）", value = "权限体系配置表（域名首页url配置在这里）")
public class BasePermissionPropertyVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="配置key") 
	private String name; //配置key 
	@ApiModelProperty(value="配置value") 
	private String propertyValue; //配置value 
	public void setId(Integer id){
	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setPropertyValue(String propertyValue){
	this.propertyValue=propertyValue;
	}
	public String getPropertyValue(){
		return propertyValue;
	}
}

