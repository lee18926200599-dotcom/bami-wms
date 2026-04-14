package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.vo.BasePermissionUserFuncVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.service.IBasePermissionUserFuncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户权限关联表管理
 */
@Api(tags = "1权限-用户权限关联表管理接口文档")
@RequestMapping(value = "permission-user-func")
@RestController
public class BasePermissionUserFuncController {
    @Autowired
    private IBasePermissionUserFuncService basePermissionUserFuncService;

    // 增加用户权限关联表
    @ApiOperation(value = "增加用户权限关联表")
    @PostMapping(value = "/addBasePermissionUserFunc")
    public RestMessage addBasePermissionUserFunc(@RequestBody BasePermissionUserFunc basePermissionUserFunc) {
        basePermissionUserFunc.setCreatedDate(new Date());
        int result = basePermissionUserFuncService.addBasePermissionUserFunc(basePermissionUserFunc);
        return RestMessage.doSuccess(result);
    }

    // 删除用户权限关联表
    @ApiOperation(value = "删除用户权限关联表")
    @GetMapping(value = "/delBasePermissionUserFunc/{Id}")
    public RestMessage delBasePermissionUserFunc(@PathVariable Integer Id) {
        int result = basePermissionUserFuncService.delBasePermissionUserFunc(Id);
        return RestMessage.doSuccess(result);
    }

    // 修改用户权限关联表
    @ApiOperation(value = "修改用户权限关联表")
    @PostMapping(value = "/updateBasePermissionUserFunc")
    public RestMessage updateBasePermissionUserFunc(@RequestBody BasePermissionUserFunc basePermissionUserFunc) {
        basePermissionUserFunc.setModifiedDate(new Date());
        int result = basePermissionUserFuncService.updateBasePermissionUserFunc(basePermissionUserFunc);
        return RestMessage.doSuccess(result);
    }

    // 分页查询用户权限关联表
    @ApiOperation(value = "分页查询用户权限关联表")
    @PostMapping(value = "/page/getListBasePermissionUserFuncsByPage")
    public RestMessage getListBasePermissionUserFuncsByPage(@RequestBody BasePermissionUserFuncVo basePermissionUserFuncVo) {
        PageHelper.startPage(basePermissionUserFuncVo.getPageNum(), basePermissionUserFuncVo.getPageSize());
        List result = basePermissionUserFuncService.getListBasePermissionUserFuncsByPage(basePermissionUserFuncVo);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }

    // 不分页查询用户权限关联表
    @ApiOperation(value = "不分页查询用户权限关联表")
    @PostMapping(value = "/getListBasePermissionUserFuncsByPOJO")
    public RestMessage getListBasePermissionUserFuncsByPOJO(@RequestBody BasePermissionUserFunc base_permission_user_func) {
        List result = basePermissionUserFuncService.getListBasePermissionUserFuncsByPOJO(base_permission_user_func);
        return RestMessage.doSuccess(result);
    }

    // 查询用户权限关联表
    @ApiOperation(value = "查询用户权限关联表")
    @GetMapping(value = "/getBasePermissionUserFuncById/{Id}")
    public RestMessage getBasePermissionUserFuncById(@PathVariable Integer Id) {
        BasePermissionUserFunc result = basePermissionUserFuncService.getBasePermissionUserFuncById(Id);
        return RestMessage.doSuccess(result);
    }

}
