package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.vo.BasePermissionTypeResourceVo;
import com.org.permission.server.permission.entity.BasePermissionTypeResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*base_permission_type_resourceDao类  业务类型域名功能权限资源表(tree)管理
*/ 
@Mapper
public interface BasePermissionTypeResourceMapper{
     int addBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource);

     int delBasePermissionTypeResource(Integer Id);

     int delBasePermissionTypeResourceTrue(Integer Id);

     int updateBasePermissionTypeResource(BasePermissionTypeResource basePermissionTypeResource);

     BasePermissionTypeResource getBasePermissionTypeResourceByID(Integer Id);

     int getBasePermissionTypeResourceCount();

     int getBasePermissionTypeResourceCountAll();

     List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPage(BasePermissionTypeResourceVo basePermissionTypeResourceVo);

     List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPOJO(BasePermissionTypeResource basePermissionTypeResource);

     List<BasePermissionTypeResource> getListBasePermissionTypeResourcesByPojoPage(Map map);

    List<ResourceDto> getPermissionByType(Map map);
}

