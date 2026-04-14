package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.vo.BasePermissionPropertyVo;
import com.org.permission.server.permission.entity.BasePermissionProperty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * base_permission_propertyDao类  权限体系配置表（域名首页url配置在这里）管理
 */
@Mapper
public interface BasePermissionPropertyMapper {
    int addBasePermissionProperty(BasePermissionProperty basePermissionProperty);

    int delBasePermissionProperty(Integer id);

    int delBasePermissionPropertyTrue(Integer id);

    int updateBasePermissionProperty(BasePermissionProperty basePermissionProperty);

    BasePermissionProperty getBasePermissionPropertyByID(Integer id);

    int getBasePermissionPropertyCount();

    int getBasePermissionPropertyCountAll();

    List<BasePermissionProperty> getListBasePermissionPropertysByPage(BasePermissionPropertyVo basePermissionPropertyVo);

    List<BasePermissionProperty> getListBasePermissionPropertysByPOJO(BasePermissionProperty basePermissionProperty);

    List<BasePermissionProperty> getListBasePermissionPropertysByPojoPage(Map map);

}

