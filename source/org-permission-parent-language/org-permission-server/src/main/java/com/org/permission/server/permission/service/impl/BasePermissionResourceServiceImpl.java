package com.org.permission.server.permission.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.common.base.enums.BooleanEnum;
import com.google.common.collect.Lists;
import com.org.permission.server.permission.dto.PermissionResourceDto;
import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.enums.ResourceTypeEnum;
import com.org.permission.server.permission.mapper.BasePermissionResourceMapper;
import com.org.permission.server.permission.service.IBasePermissionResourceService;
import com.org.permission.server.permission.vo.BasePermissionResourceVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * base_permission_resourceServiceImpl类 功能权限资源表(tree)管理
 */
@Service
public class BasePermissionResourceServiceImpl implements IBasePermissionResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePermissionResourceServiceImpl.class);
    @Autowired
    private BasePermissionResourceMapper dao;

    @Transactional
    public int addBasePermissionResource(BasePermissionResource base_permission_resource) {
        if (base_permission_resource.getSource() == 1) {
            if(Objects.equals(ResourceTypeEnum.MENU.getCode(), base_permission_resource.getResourceType())){
                Map<Long, String> parentPlatformMap = new HashMap<>();
                if (MapUtils.isNotEmpty(parentPlatformMap)) {
                    dao.batchUpdatePlatformId(parentPlatformMap);
                }
            }
        }
        return dao.addBasePermissionResource(base_permission_resource);
    }

    public int delBasePermissionResource(Integer Id) {
        return dao.delBasePermissionResource(Id);
    }

    public int delBasePermissionResourceTrue(Integer Id) {
        return dao.delBasePermissionResourceTrue(Id);
    }

    public int updateBasePermissionResource(BasePermissionResource base_permission_resource) {
        return dao.updateBasePermissionResource(base_permission_resource);
    }

    public BasePermissionResource getBasePermissionResourceById(Long Id) {
        return dao.getBasePermissionResourceByID(Id);
    }

    public int getBasePermissionResourceCount() {
        return dao.getBasePermissionResourceCount();
    }

    public int getBasePermissionResourceCountAll() {
        return dao.getBasePermissionResourceCountAll();
    }

    public List<BasePermissionResource> getListBasePermissionResourcesByPage(BasePermissionResourceVo base_permission_resource) {
        return dao.getListBasePermissionResourcesByPage(base_permission_resource);
    }

    public List<BasePermissionResource> getListBasePermissionResourcesByPOJO(BasePermissionResource base_permission_resource) {
        return dao.getListBasePermissionResourcesByPOJO(base_permission_resource);
    }


    @Override
    public Map<String, List<ResourceDto>> getBasePermissionResources(List<BasePermissionResource> basePermissionResources, List<BasePermissionGroupResource> basePermissionGroupResources, Integer queryPage) {
        List<ResourceDto> datas = Lists.newArrayList();
        if (new Integer(1).equals(queryPage)) {
            //提前过滤填充子节点数据
            basePermissionResources = basePermissionResources.stream().filter(resource -> basePermissionGroupResources.stream().map(groupResource -> groupResource.getPermissionId()).collect(Collectors.toList()).contains(resource.getId())).collect(Collectors.toList());
        }
        for (BasePermissionResource basePermissionResource : basePermissionResources) {
            if (basePermissionResource.getParentId().equals(0L)) {
                boolean anyMatch = basePermissionGroupResources.stream().anyMatch(e -> e.getPermissionId().equals(basePermissionResource.getId()));
                ResourceDto data = new ResourceDto();
                BeanUtils.copyProperties(basePermissionResource, data);
                data.setHidden(Objects.equals(basePermissionResource.getHidden(), BooleanEnum.TRUE.getCode()));
                data.setKeepAlive(Objects.equals(basePermissionResource.getKeepAlive(), BooleanEnum.TRUE.getCode()));
                data.setLeafFlag(Objects.equals(basePermissionResource.getLeafFlag(), BooleanEnum.TRUE.getCode()));
                data.setNumber(basePermissionResource.getNumber());
                if (anyMatch) {
                    LOGGER.info("与集团下功能匹配的打勾----->{},{}", anyMatch, basePermissionResource.getId());
                    data.setCheck(true);
                } else {
                    data.setCheck(false);
                }
                datas.add(data);
                build(basePermissionResources, basePermissionGroupResources, data);
            }
        }
        Map<String, List<ResourceDto>> grouped = datas.stream().collect(Collectors.groupingBy(resourceDto -> ResourceMenuTypeEnum.getEnum(resourceDto.getType()).name()));
        return grouped;
    }

    /*
     * 构造功能树
     */
    public void build(List<BasePermissionResource> basePermissionResources, List<BasePermissionGroupResource> basePermissionGroupResources, ResourceDto data) {
        List<ResourceDto> childs = getChildren(basePermissionResources, basePermissionGroupResources, data);
        data.setChildren(childs);
        for (ResourceDto resourceDto : childs) {
            build(basePermissionResources, basePermissionGroupResources, resourceDto);
        }
    }

    /*
     * 每个根节点下的列表
     */
    private List<ResourceDto> getChildren(List<BasePermissionResource> basePermissionResources, List<BasePermissionGroupResource> basePermissionGroupResources, ResourceDto data) {
        List<ResourceDto> children = new ArrayList<ResourceDto>();
        for (BasePermissionResource child : basePermissionResources) {
            if (child.getParentId().intValue() == data.getId().intValue()) {
                boolean anyMatch = basePermissionGroupResources.stream().anyMatch(e -> e.getPermissionId().equals(child.getId()));
                ResourceDto resourceDto = BeanUtil.copyProperties(child, ResourceDto.class);
                resourceDto.setHidden(Objects.equals(child.getHidden(), BooleanEnum.TRUE.getCode()));
                resourceDto.setKeepAlive(Objects.equals(child.getKeepAlive(), BooleanEnum.TRUE.getCode()));
                resourceDto.setLeafFlag(Objects.equals(child.getLeafFlag(), BooleanEnum.TRUE.getCode()));
                resourceDto.setNumber(child.getNumber());
                if (anyMatch) {
                    LOGGER.info("getchildren与集团下功能匹配的打勾----->{},{}", anyMatch, child.getId());
                    resourceDto.setCheck(true);
                } else {
                    resourceDto.setCheck(false);
                }
                children.add(resourceDto);
            }
        }
        return children;
    }


    /**
     * 菜单配置页面数据组装
     *
     * @param list
     * @return
     */
    public List<PermissionResourceDto> getWebTreeList(List<BasePermissionResource> list) {
        List<PermissionResourceDto> dtoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return dtoList;
        }
        List<Long> pidList = list.stream().map(BasePermissionResource::getId).collect(Collectors.toList());
        List<BasePermissionResource> parentList = dao.getListByPid(pidList);
        Map<Long, List<BasePermissionResource>> map = parentList.stream().collect(Collectors.groupingBy(BasePermissionResource::getParentId));
        for (BasePermissionResource basePermissionResource : list) {
            PermissionResourceDto permissionResourceDto = BeanUtil.copyProperties(basePermissionResource, PermissionResourceDto.class);
            permissionResourceDto.setChildren(getWebTreeList(map.get(basePermissionResource.getId())));
            dtoList.add(permissionResourceDto);
        }
        return dtoList;
    }
}
