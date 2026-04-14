package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *  用户角色关联表管理
 */

@ApiModel(description = "用户角色关联表", value = "用户角色关联表")
public class BasePermissionUserRoleVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value = "")
	private Integer id; // 
	@ApiModelProperty(value = "用户id")
	private Long userId; //用户id 
	@ApiModelProperty(value = "角色id")
	private Integer roleId; //角色id 
	@ApiModelProperty(value = "所属集团id")
	private Integer groupId; //所属集团id 
	@ApiModelProperty(value = "所属组织id")
	private Integer orgId; //所属组织id 
	@ApiModelProperty(value = "关联生效时间")
	private Long effectiveTime; //关联生效时间 
	@ApiModelProperty(value = "关联失效时间")
	private Long expireTime; //关联失效时间 
	@ApiModelProperty(value = "授权人")
	private Long authorUser; //授权人 
	@ApiModelProperty(value = "授权时间")
	private Long authorTime; //授权时间 
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Long getEffectiveTime() {
		return effectiveTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setAuthorUser(Long authorUser) {
		this.authorUser = authorUser;
	}

	public Long getAuthorUser() {
		return authorUser;
	}

	public void setAuthorTime(Long authorTime) {
		this.authorTime = authorTime;
	}

	public Long getAuthorTime() {
		return authorTime;
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

