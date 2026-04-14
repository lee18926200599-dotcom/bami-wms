package com.org.permission.server.permission.service;

import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;

import java.util.List;
import java.util.Map;

/**
*用户基本信息表管理
*/ 
public interface IBaseUserService{
	//查询用户特殊权限 功能权限列表
	public Map<String, List<UserPermission>> getUserFuncPermissionList(List<UserPermission> chooseUsers, int i);
	//查询用户特殊权限 组织权限
	public List<UserOrgPermissionDto> getUserOrgPermissionsList(List<UserOrgPermissionDto> chooseOrgs);
	//查询用户特殊权限 数据权限
	public List<UserDataPermissionDto> getUserDataPermissionsList(List<UserDataPermissionDto> chooseDatas);
	
	
}

