package com.org.permission.server.permission.mapper;


import com.org.permission.server.permission.dto.InputRoleManageMentDto;
import com.org.permission.server.permission.dto.InputUserManageMentDto;
import com.org.permission.server.permission.dto.OutputRoleManageMentDto;
import com.org.permission.server.permission.entity.BasePermissionGroupData;
import com.org.permission.server.permission.vo.BasePermissionGroupDataVo;
import com.org.permission.common.permission.dto.RoleDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_group_dataDao类  集团的数据权限关系表管理
 */
@Mapper
public interface BasePermissionGroupDataMapper  {
    public int addBasePermissionGroupData(BasePermissionGroupData basePermissionGroupData);

    public int delBasePermissionGroupData(Integer Id);

    public int delBasePermissionGroupDataTrue(Integer Id);

    public int updateBasePermissionGroupData(BasePermissionGroupData basePermissionGroupData);

    public BasePermissionGroupData getBasePermissionGroupDataByID(Integer Id);

    public int getBasePermissionGroupDataCount();

    public int getBasePermissionGroupDataCountAll();

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPage(BasePermissionGroupDataVo basePermissionGroupDataVo);

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPOJO(BasePermissionGroupData basePermissionGroupData);

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPojoPage(Map map);

    List<OutputRoleManageMentDto> getDataByRoleAndManageMent(InputRoleManageMentDto inputRoleManageMentDto);

    List<OutputRoleManageMentDto> getDataByUserAndManageMent(InputUserManageMentDto inputUserManageMentDto);

    List<RoleDto> getRoleListByOrgPermission(List<UserOrgPermissionDto> list);

    void inserBath(HashMap<String, Object> immutableMap);

    void delBatch(HashMap<String, Object> immutableMap);

    public int inserRoleDataBath(HashMap<String, Object> immutableMap);

    public int delRoleDataBatch(HashMap<String, Object> immutableMap);

    void batchAddBasePermissionGroupData(List<BasePermissionGroupData> permissionGroupDataList);
}

