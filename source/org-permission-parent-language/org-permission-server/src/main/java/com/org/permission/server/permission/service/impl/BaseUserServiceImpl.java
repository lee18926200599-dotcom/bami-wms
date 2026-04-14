package com.org.permission.server.permission.service.impl;

import com.google.common.collect.Lists;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.mapper.BaseUserMapper;
import com.org.permission.server.permission.service.IBaseUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * base_userServiceImpl类 用户基本信息表管理
 */
@Service
public class BaseUserServiceImpl implements IBaseUserService {
    @Autowired
    private BaseUserMapper dao;


    /*
     * 用户--获取当前用户的功能权限树
     */
    public Map<String, List<UserPermission>> getUserFuncPermissionList(List<UserPermission> userPermissions, int menuId) {
        List<UserPermission> datas = Lists.newArrayList();
        for (UserPermission userPermission : userPermissions) {
            if (-1 == userPermission.getParentId()) {
                UserPermission data = new UserPermission();
                data.setPermissionId(userPermission.getPermissionId());
                BeanUtils.copyProperties(userPermission, data);
                data.setType(userPermission.getType());
                data.setResourceDesc(userPermission.getResourceDesc());
                data.setAuthorUser(userPermission.getAuthorUser());
                data.setAuthorTime(userPermission.getAuthorTime());
                data.setAuthor(userPermission.getAuthor());
                datas.add(data);
                build(userPermissions, data);
            }
        }
        Map<String, List<UserPermission>> grouped = datas.stream().collect(Collectors.groupingBy(userPermission -> ResourceMenuTypeEnum.getEnum(userPermission.getType()).name()));
        return grouped;
    }

    /*
     * 用户--构造功能树
     */
    public void build(List<UserPermission> userPermissions, UserPermission data) {
        List<UserPermission> childFuncs = getChildren(userPermissions, data);
        data.setChildFuncs(childFuncs);
        for (UserPermission userPermission : childFuncs) {
            build(userPermissions, userPermission);
        }
    }

    /*
     * 用户--获取每个根节点下的列表
     */
    private List<UserPermission> getChildren(List<UserPermission> userPermissions, UserPermission data) {
        List<UserPermission> children = new ArrayList<UserPermission>();
        for (UserPermission child : userPermissions) {
            if (child.getParentId().intValue() == data.getPermissionId().intValue()) {
                UserPermission userPermission = new UserPermission();
                userPermission.setPermissionId(child.getPermissionId());
                BeanUtils.copyProperties(child, userPermission);
                userPermission.setResourceDesc(child.getResourceDesc());
                userPermission.setAuthorUser(child.getAuthorUser());
                userPermission.setAuthorTime(child.getAuthorTime());
                userPermission.setAuthor(child.getAuthor());
                children.add(userPermission);
            }

        }
        return children;
    }

    @Override
    public List<UserOrgPermissionDto> getUserOrgPermissionsList(List<UserOrgPermissionDto> chooseOrgs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserDataPermissionDto> getUserDataPermissionsList(List<UserDataPermissionDto> chooseDatas) {
        // TODO Auto-generated method stub
        return null;
    }

}
