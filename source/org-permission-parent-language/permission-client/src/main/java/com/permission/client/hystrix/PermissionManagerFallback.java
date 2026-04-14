package com.permission.client.hystrix;

import com.common.util.message.RestMessage;
import com.permission.client.feign.PermissionManagerFeign;
import com.org.permission.common.permission.dto.OrgDto;
import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.common.permission.param.UserMenuParam;
import com.org.permission.common.permission.dto.GetUserMenuPermissionsDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PermissionManagerFallback implements FallbackFactory<PermissionManagerFeign> {
    @Override
    public PermissionManagerFeign create(Throwable throwable) {
        return new PermissionManagerFeign() {
            @Override
            public RestMessage<List<OrgDto>> getRoleTree(Long loginUserId, Long userId, Long groupId, String roleName, String orgName, String chooseRoleName, String chooseOrgName) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户获取组织权限下的角色树|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<OrgDto>> getUserTree(Long userId, Long groupId, Integer roleId, String userName, String orgName) {
                return RestMessage.newInstance(false, "【org-permission-service|获取用户组织权限下的用户树|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<RoleUser>> getRoleUsers(Integer roleId, Long groupId) {
                return RestMessage.newInstance(false, "【org-permission-service|获取角色下用户|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<GetUserMenuPermissionsDto>> getUserMenuPermissions(List<UserMenuParam> userMenuParams) {
                return RestMessage.newInstance(false, "【org-permission-service|批量获取用户所在集团的菜单权限|请求服务失败！", null);
            }

            @Override
            public RestMessage getBusinessTypesByPermissionId(Integer permissionId) {
                return RestMessage.newInstance(false, "【org-permission-service|根据权限id获取支持的客户业务类型|请求服务失败！", null);
            }
        };
    }
}
