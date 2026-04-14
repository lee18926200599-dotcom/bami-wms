package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.EnableUserDataPermission;
import com.org.permission.server.permission.dto.UserAndRoleOrgDto;
import com.org.permission.server.permission.dto.UserAndRolePermissionDto;
import com.org.permission.server.permission.vo.BasePermissionUserFuncVo;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;

import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * base_permission_user_funcDao类  用户权限关联表管理
 */
@Mapper
public interface BasePermissionUserFuncMapper {
    int addBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc);

    int delBasePermissionUserFunc(Integer Id);

    int delBasePermissionUserFuncTrue(Integer Id);

    int updateBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc);

    BasePermissionUserFunc getBasePermissionUserFuncByID(Integer Id);

    int getBasePermissionUserFuncCount();

    int getBasePermissionUserFuncCountAll();

    List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPage(BasePermissionUserFuncVo basePermissionUserFuncVo);

    List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPOJO(BasePermissionUserFunc basePermissionUserFunc);

    List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPojoPage(Map map);

    List<UserPermission> getUserPermission(UserPermission userPermission);

    List<UserOrgPermissionDto> getUserOrgPermission(UserOrgPermissionDto userOrgPermissionDto);

    List<UserDataPermissionDto> getUserDataPermission(UserDataPermissionDto userDataPermissionDto);

    List<EnableUserDataPermission> getEnableUserDataPermission(UserDataPermissionDto userDataPermissionDto);

    List<UserDataPermissionDto> getOnlyRoleDataPermission(Map map);

    List<UserAndRolePermissionDto> getRolePermissionByUidAndGroupId(Map map);

    List<UserAndRolePermissionDto> getUserPermissionsByUidAndGroupId(Map map);

    List<UserAndRoleOrgDto> getUserAndRoleOrgs(Map map);

    List<UserAndRoleOrgDto> batchGetUserAndRoleOrgs(@Param("userIdSet") Set<Integer> userIdSet);

    List<UserAndRoleOrgDto> getUserOrgsByUidAndGroupId(Map map);

    List<UserAndRoleOrgDto> getRoleOrgsByUidAndGroupId(Map map);

    List<UserDataPermissionDto> getGroupDataPermission(Map<String, Object> map);

    List<UserDataPermissionDto> getPlatGroupDataPermission(Map<String, Object> map);
    int delDataByCondition(BasePermissionUserFunc basePermissionUserFunc);
    void batchUserFuncInsert(Map map);
    void insertUserRoleAndPermission(Map<String, Object> map);
    void updateUserRoleAndPermission(Map<String, Object> map);
    void delUserRoleAndPermission(Map<String, Object> map);
    void delRoleAndPermission(Map<String, Object> map);
    int updateBasePermissionUserFuncExtends(BasePermissionUserFunc basePermissionUserFunc);
}

