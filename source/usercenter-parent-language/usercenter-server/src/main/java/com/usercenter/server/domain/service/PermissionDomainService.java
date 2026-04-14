package com.usercenter.server.domain.service;

import com.common.util.message.RestMessage;
import com.common.util.message.RestMsgUtils;
import com.org.permission.common.permission.dto.*;
import com.org.permission.common.permission.param.PermissionUserParam;
import com.org.permission.common.permission.param.UserMenuParam;
import com.org.permission.common.query.BatchQueryParam;
import com.permission.client.feign.PermissionFeign;
import com.permission.client.feign.PermissionForUserFeign;
import com.permission.client.feign.PermissionManagerFeign;
import com.usercenter.common.dto.FplUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component(value = "permissionDomainService")
public class PermissionDomainService {

    @Resource
    private PermissionFeign permissionFeign;
    @Resource
    private PermissionManagerFeign permissionManagerFeign;
    @Resource
    private PermissionForUserFeign permissionForUserFeign;

    public Set<Long> getOrgsByUidAndGroupId(Long userId, Long groupId) {
        RestMessage<Set<Long>> restMessage = permissionFeign.getOrgsByUidAndGroupId(userId, groupId);
        return restMessage.getData();
    }

    public UserAllPermissionDto getPermissonsByUid(Long userId, Long groupId, Integer domain, String sourceType, FplUser fplUser) {
        PermissionUserParam permissionUserParam = new PermissionUserParam();
        permissionUserParam.setDomainId(Long.valueOf(domain));
        permissionUserParam.setUserId(userId);
        permissionUserParam.setGroupId(groupId);
        permissionUserParam.setSource(sourceType);
        permissionUserParam.setUser(fplUser);
        RestMessage<UserAllPermissionDto> restMessage = permissionFeign.getPermissionByUid(permissionUserParam);
        return RestMsgUtils.retrieveResult(restMessage);
    }

    public UserAllPermissionDto getPermissonsByToken(Long userId, Long groupId, Integer domain, String sourceType, FplUser fplUser) {
        PermissionUserParam permissionUserParam = new PermissionUserParam();
        permissionUserParam.setDomainId(Long.valueOf(domain));
        permissionUserParam.setUserId(userId);
        permissionUserParam.setGroupId(groupId);
        permissionUserParam.setSource(sourceType);
        permissionUserParam.setUser(fplUser);
        RestMessage<UserAllPermissionDto> restMessage = permissionFeign.getPermissonsByToken(permissionUserParam);
        return RestMsgUtils.retrieveResult(restMessage);
    }

    public List<GetUserMenuPermissionsDto> getUserMenuPermissions(List<UserMenuParam> userMenuParamList) {
        RestMessage<List<GetUserMenuPermissionsDto>> restMessage = permissionManagerFeign.getUserMenuPermissions(userMenuParamList);
        return restMessage.getData();
    }

    public RestMessage insertUserPermission(InputUserDto inputUserDto) {
        return permissionForUserFeign.insertUserPermission(inputUserDto);
    }

    public RestMessage updateUserPermission(InputUserUpdateDto inputUserUpdateDto) {
        return permissionForUserFeign.updateUserPermission(inputUserUpdateDto);
    }

    public RestMessage<List<AdminGroupDto>> getGroupsByIds(Set<Long> ids) {
        BatchQueryParam param = new BatchQueryParam();
        param.setIds(new ArrayList<>(ids));
        RestMessage<List<AdminGroupDto>> restMessage = permissionFeign.getGroupsByIdSet(param);
        return restMessage;
    }

    public RestMessage<Set<Long>> batchGetOrgPermissions(Set<Long> userIds) {
        RestMessage<Set<Long>> restMessage = permissionFeign.batchGetOrgPermissions(userIds);
        return restMessage;
    }
}
