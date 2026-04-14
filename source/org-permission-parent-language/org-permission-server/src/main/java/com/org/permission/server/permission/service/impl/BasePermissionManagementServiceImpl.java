package com.org.permission.server.permission.service.impl;


import com.org.permission.server.permission.vo.BasePermissionManagementVo;
import com.org.permission.server.permission.entity.BasePermissionManagement;
import com.org.permission.server.permission.mapper.BasePermissionManagementMapper;
import com.org.permission.server.permission.service.IBasePermissionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*base_permission_managementServiceImpl类  管理维度表管理
*/ 
@Service
public class BasePermissionManagementServiceImpl implements IBasePermissionManagementService {
@Autowired
private BasePermissionManagementMapper dao;
	public int addBasePermissionManagement(BasePermissionManagement base_permission_management){
		return dao.addBasePermissionManagement(base_permission_management);
	}

	public int delBasePermissionManagement(Integer Id){
		return dao.delBasePermissionManagement(Id);
	}

	public int delBasePermissionManagementTrue(Integer Id){
		return dao.delBasePermissionManagementTrue(Id);
	}

	public int updateBasePermissionManagement(BasePermissionManagement base_permission_management){
		return dao.updateBasePermissionManagement(base_permission_management);
	}

	public BasePermissionManagement getBasePermissionManagementById(Integer Id){
		return dao.getBasePermissionManagementByID(Id);
	}

	public int getBasePermissionManagementCount(){
		return dao.getBasePermissionManagementCount();
	}

	public int getBasePermissionManagementCountAll(){
		return dao.getBasePermissionManagementCountAll();
	}

	public List<BasePermissionManagement> getListBasePermissionManagementsByPage(BasePermissionManagementVo base_permission_management){
		return dao.getListBasePermissionManagementsByPage(base_permission_management);
	}

	public List<BasePermissionManagement> getListBasePermissionManagementsByPOJO(BasePermissionManagement base_permission_management){
		return dao.getListBasePermissionManagementsByPOJO(base_permission_management);
	}

	public List<BasePermissionManagement> getListBasePermissionManagementsByPojoPage(BasePermissionManagement base_permission_management){
		Map map = new HashMap();
		map.put("pojo",base_permission_management);
		return dao.getListBasePermissionManagementsByPojoPage(map);
	}

}

