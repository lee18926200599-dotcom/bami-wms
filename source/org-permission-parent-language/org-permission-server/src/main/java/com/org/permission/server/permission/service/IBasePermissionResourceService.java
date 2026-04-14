package com.org.permission.server.permission.service;

import com.org.permission.server.permission.dto.PermissionResourceDto;
import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.vo.BasePermissionResourceVo;

import java.util.List;
import java.util.Map;

/**
 * 功能权限资源表(tree)管理
 */
public interface IBasePermissionResourceService {
    int addBasePermissionResource(BasePermissionResource base_permission_resource);

    int delBasePermissionResource(Integer Id);

    int delBasePermissionResourceTrue(Integer Id);

    int updateBasePermissionResource(BasePermissionResource base_permission_resource);

    BasePermissionResource getBasePermissionResourceById(Long Id);

    int getBasePermissionResourceCount();

    int getBasePermissionResourceCountAll();

    List<BasePermissionResource> getListBasePermissionResourcesByPage(BasePermissionResourceVo base_permission_resource);

    List<BasePermissionResource> getListBasePermissionResourcesByPOJO(BasePermissionResource base_permission_resource);

    //查询所有资源功能权限树
    Map<String, List<ResourceDto>> getBasePermissionResources(List<BasePermissionResource> basePermissionResources, List<BasePermissionGroupResource> basePermissionGroupResources, Integer queryPage);

    List<PermissionResourceDto> getWebTreeList(List<BasePermissionResource> list);

}

