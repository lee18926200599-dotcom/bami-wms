package com.usercenter.server.service.command.updateusercommand;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.command.UpdateUserCommand;
import org.springframework.stereotype.Service;

/**
 * 启用命令
 */
@Service("enableCommand")
public class EnableCommand implements UpdateUserCommand {


    @Override
    public UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO) {
        updateUserStrategyDTO.setEnable(UserStateEnum.ENABLE.getCode());
        return strategyService.update(updateUserStrategyDTO);
    }
}
