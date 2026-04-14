package com.org.permission.server.permission.service.impl;


import com.org.permission.server.permission.vo.BasePermissionPropertyVo;
import com.org.permission.server.permission.entity.BasePermissionProperty;
import com.org.permission.server.permission.mapper.BasePermissionPropertyMapper;
import com.org.permission.server.permission.service.IBasePermissionPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_propertyServiceImpl类  权限体系配置表（域名首页url配置在这里）管理
 */
@Service
public class BasePermissionPropertyServiceImpl implements IBasePermissionPropertyService {
    @Autowired
    private BasePermissionPropertyMapper dao;

    public int addBasePermissionProperty(BasePermissionProperty base_permission_property) {
        return dao.addBasePermissionProperty(base_permission_property);
    }

    public int delBasePermissionProperty(Integer Id) {
        return dao.delBasePermissionProperty(Id);
    }

    public int delBasePermissionPropertyTrue(Integer Id) {
        return dao.delBasePermissionPropertyTrue(Id);
    }

    public int updateBasePermissionProperty(BasePermissionProperty base_permission_property) {
        return dao.updateBasePermissionProperty(base_permission_property);
    }

    public BasePermissionProperty getBasePermissionPropertyById(Integer Id) {
        return dao.getBasePermissionPropertyByID(Id);
    }

    public int getBasePermissionPropertyCount() {
        return dao.getBasePermissionPropertyCount();
    }

    public int getBasePermissionPropertyCountAll() {
        return dao.getBasePermissionPropertyCountAll();
    }

    public List<BasePermissionProperty> getListBasePermissionPropertysByPage(BasePermissionPropertyVo base_permission_property) {
        return dao.getListBasePermissionPropertysByPage(base_permission_property);
    }

    public List<BasePermissionProperty> getListBasePermissionPropertysByPOJO(BasePermissionProperty base_permission_property) {
        return dao.getListBasePermissionPropertysByPOJO(base_permission_property);
    }

    public List<BasePermissionProperty> getListBasePermissionPropertysByPojoPage(BasePermissionProperty base_permission_property) {
        Map map = new HashMap();
        map.put("pojo", base_permission_property);
        return dao.getListBasePermissionPropertysByPojoPage(map);
    }

}

