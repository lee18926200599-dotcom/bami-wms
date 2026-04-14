package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.vo.BasePermissionMenuVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionMenu;
import com.org.permission.server.permission.service.IBasePermissionMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户表中管理员和菜单关系表管理
 */
@Api(tags = "1权限-用户表中管理员和菜单关系表管理接口文档")
@RequestMapping(value = "permission-menu")
@RestController
public class BasePermissionMenuController {
    @Autowired
    private IBasePermissionMenuService basePermissionMenuService;

    @ApiOperation(value = "增加用户表中管理员和菜单关系表", httpMethod = "POST")
    @PostMapping(value = "/addBasePermissionMenu")
    public RestMessage addBasePermissionMenu(@RequestBody BasePermissionMenu basePermissionMenu) {
        basePermissionMenu.setCreatedDate(new Date());
        int result = basePermissionMenuService.addBasePermissionMenu(basePermissionMenu);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "删除用户表中管理员和菜单关系表", httpMethod = "GET")
    @GetMapping(value = "/delBasePermissionMenu/{Id}")
    public RestMessage delBasePermissionMenu(@PathVariable Integer Id) {
        int result = basePermissionMenuService.delBasePermissionMenu(Id);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "修改用户表中管理员和菜单关系表", httpMethod = "POST")
    @PostMapping(value = "/updateBasePermissionMenu")
    public RestMessage updateBasePermissionMenu(@RequestBody BasePermissionMenu basePermissionMenu) {
        int result = basePermissionMenuService.updateBasePermissionMenu(basePermissionMenu);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "分页查询用户表中管理员和菜单关系表", httpMethod = "POST")
    @PostMapping(value = "/page/getListBasePermissionMenusByPage")
    public RestMessage getListBasePermissionMenusByPage(@RequestBody BasePermissionMenuVo basePermissionMenuVo) {
        PageHelper.startPage(basePermissionMenuVo.getPageNum(), basePermissionMenuVo.getPageSize());
        List result = basePermissionMenuService.getListBasePermissionMenusByPage(basePermissionMenuVo);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }

    @ApiOperation(value = "不分页查询用户表中管理员和菜单关系表", httpMethod = "POST")
    @PostMapping(value = "/getListBasePermissionMenusByPOJO")
    public RestMessage getListBasePermissionMenusByPOJO(@RequestBody BasePermissionMenu basePermissionMenu) {
        List result = basePermissionMenuService.getListBasePermissionMenusByPOJO(basePermissionMenu);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "查询用户表中管理员和菜单关系表", httpMethod = "GET")
    @GetMapping(value = "/getBasePermissionMenuById/{Id}")
    public RestMessage getBasePermissionMenuById(@PathVariable Integer Id) {
        BasePermissionMenu result = basePermissionMenuService.getBasePermissionMenuById(Id);
        return RestMessage.doSuccess(result);
    }

}
