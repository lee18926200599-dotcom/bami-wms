package com.usercenter.server.service.command.updateusercommand.strategy;


import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;

public interface UpdateUserStrategyService {

    /**
     * 更新启用，锁定状态
     * @param updateUserStrategyDTO
     * @return
     */
    UpdateUserCommandResp update(UpdateUserStrategyDTO updateUserStrategyDTO);
}
