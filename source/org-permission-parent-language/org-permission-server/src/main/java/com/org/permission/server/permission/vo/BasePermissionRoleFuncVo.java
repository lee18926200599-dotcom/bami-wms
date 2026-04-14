package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*角色权限关联表管理
*/ 

 @ApiModel(description = "角色权限关联表", value = "角色权限关联表")
public class BasePermissionRoleFuncVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="角色id") 
	private Integer roleId; //角色id 
	@ApiModelProperty(value="权限类型") 
	private String permissionType; //权限类型 
	@ApiModelProperty(value="权限id(功能、组织、数据)") 
	private Integer permissionId; //权限id(功能、组织、数据) 
	@ApiModelProperty(value="数据权限（1：查询，2：查询、维护）") 
	private Integer optionPermission; //数据权限（1：查询，2：查询、维护） 
	@ApiModelProperty(value="集团id") 
	private Integer groupId; //集团id 
	@ApiModelProperty(value="授权人") 
	private Integer authorUser; //授权人 
	@ApiModelProperty(value="授权时间") 
	private Long authorTime; //授权时间 
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
	public void setRoleId(Integer roleId){
	this.roleId=roleId;
	}
	public Integer getRoleId(){
		return roleId;
	}
	public void setPermissionType(String permissionType){
	this.permissionType=permissionType;
	}
	public String getPermissionType(){
		return permissionType;
	}
	public void setPermissionId(Integer permissionId){
	this.permissionId=permissionId;
	}
	public Integer getPermissionId(){
		return permissionId;
	}
	public void setOptionPermission(Integer optionPermission){
	this.optionPermission=optionPermission;
	}
	public Integer getOptionPermission(){
		return optionPermission;
	}
	public void setGroupId(Integer groupId){
	this.groupId=groupId;
	}
	public Integer getGroupId(){
		return groupId;
	}
	public void setAuthorUser(Integer authorUser){
	this.authorUser=authorUser;
	}
	public Integer getAuthorUser(){
		return authorUser;
	}
	public void setAuthorTime(Long authorTime){
	this.authorTime=authorTime;
	}
	public Long getAuthorTime(){
		return authorTime;
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

