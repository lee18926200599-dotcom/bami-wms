package com.org.permission.server.permission.service.impl;


import com.org.permission.server.permission.vo.BasePermissionUserFuncVo;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.org.permission.server.permission.service.IBasePermissionUserFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_user_funcServiceImpl类  用户权限关联表管理
 */
@Service
public class BasePermissionUserFuncServiceImpl implements IBasePermissionUserFuncService {
    @Autowired
    private BasePermissionUserFuncMapper dao;

    public int addBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc) {
        return dao.addBasePermissionUserFunc(basePermissionUserFunc);
    }

    public int delBasePermissionUserFunc(Integer id) {
        return dao.delBasePermissionUserFunc(id);
    }

    public int delBasePermissionUserFuncTrue(Integer id) {
        return dao.delBasePermissionUserFuncTrue(id);
    }

    public int updateBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc) {
        return dao.updateBasePermissionUserFunc(basePermissionUserFunc);
    }

    public BasePermissionUserFunc getBasePermissionUserFuncById(Integer id) {
        return dao.getBasePermissionUserFuncByID(id);
    }

    public int getBasePermissionUserFuncCount() {
        return dao.getBasePermissionUserFuncCount();
    }

    public int getBasePermissionUserFuncCountAll() {
        return dao.getBasePermissionUserFuncCountAll();
    }

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPage(BasePermissionUserFuncVo basePermissionUserFuncVo) {
        return dao.getListBasePermissionUserFuncsByPage(basePermissionUserFuncVo);
    }

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPOJO(BasePermissionUserFunc basePermissionUserFunc) {
        return dao.getListBasePermissionUserFuncsByPOJO(basePermissionUserFunc);
    }

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPojoPage(BasePermissionUserFunc basePermissionUserFunc) {
        Map map = new HashMap();
        map.put("pojo", basePermissionUserFunc);
        return dao.getListBasePermissionUserFuncsByPojoPage(map);
    }

}

