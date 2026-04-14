package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.BasePermissionManagementDto;
import com.org.permission.server.permission.dto.GroupManageMentDto;
import com.org.permission.server.permission.vo.BasePermissionGroupManagementVo;
import com.org.permission.server.permission.entity.BasePermissionGroupManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * base_permission_group_managementDao类  集团-管理维度表管理
 */
@Mapper
public interface BasePermissionGroupManagementMapper {
    int addBasePermissionGroupManagement(BasePermissionGroupManagement basePermissionGroupManagement);

    int delBasePermissionGroupManagement(Integer Id);

    int delBasePermissionGroupManagementTrue(Integer Id);

    int updateBasePermissionGroupManagement(BasePermissionGroupManagement basePermissionGroupManagement);

    BasePermissionGroupManagement getBasePermissionGroupManagementByID(Integer Id);

    int getBasePermissionGroupManagementCount();

    int getBasePermissionGroupManagementCountAll();

    List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPage(BasePermissionGroupManagementVo basePermissionGroupManagementVo);

    List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPOJO(BasePermissionGroupManagement basePermissionGroupManagement);

    List<BasePermissionGroupManagement> getListBasePermissionGroupManagementsByPojoPage(Map map);

    List<BasePermissionManagementDto> getManageMentByGroupId(GroupManageMentDto groupManageMentDto);

    Integer insertList(List<BasePermissionGroupManagement> permissionGroupManagementList);
}

