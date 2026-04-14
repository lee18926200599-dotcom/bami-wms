package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
* 用户基本信息表管理
*/ 

 @ApiModel(description = "用户基本信息表", value = "用户基本信息表")
public class BaseUserVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="ID") 
	private Long id; //ID 
	@ApiModelProperty(value="用户编码") 
	private String userNumber; //用户编码 
	@ApiModelProperty(value="所属组织") 
	private Long orgId; //所属组织 
	@ApiModelProperty(value="用户所属集团")
	private Long groupId; //用户所属集团 
	@ApiModelProperty(value="注册来源") 
	private Integer source; //注册来源 
	@ApiModelProperty(value="真实姓名") 
	private String realName; //真实姓名 
	@ApiModelProperty(value="管理员等级") 
	private Integer managerLevel; //管理员等级 
	@ApiModelProperty(value="身份类型") 
	private Integer IdentityType; //身份类型 
	@ApiModelProperty(value="启用状态（0非启用，1启用，2停用）") 
	private Integer enable; //启用状态（0非启用，1启用，2停用） 
	@ApiModelProperty(value="锁定状态（0未锁定，1锁定）") 
	private Integer lock; //锁定状态（0未锁定，1锁定） 
	@ApiModelProperty(value="用户密码") 
	private String password; //用户密码 
	@ApiModelProperty(value="用户帐号") 
	private String userName; //用户帐号 
	@ApiModelProperty(value="手机号码") 
	private String phone; //手机号码 
	@ApiModelProperty(value="电子邮件") 
	private String email; //电子邮件 
	@ApiModelProperty(value="生效时间") 
	private Long effectiveDate; //生效时间 
	@ApiModelProperty(value="失效日期") 
	private Long expireTime; //失效日期 
	@ApiModelProperty(value="创建人") 
	private Long createUser; //创建人 
	@ApiModelProperty(value="创建时间") 
	private Long createTime; //创建时间 
	@ApiModelProperty(value="更新人") 
	private String updateUser; //更新人 
	@ApiModelProperty(value="更新时间") 
	private Long updateTime; //更新时间 
	@ApiModelProperty(value="备注信息") 
	private String remark; //备注信息 
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setUserNumber(String userNumber){
	this.userNumber=userNumber;
	}
	public String getUserNumber(){
		return userNumber;
	}
	public void setOrgId(Long orgId){
	this.orgId=orgId;
	}
	public Long getOrgId(){
		return orgId;
	}
	public void setGroupId(Long groupId){
	this.groupId=groupId;
	}
	public Long getGroupId(){
		return groupId;
	}
	public void setSource(Integer source){
	this.source=source;
	}
	public Integer getSource(){
		return source;
	}
	public void setRealName(String realName){
	this.realName=realName;
	}
	public String getRealName(){
		return realName;
	}
	public void setManagerLevel(Integer managerLevel){
	this.managerLevel=managerLevel;
	}
	public Integer getManagerLevel(){
		return managerLevel;
	}
	public void setIdentityType(Integer IdentityType){
	this.IdentityType=IdentityType;
	}
	public Integer getIdentityType(){
		return IdentityType;
	}
	public void setEnable(Integer enable){
	this.enable=enable;
	}
	public Integer getEnable(){
		return enable;
	}
	public void setLock(Integer lock){
	this.lock=lock;
	}
	public Integer getLock(){
		return lock;
	}
	public void setPassword(String password){
	this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setUserName(String userName){
	this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setPhone(String phone){
	this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setEmail(String email){
	this.email=email;
	}
	public String getEmail(){
		return email;
	}
	public void setEffectiveDate(Long effectiveDate){
	this.effectiveDate=effectiveDate;
	}
	public Long getEffectiveDate(){
		return effectiveDate;
	}
	public void setExpireTime(Long expireTime){
	this.expireTime=expireTime;
	}
	public Long getExpireTime(){
		return expireTime;
	}
	public void setCreateUser(Long createUser){
	this.createUser=createUser;
	}
	public Long getCreateUser(){
		return createUser;
	}
	public void setCreateTime(Long createTime){
	this.createTime=createTime;
	}
	public Long getCreateTime(){
		return createTime;
	}
	public void setUpdateUser(String updateUser){
	this.updateUser=updateUser;
	}
	public String getUpdateUser(){
		return updateUser;
	}
	public void setUpdateTime(Long updateTime){
	this.updateTime=updateTime;
	}
	public Long getUpdateTime(){
		return updateTime;
	}
	public void setRemark(String remark){
	this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

