package com.org.permission.server.permission.service.impl;

import com.org.permission.server.permission.vo.BasePermissionGroupManagementVo;
import com.org.permission.server.permission.entity.BasePermissionGroupManagement;
import com.org.permission.server.permission.mapper.BasePermissionGroupManagementMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*base_permission_group_managementServiceImpl类  集团-管理维度表管理
*/ 
@Service
public class BasePermissionGroupManagementServiceImpl implements IBasePermissionGroupManagementService {
@Autowired
private BasePermissionGroupManagementMapper dao;
	public int addBasePermissionGroupManagement(BasePermissionGroupManagement base_permission_group_management){
		return dao.addBasePermissionGroupManagement(base_permission_group_management);
	}

	public int delBasePermissionGroupManagement(Integer Id){
		return dao.delBasePermissionGroupManagement(Id);
	}

	public int delBasePermissionGroupManagementTrue(Integer Id){
		return dao.delBasePermissionGroupManagementTrue(Id);
	}

	public int updateBasePermissionGroupManagement(BasePermissionGroupManagement base_permission_group_management){
		return dao.updateBasePermissionGroupManagement(base_permission_group_management);
	}

	public BasePermissionGroupManagement getBasePermissionGroupManagementById(Integer Id){
		return dao.getBasePermissionGroupManagementByID(Id);
	}

	public int getBasePermissionGroupManagementCount(){
		return dao.getBasePermissionGroupManagementCount();
	}

	public int getBasePermissionGroupManagementCountAll(){
		return dao.getBasePermissionGroupManagementCountAll();
	}

	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPage(BasePermissionGroupManagementVo base_permission_group_management){
		return dao.getListBasePermissionGroupManagementsByPage(base_permission_group_management);
	}

	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPOJO(BasePermissionGroupManagement base_permission_group_management){
		return dao.getListBasePermissionGroupManagementsByPOJO(base_permission_group_management);
	}

	public List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPojoPage(BasePermissionGroupManagement base_permission_group_management){
		Map map = new HashMap();
		map.put("pojo",base_permission_group_management);
		return dao.getListBasePermissionGroupManagementsByPojoPage(map);
	}

}

