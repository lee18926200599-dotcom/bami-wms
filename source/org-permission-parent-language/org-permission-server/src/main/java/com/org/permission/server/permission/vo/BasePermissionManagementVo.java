package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*管理维度表管理
*/ 

 @ApiModel(description = "管理维度表", value = "管理维度表")
public class BasePermissionManagementVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="")
	private Integer id; // 
	@ApiModelProperty(value="管理维度名称") 
	private String name; //管理维度名称 
	@ApiModelProperty(value="描述") 
	private String des; //描述 
	@ApiModelProperty(value="备注") 
	private String remark; //备注 
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
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setDes(String des){
	this.des=des;
	}
	public String getDes(){
		return des;
	}
	public void setRemark(String remark){
	this.remark=remark;
	}
	public String getRemark(){
		return remark;
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

