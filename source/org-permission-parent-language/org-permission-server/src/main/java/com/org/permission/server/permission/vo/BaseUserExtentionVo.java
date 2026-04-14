package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*用户扩展信息管理
*/ 

 @ApiModel(description = "用户扩展信息", value = "用户扩展信息")
public class BaseUserExtentionVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="编号") 
	private Long id; //编号 
	@ApiModelProperty(value="用户ID")
	private Long userId; //用户ID 
	@ApiModelProperty(value="关键字") 
	private String userKey; //关键字 
	@ApiModelProperty(value="值") 
	private String userValue; //值 
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setUserId(Long userId){
	this.userId=userId;
	}
	public Long getUserId(){
		return userId;
	}
	public void setUserKey(String userKey){
	this.userKey=userKey;
	}
	public String getUserKey(){
		return userKey;
	}
	public void setUserValue(String userValue){
	this.userValue=userValue;
	}
	public String getUserValue(){
		return userValue;
	}
}

