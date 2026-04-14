package com.org.permission.server.permission.mapper;

import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.server.permission.dto.OutPutRoleDto;
import com.org.permission.server.permission.dto.RoleOrgPermission;
import com.org.permission.server.permission.dto.RolePermission;
import com.org.permission.server.permission.entity.BasePermissionRole;
import com.org.permission.server.permission.vo.BasePermissionRoleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*base_permission_roleDao类  用户角色关联表管理
*/ 
@Mapper
public interface BasePermissionRoleMapper{
     int addBasePermissionRole(BasePermissionRole basePermissionRole);

     int delBasePermissionRole(Integer Id);

     int delBasePermissionRoleExtendTrue(Map<String, Long> map);

     int updateBasePermissionRole(BasePermissionRole basePermissionRole);

     BasePermissionRole getBasePermissionRoleByID(Long id);

     int getBasePermissionRoleCount();

     int getBasePermissionRoleCountAll();

     List<BasePermissionRole> getListBasePermissionRolesByPage(BasePermissionRoleVo basePermissionRoleVo);

     List<BasePermissionRole> getListBasePermissionRolesByPOJO(BasePermissionRole basePermissionRole);

     List<BasePermissionRole> getListBasePermissionRolesByPojoPage(Map map);

     List<OutPutRoleDto> getRoleByOrgPermission(Map<String, Object> mapParam);

     List<RoleUser> getUserByRoleId(Map<String, Long> map);

     List<RolePermission> getRoleFuncPermissions(Map<String, Object> map);

     List<RoleOrgPermission> getRoleOrgPermissions(Map<String, Object> map);

     OutPutRoleDto getRoleMessageByRoleIdAndGroupId(Map<String, Object> map);

    /**
     * 分页查询已经关联角色的用户
     **/
    List<RoleUser> getRoleUserList(Map<String, Object> mapParam);

    /**
     * 询已经关联角色的用户总数
     **/
    Integer getRoleUserCount(Map<String, Object> mapParam);

}

