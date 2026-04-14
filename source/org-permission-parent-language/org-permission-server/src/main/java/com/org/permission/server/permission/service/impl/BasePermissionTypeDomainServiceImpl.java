package com.org.permission.server.permission.service.impl;


import com.org.permission.server.permission.vo.BasePermissionTypeDomainVo;
import com.org.permission.server.permission.entity.BasePermissionTypeDomain;
import com.org.permission.server.permission.mapper.BasePermissionTypeDomainMapper;
import com.org.permission.server.permission.service.IBasePermissionTypeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_type_domainServiceImpl类  业务类型和域名关系表（初始化表）管理
 */
@Service
public class BasePermissionTypeDomainServiceImpl implements IBasePermissionTypeDomainService {
    @Autowired
    private BasePermissionTypeDomainMapper dao;

    public int addBasePermissionTypeDomain(BasePermissionTypeDomain basePermissionTypeDomain) {
        return dao.addBasePermissionTypeDomain(basePermissionTypeDomain);
    }

    public int delBasePermissionTypeDomain(Integer id) {
        return dao.delBasePermissionTypeDomain(id);
    }

    public int delBasePermissionTypeDomainTrue(Integer id) {
        return dao.delBasePermissionTypeDomainTrue(id);
    }

    public int updateBasePermissionTypeDomain(BasePermissionTypeDomain basePermissionTypeDomain) {
        return dao.updateBasePermissionTypeDomain(basePermissionTypeDomain);
    }

    public BasePermissionTypeDomain getBasePermissionTypeDomainById(Integer id) {
        return dao.getBasePermissionTypeDomainByID(id);
    }

    public int getBasePermissionTypeDomainCount() {
        return dao.getBasePermissionTypeDomainCount();
    }

    public int getBasePermissionTypeDomainCountAll() {
        return dao.getBasePermissionTypeDomainCountAll();
    }

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPage(BasePermissionTypeDomainVo basePermissionTypeDomainVo) {
        return dao.getListBasePermissionTypeDomainsByPage(basePermissionTypeDomainVo);
    }

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPOJO(BasePermissionTypeDomain basePermissionTypeDomain) {
        return dao.getListBasePermissionTypeDomainsByPOJO(basePermissionTypeDomain);
    }

    public List<BasePermissionTypeDomain> getListBasePermissionTypeDomainsByPojoPage(BasePermissionTypeDomain basePermissionTypeDomain) {
        Map map = new HashMap();
        map.put("pojo", basePermissionTypeDomain);
        return dao.getListBasePermissionTypeDomainsByPojoPage(map);
    }

    @Override
    public List<BasePermissionTypeDomain> getDomainByType(Map<String, Object> map) {
        return dao.getDomainByType(map);
    }

}

