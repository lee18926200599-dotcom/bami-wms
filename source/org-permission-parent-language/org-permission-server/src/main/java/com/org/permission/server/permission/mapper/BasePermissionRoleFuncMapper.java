package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.PlatformPermissionDto;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.vo.BasePermissionRoleFuncVo;
import com.org.permission.common.permission.dto.PermissionDto;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * base_permission_role_funcDao类  角色权限关联表管理
 */
@Mapper
public interface BasePermissionRoleFuncMapper {
    int addBasePermissionRoleFunc(BasePermissionRoleFunc basePermissionRoleFunc);

    int delBasePermissionRoleFunc(Integer Id);

    int delBasePermissionRoleFuncTrue(Integer Id);

    int updateBasePermissionRoleFunc(BasePermissionRoleFunc basePermissionRoleFunc);

    BasePermissionRoleFunc getBasePermissionRoleFuncByID(Integer Id);

    int getBasePermissionRoleFuncCount();

    int getBasePermissionRoleFuncCountAll();

    List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPage(BasePermissionRoleFuncVo basePermissionRoleFuncVo);

    List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPOJO(BasePermissionRoleFunc basePermissionRoleFunc);

    List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPojoPage(Map map);

    List<UserPermission> getRolePermission(UserPermission userPermission);

    List<UserOrgPermissionDto> getRoleOrgPermission(UserOrgPermissionDto userOrgPermissionDto);

    List<UserDataPermissionDto> getRoleDataPermission(UserDataPermissionDto userDataPermissionDto);

    void delBasePermissionRoleFuncTrueByRoleId(Map<String, Long> map);

    void batchUpdateRolePermission(Map<String, Object> mapParam);

    List<PermissionDto> findPermissionIdsByRole(Map<String, Object> map);

    List<PlatformPermissionDto> platformFindPermissionIdsByRole(Map<String, Object> map);

    Integer delByCondition(BasePermissionRoleFunc basePermissionRoleFunc);

    Integer batchInsert(@Param("funcList") List<BasePermissionRoleFunc> funcList);

    int delDataByCondition(BasePermissionRoleFunc basePermissionRoleFunc);
}

