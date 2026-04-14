package com.org.permission.server.permission.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.dto.GroupPermissionDto;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.vo.BasePermissionGroupResourceVo;
import com.org.permission.server.permission.mapper.BasePermissionGroupResourceMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupResourceService;
import com.usercenter.common.enums.SourceTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * base_permission_group_resourceServiceImpl类 集团的功能权限权限表管理
 */
@Service
public class BasePermissionGroupResourceServiceImpl implements IBasePermissionGroupResourceService {
    @Autowired
    private BasePermissionGroupResourceMapper dao;
    public static String[] FUNC_GROUP_PC = new String[]{SourceTypeEnum.PC.name()};
    public static String[] FUNC_GROUP_APPS = new String[]{SourceTypeEnum.APP.name(), SourceTypeEnum.RF.name()};

    public int addBasePermissionGroupResource(BasePermissionGroupResource base_permission_group_resource) {
        return dao.addBasePermissionGroupResource(base_permission_group_resource);
    }

    public int delBasePermissionGroupResource(Integer Id) {
        return dao.delBasePermissionGroupResource(Id);
    }

    public int delBasePermissionGroupResourceTrue(Integer Id) {
        return dao.delBasePermissionGroupResourceTrue(Id);
    }

    public int updateBasePermissionGroupResource(BasePermissionGroupResource base_permission_group_resource) {
        return dao.updateBasePermissionGroupResource(base_permission_group_resource);
    }

    public BasePermissionGroupResource getBasePermissionGroupResourceById(Integer Id) {
        return dao.getBasePermissionGroupResourceByID(Id);
    }

    public int getBasePermissionGroupResourceCount() {
        return dao.getBasePermissionGroupResourceCount();
    }

    public int getBasePermissionGroupResourceCountAll() {
        return dao.getBasePermissionGroupResourceCountAll();
    }

    public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPage(BasePermissionGroupResourceVo base_permission_group_resource) {
        return dao.getListBasePermissionGroupResourcesByPage(base_permission_group_resource);
    }

    public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPOJO(BasePermissionGroupResource base_permission_group_resource) {
        return dao.getListBasePermissionGroupResourcesByPOJO(base_permission_group_resource);
    }

    public List<BasePermissionGroupResource> getListBasePermissionGroupResourcesByPojoPage(BasePermissionGroupResource base_permission_group_resource) {
        Map map = new HashMap();
        map.put("pojo", base_permission_group_resource);
        return dao.getListBasePermissionGroupResourcesByPojoPage(map);
    }

    @Override
    public void updateGroupResource(Map<String, Object> map) {

        dao.updateGroupResource(map);
    }

    @Override
    public Map<String, List<GroupPermissionDto>> getGroupFuncTree(Map<String, Object> map) {
        List<GroupPermissionDto> datas = Lists.newArrayList();
        List<GroupPermissionDto> groupPermissionDtos = dao.getGroupFuncTree(map);
        for (GroupPermissionDto groupPermissionDto : groupPermissionDtos) {
            boolean isRoot = checkIsRoot(groupPermissionDtos, groupPermissionDto.getParentId());
            if (-1 == groupPermissionDto.getParentId() || isRoot) {
                GroupPermissionDto data = new GroupPermissionDto();
                BeanUtils.copyProperties(groupPermissionDto, data);
                datas.add(data);
                build(groupPermissionDtos, data);
            }
        }
        Map<String, List<GroupPermissionDto>> grouped = datas.stream().collect(Collectors.groupingBy(groupPermissionDto -> ResourceMenuTypeEnum.getEnum(groupPermissionDto.getType()).name()));

        Map<String, List<GroupPermissionDto>> result = Maps.newHashMap();
        List<GroupPermissionDto> pcPermissions = Lists.newArrayList();
        List<GroupPermissionDto> appPermissions = Lists.newArrayList();
        for (String key : grouped.keySet()) {
            for (String appName : FUNC_GROUP_PC) {
                if (key.equals(appName)) {
                    pcPermissions.addAll(grouped.get(appName));
                }
            }
            for (String appName : FUNC_GROUP_APPS) {
                if (key.equals(appName)) {
                    appPermissions.addAll(grouped.get(appName));
                }
            }
        }
        result.put("PC", pcPermissions);
        result.put("APP", appPermissions);
        return result;
    }

    @Override
    public void batchAddDistributionMenus(Map map) {
        dao.batchAddDistributionMenus(map);
    }

    @Override
    public void delDistributionMenus(Map map) {
        dao.delDistributionMenus(map);
    }

    @Override
    public List<BasePermissionGroupResource> getListBasePermissionGroupResources(Map map) {
        return dao.getListBasePermissionGroupResources(map);
    }

    /*
     * 不是完整的树，需要找根节点
     */
    private boolean checkIsRoot(List<GroupPermissionDto> groupPermissionDtos, Long parentId) {

        for (GroupPermissionDto groupPermissionDto : groupPermissionDtos) {
            if ((groupPermissionDto.getPermissionId() == null ? 0 : groupPermissionDto.getPermissionId().intValue()) == (parentId == null ? 0 : parentId.intValue())) {
                return false;
            }
        }
        return true;
    }

    /*
     * 构造功能树
     */
    public void build(List<GroupPermissionDto> groupPermissionDtos, GroupPermissionDto data) {
        List<GroupPermissionDto> childrds = getChildren(groupPermissionDtos, data);
        data.setChildFuncs(childrds);
        for (GroupPermissionDto groupPermissionDto : childrds) {
            build(groupPermissionDtos, groupPermissionDto);
        }
    }

    /*
     * 每个根节点下的列表
     */
    private List<GroupPermissionDto> getChildren(List<GroupPermissionDto> groupPermissionDtos, GroupPermissionDto data) {
        List<GroupPermissionDto> children = new ArrayList<GroupPermissionDto>();
        for (GroupPermissionDto child : groupPermissionDtos) {
            if (child.getParentId().intValue() == data.getPermissionId().intValue()) {
                GroupPermissionDto groupPermissionDto = new GroupPermissionDto();
                BeanUtils.copyProperties(child,groupPermissionDto);
                children.add(groupPermissionDto);
            }
        }
        return children;
    }

}
