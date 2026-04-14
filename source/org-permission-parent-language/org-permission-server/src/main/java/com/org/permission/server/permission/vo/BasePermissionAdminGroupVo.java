package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*base_permission_admin_groupVo类  用户表中的管理员和集团关系表管理
*/ 

 @ApiModel(description = "用户表中的管理员和集团关系表", value = "用户表中的管理员和集团关系表")
public class BasePermissionAdminGroupVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="集团id") 
	private Integer groupId; //集团id 
	@ApiModelProperty(value="集团编码") 
	private String groupCode; //集团编码 
	@ApiModelProperty(value="集团名称") 
	private String groupName; //集团名称 
	@ApiModelProperty(value="管理员id") 
	private Long adminId; //管理员id 
	@ApiModelProperty(value="管理员名称") 
	private String adminName; //管理员名称 
	@ApiModelProperty(value="管理生效时间") 
	private Long effectiveTime; //管理生效时间 
	@ApiModelProperty(value="管理失效时间") 
	private Long expireTime; //管理失效时间 
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
	public void setGroupId(Integer groupId){
	this.groupId=groupId;
	}
	public Integer getGroupId(){
		return groupId;
	}
	public void setGroupCode(String groupCode){
	this.groupCode=groupCode;
	}
	public String getGroupCode(){
		return groupCode;
	}
	public void setGroupName(String groupName){
	this.groupName=groupName;
	}
	public String getGroupName(){
		return groupName;
	}
	public void setAdminId(Long adminId){
	this.adminId=adminId;
	}
	public Long getAdminId(){
		return adminId;
	}
	public void setAdminName(String adminName){
	this.adminName=adminName;
	}
	public String getAdminName(){
		return adminName;
	}
	public void setEffectiveTime(Long effectiveTime){
	this.effectiveTime=effectiveTime;
	}
	public Long getEffectiveTime(){
		return effectiveTime;
	}
	public void setExpireTime(Long expireTime){
	this.expireTime=expireTime;
	}
	public Long getExpireTime(){
		return expireTime;
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

