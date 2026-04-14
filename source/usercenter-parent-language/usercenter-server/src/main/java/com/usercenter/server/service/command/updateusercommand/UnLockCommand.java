package com.usercenter.server.service.command.updateusercommand;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.command.UpdateUserCommand;
import org.springframework.stereotype.Service;

/**
 * 解锁命令
 */
@Service("unLockCommand")
public class UnLockCommand implements UpdateUserCommand {


    @Override
    public UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO) {
        updateUserStrategyDTO.setLock(FlagEnum.FALSE.getCode());
        return strategyService.update(updateUserStrategyDTO);
    }
}
