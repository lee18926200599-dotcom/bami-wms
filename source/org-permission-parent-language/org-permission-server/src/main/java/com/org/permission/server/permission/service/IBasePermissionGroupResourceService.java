package com.org.permission.server.permission.service;

import com.org.permission.server.permission.dto.GroupPermissionDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.vo.BasePermissionGroupResourceVo;

import java.util.List;
import java.util.Map;
/**
*集团的功能权限权限表管理
*/ 
public interface IBasePermissionGroupResourceService{
	public int addBasePermissionGroupResource(BasePermissionGroupResource base_permission_group_resource);
	public int delBasePermissionGroupResource(Integer Id);
	public int delBasePermissionGroupResourceTrue(Integer Id);
	public int updateBasePermissionGroupResource(BasePermissionGroupResource base_permission_group_resource);
	public BasePermissionGroupResource getBasePermissionGroupResourceById(Integer Id);
	public int getBasePermissionGroupResourceCount();
	public int getBasePermissionGroupResourceCountAll();
	public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPage(BasePermissionGroupResourceVo base_permission_group_resource);
	public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPOJO(BasePermissionGroupResource base_permission_group_resource);
	public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPojoPage(BasePermissionGroupResource base_permission_group_resource);
	//集团功能调整
	public void updateGroupResource(Map<String, Object> map);

	//查集团下功能权限树
    Map<String, List<GroupPermissionDto>> getGroupFuncTree(Map<String, Object> map);

    void batchAddDistributionMenus(Map map);

	void delDistributionMenus(Map map);

    List<BasePermissionGroupResource> getListBasePermissionGroupResources(Map map);
}

