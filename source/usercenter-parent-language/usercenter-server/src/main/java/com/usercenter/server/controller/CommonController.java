package com.usercenter.server.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.usercenter.common.dto.RefreshRedisUserDto;
import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.common.factory.BeanEnumFactory;
import com.usercenter.server.constant.command.enums.UpdateUserCommandBeanEnum;
import com.usercenter.server.constant.command.enums.UpdateUserStrategyBeanEnum;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.domain.vo.req.UpdateUserCommandReq;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.service.IBaseAdministratorsUserService;
import com.usercenter.server.service.command.UpdateUserCommand;
import com.usercenter.server.service.command.updateusercommand.strategy.UpdateUserStrategyService;
import com.usercenter.server.utils.RegexUtils;
import com.usercenter.server.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户状态更新", description = "包括启用，停用，锁定，解锁，密码重置")
@RestController
@RequestMapping("/common")
public class CommonController extends AbstractUserCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    /**
     * 枚举对象获取工厂
     */
    @Autowired
    private BeanEnumFactory beanEnumFactory;

    /**
     * 数据查询
     */
    @Autowired
    private IBaseAdministratorsUserService baseAdministratorsUserService;


    /**
     * 用户账号状态更新
     * 包括。启用，锁定。重置密码
     */
    @ApiOperation(value = "更新用户账户状态，【启用，停用，锁定，解锁，密码重置】")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestMessage<UpdateUserCommandResp> updateUserState(@RequestBody UpdateUserCommandReq req) {
        req.setUserId(UserUtil.getUserId());
        RestMessage<BaseUserDetail> restMessage = super.checkUser(req.getUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        UpdateUserCommand updateUserCommand = beanEnumFactory.getBean(UpdateUserCommandBeanEnum.getBeanEnum(req.getOperate()), UpdateUserCommand.class);
        UpdateUserStrategyService strategy = beanEnumFactory.getBean(UpdateUserStrategyBeanEnum.getBeanEnum(req), UpdateUserStrategyService.class);
        UpdateUserStrategyDTO updateUserStrategyDTO = new UpdateUserStrategyDTO();
        updateUserStrategyDTO.setId(req.getId());
        updateUserStrategyDTO.setCode(req.getCode());
        updateUserStrategyDTO.setUserId(req.getUserId());
        updateUserStrategyDTO.setDetailId(req.getDetailId());
        updateUserStrategyDTO.setPhone(req.getPhone());
        UpdateUserCommandResp execute = updateUserCommand.execute(strategy, updateUserStrategyDTO);
        if (execute == null) {
            return RestMessage.doSuccess(execute);
        }
        if (execute.getReturnCodesEnum() == null) {
            return RestMessage.doSuccess(execute);
        } else {
            ReturnCodesEnum returnCodesEnum = execute.getReturnCodesEnum();
            return RestMessage.error(returnCodesEnum.getMessage());
        }
    }


    @ApiOperation(value = "[重置密码]发送手机验证码")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public RestMessage sendCode(@RequestBody UpdateUserCommandReq req) {
        RestMessage restMessage = checkUser(req.getUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        //手机验证码发送
        String phone = req.getPhone();
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.phone.null"));
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.REGEX_FAILD_PHONE.getCode()), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
        }
        //根据手机号查找用户
        BaseUserInfo existUser = baseAdministratorsUserService.getUserByPhone(phone, null, null);
        if (existUser == null) {
            return RestMessage.error(ReturnCodesEnum.USERNAME_NOTEXISTS.getMessage());
        }
        //TODO 发送验证码
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.send.success"));
    }

    @ApiOperation(value = "刷新用户缓存")
    @RequestMapping(value = "/refreshUserRedis", method = RequestMethod.POST)
    public RestMessage refreshUserRedis(@RequestBody RefreshRedisUserDto req) {
        if (StringUtils.isBlank(req.getUserName())) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.account.null"));
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


}
