package com.org.permission.server.permission.service;

import com.org.permission.server.permission.entity.BasePermissionGroupParam;
import com.org.permission.server.permission.vo.BasePermissionGroupParamVo;
import com.common.util.message.RestMessage;

import java.util.List;

/**
 * 集团参数配置管理
 */
public interface IBasePermissionGroupParamService {
    int addBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam);

    int delBasePermissionGroupParam(Long id);

    int delBasePermissionGroupParamTrue(Long id);

    int updateBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam);

    BasePermissionGroupParam getBasePermissionGroupParamById(Long id);


    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPage(BasePermissionGroupParamVo basePermissionGroupParamVo);

    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPOJO(BasePermissionGroupParam basePermissionGroupParam);

    List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPojoPage(BasePermissionGroupParam basePermissionGroupParam);

    RestMessage initGroupPermission(Long userId,String userName, Long groupId, String... type);

}
