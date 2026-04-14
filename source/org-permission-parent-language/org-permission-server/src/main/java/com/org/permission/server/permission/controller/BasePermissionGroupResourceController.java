package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.dto.GroupPermissionDto;
import com.org.permission.server.permission.dto.InputGroupPermissionDto;
import com.org.permission.server.permission.dto.InputGroupResourceDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.enums.ValidEnum;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.IBasePermissionGroupResourceService;
import com.org.permission.server.permission.service.impl.BasePermissionResourceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 集团的功能权限权限表管理
 */
@Api(tags = "1权限-集团的功能权限表管理接口文档")
@ApiOperation(value = "")
    @RequestMapping(value = "permission-group-resource")
@RestController
public class BasePermissionGroupResourceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePermissionResourceServiceImpl.class);

    @Autowired
    private IBasePermissionGroupResourceService basePermissionGroupResourceService;

    // 增加集团的功能权限权限表
    @ApiOperation(value = "增加集团的功能权限权限表")
    @PostMapping(value = "/adjustGroupResources")
    public RestMessage adjustGroupResources(@RequestBody InputGroupResourceDto inputGroupResourceDto) {
        try {
            List<Long> addList = Lists.newArrayList();
            List<Long> delList = Lists.newArrayList();
            BasePermissionGroupResource basePermissionGroupResource = new BasePermissionGroupResource();
            basePermissionGroupResource.setGroupId(inputGroupResourceDto.getGroupId());
            List<BasePermissionGroupResource> basePermissionGroupResources = basePermissionGroupResourceService.getListBasePermissionGroupResourcesByPOJO(basePermissionGroupResource);
            List<Long> funcList = inputGroupResourceDto.getFuncList();
            for (BasePermissionGroupResource bpgr : basePermissionGroupResources) {
                boolean anyMatch = funcList.stream().anyMatch(e -> e.equals(bpgr.getPermissionId()));
                LOGGER.info("新选中功能权限里面没有集团中原来的" + anyMatch);
                if (!anyMatch) {
                    delList.add(bpgr.getPermissionId());
                }
            }

            for (Long funcId : funcList) {
                boolean anyMatch = basePermissionGroupResources.stream().anyMatch(e -> e.getPermissionId().equals(funcId));
                System.out.println("集团中原来的功能权限里面没有新的" + anyMatch);
                if (!anyMatch) {
                    addList.add(funcId);
                }
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("userId", inputGroupResourceDto.getUserId());
            map.put("groupId", inputGroupResourceDto.getGroupId());
            map.put("delList", delList);
            map.put("addList", addList);
            map.put("createdDate", new Date());
            map.put("createdName", "System");
            map.put("createdBy", inputGroupResourceDto.getUserId());
            map.put("modifiedBy", inputGroupResourceDto.getUserId());
            map.put("modifiedName", "System");
            map.put("modifiedDate", new Date());
            map.put("state", ValidEnum.YES.getCode());
            if (delList.size() > 0 || addList.size() > 0) {
                basePermissionGroupResourceService.updateGroupResource(map);
            }
            return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error("5111", I18nUtils.getMessage("org.common.fail"));
        }

    }

    // 获取集团下的功能权限树 是否启用
    @ApiOperation(value = "获取集团下的功能权限树")
    @PostMapping(value = "/getGroupFuncTree")
    public RestMessage<Map<String, List<GroupPermissionDto>>> getGroupFuncTree(@RequestParam(required = true) Integer groupId, @RequestParam(required = false, defaultValue = "") String subject, @RequestParam(required = false, defaultValue = "") String type, @RequestParam(required = false, defaultValue = "") String resourceName) {
        long begin = System.currentTimeMillis();
        Map<String, Object> map = Maps.newHashMap();
        map.put("groupId", groupId);
        map.put("subject", subject);
        map.put("type", type);
        map.put("resourceName", resourceName);
        Map<String, List<GroupPermissionDto>> result = basePermissionGroupResourceService.getGroupFuncTree(map);
        LOGGER.info("集团树耗时----->" + (System.currentTimeMillis() - begin));
        return RestMessage.doSuccess(result);
    }

    // 根据id更新集团功能权限是否启用
    @ApiOperation(value = "根据id更新集团功能权限是否启用")
    @PostMapping(value = "/updateGroupFunc")
    public RestMessage<Integer> updateGroupFunc(@RequestBody List<InputGroupPermissionDto> inputGroupPermissionDtos) {
        int result = 0;
        for (InputGroupPermissionDto inputGroupPermissionDto : inputGroupPermissionDtos) {
            BasePermissionGroupResource basePermissionGroupResource = new BasePermissionGroupResource();
            basePermissionGroupResource.setId(inputGroupPermissionDto.getId());
            basePermissionGroupResource.setState(inputGroupPermissionDto.getState());
            result = basePermissionGroupResourceService.updateBasePermissionGroupResource(basePermissionGroupResource);
        }
        return RestMessage.doSuccess(result);

    }
}
