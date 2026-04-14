package com.org.permission.server.permission.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.dto.PermissionResourceDto;
import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.vo.BasePermissionResourceVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.IBasePermissionGroupResourceService;
import com.org.permission.server.permission.service.IBasePermissionResourceService;
import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 功能权限资源表(tree)管理
 */
@Api(tags = "1权限-功能权限资源表管理接口文档")
@RequestMapping(value = "permission-resource")
@RestController
public class BasePermissionResourceController {
    @Autowired
    private IBasePermissionResourceService basePermissionResourceService;
    @Autowired
    private IBasePermissionGroupResourceService basePermissionGroupResourceService;

    // 查所有资源权限树
    @ApiOperation(value = "查所有资源权限树")
    @PostMapping(value = "/getResources")
    public RestMessage<Map<String, List<ResourceDto>>> getResources(@RequestParam Long groupId) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setState(ValidEnum.YES.getCode());
        List<BasePermissionResource> basePermissionResources = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        BasePermissionGroupResource basePermissionGroupResource = new BasePermissionGroupResource();
        basePermissionGroupResource.setGroupId(groupId);
        List<BasePermissionGroupResource> basePermissionGroupResources = basePermissionGroupResourceService.getListBasePermissionGroupResourcesByPOJO(basePermissionGroupResource);
        Map<String, List<ResourceDto>> grouped = basePermissionResourceService.getBasePermissionResources(basePermissionResources, basePermissionGroupResources, 0);
        Map<String, List<ResourceDto>> result = Maps.newHashMap();
        List<ResourceDto> pcPermissions = Lists.newArrayList();
        List<ResourceDto> appPermissions = Lists.newArrayList();
        for (String key : grouped.keySet()) {
            if (key.equals(ResourceMenuTypeEnum.PC.name())) {
                pcPermissions.addAll(grouped.get("PC"));
            }
            if (key.equals(ResourceMenuTypeEnum.APP.name())) {
                appPermissions.addAll(grouped.get("APP"));
            }
            if (key.equals(ResourceMenuTypeEnum.RF.name())) {
                appPermissions.addAll(grouped.get("RF"));
            }
        }

        result.put("PC", pcPermissions);
        result.put("APP", appPermissions);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "新增菜单资源")
    @PostMapping(value = "/add")
    public RestMessage<Boolean> add(@RequestBody PermissionResourceDto permissionResourceDto) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        BeanUtils.copyProperties(permissionResourceDto, basePermissionResource);
        basePermissionResource.setCreatedBy(FplUserUtil.getUserId());
        basePermissionResource.setCreatedName(FplUserUtil.getUserName());
        basePermissionResource.setCreatedDate(new Date());
        basePermissionResource.setState(StateEnum.ENABLE.getCode());
        basePermissionResource.setDeletedFlag(0);
        Integer result = basePermissionResourceService.addBasePermissionResource(basePermissionResource);
        return RestMessage.doSuccess(result > 0);
    }

    @ApiOperation(value = "修改菜单资源")
    @PostMapping(value = "/update")
    public RestMessage<Boolean> update(@RequestBody PermissionResourceDto permissionResourceDto) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        BeanUtils.copyProperties(permissionResourceDto, basePermissionResource);
        basePermissionResource.setModifiedBy(FplUserUtil.getUserId());
        basePermissionResource.setModifiedName(FplUserUtil.getUserName());
        basePermissionResource.setModifiedDate(new Date());
        Integer result = basePermissionResourceService.updateBasePermissionResource(basePermissionResource);
        return RestMessage.doSuccess(result > 0);
    }

    @ApiOperation(value = "查询菜单资源,tree结构")
    @PostMapping(value = "/getList")
    public RestMessage<PageInfo<PermissionResourceDto>> getList(@RequestBody BasePermissionResourceVo permissionResourceVo) {
        PageHelper.startPage(permissionResourceVo.getPageNum(), permissionResourceVo.getPageSize());
        permissionResourceVo.setParentId(0L);
        List<BasePermissionResource> list = basePermissionResourceService.getListBasePermissionResourcesByPage(permissionResourceVo);
        PageInfo<BasePermissionResource> pageInfo = new PageInfo<>(list);
        List<PermissionResourceDto> dtoList = basePermissionResourceService.getWebTreeList(list);
        PageInfo<PermissionResourceDto> dtoPageInfo = new PageInfo<>();
        BeanUtil.copyProperties(pageInfo, dtoPageInfo);
        dtoPageInfo.setList(dtoList);
        return RestMessage.doSuccess(dtoPageInfo);
    }
}
