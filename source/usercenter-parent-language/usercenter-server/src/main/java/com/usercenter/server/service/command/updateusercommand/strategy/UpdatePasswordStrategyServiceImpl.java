package com.usercenter.server.service.command.updateusercommand.strategy;

import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.mapper.BaseUserMapper;
import com.usercenter.server.constant.command.enums.BaseUserPasswordFlagEnum;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.utils.PasswordEncoder;
import com.usercenter.server.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("updatePasswordStrategyServiceImpl")
public class UpdatePasswordStrategyServiceImpl implements UpdateUserStrategyService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateUserCommandResp update(UpdateUserStrategyDTO updateUserStrategyDTO) {
        //根据手机号从查找用户
        BaseUser baseUser = null;
        BaseUser params = new BaseUser();
        UpdateUserCommandResp resp = new UpdateUserCommandResp();
        if (baseUser == null && updateUserStrategyDTO.getId() != null) {
            params.setPhone(null);
            params.setId(updateUserStrategyDTO.getId());
            baseUser = baseUserMapper.loadByParam(params);
        }
        if (baseUser == null) {
            resp.setReturnCodesEnum(ReturnCodesEnum.USER_NOTEXISTS);
            return resp;
        }
        //验证码相同则进行重置
        String password = BaseUserConstants.USER_PASSWORD_PREFIX + UserUtil.getCode();
        baseUser.setPassword(passwordEncoder.encode(password));
        baseUser.setPasswordFlag(BaseUserPasswordFlagEnum.YES.getId().equals(1) ? 1 : 0);
        baseUser.setFirstTimeLoginFlag(FlagEnum.FALSE.getCode());
        baseUser.setModifiedDate(new Date());
        baseUser.setModifiedBy(updateUserStrategyDTO.getUserId());
        baseUserMapper.update(baseUser);
        return resp.setMessage(password);
    }
}
