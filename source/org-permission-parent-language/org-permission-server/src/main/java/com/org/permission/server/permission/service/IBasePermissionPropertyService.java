package com.org.permission.server.permission.service;


import com.org.permission.server.permission.vo.BasePermissionPropertyVo;
import com.org.permission.server.permission.entity.BasePermissionProperty;

import java.util.List;

/**
 * 权限体系配置表（域名首页url配置在这里）管理
 */
public interface IBasePermissionPropertyService {
    public int addBasePermissionProperty(BasePermissionProperty base_permission_property);

    public int delBasePermissionProperty(Integer Id);

    public int delBasePermissionPropertyTrue(Integer Id);

    public int updateBasePermissionProperty(BasePermissionProperty base_permission_property);

    public BasePermissionProperty getBasePermissionPropertyById(Integer Id);

    public int getBasePermissionPropertyCount();

    public int getBasePermissionPropertyCountAll();

    public List<BasePermissionProperty> getListBasePermissionPropertysByPage(BasePermissionPropertyVo base_permission_property);

    public List<BasePermissionProperty> getListBasePermissionPropertysByPOJO(BasePermissionProperty base_permission_property);

    public List<BasePermissionProperty> getListBasePermissionPropertysByPojoPage(BasePermissionProperty base_permission_property);
}

