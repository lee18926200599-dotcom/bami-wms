package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.command.dto.PermissionDto;
import com.org.permission.server.permission.service.command.enums.PermissionStrategyEnum;
import com.org.permission.server.permission.service.command.interfaces.PermissionStrategyService;
import com.org.permission.server.permission.service.command.utils.BeanFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户停用启用对权限的操作
 */
@Api(tags = "1权限-用户停用启用对权限的操作接口文档")
@RequestMapping(value = "permission-for-user")
@RestController
public class PermissionForUserController {
    private final static Logger LOG = LoggerFactory.getLogger(PermissionForUserController.class);
    @Autowired
    private BeanFactory beanFactory;

    /**
     * 启用用户
     * 停用用户
     * 启用管理员
     * 停用管理员
     * 四种操作，权限的变化接口
     *
     * @param permissionDto
     * @return
     */
    @ApiOperation(value = "启用用户、停用用户、启用管理员、停用管理员，四种操作，权限的变化接口")
    @PostMapping(value = "/optionUser")
    public RestMessage optionUser(@RequestBody PermissionDto permissionDto) {
        RestMessage restMessage = checkPermissionDto(permissionDto);
        if (!restMessage.isSuccess()) {
            return restMessage;
        }
        //获取策略
        PermissionStrategyService permissionStrategyService = beanFactory.getBean(PermissionStrategyEnum.getBeanByStrategy(permissionDto.getStrategy()), PermissionStrategyService.class);
        //根据策略，获取操作权限方式
        boolean result = false;
        try {
            result = permissionStrategyService.optionPermission(permissionDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(result);
    }

    /**
     * 校验入参有效性
     *
     * @param permissionDto
     * @return
     */
    private RestMessage checkPermissionDto(PermissionDto permissionDto) {
        if (permissionDto.getLoginUserId() <= 0 || permissionDto.getLoginUserId() == null) {
            return RestMessage.error(PermissionErrorCode.LOGIN_PARAM_ERROR.getErrorCode() + "", PermissionErrorCode.LOGIN_PARAM_ERROR.getErrorReason());
        } else if (permissionDto.getStrategy() == 1 || permissionDto.getStrategy() == 2) {
            if (permissionDto.getGroupId() == null && permissionDto.getGroupId() <= 0) {
                return RestMessage.error(PermissionErrorCode.STRATEGY_USER_ERROR.getErrorCode() + "", PermissionErrorCode.STRATEGY_USER_ERROR.getErrorReason());
            }
        } else if (permissionDto.getStrategy() == 3 || permissionDto.getStrategy() == 4) {
            if (permissionDto.getGroupId() > 0) {
                return RestMessage.error(PermissionErrorCode.STRATEGY_ADMIN_ERROR.getErrorCode() + "", PermissionErrorCode.STRATEGY_ADMIN_ERROR.getErrorReason());
            }
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }


}
