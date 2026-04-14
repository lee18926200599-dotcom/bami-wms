package com.org.permission.server.permission.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.org.permission.server.permission.vo.BasePermissionTypeResourceVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionTypeResource;
import com.org.permission.server.permission.service.IBasePermissionTypeResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务类型域名功能权限资源表(tree)管理
 */
@RequestMapping(value = "permission-type-resource")
@Api(tags = "1权限-业务类型域名功能权限资源表(tree)管理接口文档")
@RestController
public class BasePermissionTypeResourceController {
    @Autowired
    private IBasePermissionTypeResourceService basePermissionTypeResourceService;

    //增加业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "增加业务类型域名功能权限资源表(tree)")
    @PostMapping(value = "/addBasePermissionTypeResource")
    public RestMessage addBasePermissionTypeResource(@RequestBody BasePermissionTypeResource base_permission_type_resource) {
        int result = basePermissionTypeResourceService.addBasePermissionTypeResource(base_permission_type_resource);
        return RestMessage.doSuccess(result);
    }


    //删除业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "删除业务类型域名功能权限资源表(tree)")
    @GetMapping(value = "/delBasePermissionTypeResource/{Id}")
    public RestMessage delBasePermissionTypeResource(@PathVariable Integer Id) {
        int result = basePermissionTypeResourceService.delBasePermissionTypeResource(Id);
        return RestMessage.doSuccess(result);
    }


    //修改业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "修改业务类型域名功能权限资源表(tree)")
    @PostMapping(value = "/updateBasePermissionTypeResource")
    public RestMessage updateBasePermissionTypeResource(@RequestBody BasePermissionTypeResource base_permission_type_resource) {
        int result = basePermissionTypeResourceService.updateBasePermissionTypeResource(base_permission_type_resource);
        return RestMessage.doSuccess(result);
    }


    //分页查询业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "分页查询业务类型域名功能权限资源表(tree)")
    @PostMapping(value = "/page/getListBasePermissionTypeResourcesByPage")
    public RestMessage getListBasePermissionTypeResourcesByPage(@RequestBody BasePermissionTypeResourceVo base_permission_type_resource) {
        List result = basePermissionTypeResourceService.getListBasePermissionTypeResourcesByPage(base_permission_type_resource);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }


    //不分页查询业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "不分页查询业务类型域名功能权限资源表(tree)")
    @PostMapping(value = "/getListBasePermissionTypeResourcesByPOJO")
    public RestMessage getListBasePermissionTypeResourcesByPOJO(@RequestBody BasePermissionTypeResource base_permission_type_resource) {
        List result = basePermissionTypeResourceService.getListBasePermissionTypeResourcesByPOJO(base_permission_type_resource);
        return RestMessage.doSuccess(result);
    }


    //查询业务类型域名功能权限资源表(tree)
    @ApiOperation(value = "查询业务类型域名功能权限资源表(tree)")
    @GetMapping(value = "/getBasePermissionTypeResourceById/{Id}")
    public RestMessage getBasePermissionTypeResourceById(@PathVariable Integer Id) {
        BasePermissionTypeResource result = basePermissionTypeResourceService.getBasePermissionTypeResourceById(Id);
        return RestMessage.doSuccess(result);
    }


    @ApiOperation(value = "根据权限id获取支持的客户业务类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "permissionId", value = "权限id", required = true, paramType = "query", dataType = "int"),})
    @GetMapping(value = "/getBusinessTypesByPermissionId")
    public RestMessage getBusinessTypesByPermissionId(@RequestParam("permissionId") Long permissionId) {
        BasePermissionTypeResource query = new BasePermissionTypeResource();
        query.setPermissionId(permissionId);
        List<BasePermissionTypeResource> result = basePermissionTypeResourceService.getListBasePermissionTypeResourcesByPOJO(query);
        if (ObjectUtils.isEmpty(result)) {
            return RestMessage.doSuccess(Lists.newArrayList());
        }
        return RestMessage.doSuccess(result.stream().map(basePermissionTypeResource -> basePermissionTypeResource.getBusinessTypeId()).collect(Collectors.toList()));
    }

}

