package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *   集团的功能权限权限表管理
 */

@ApiModel(description = "集团的功能权限权限表", value = "集团的功能权限权限表")
public class BasePermissionGroupResourceVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value = "")
	private Integer id; // 
	@ApiModelProperty(value = "集团id")
	private Integer groupId; //集团id 
	@ApiModelProperty(value = "功能权限id")
	private Integer permissionId; //功能权限id 
	@ApiModelProperty(value = "创建时间")
	private Long createTime; //创建时间 
	@ApiModelProperty(value = "创建人")
	private Long createUser; //创建人 
	@ApiModelProperty(value = "修改人")
	private Long updateUser; //修改人 
	@ApiModelProperty(value = "修改时间")
	private Long updateTime; //修改时间 
	@ApiModelProperty(value = "状态")
	private Integer status; //状态 

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}

