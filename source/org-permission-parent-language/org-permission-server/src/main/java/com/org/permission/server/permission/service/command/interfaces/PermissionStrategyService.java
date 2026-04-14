package com.org.permission.server.permission.service.command.interfaces;


import com.org.permission.server.permission.service.command.dto.PermissionDto;

public interface PermissionStrategyService {

    /**
     * 操作权限
     * @param permissionDto
     * @return
     */
    boolean optionPermission(PermissionDto permissionDto);
}