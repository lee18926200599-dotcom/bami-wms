package com.org.permission.server.permission.service;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.server.permission.dto.InputRoleInsertOrUpdateDto;
import com.org.permission.server.permission.dto.OutPutRoleDto;
import com.org.permission.server.permission.dto.req.GetRoleUserListReq;
import com.org.permission.server.permission.dto.req.RolePermissionParam;
import com.org.permission.server.permission.entity.BasePermissionRole;
import com.org.permission.server.permission.vo.BasePermissionRoleVo;
import com.common.util.message.RestMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 角色管理
 */
public interface IBasePermissionRoleService {
    int addBasePermissionRole(BasePermissionRole base_permission_role);

    int delBasePermissionRole(Integer Id);

    int updateBasePermissionRole(BasePermissionRole base_permission_role);

    BasePermissionRole getBasePermissionRoleById(Long Id);

    int getBasePermissionRoleCount();

    int getBasePermissionRoleCountAll();

    List<BasePermissionRole> getListBasePermissionRolesByPage(BasePermissionRoleVo base_permission_role);

    List<BasePermissionRole> getListBasePermissionRolesByPOJO(BasePermissionRole base_permission_role);

    List<BasePermissionRole> getListBasePermissionRolesByPojoPage(BasePermissionRole base_permission_role);

    List<OutPutRoleDto> getRoleByOrgPermission(Map<String, Object> mapParam);

    List<RoleUser> getUserByRoleId(Long id, Long groupId) throws ExecutionException, InterruptedException;

    /**
     * 分页查询可以分配的用户
     *
     * @param getAssignRoleUserReq
     * @return
     */
    PageInfo<RoleUser> getAssignRoleUser(GetRoleUserListReq getAssignRoleUserReq);

    int delRole(Long id, Long groupId);

    RestMessage<BasePermissionRoleVo> insertOrupdateBasePermissionRole(InputRoleInsertOrUpdateDto inputRoleInsertOrUpdateDto);


    List<UserOrgPermissionDto> getUserOrgPermissionsList(List<UserOrgPermissionDto> userOrgPermissionDtos);

    /**
     * 部门权限
     **/
    RestMessage getRolePermission(RolePermissionParam rolePermissionParam);

    List<UserPermission> getUserFuncPermissionList(List<UserPermission> userPermissions, Long menuId);

    List<UserDataPermissionDto> getUserDataPermissionsList(List<UserDataPermissionDto> allDatas);

    List<UserPermission> getUserFuncPermissionListP(List<UserPermission> userPermissions, Long parentId);

    List<UserPermission> searchMenuList(List<UserPermission> userPermissions, List<UserPermission> lastStageMenus);
}
