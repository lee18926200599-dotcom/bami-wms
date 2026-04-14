package com.org.permission.server.permission.mapper;


import com.org.permission.server.permission.vo.BasePermissionDataVo;
import com.org.permission.server.permission.entity.BasePermissionData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*base_permission_dataDao类  数据权限资源表管理
*/
@Mapper
 public interface BasePermissionDataMapper{
     int addBasePermissionData(BasePermissionData basePermissionData);

     int delBasePermissionData(Integer Id);

     int delBasePermissionDataTrue(Integer Id);

     int updateBasePermissionData(BasePermissionData basePermissionData);

     BasePermissionData getBasePermissionDataByID(Integer Id);

     int getBasePermissionDataCount();

     int getBasePermissionDataCountAll();

     List<BasePermissionData> getListBasePermissionDatasByPage(BasePermissionDataVo basePermissionDataVo);

     List<BasePermissionData> getListBasePermissionDatasByPOJO(BasePermissionData basePermissionData);

     List<BasePermissionData> getListBasePermissionDatasByPojoPage(Map map);
}

