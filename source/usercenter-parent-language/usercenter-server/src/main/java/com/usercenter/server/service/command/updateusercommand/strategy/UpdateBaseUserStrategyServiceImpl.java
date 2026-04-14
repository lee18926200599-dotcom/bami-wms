package com.usercenter.server.service.command.updateusercommand.strategy;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("updateBaseUserStrategyServiceImpl")
public class UpdateBaseUserStrategyServiceImpl extends AbstractUpdateUserStrategyService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateUserCommandResp updateDetail(UpdateUserStrategyDTO updateUserStrategyDTO) {
        BaseUser baseUser = new BaseUser();

        if(updateUserStrategyDTO.getDelete()!=null){
            return new UpdateUserCommandResp().setReturnCodesEnum(ReturnCodesEnum.NOT_DISABLED);
        }
        baseUser.setLockFlag(updateUserStrategyDTO.getLock());
        baseUser.setState(updateUserStrategyDTO.getEnable());
        baseUser.setModifiedBy(updateUserStrategyDTO.getUserId());
        baseUser.setModifiedDate(new Date());
        if(updateUserStrategyDTO.getId()!=null) {
            baseUser.setId(updateUserStrategyDTO.getId());
            baseUserMapper.update(baseUser);
        }
        BaseUserDetail detail = new BaseUserDetail();
        detail.setModifiedDate(new Date());
        detail.setModifiedBy(updateUserStrategyDTO.getUserId());
        detail.setLockFlag(updateUserStrategyDTO.getLock());
        detail.setState(updateUserStrategyDTO.getEnable());
        if(updateUserStrategyDTO.getId()!=null){
            detail.setUserId(updateUserStrategyDTO.getId());
            //锁定,解锁,禁用主表,(则子表的状态需要保持一致)。启用主表，则不更新子表状态
            if(!UserStateEnum.ENABLE.getCode().equals(updateUserStrategyDTO.getEnable())) {
                baseUserDetailMapper.updateLockAndEnableByUserId(detail);
            }
        }
        return new UpdateUserCommandResp();
    }
}
