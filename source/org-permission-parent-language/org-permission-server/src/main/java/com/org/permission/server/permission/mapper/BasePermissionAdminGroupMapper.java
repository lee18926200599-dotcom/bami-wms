package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.common.permission.dto.GroupDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * base_permission_admin_groupDao类  用户表中的管理员和集团关系表管理
 */
@Mapper
public interface BasePermissionAdminGroupMapper {
    /**
     * 单条更新 用户表中的管理员和集团关系表 数据
     *
     * @param basePermissionAdminGroup
     * @return 更新数据 影响行数
     */
    int update(BasePermissionAdminGroup basePermissionAdminGroup);

    int addBasePermissionAdminGroup(BasePermissionAdminGroup basePermissionAdminGroup);
    int insertList(List<BasePermissionAdminGroup> basePermissionAdminGroups);

    /**
     * 根据主键查询 用户表中的管理员和集团关系表 数据
     *
     * @param id
     * @return 查询到的用户表中的管理员和集团关系表数据
     */
    BasePermissionAdminGroup load(Integer id);
    Integer deleteByAdminId(@Param("adminId") Long adminId);

    List<BasePermissionAdminGroup> getListUnion(BasePermissionAdminGroup basePermissionAdminGroup);

    List<BasePermissionAdminGroup> getListByAdminId(@Param("userIdSet") Set<Long> userIdSet);

    List<BasePermissionAdminGroup> listByParam(BasePermissionAdminGroup basePermissionAdminGroup);

    int batchUpdateLose(Set<Long> userIdSet);

    List<GroupDto> getChooseList(Map<String, Object> param);

    void insertAdminGroup(Map<String, Object> map);

    void updateAdminGroup(Map<String, Object> map);

    void batchUpdateGroupAdmin(Map map);

    int updateBasePermissionAdminGroupByAdminId(BasePermissionAdminGroup basePermissionAdminGroup);

}

