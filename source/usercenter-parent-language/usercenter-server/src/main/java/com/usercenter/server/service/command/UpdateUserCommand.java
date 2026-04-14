package com.usercenter.server.service.command;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;

/**
 * 用户部分公用接口
 */
public interface UpdateUserCommand {


    /**
     * 执行接口
     * @param strategyService
     * @param updateUserStrategyDTO
     * @return
     */
    UpdateUserCommandResp execute(UpdateUserStrategyService strategyService, UpdateUserStrategyDTO updateUserStrategyDTO);




}
