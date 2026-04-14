package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*业务类型域名功能权限资源表(tree)管理
*/ 

 @ApiModel(description = "业务类型域名功能权限资源表(tree)", value = "业务类型域名功能权限资源表(tree)")
public class BasePermissionTypeResourceVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="域名id") 
	private Integer domianId; //域名id 
	@ApiModelProperty(value="客户业务类型id") 
	private Integer businessTypeId; //客户业务类型id 
	@ApiModelProperty(value="资源id") 
	private Integer permissionId; //资源id 
	@ApiModelProperty(value="状态") 
	private Integer status; //状态 
	public void setId(Integer id){
	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setDomianId(Integer domianId){
	this.domianId=domianId;
	}
	public Integer getDomianId(){
		return domianId;
	}
	public void setBusinessTypeId(Integer businessTypeId){
	this.businessTypeId=businessTypeId;
	}
	public Integer getBusinessTypeId(){
		return businessTypeId;
	}
	public void setPermissionId(Integer permissionId){
	this.permissionId=permissionId;
	}
	public Integer getPermissionId(){
		return permissionId;
	}
	public void setStatus(Integer status){
	this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
}

