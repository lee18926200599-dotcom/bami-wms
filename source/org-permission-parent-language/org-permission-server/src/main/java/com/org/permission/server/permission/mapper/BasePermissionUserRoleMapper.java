package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.*;
import com.org.permission.server.permission.dto.req.BatchDeleteUserRoleReq;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.vo.BasePermissionUserRoleVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * base_permission_user_roleDao类  用户角色关联表管理
 */
@Mapper
public interface BasePermissionUserRoleMapper {
    int addBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole);

    int delBasePermissionUserRole(Integer Id);

    int delBasePermissionUserRoleTrue(Integer Id);

    int updateBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole);

    BasePermissionUserRole getBasePermissionUserRoleByID(Integer Id);

    int getBasePermissionUserRoleCount();

    int getBasePermissionUserRoleCountAll();

    List<BasePermissionUserRole> getListBasePermissionUserRolesByPage(BasePermissionUserRoleVo basePermissionUserRoleVo);

    List<BasePermissionUserRole> getListBasePermissionUserRolesByPOJO(BasePermissionUserRole basePermissionUserRole);

    List<BasePermissionUserRole> getListBasePermissionUserRolesByPojoPage(Map map);

    int delBasePermissionUserRoleTrueByRoleId(Map<String, Long> map);

    /* List<OrgUserDto> getOrgUserList(Map<String, Object> map);*/

    List<OrgUserDto> getRoleOrgUserList(Map<String, Object> map);

    void batchUserRoleCreate(Map<String, Object> paramMap);

    UserExpireDto selectUserExpire(Map<String, Object> map);

    List<OutPutUserRoleDto> getUserRoleList(InputParentDto inputParentDto);

    List<OrgRoleDto> getOrgRoleList(Map<String, Object> map);

    List<OrgRoleDto> getUserOrgRoleList(Map<String, Object> map);

    void deleteUserRoleCreate(Map<String, Object> paramMap);

    void batchUpdateUserRole(Map map);

    void batchSaveUserRole(@Param("saveList") List<BasePermissionUserRole> saveList);

    void batchDeleteUserRole(BatchDeleteUserRoleReq batchDeleteUserRoleReq);


    int updateBasePermissionUserRoleExtends(BasePermissionUserRole basePermissionUserRole);

}

