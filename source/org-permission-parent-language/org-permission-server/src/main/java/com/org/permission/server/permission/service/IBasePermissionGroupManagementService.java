package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionGroupManagementVo;
import com.org.permission.server.permission.entity.BasePermissionGroupManagement;

import java.util.List;

/**
*集团-管理维度表管理
*/ 
public interface IBasePermissionGroupManagementService{
	public int addBasePermissionGroupManagement(BasePermissionGroupManagement base_permission_group_management);
	public int delBasePermissionGroupManagement(Integer Id);
	public int delBasePermissionGroupManagementTrue(Integer Id);
	public int updateBasePermissionGroupManagement(BasePermissionGroupManagement base_permission_group_management);
	public BasePermissionGroupManagement getBasePermissionGroupManagementById(Integer Id);
	public int getBasePermissionGroupManagementCount();
	public int getBasePermissionGroupManagementCountAll();
	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPage(BasePermissionGroupManagementVo base_permission_group_management);
	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPOJO(BasePermissionGroupManagement base_permission_group_management);
	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPojoPage(BasePermissionGroupManagement base_permission_group_management);
}

