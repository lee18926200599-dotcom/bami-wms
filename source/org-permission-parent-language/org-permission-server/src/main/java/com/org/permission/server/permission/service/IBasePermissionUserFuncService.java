package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionUserFuncVo;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;

import java.util.List;

/**
 * 用户权限关联表管理
 */
public interface IBasePermissionUserFuncService {
    public int addBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc);

    public int delBasePermissionUserFunc(Integer id);

    public int delBasePermissionUserFuncTrue(Integer id);

    public int updateBasePermissionUserFunc(BasePermissionUserFunc basePermissionUserFunc);

    public BasePermissionUserFunc getBasePermissionUserFuncById(Integer id);

    public int getBasePermissionUserFuncCount();

    public int getBasePermissionUserFuncCountAll();

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPage(BasePermissionUserFuncVo basePermissionUserFuncVo);

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPOJO(BasePermissionUserFunc basePermissionUserFunc);

    public List<BasePermissionUserFunc> getListBasePermissionUserFuncsByPojoPage(BasePermissionUserFunc basePermissionUserFunc);
}

