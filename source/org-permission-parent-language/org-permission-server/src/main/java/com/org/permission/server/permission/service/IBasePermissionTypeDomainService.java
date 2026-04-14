package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionTypeDomainVo;
import com.org.permission.server.permission.entity.BasePermissionTypeDomain;

import java.util.List;
import java.util.Map;

/**
 * 业务类型和域名关系表（初始化表）管理
 */
public interface IBasePermissionTypeDomainService {
    public int addBasePermissionTypeDomain(BasePermissionTypeDomain basePermissionTypeDomain);

    public int delBasePermissionTypeDomain(Integer id);

    public int delBasePermissionTypeDomainTrue(Integer id);

    public int updateBasePermissionTypeDomain(BasePermissionTypeDomain base_permission_type_domain);

    public BasePermissionTypeDomain getBasePermissionTypeDomainById(Integer id);

    public int getBasePermissionTypeDomainCount();

    public int getBasePermissionTypeDomainCountAll();

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPage(BasePermissionTypeDomainVo base_permission_type_domain);

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPOJO(BasePermissionTypeDomain base_permission_type_domain);

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPojoPage(BasePermissionTypeDomain base_permission_type_domain);

    List<BasePermissionTypeDomain> getDomainByType(Map<String, Object> map);
}

