package com.usercenter.server.controller;


import com.common.util.message.RestMessage;
import com.usercenter.server.domain.vo.req.globaluser.GlobalUserReq;
import com.usercenter.server.domain.vo.req.globaluser.UpdateGlobalReq;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.service.IBaseUserGlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 全局用户
 */
@Api(tags = "全局用户接口", description = "全局用户接口")
@RestController
@RequestMapping("/globalUser")
public class GlobalUserController {

    @Autowired
    private IBaseUserGlobalService baseUserGlobalService;

    /**
     * 全局用户列表
     * @return
     */
    @ApiOperation(value = "获取分页", httpMethod = "POST")
    @RequestMapping(value = "/list",method =RequestMethod.POST)
    public RestMessage<PageResult<BaseUser>> getGlobalUserList(@RequestBody GlobalUserReq globalUserReq){
        return RestMessage.doSuccess(baseUserGlobalService.getGlobalUserList(globalUserReq));
    }

    /**
     * 查询全局用户详情
    */
    @ApiOperation(value = "获取用户详情", httpMethod = "POST")
    @RequestMapping(value = "/getUserDetil",method =RequestMethod.POST)
    public RestMessage<BaseUser> getUserDetil(@RequestParam("userId") Long userId){
        return RestMessage.doSuccess(baseUserGlobalService.getUserDetail(userId));
    }

    /**
     全局用户修改(起停用, 锁定解锁)
     */
    @ApiOperation(value = "更新用户", httpMethod = "POST")
    @RequestMapping(value = "/updateUserGlobal",method =RequestMethod.POST)
    public RestMessage<Boolean> updateUserGlobal(@RequestBody UpdateGlobalReq updateGlobalReq){
        return RestMessage.doSuccess(baseUserGlobalService.updateUserGlobal(updateGlobalReq));
    }


}
