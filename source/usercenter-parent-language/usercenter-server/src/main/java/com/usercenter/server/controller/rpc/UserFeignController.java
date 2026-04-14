package com.usercenter.server.controller.rpc;

import com.common.util.message.RestMessage;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.request.UserTokenParam;
import com.usercenter.server.controller.AbstractUserController;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.domain.service.PermissionDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/rpc/user")
@Api(tags = "用户管理", description = "用户管理")
public class UserFeignController extends AbstractUserController {

    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private DictionaryDomainService dictionaryDomainService;

    @Resource
    private PermissionDomainService permissionDomainService;
    @PostMapping(value = "/getUserByToken")
    @ApiOperation(value = "根据token获取用户信息（groupId切换集团）", httpMethod = "POST")
    @ApiImplicitParam(name = "param", value = "参数", required = true, paramType = "body", dataType = "UserTokenParam")
    public RestMessage<FplUser> getUserByToken(@RequestBody UserTokenParam param) {
        FplUser fplUser = userApiService.getUserByToken(param.getToken(), param.getGroupId());
        return RestMessage.doSuccess(fplUser);
    }
}
