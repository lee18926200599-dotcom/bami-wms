package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.vo.BasePermissionPropertyVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionProperty;
import com.org.permission.server.permission.service.IBasePermissionPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限体系配置表（域名首页url配置在这里）管理
 */
@Api(tags = "1权限-权限体系配置表（域名首页url配置在这里）管理接口文档")
@RequestMapping(value = "permission-property")
@RestController
public class BasePermissionPropertyController {
    @Autowired
    private IBasePermissionPropertyService basePermissionPropertyService;

    //增加权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "增加权限体系配置表")
    @PostMapping(value = "/addBasePermissionProperty")
    public RestMessage addBasePermissionProperty(@RequestBody BasePermissionProperty base_permission_property) {
        int result = basePermissionPropertyService.addBasePermissionProperty(base_permission_property);
        return RestMessage.doSuccess(result);
    }


    //删除权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "删除权限体系配置表")
    @GetMapping(value = "/delBasePermissionProperty/{Id}")
    public RestMessage delBasePermissionProperty(@PathVariable Integer Id) {
        int result = basePermissionPropertyService.delBasePermissionProperty(Id);
        return RestMessage.doSuccess(result);
    }


    //修改权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "修改权限体系配置表")
    @PostMapping(value = "/updateBasePermissionProperty")
    public RestMessage updateBasePermissionProperty(@RequestBody BasePermissionProperty base_permission_property) {
        int result = basePermissionPropertyService.updateBasePermissionProperty(base_permission_property);
        return RestMessage.doSuccess(result);
    }


    //分页查询权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "分页查询权限体系配置表")
    @PostMapping(value = "/page/getListBasePermissionPropertysByPage")
    public RestMessage getListBasePermissionPropertysByPage(@RequestBody BasePermissionPropertyVo base_permission_property) {
        List result = basePermissionPropertyService.getListBasePermissionPropertysByPage(base_permission_property);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }


    //不分页查询权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "不分页查询权限体系配置表")
    @PostMapping(value = "/getListBasePermissionPropertysByPOJO")
    public RestMessage getListBasePermissionPropertysByPOJO(@RequestBody BasePermissionProperty base_permission_property) {
        List result = basePermissionPropertyService.getListBasePermissionPropertysByPOJO(base_permission_property);
        return RestMessage.doSuccess(result);
    }


    //查询权限体系配置表（域名首页url配置在这里）
    @ApiOperation(value = "查询权限体系配置表")
    @GetMapping(value = "/getBasePermissionPropertyById/{Id}")
    public RestMessage getBasePermissionPropertyById(@PathVariable Integer Id) {
        BasePermissionProperty result = basePermissionPropertyService.getBasePermissionPropertyById(Id);
        return RestMessage.doSuccess(result);
    }

}

