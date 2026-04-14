package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionDataVo;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.server.permission.entity.BasePermissionData;

import java.util.List;
/**
* 数据权限资源表管理
*/ 
public interface IBasePermissionDataService{
	public int addBasePermissionData(BasePermissionData base_permission_data);
	public int delBasePermissionData(Integer id);
	public int delBasePermissionDataTrue(Integer id);
	public int updateBasePermissionData(BasePermissionData base_permission_data);
	public BasePermissionData getBasePermissionDataById(Integer id);
	public int getBasePermissionDataCount();
	public int getBasePermissionDataCountAll();
	public List<BasePermissionData> getListBasePermissionDatasByPage(BasePermissionDataVo base_permission_data);
	public List<BasePermissionData> getListBasePermissionDatasByPOJO(BasePermissionData base_permission_data);
	public List<BasePermissionData> getListBasePermissionDatasByPojoPage(BasePermissionData base_permission_data);

	RestMessage syncwarehouse(SyncDataPermissionParam param);

	RestMessage site(Long userId, Long groupId, String siteId, String siteName,Long newGroupId);

	RestMessage siteName(Long userId, Long groupId, String siteId, String siteName);

	RestMessage syncCustomer(Long userId, Long groupId, String dataId, String dataName, Long parentId, Integer type);

	RestMessage syncSupplier(Long userId, Long groupId, String supplierId, String supplierName);

	RestMessage syncBusinessLine(SyncDataPermissionParam param);
}

