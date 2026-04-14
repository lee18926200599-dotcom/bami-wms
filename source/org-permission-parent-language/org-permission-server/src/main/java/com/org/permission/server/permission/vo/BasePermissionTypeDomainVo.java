package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*业务类型和域名关系表（初始化表）管理
*/ 

 @ApiModel(description = "业务类型和域名关系表（初始化表）", value = "业务类型和域名关系表（初始化表）")
public class BasePermissionTypeDomainVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="业务类型id") 
	private Integer typeId; //业务类型id 
	@ApiModelProperty(value="业务类型名称（查看使用）") 
	private String typeName; //业务类型名称（查看使用） 
	@ApiModelProperty(value="域名id") 
	private Integer domainId; //域名id 
	@ApiModelProperty(value="域名名称（查看使用）") 
	private String domainName; //域名名称（查看使用） 
	public void setId(Integer id){
	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setTypeId(Integer typeId){
	this.typeId=typeId;
	}
	public Integer getTypeId(){
		return typeId;
	}
	public void setTypeName(String typeName){
	this.typeName=typeName;
	}
	public String getTypeName(){
		return typeName;
	}
	public void setDomainId(Integer domainId){
	this.domainId=domainId;
	}
	public Integer getDomainId(){
		return domainId;
	}
	public void setDomainName(String domainName){
	this.domainName=domainName;
	}
	public String getDomainName(){
		return domainName;
	}
}

