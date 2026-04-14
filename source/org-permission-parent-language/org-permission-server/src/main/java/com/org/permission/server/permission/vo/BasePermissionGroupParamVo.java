package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*集团参数配置管理
*/ 

 @ApiModel(description = "集团参数配置", value = "集团参数配置")
public class BasePermissionGroupParamVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="所属集团id") 
	private Integer groupId; //所属集团id 
	@ApiModelProperty(value="参数代码") 
	private String paramCode; //参数代码 
	@ApiModelProperty(value="参数名称") 
	private String paramName; //参数名称 
	@ApiModelProperty(value="备注") 
	private String remark; //备注 
	@ApiModelProperty(value="参数值") 
	private String paramValue; //参数值 
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
	public void setParamCode(String paramCode){
	this.paramCode=paramCode;
	}
	public String getParamCode(){
		return paramCode;
	}
	public void setParamName(String paramName){
	this.paramName=paramName;
	}
	public String getParamName(){
		return paramName;
	}
	public void setRemark(String remark){
	this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setParamValue(String paramValue){
	this.paramValue=paramValue;
	}
	public String getParamValue(){
		return paramValue;
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

