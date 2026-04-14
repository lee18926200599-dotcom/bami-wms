package com.org.permission.server.permission.service.command.interfaces;


import com.org.permission.server.permission.service.command.dto.PermissionDto;

public interface IPermissionCommand {


    /**
     * 执行接口
     *
     * @param strategyService
     * @return
     */
    boolean execute(PermissionStrategyService strategyService, PermissionDto permissionDto);


}