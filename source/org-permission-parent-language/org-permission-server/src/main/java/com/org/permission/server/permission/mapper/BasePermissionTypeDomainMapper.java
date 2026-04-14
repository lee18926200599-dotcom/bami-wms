package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.vo.BasePermissionTypeDomainVo;
import com.org.permission.server.permission.entity.BasePermissionTypeDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*base_permission_type_domainDao类  业务类型和域名关系表（初始化表）管理
*/ 
@Mapper
public interface BasePermissionTypeDomainMapper{
     int addBasePermissionTypeDomain(BasePermissionTypeDomain basePermissionTypeDomain);

     int delBasePermissionTypeDomain(Integer Id);

     int delBasePermissionTypeDomainTrue(Integer Id);

     int updateBasePermissionTypeDomain(BasePermissionTypeDomain basePermissionTypeDomain);

     BasePermissionTypeDomain getBasePermissionTypeDomainByID(Integer Id);

     int getBasePermissionTypeDomainCount();

     int getBasePermissionTypeDomainCountAll();

     List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPage(BasePermissionTypeDomainVo basePermissionTypeDomainVo);

     List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPOJO(BasePermissionTypeDomain basePermissionTypeDomain);

     List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPojoPage(Map map);

    List<BasePermissionTypeDomain> getDomainByType(Map<String, Object> map);
}

