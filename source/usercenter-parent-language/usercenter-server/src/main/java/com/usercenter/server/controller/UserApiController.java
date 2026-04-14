package com.usercenter.server.controller;


import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.org.permission.common.permission.dto.GetUserMenuPermissionsDto;
import com.usercenter.common.dto.request.GetMenusByPhoneReq;
import com.usercenter.common.dto.request.UserApiReq;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.service.IBaseUserApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户Api接口", description = "用户Api接口")
public class UserApiController extends BaseController {

    @Autowired
    protected IBaseUserApiService userApiService;

    @RequestMapping(value = "/getMenusByPhone", method = RequestMethod.POST)
    @ApiOperation(value = "根据手机号获取用户菜单权限", httpMethod = "POST")
    public RestMessage<List<GetUserMenuPermissionsDto>>  getMenusByPhone(@RequestBody UserApiReq<GetMenusByPhoneReq> getMenusByPhoneReq) {
        AssertUtils.notNull(getMenusByPhoneReq.getRequestBody(), ReturnCodesEnum.PARAM_BODY_NULL.getMessage());
        AssertUtils.notNull(getMenusByPhoneReq.getRequestBody().getPhone(), ReturnCodesEnum.PARAM_PHONE_NULL.getMessage());
        AssertUtils.notNull(getMenusByPhoneReq.getRequestBody().getSourceTypeEnum(), ReturnCodesEnum.PARAM_SOURCE_ERROR.getMessage());
        GetMenusByPhoneReq requestBody = userReqHandler.getRequestBody(GetMenusByPhoneReq.class, getMenusByPhoneReq, "getMenusByPhone");
        List<GetUserMenuPermissionsDto> menus = userApiService.getMenusByPhone(requestBody);
        return RestMessage.doSuccess(menus);
    }
}
