package com.org.permission.server.permission.service.impl;

import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionRole;
import com.org.permission.server.permission.mapper.BasePermissionRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色相关的验证和帮助类
 */
@Service
public class RoleManagerHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleManagerHelper.class);

    @Autowired
    private BasePermissionRoleMapper basePermissionRoleMapper;

    // 验证逻辑 编码不能重复
    public RestMessage<String> checkRoleCode(String roleCode, Long groupId) {
        BasePermissionRole roleCodeParam = new BasePermissionRole();
        roleCodeParam.setRoleCode(roleCode);
        roleCodeParam.setGroupId(groupId);
        List<BasePermissionRole> roleCodes = basePermissionRoleMapper.getListBasePermissionRolesByPOJO(roleCodeParam);
        if (roleCodes.size() > 0) {
            return RestMessage.error(PermissionErrorCode.ROLE_CODE_ERROR.getErrorCode()+"",
                    PermissionErrorCode.ROLE_CODE_ERROR.getErrorReason());
        } else {
            return RestMessage.doSuccess(roleCode);
        }
    }

    // 角色名称不能重复
    public RestMessage<String> checkRoleName(String roleName, Long groupId, Long roleId) {
        BasePermissionRole roleNameParam = new BasePermissionRole();
        roleNameParam.setRoleName(roleName);
        roleNameParam.setGroupId(groupId);
        List<BasePermissionRole> roleNames = basePermissionRoleMapper.getListBasePermissionRolesByPOJO(roleNameParam);
        if (!ObjectUtils.isEmpty(roleId)) {
            roleNames = roleNames.stream().filter(basePermissionRole -> !basePermissionRole.getId().equals(roleId)).collect(Collectors.toList());
        }
        if (roleNames.size() > 0) {
            return RestMessage.error(String.valueOf(PermissionErrorCode.ROLE_NAME_ERROR.getErrorCode()),
                    PermissionErrorCode.ROLE_NAME_ERROR.getErrorReason());
        } else {
            return RestMessage.doSuccess(roleName);
        }
    }
}
