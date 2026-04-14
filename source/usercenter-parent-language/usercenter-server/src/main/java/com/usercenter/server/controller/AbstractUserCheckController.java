package com.usercenter.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.common.util.message.RestMessage;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.service.IBaseAdministratorsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 校验用户登录
 */
public abstract class AbstractUserCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserCheckController.class);


    @Autowired
    private IBaseAdministratorsUserService iBaseAdministratorsUserService;


    /**
     * 校验当前登录用户
     *
     * @param userId
     */
    protected RestMessage<BaseUserDetail> checkUser(Long userId) {
        if (userId == null) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.PARAM_USER_ID_NOT_EXIST.getCode()), ReturnCodesEnum.PARAM_USER_ID_NOT_EXIST.getMessage());
        }
        //用户不存在
        BaseUserDetail params = new BaseUserDetail();
        params.setId(userId);
        params.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserDetail baseUserDetail = iBaseAdministratorsUserService.getOneDetail(params);
        if (baseUserDetail == null) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());

        }
        //用户被停用
        if (!UserStateEnum.ENABLE.getCode().equals(baseUserDetail.getState())) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.NOT_ENABLE.getCode()), ReturnCodesEnum.NOT_ENABLE.getMessage());

        }
        //账号被锁定
        if (baseUserDetail.getLockFlag() == FlagEnum.TRUE.getCode()) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_LOCK.getCode()), ReturnCodesEnum.USER_LOCK.getMessage());
        }
        LOGGER.info("当前登录用户信息:{}", JSONObject.toJSONString(baseUserDetail));
        return RestMessage.doSuccess(baseUserDetail);
    }

}
