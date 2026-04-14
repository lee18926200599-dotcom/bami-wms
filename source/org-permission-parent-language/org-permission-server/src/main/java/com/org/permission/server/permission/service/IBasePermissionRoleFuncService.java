package com.org.permission.server.permission.service;

import com.org.permission.server.permission.dto.req.BatchSaveFuncReq;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.vo.BasePermissionRoleFuncVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*角色权限关联表管理
*/ 
public interface IBasePermissionRoleFuncService{
	public int addBasePermissionRoleFunc(BasePermissionRoleFunc base_permission_role_func);
	public int delBasePermissionRoleFunc(Integer Id);
	public int delBasePermissionRoleFuncTrue(Integer Id);
	public int updateBasePermissionRoleFunc(BasePermissionRoleFunc base_permission_role_func);
	public BasePermissionRoleFunc getBasePermissionRoleFuncById(Integer Id);
	public int getBasePermissionRoleFuncCount();
	public int getBasePermissionRoleFuncCountAll();
	public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPage(BasePermissionRoleFuncVo base_permission_role_func);
	public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPOJO(BasePermissionRoleFunc base_permission_role_func);
	public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPojoPage(BasePermissionRoleFunc base_permission_role_func);

	/**
	 * 批量插入权限信息
	 * @param batchSaveFuncReq
	 */
	@Transactional
	void batchSaveFunc(BatchSaveFuncReq batchSaveFuncReq);
}

