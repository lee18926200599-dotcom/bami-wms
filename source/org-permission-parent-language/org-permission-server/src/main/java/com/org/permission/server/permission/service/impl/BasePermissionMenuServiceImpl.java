package com.org.permission.server.permission.service.impl;


import com.org.permission.server.permission.vo.BasePermissionMenuVo;
import com.org.permission.server.permission.entity.BasePermissionMenu;
import com.org.permission.server.permission.mapper.BasePermissionMenuMapper;
import com.org.permission.server.permission.service.IBasePermissionMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * base_permission_menuServiceImpl类  用户表中管理员和菜单关系表管理
 */
@Service
public class BasePermissionMenuServiceImpl implements IBasePermissionMenuService {
    @Autowired
    private BasePermissionMenuMapper dao;

    public int addBasePermissionMenu(BasePermissionMenu base_permission_menu) {
        return dao.insert(base_permission_menu);
    }

    public int delBasePermissionMenu(Integer Id) {
        return dao.delete(Id);
    }

    public int delBasePermissionMenuTrue(Integer Id) {
        return dao.delete(Id);
    }

    public int updateBasePermissionMenu(BasePermissionMenu base_permission_menu) {
        return dao.update(base_permission_menu);
    }

    public BasePermissionMenu getBasePermissionMenuById(Integer Id) {
        return dao.load(Id);
    }


    public List<BasePermissionMenu> getListBasePermissionMenusByPage(BasePermissionMenuVo base_permission_menu) {
        return dao.getListBasePermissionMenusByPage(base_permission_menu);
    }

    public List<BasePermissionMenu> getListBasePermissionMenusByPOJO(BasePermissionMenu base_permission_menu) {
        return dao.getListBasePermissionMenusByPOJO(base_permission_menu);
    }


}

