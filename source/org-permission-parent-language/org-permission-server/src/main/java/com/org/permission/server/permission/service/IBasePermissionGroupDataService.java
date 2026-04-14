package com.org.permission.server.permission.service;

import com.org.permission.server.permission.entity.BasePermissionGroupData;
import com.org.permission.server.permission.vo.BasePermissionGroupDataVo;

import java.util.HashMap;
import java.util.List;
/**
*集团的数据权限关系表管理
*/ 
public interface IBasePermissionGroupDataService{
	public int addBasePermissionGroupData(BasePermissionGroupData base_permission_group_data);
	public int delBasePermissionGroupData(Integer Id);
	public int delBasePermissionGroupDataTrue(Integer Id);
	public int updateBasePermissionGroupData(BasePermissionGroupData base_permission_group_data);
	public BasePermissionGroupData getBasePermissionGroupDataById(Integer Id);
	public int getBasePermissionGroupDataCount();
	public int getBasePermissionGroupDataCountAll();
	public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPage(BasePermissionGroupDataVo base_permission_group_data);
	public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPOJO(BasePermissionGroupData base_permission_group_data);
	public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPojoPage(BasePermissionGroupData base_permission_group_data);

    void adjustUserData(HashMap<String, Object> immutableMap);

	void adjustRoleData(HashMap<String, Object> immutableMap);
}

