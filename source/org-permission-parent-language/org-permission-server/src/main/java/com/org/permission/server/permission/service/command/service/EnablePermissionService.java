package com.org.permission.server.permission.service.command.service;

import com.org.permission.server.permission.service.command.dto.PermissionDto;
import com.org.permission.server.permission.service.command.interfaces.IPermissionCommand;
import com.org.permission.server.permission.service.command.interfaces.PermissionStrategyService;
import org.springframework.stereotype.Service;

@Service("enablePermissionService")
public class EnablePermissionService implements IPermissionCommand {

    @Override
    public boolean execute(PermissionStrategyService strategyService, PermissionDto permissionDto) {
        return strategyService.optionPermission(permissionDto);
    }
}
