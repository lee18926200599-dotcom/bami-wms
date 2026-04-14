package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.vo.BasePermissionDataVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionData;
import com.org.permission.server.permission.service.IBasePermissionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 数据权限资源表管理
 */
@Api(tags = "1权限-数据权限资源表管理接口文档")
@RequestMapping(value = "/permission-data")
@RestController
public class BasePermissionDataController {
    @Autowired
    private IBasePermissionDataService basePermissionDataService;

    @ApiOperation(value = "增加数据权限资源表", httpMethod = "POST")
    @PostMapping(value = "/addBasePermissionData")
    public RestMessage addBasePermissionData(@RequestBody BasePermissionData basePermissionData) {
        basePermissionData.setCreatedDate(new Date());
        int result = basePermissionDataService.addBasePermissionData(basePermissionData);
        return RestMessage.doSuccess(result);
    }


    //删除数据权限资源表
    @ApiOperation(value = "删除数据权限资源表", httpMethod = "GET")
    @GetMapping(value = "/delBasePermissionData/{Id}")
    public RestMessage delBasePermissionData(@PathVariable Integer Id) {
        int result = basePermissionDataService.delBasePermissionData(Id);
        return RestMessage.doSuccess(result);
    }


    //修改数据权限资源表
    @ApiOperation(value = "修改数据权限资源表", httpMethod = "POST")
    @PostMapping(value = "/updateBasePermissionData")
    public RestMessage updateBasePermissionData(@RequestBody BasePermissionData basePermissionData) {
        basePermissionData.setModifiedDate(new Date());
        int result = basePermissionDataService.updateBasePermissionData(basePermissionData);
        return RestMessage.doSuccess(result);
    }


    //分页查询数据权限资源表
    @ApiOperation(value = "分页查询数据权限资源表", httpMethod = "POST")
    @PostMapping(value = "/page/getListBasePermissionDatasByPage")
    public RestMessage getListBasePermissionDatasByPage(@RequestBody BasePermissionDataVo base_permission_data) {
        List result = basePermissionDataService.getListBasePermissionDatasByPage(base_permission_data);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }


    //不分页查询数据权限资源表
    @ApiOperation(value = "不分页查询数据权限资源表", httpMethod = "POST")
    @PostMapping(value = "/getListBasePermissionDatasByPOJO")
    public RestMessage getListBasePermissionDatasByPOJO(@RequestBody BasePermissionData base_permission_data) {
        List result = basePermissionDataService.getListBasePermissionDatasByPOJO(base_permission_data);
        return RestMessage.doSuccess(result);
    }


    //查询数据权限资源表
    @ApiOperation(value = "查询数据权限资源表")
    @GetMapping(value = "/getBasePermissionDataById/{Id}")
    public RestMessage getBasePermissionDataById(@PathVariable Integer Id) {
        BasePermissionData result = basePermissionDataService.getBasePermissionDataById(Id);
        return RestMessage.doSuccess(result);
    }

}

