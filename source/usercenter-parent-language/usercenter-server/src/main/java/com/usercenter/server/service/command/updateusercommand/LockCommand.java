package com.usercenter.server.service.command.updateusercommand;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.command.UpdateUserCommand;
import org.springframework.stereotype.Service;

/**
 * 锁定命令
 */
@Service("lockCommand")
public class LockCommand implements UpdateUserCommand {


    @Override
    public UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO) {
        updateUserStrategyDTO.setLock(FlagEnum.TRUE.getCode());
        return strategyService.update(updateUserStrategyDTO);
    }
}
