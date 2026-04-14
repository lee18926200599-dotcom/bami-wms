package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.vo.BasePermissionMenuVo;
import com.org.permission.server.permission.entity.BasePermissionMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * base_permission_menuDao类  用户表中管理员和菜单关系表管理
 */
@Mapper
public interface BasePermissionMenuMapper {
    /**
     * 单条插入 用户表中管理员和菜单关系表 数据
     *
     * @param basePermissionMenu
     * @return 插入数据 影响行数
     */
    int insert(BasePermissionMenu basePermissionMenu);

    /**
     * 单条更新 用户表中管理员和菜单关系表 数据
     *
     * @param basePermissionMenu
     * @return 更新数据 影响行数
     */
    int update(BasePermissionMenu basePermissionMenu);

    /**
     * 根据主键删除 用户表中管理员和菜单关系表 数据
     *
     * @param id 主键id
     * @return 删除数据 影响行数
     */
    int delete(Integer id);

    /**
     * 根据主键查询 用户表中管理员和菜单关系表 数据
     *
     * @param id
     * @return 查询到的用户表中管理员和菜单关系表数据
     */
    BasePermissionMenu load(Integer id);

    /**
     * 根据条件查询 用户表中管理员和菜单关系表 数据
     *
     * @param basePermissionMenu
     * @return 查询到的用户表中管理员和菜单关系表数据
     */
    BasePermissionMenu loadByParam(BasePermissionMenu basePermissionMenu);

    /**
     * 根据查询条件分页查询 用户表中管理员和菜单关系表 数据
     *
     * @param basePermissionMenuVo
     * @return 查询到的列表数据 分页
     */
    List<BasePermissionMenu> find(BasePermissionMenuVo basePermissionMenuVo);

    /**
     * 不分页根据条件查询 用户表中管理员和菜单关系表 数据
     *
     * @param basePermissionMenu
     * @return 查询到的列表数据 不分页
     */
    List<BasePermissionMenu> listByParam(BasePermissionMenu basePermissionMenu);

    /**
     * 查询满足条件的 用户表中管理员和菜单关系表数据的记录数
     *
     * @param basePermissionMenu
     * @return 满足条件的记录数
     */
    int findCount(BasePermissionMenu basePermissionMenu);

    List<BasePermissionMenu> getListBasePermissionMenusByPage(BasePermissionMenuVo basePermissionMenuVo);

    List<BasePermissionMenu> getListBasePermissionMenusByPOJO(BasePermissionMenu basePermissionMenu);


}

