package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionManagementVo;
import com.org.permission.server.permission.entity.BasePermissionManagement;

import java.util.List;
/**
*管理维度表管理
*/ 
public interface IBasePermissionManagementService{
	public int addBasePermissionManagement(BasePermissionManagement base_permission_management);
	public int delBasePermissionManagement(Integer Id);
	public int delBasePermissionManagementTrue(Integer Id);
	public int updateBasePermissionManagement(BasePermissionManagement base_permission_management);
	public BasePermissionManagement getBasePermissionManagementById(Integer Id);
	public int getBasePermissionManagementCount();
	public int getBasePermissionManagementCountAll();
	public List<BasePermissionManagement> getListBasePermissionManagementsByPage(BasePermissionManagementVo base_permission_management);
	public List<BasePermissionManagement> getListBasePermissionManagementsByPOJO(BasePermissionManagement base_permission_management);
	public List<BasePermissionManagement> getListBasePermissionManagementsByPojoPage(BasePermissionManagement base_permission_management);
}

