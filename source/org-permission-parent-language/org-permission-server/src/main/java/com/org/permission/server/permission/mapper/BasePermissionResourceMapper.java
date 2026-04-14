package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.vo.BasePermissionResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * base_permission_resourceDao类  功能权限资源表(tree)管理
 */
@Mapper
public interface BasePermissionResourceMapper {
    List<BasePermissionResource> getListBasePermissionResourcesByPOJO(BasePermissionResource basePermissionResource);

    int addBasePermissionResource(BasePermissionResource basePermissionResource);

    int delBasePermissionResource(Integer Id);

    int delBasePermissionResourceTrue(Integer Id);

    int updateBasePermissionResource(BasePermissionResource basePermissionResource);

    BasePermissionResource getBasePermissionResourceByID(Long Id);

    int getBasePermissionResourceCount();

    int getBasePermissionResourceCountAll();

    List<BasePermissionResource> getListBasePermissionResourcesByPage(BasePermissionResourceVo basePermissionResourceVo);
    List<BasePermissionResource> getListByPid(@Param("parentIdList") List<Long> parentIdList);

    void batchUpdatePlatformId(@Param("platformIdMap") Map<Long, String> platformIdMap);
}

