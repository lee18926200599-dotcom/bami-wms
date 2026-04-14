package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.enums.ValidEnum;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.IBasePermissionGroupResourceService;
import com.org.permission.server.permission.service.IBasePermissionResourceService;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制类
 */
@Api(tags = "1权限-菜单控制类接口文档")
@RequestMapping(value = "menu")
@RestController
public class MenusController {
    private final static Logger LOG = LoggerFactory.getLogger(MenusController.class);

    @Autowired
    private IBasePermissionResourceService basePermissionResourceService;
    @Autowired
    private IBasePermissionGroupResourceService basePermissionGroupResourceService;

    /**
     * 给集团新增菜单权限
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "给集团新增菜单权限")
    @PostMapping(value = "/distribution/menus/add")
    public RestMessage addDistributionMenus(@RequestParam Long groupId) {
        List<BasePermissionGroupResource> list1 = getMenusByFlag(groupId, 2);
        if (ObjectUtils.isEmpty(list1)) {
            return RestMessage.error("1001", I18nUtils.getMessage("permission.menus.config.null"));
        }
        Map map = Maps.newHashMap(ImmutableMap.of("groupId", groupId, "list", list1));
        List<BasePermissionGroupResource> list2 = basePermissionGroupResourceService.getListBasePermissionGroupResources(map);
        insertGroupResourceList(list1, list2);
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    /**
     * 插入菜单
     *
     * @param list1
     * @param list2
     */
    private void insertGroupResourceList(List<BasePermissionGroupResource> list1, List<BasePermissionGroupResource> list2) {
        List<BasePermissionGroupResource> insertList = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(list1)) {
            if (!ObjectUtils.isEmpty(list2)) {
                for (BasePermissionGroupResource basePermissionGroupResource : list1) {
                    boolean anyMatch = list2.stream().anyMatch(e -> e.getPermissionId().equals(basePermissionGroupResource.getPermissionId()));
                    if (!anyMatch) {
                        LOG.info("插入的菜单id：{}", basePermissionGroupResource.getPermissionId());
                        insertList.add(basePermissionGroupResource);
                    }
                }
            } else {
                insertList = list1;
            }
            Map mapInsert = Maps.newHashMap(ImmutableMap.of("list", insertList));
            basePermissionGroupResourceService.batchAddDistributionMenus(mapInsert);
        }
    }

    /**
     * 根据集团id和菜单标识获取对应菜单集合
     *
     * @param groupId
     * @param menuFlag
     * @return
     */
    private List<BasePermissionGroupResource> getMenusByFlag(Long groupId, int menuFlag) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setResourceType(menuFlag);
        basePermissionResource.setState(ValidEnum.YES.getCode());
        List<BasePermissionResource> basePermissionResources = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        List<BasePermissionGroupResource> list = Lists.newArrayList();
        for (BasePermissionResource bpr : basePermissionResources) {
            BasePermissionGroupResource basePermissionGroupResource = new BasePermissionGroupResource();
            basePermissionGroupResource.setGroupId(groupId);
            basePermissionGroupResource.setPermissionId(bpr.getId());
            basePermissionGroupResource.setCreatedDate(new Date());
            basePermissionGroupResource.setCreatedBy(FplUserUtil.getUserId());
            basePermissionGroupResource.setModifiedDate(new Date());
            basePermissionGroupResource.setModifiedBy(1L);
            basePermissionGroupResource.setState(ValidEnum.YES.getCode());
            list.add(basePermissionGroupResource);
        }
        return list;
    }

    /**
     * 集团删除菜单权限
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "集团删除菜单权限")
    @PostMapping(value = "/distribution/menus/del")
    public RestMessage delDistributionMenus(@RequestParam Long groupId) {
        if (groupId > 0) {
            List<BasePermissionGroupResource> list = getMenusByFlag(groupId, 2);
            if (list.size() > 0) {
                Map map = Maps.newHashMap(ImmutableMap.of("groupId", groupId, "list", list));
                basePermissionGroupResourceService.delDistributionMenus(map);
            }
            return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
        } else {
            return RestMessage.error("5500", I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        }
    }


    /**
     * 给集团新增菜单权限
     *
     * @param groupId
     * @param menuFlag
     * @return
     */
    @ApiOperation(value = "给集团新增菜单权限")
    @PostMapping(value = "/menus/add")
    public RestMessage addMenus(@RequestParam Long groupId, @RequestParam int menuFlag) {
        List<BasePermissionGroupResource> list1 = getMenusByFlag(groupId, menuFlag);
        if (ObjectUtils.isEmpty(list1)) {
            return RestMessage.error("1001", I18nUtils.getMessage("permission.menus.config.null"));
        }
        Map map = Maps.newHashMap(ImmutableMap.of("groupId", groupId, "list", list1));
        List<BasePermissionGroupResource> list2 = basePermissionGroupResourceService.getListBasePermissionGroupResources(map);
        insertGroupResourceList(list1, list2);
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    /**
     * 集团删除菜单权限
     *
     * @param groupId
     * @param menuFlag
     * @return
     */
    @ApiOperation(value = "集团删除菜单权限")
    @PostMapping(value = "/menus/del")
    public RestMessage delMenus(@RequestParam Long groupId, @RequestParam int menuFlag) {
        if (groupId > 0 && menuFlag > 0) {
            List<BasePermissionGroupResource> list = getMenusByFlag(groupId, menuFlag);
            if (list.size() > 0) {
                Map map = Maps.newHashMap(ImmutableMap.of("groupId", groupId, "list", list));
                basePermissionGroupResourceService.delDistributionMenus(map);
            }
            return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
        } else {
            return RestMessage.error("1001", I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        }

    }


}
