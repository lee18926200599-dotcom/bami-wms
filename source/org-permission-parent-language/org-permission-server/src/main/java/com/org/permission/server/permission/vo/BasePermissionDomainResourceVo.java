package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*业务类型域名功能权限资源表(tree)管理
*/ 

 @ApiModel(description = "业务类型域名功能权限资源表(tree)", value = "业务类型域名功能权限资源表(tree)")
public class BasePermissionDomainResourceVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="域名id") 
	private Integer domianId; //域名id 
	@ApiModelProperty(value="客户业务类型id") 
	private Integer businessTypeId; //客户业务类型id 
	@ApiModelProperty(value="资源名称") 
	private String resourceName; //资源名称 
	@ApiModelProperty(value="资源父编码") 
	private Integer parentId; //资源父编码 
	@ApiModelProperty(value="资源描述") 
	private String resourceDesc; //资源描述 
	@ApiModelProperty(value="相关地址") 
	private String url; //相关地址 
	@ApiModelProperty(value="功能资源标签（唯一不变的权限标识）") 
	private String tag; //功能资源标签（唯一不变的权限标识） 
	@ApiModelProperty(value="排序") 
	private Integer sort; //排序 
	@ApiModelProperty(value="权限主体") 
	private String subject; //权限主体 
	@ApiModelProperty(value="权限类型") 
	private String type; //权限类型 
	@ApiModelProperty(value="资源类型") 
	private Integer resourceType; //资源类型 
	@ApiModelProperty(value="所属系统") 
	private Integer belong; //所属系统 
	@ApiModelProperty(value="权限资源分组名称") 
	private String permissionShow; //权限资源分组名称 
	@ApiModelProperty(value="权限资源分组标识") 
	private String permissionGroup; //权限资源分组标识 
	@ApiModelProperty(value="分组关系id") 
	private Integer relationId; //分组关系id 
	@ApiModelProperty(value="创建时间") 
	private Long createTime; //创建时间 
	@ApiModelProperty(value="创建人") 
	private Long createUser; //创建人 
	@ApiModelProperty(value="修改人") 
	private Long updateUser; //修改人 
	@ApiModelProperty(value="修改时间") 
	private Long updateTime; //修改时间 
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
	public void setResourceName(String resourceName){
	this.resourceName=resourceName;
	}
	public String getResourceName(){
		return resourceName;
	}
	public void setParentId(Integer parentId){
	this.parentId=parentId;
	}
	public Integer getParentId(){
		return parentId;
	}
	public void setResourceDesc(String resourceDesc){
	this.resourceDesc=resourceDesc;
	}
	public String getResourceDesc(){
		return resourceDesc;
	}
	public void setUrl(String url){
	this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setTag(String tag){
	this.tag=tag;
	}
	public String getTag(){
		return tag;
	}
	public void setSort(Integer sort){
	this.sort=sort;
	}
	public Integer getSort(){
		return sort;
	}
	public void setSubject(String subject){
	this.subject=subject;
	}
	public String getSubject(){
		return subject;
	}
	public void setType(String type){
	this.type=type;
	}
	public String getType(){
		return type;
	}
	public void setResourceType(Integer resourceType){
	this.resourceType=resourceType;
	}
	public Integer getResourceType(){
		return resourceType;
	}
	public void setBelong(Integer belong){
	this.belong=belong;
	}
	public Integer getBelong(){
		return belong;
	}
	public void setPermissionShow(String permissionShow){
	this.permissionShow=permissionShow;
	}
	public String getPermissionShow(){
		return permissionShow;
	}
	public void setPermissionGroup(String permissionGroup){
	this.permissionGroup=permissionGroup;
	}
	public String getPermissionGroup(){
		return permissionGroup;
	}
	public void setRelationId(Integer relationId){
	this.relationId=relationId;
	}
	public Integer getRelationId(){
		return relationId;
	}
	public void setCreateTime(Long createTime){
	this.createTime=createTime;
	}
	public Long getCreateTime(){
		return createTime;
	}
	public void setCreateUser(Long createUser){
	this.createUser=createUser;
	}
	public Long getCreateUser(){
		return createUser;
	}
	public void setUpdateUser(Long updateUser){
	this.updateUser=updateUser;
	}
	public Long getUpdateUser(){
		return updateUser;
	}
	public void setUpdateTime(Long updateTime){
	this.updateTime=updateTime;
	}
	public Long getUpdateTime(){
		return updateTime;
	}
	public void setStatus(Integer status){
	this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
}

