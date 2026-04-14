package com.org.permission.server.permission.service;


import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.vo.BasePermissionTypeResourceVo;
import com.org.permission.server.permission.entity.BasePermissionTypeResource;

import java.util.List;
import java.util.Map;

/**
 * 业务类型域名功能权限资源表(tree)管理
 */
public interface IBasePermissionTypeResourceService {
    public int addBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource);

    public int delBasePermissionTypeResource(Integer id);

    public int delBasePermissionTypeResourceTrue(Integer id);

    public int updateBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource);

    public BasePermissionTypeResource getBasePermissionTypeResourceById(Integer id);

    public int getBasePermissionTypeResourceCount();

    public int getBasePermissionTypeResourceCountAll();

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPage(BasePermissionTypeResourceVo basePermissionTypeResourceVo);

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPOJO(BasePermissionTypeResource basePermissionTypeResource);

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPojoPage(BasePermissionTypeResource basePermissionTypeResource);

    List<ResourceDto> getPermissionByType(Map map);
}

