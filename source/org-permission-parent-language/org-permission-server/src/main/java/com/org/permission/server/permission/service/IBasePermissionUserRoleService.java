package com.org.permission.server.permission.service;


import com.org.permission.common.permission.dto.OrgDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.server.permission.dto.InputParentDto;
import com.org.permission.server.permission.dto.InputUserRoleDto;
import com.org.permission.server.permission.dto.OutPutUserRoleDto;
import com.org.permission.server.permission.dto.UserExpireDto;
import com.org.permission.server.permission.dto.req.BatchDeleteUserRoleReq;
import com.org.permission.server.permission.dto.req.BatchSaveUserRoleReq;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.vo.BasePermissionUserRoleVo;
import com.common.util.message.RestMessage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  用户角色关联表管理
 */
public interface IBasePermissionUserRoleService {
    public int addBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole);

    public int delBasePermissionUserRole(Integer Id);

    public int delBasePermissionUserRoleTrue(Integer Id);

    public int updateBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole);

    public BasePermissionUserRole getBasePermissionUserRoleById(Integer Id);

    public int getBasePermissionUserRoleCount();

    public int getBasePermissionUserRoleCountAll();

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPage(
            BasePermissionUserRoleVo basePermissionUserRole);

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPOJO(
            BasePermissionUserRole basePermissionUserRole);

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPojoPage(
            BasePermissionUserRole basePermissionUserRole);

    public List<OrgDto> getOrgUserList(Map<String, Object> map);

    public List<OrgDto> getRoleOrgUserList(Map<String, Object> map);

    public RestMessage<String> batchUserRoleCreate(InputUserRoleDto inputUserRoleDto);

    /**
     * 批量保存角色和用户的关系
     **/
    RestMessage batchSaveUserRole(BatchSaveUserRoleReq batchSaveUserRoleReq);

    /**
     * 批量删除角色和用户的关系
     **/
    RestMessage batchDeleteUserRole(BatchDeleteUserRoleReq batchDeleteUserRoleReq);

    public RestMessage<UserExpireDto> selectUserExpire(Long userId, Integer roleId, Long groupId);

    public List<OutPutUserRoleDto> getUserRoleList(InputParentDto inputParentDto);

    public RestMessage<List<UserOrgPermissionDto>> getOrgTree(InputParentDto inputParentDto);

    //当前操作人组织权限下有哪些角色，树状结构列出
    public List<OrgDto> getOrgRoleList(Map<String, Object> map);

    //获取当前用户已分配了哪些角色，树状结构列出
    public Map<String, Set<String>> getUserOrgRoleList(Map<String, Object> map);

    void batchUpdateUserRole(Map map);
}
