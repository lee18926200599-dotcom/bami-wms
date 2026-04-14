package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.vo.BasePermissionManagementVo;
import com.org.permission.server.permission.entity.BasePermissionManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*base_permission_managementDao类  管理维度表管理
*/ 
@Mapper
public interface BasePermissionManagementMapper{

     int addBasePermissionManagement(BasePermissionManagement basePermissionManagement);

     int delBasePermissionManagement(Integer Id);

     int delBasePermissionManagementTrue(Integer Id);

     int updateBasePermissionManagement(BasePermissionManagement basePermissionManagement);

     BasePermissionManagement getBasePermissionManagementByID(Integer Id);

     int getBasePermissionManagementCount();

     int getBasePermissionManagementCountAll();

     List<BasePermissionManagement> getListBasePermissionManagementsByPage(BasePermissionManagementVo basePermissionManagementVo);

     List<BasePermissionManagement> getListBasePermissionManagementsByPOJO(BasePermissionManagement basePermissionManagement);

     List<BasePermissionManagement> getListBasePermissionManagementsByPojoPage(Map map);

}

