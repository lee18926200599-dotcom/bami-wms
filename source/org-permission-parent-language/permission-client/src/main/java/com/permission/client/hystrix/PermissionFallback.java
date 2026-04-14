package com.permission.client.hystrix;

import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.AdminGroupDto;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.common.permission.param.MenuButtonPermissionParam;
import com.org.permission.common.permission.param.PermissionUserParam;
import com.org.permission.common.query.BatchQueryParam;
import com.permission.client.feign.PermissionFeign;
import com.usercenter.common.dto.FplUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class PermissionFallback implements FallbackFactory<PermissionFeign> {
    @Override
    public PermissionFeign create(Throwable throwable) {
        return new PermissionFeign() {
            @Override
            public RestMessage<UserAllPermissionDto> getPermissionByUid(PermissionUserParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户id，获取用户的 功能权限（菜单级）、组织权限、数据权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<UserAllPermissionDto> getPermissonsByToken(PermissionUserParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|根据token获取权限数据|请求服务失败！", null);
            }

            @Override
            public RestMessage<Map<String, Object>> getMenus(Long userId, Long groupId, Integer domainId) {
                return RestMessage.newInstance(false, "【org-permission-service|获取菜单数据|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<UserPermission>> getPermissonsByMenuIdAndUid(@RequestBody MenuButtonPermissionParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|根据菜单获取权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<UserPermission>> getPermissonsByParentIdAndUid(Long userId, Long groupId, Integer parentId) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户id获取父级下的子菜单权限|请求服务失败！", null);
            }

            @Override
            public RestMessage checkPermissionById(Long userId, Long groupId, String token, Integer permissionId, String uri) {
                return RestMessage.newInstance(false, "【org-permission-service|按照权限id和uri后台校验用户是否有权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<AdminGroupDto>> getGroupsById(Long userId) {
                return RestMessage.newInstance(false, "【org-permission-service|获取集团列表|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<AdminGroupDto>> getGroupsByIdSet(BatchQueryParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|批量获取集团列表|请求服务失败！", null);
            }

            @Override
            public RestMessage<Set<Long>> getOrgPermissions(FplUser user) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户信息获取组织权限数据|请求服务失败！", null);
            }

            @Override
            public RestMessage<Set<Long>> batchGetOrgPermissions(Set<Long> userIdSet) {
                return RestMessage.newInstance(false, "【org-permission-service|批量获取用户跨组织权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<Set<Long>> getOrgsByUidAndGroupId(Long userId, Long groupId) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户id和集团id获取用户的组织权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<Set<Long>> getOrgIdListByUidAndGroupId(PermissionUserParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|getOrgIdListByUidAndGroupId|根据用户id和集团id获取用户的组织权限|请求服务失败！", null);
            }
        };
    }
}
