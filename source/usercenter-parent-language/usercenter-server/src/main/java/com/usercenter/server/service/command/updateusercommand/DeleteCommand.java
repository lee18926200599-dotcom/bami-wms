package com.usercenter.server.service.command.updateusercommand;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.constant.command.enums.BaseUserDeleteEnum;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.command.UpdateUserCommand;
import org.springframework.stereotype.Service;

@Service("deleteCommand")
public class DeleteCommand implements UpdateUserCommand {


    @Override
    public UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO) {
        //主表信息并不删除
        updateUserStrategyDTO.setDelete(BaseUserDeleteEnum.DELETE.getId());
        return strategyService.update(updateUserStrategyDTO);
    }
}
