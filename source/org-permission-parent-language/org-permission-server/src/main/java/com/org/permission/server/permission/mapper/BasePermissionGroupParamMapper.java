package com.org.permission.server.permission.mapper;

import com.org.permission.server.permission.entity.BasePermissionGroupParam;
import com.org.permission.server.permission.vo.BasePermissionGroupParamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * base_permission_group_paramDao类  集团参数配置管理
 */
@Mapper
public interface BasePermissionGroupParamMapper  {
    int addBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam);

    int delBasePermissionGroupParam(Long id);

    int delBasePermissionGroupParamTrue(Long id);

    int updateBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam);

    BasePermissionGroupParam getBasePermissionGroupParamByID(Long id);

    int getBasePermissionGroupParamCount();

    int getBasePermissionGroupParamCountAll();

    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPage(BasePermissionGroupParamVo basePermissionGroupParamVo);

    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPOJO(BasePermissionGroupParam basePermissionGroupParam);

    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPojoPage(Map map);

}

