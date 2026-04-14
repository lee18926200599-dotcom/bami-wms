package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.GroupPermissionDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.vo.BasePermissionGroupResourceVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * base_permission_group_resourceDao类  集团的功能权限权限表管理
 */
@Mapper
public interface BasePermissionGroupResourceMapper {
    List<GroupPermissionDto> getGroupFuncTree(Map<String, Object> map);

    int addBasePermissionGroupResource(BasePermissionGroupResource basePermissionGroupResource);

    int delBasePermissionGroupResource(Integer Id);

    int delBasePermissionGroupResourceTrue(Integer Id);

    int updateBasePermissionGroupResource(BasePermissionGroupResource basePermissionGroupResource);

    BasePermissionGroupResource getBasePermissionGroupResourceByID(Integer Id);

    int getBasePermissionGroupResourceCount();

    int getBasePermissionGroupResourceCountAll();

    List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPage(BasePermissionGroupResourceVo basePermissionGroupResourceVo);

    List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPOJO(BasePermissionGroupResource basePermissionGroupResource);

    List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPojoPage(Map map);

    void updateGroupResource(Map<String, Object> map);

    void batchAddDistributionMenus(Map map);

    void delDistributionMenus(Map map);

    void batchAddBasePermissionGroupResource(List<BasePermissionGroupResource> permissionGroupResourceList);

    List<BasePermissionGroupResource> getListBasePermissionGroupResources(Map map);
}

