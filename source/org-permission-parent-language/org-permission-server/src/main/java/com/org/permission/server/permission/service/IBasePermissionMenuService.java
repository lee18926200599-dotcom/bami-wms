package com.org.permission.server.permission.service;

import com.org.permission.server.permission.vo.BasePermissionMenuVo;
import com.org.permission.server.permission.entity.BasePermissionMenu;

import java.util.List;

/**
 * 用户表中管理员和菜单关系表管理
 */
public interface IBasePermissionMenuService {
    int addBasePermissionMenu(BasePermissionMenu base_permission_menu);

    int delBasePermissionMenu(Integer Id);

    int delBasePermissionMenuTrue(Integer Id);

    int updateBasePermissionMenu(BasePermissionMenu base_permission_menu);

    BasePermissionMenu getBasePermissionMenuById(Integer Id);


    List<BasePermissionMenu> getListBasePermissionMenusByPage(BasePermissionMenuVo base_permission_menu);

    List<BasePermissionMenu> getListBasePermissionMenusByPOJO(BasePermissionMenu base_permission_menu);
}

