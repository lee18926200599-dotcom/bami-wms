package com.org.permission.server.permission.service;

import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.InputAdminDto;
import com.org.permission.common.permission.dto.InputAdminUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户表中的管理员和集团关系表管理
 */
public interface IBasePermissionAdminGroupService {

    public int update(BasePermissionAdminGroup base_permission_admin_group);

    public BasePermissionAdminGroup getBasePermissionAdminGroupById(Integer id);

    public List<BasePermissionAdminGroup> getListBasePermissionAdminGroupsByPOJO(BasePermissionAdminGroup base_permission_admin_group);

    /**
     * 根据用户子账号集合查询集团集合
     *
     * @param userIdSet
     * @return
     */
    List<BasePermissionAdminGroup> getListBasePermissionAdminByUserIdSet(Set<Long> userIdSet);

    //根据adminid和集团名称获取集团列表
    List<BasePermissionAdminGroup> getChooseList(BasePermissionAdminGroup basePermissionAdminGroup);

    void batchUpdateGroupAdmin(Map map);

    RestMessage insertAdminGroup(InputAdminDto inputAdminDto);

    RestMessage updateAdminGroup(InputAdminUpdateDto inputAdminUpdateDto);

}

