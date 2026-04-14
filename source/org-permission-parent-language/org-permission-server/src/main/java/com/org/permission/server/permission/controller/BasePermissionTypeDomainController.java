package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.vo.BasePermissionTypeDomainVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionTypeDomain;
import com.org.permission.server.permission.service.IBasePermissionTypeDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务类型和域名关系表（初始化表）管理
 */
@Api(tags = "1权限-业务类型和域名关系表（初始化表）管理接口文档")
@RequestMapping(value = "permission-type-domain")
@RestController
public class BasePermissionTypeDomainController {
    @Autowired
    private IBasePermissionTypeDomainService basePermissionTypeDomainService;

    //增加业务类型和域名关系表（初始化表）
    @ApiOperation(value = "增加业务类型和域名关系表（初始化表）")
    @PostMapping(value = "/addBasePermissionTypeDomain")
    public RestMessage addBasePermissionTypeDomain(@RequestBody BasePermissionTypeDomain base_permission_type_domain) {
        int result = basePermissionTypeDomainService.addBasePermissionTypeDomain(base_permission_type_domain);
        return RestMessage.doSuccess(result);
    }


    //删除业务类型和域名关系表（初始化表）
    @ApiOperation(value = "删除业务类型和域名关系表")
    @GetMapping(value = "/delBasePermissionTypeDomain/{Id}")
    public RestMessage delBasePermissionTypeDomain(@PathVariable Integer Id) {
        int result = basePermissionTypeDomainService.delBasePermissionTypeDomain(Id);
        return RestMessage.doSuccess(result);
    }


    //修改业务类型和域名关系表（初始化表）
    @ApiOperation(value = "修改业务类型和域名关系表")
    @PostMapping(value = "/updateBasePermissionTypeDomain")
    public RestMessage updateBasePermissionTypeDomain(@RequestBody BasePermissionTypeDomain base_permission_type_domain) {
        int result = basePermissionTypeDomainService.updateBasePermissionTypeDomain(base_permission_type_domain);
        return RestMessage.doSuccess(result);
    }


    //分页查询业务类型和域名关系表（初始化表）
    @ApiOperation(value = "分页查询业务类型和域名关系表")
    @PostMapping(value = "/page/getListBasePermissionTypeDomainsByPage")
    public RestMessage getListBasePermissionTypeDomainsByPage(@RequestBody BasePermissionTypeDomainVo base_permission_type_domain) {
        List result = basePermissionTypeDomainService.getListBasePermissionTypeDomainsByPage(base_permission_type_domain);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }


    //不分页查询业务类型和域名关系表（初始化表）
    @ApiOperation(value = "不分页查询业务类型和域名关系表")
    @PostMapping(value = "/getListBasePermissionTypeDomainsByPOJO")
    public RestMessage getListBasePermissionTypeDomainsByPOJO(@RequestBody BasePermissionTypeDomain base_permission_type_domain) {
        List result = basePermissionTypeDomainService.getListBasePermissionTypeDomainsByPOJO(base_permission_type_domain);
        return RestMessage.doSuccess(result);
    }


    //查询业务类型和域名关系表（初始化表）
    @ApiOperation(value = "查询业务类型和域名关系表")
    @GetMapping(value = "/getBasePermissionTypeDomainById/{Id}")
    public RestMessage getBasePermissionTypeDomainById(@PathVariable Integer Id) {
        BasePermissionTypeDomain result = basePermissionTypeDomainService.getBasePermissionTypeDomainById(Id);
        return RestMessage.doSuccess(result);
    }

}

