package com.usercenter.server.service.command.updateusercommand;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.command.UpdateUserCommand;
import org.springframework.stereotype.Service;

/**
 * 重置密码
 */
@Service("resetPasswordCommand")
public class ResetPasswordCommand implements UpdateUserCommand {

    @Override
    public UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO) {
        return strategyService.update(updateUserStrategyDTO);
    }
}
