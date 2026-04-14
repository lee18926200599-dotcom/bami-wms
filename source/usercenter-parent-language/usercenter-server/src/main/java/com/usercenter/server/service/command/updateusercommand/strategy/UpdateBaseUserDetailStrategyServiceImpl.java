package com.usercenter.server.service.command.updateusercommand.strategy;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;

@Service("updateBaseUserDetailStrategyServiceImpl")
public class UpdateBaseUserDetailStrategyServiceImpl extends AbstractUpdateUserStrategyService {



    @Override
    public UpdateUserCommandResp updateDetail(UpdateUserStrategyDTO updateUserStrategyDTO) {
        BaseUserDetail baseUserDetail = new BaseUserDetail();
        baseUserDetail.setLockFlag(updateUserStrategyDTO.getLock());
        baseUserDetail.setState(updateUserStrategyDTO.getEnable());
        baseUserDetail.setDeletedFlag(updateUserStrategyDTO.getDelete());
        baseUserDetail.setModifiedBy(updateUserStrategyDTO.getUserId());
        baseUserDetail.setModifiedDate(new Date());
        baseUserDetail.setGroupId(updateUserStrategyDTO.getGroupId());
        if(updateUserStrategyDTO.getDetailId()!=null) {
            baseUserDetail.setId(updateUserStrategyDTO.getDetailId());
            baseUserDetailMapper.update(baseUserDetail);
        }
        if(!CollectionUtils.isEmpty(updateUserStrategyDTO.getDetailIds())){
            updateUserStrategyDTO.setModifiedDate(new Date());
            baseUserDetailMapper.updateByIds(updateUserStrategyDTO);
        }
        if(updateUserStrategyDTO.getGroupId()!=null){
            updateUserStrategyDTO.setModifiedDate(new Date());
            baseUserDetailMapper.updateByIds(updateUserStrategyDTO);
        }
        return new UpdateUserCommandResp();
    }
}
