package com.org.permission.server.permission.service.impl;

import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.vo.BasePermissionTypeResourceVo;
import com.org.permission.server.permission.entity.BasePermissionTypeResource;
import com.org.permission.server.permission.mapper.BasePermissionTypeResourceMapper;
import com.org.permission.server.permission.service.IBasePermissionTypeResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_type_resourceServiceImpl类  业务类型域名功能权限资源表(tree)管理
 */
@Service
public class BasePermissionTypeResourceServiceImpl implements IBasePermissionTypeResourceService {
    @Autowired
    private BasePermissionTypeResourceMapper dao;

    public int addBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource) {
        return dao.addBasePermissionTypeResource(basePermissionTypeResource);
    }

    public int delBasePermissionTypeResource(Integer id) {
        return dao.delBasePermissionTypeResource(id);
    }

    public int delBasePermissionTypeResourceTrue(Integer id) {
        return dao.delBasePermissionTypeResourceTrue(id);
    }

    public int updateBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource) {
        return dao.updateBasePermissionTypeResource(basePermissionTypeResource);
    }

    public BasePermissionTypeResource getBasePermissionTypeResourceById(Integer Id) {
        return dao.getBasePermissionTypeResourceByID(Id);
    }

    public int getBasePermissionTypeResourceCount() {
        return dao.getBasePermissionTypeResourceCount();
    }

    public int getBasePermissionTypeResourceCountAll() {
        return dao.getBasePermissionTypeResourceCountAll();
    }

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPage(BasePermissionTypeResourceVo basePermissionTypeResourceVo) {
        return dao.getListBasePermissionTypeResourcesByPage(basePermissionTypeResourceVo);
    }

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPOJO(BasePermissionTypeResource basePermissionTypeResource) {
        return dao.getListBasePermissionTypeResourcesByPOJO(basePermissionTypeResource);
    }

    public List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPojoPage(BasePermissionTypeResource base_permission_type_resource) {
        Map map = new HashMap();
        map.put("pojo", base_permission_type_resource);
        return dao.getListBasePermissionTypeResourcesByPojoPage(map);
    }

    @Override
    public List<ResourceDto> getPermissionByType(Map map) {
        return dao.getPermissionByType(map);
    }

}

