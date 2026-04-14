package com.permission.client.hystrix;

import com.org.permission.common.permission.dto.InputAdminDto;
import com.permission.client.feign.PermissionForUserFeign;
import com.common.util.message.RestMessage;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class PermissionForUserFallback implements FallbackFactory<PermissionForUserFeign> {
    @Override
    public PermissionForUserFeign create(Throwable throwable) {
        return new PermissionForUserFeign() {
            @Override
            public RestMessage insertUserPermission(com.org.permission.common.permission.dto.InputUserDto inputUserDto) {
                return RestMessage.newInstance(false, "【org-permission-service|新增用户权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<String> updateUserPermission(com.org.permission.common.permission.dto.InputUserUpdateDto inputUserUpdateDto) {
                return RestMessage.newInstance(false, "【org-permission-service|修改用户权限|请求服务失败！", null);
            }

            @Override
            public RestMessage insertAdminGroup(InputAdminDto inputAdminDto) {
                return RestMessage.newInstance(false, "【org-permission-service|新增集团管理员|请求服务失败！", null);
            }

            @Override
            public RestMessage updateAdminGroup(com.org.permission.common.permission.dto.InputAdminUpdateDto inputAdminUpdateDto) {
                return RestMessage.newInstance(false, "【org-permission-service|修改集团管理员|请求服务失败！", null);
            }

            @Override
            public RestMessage optionUser(com.org.permission.common.permission.dto.PermissionDto permissionDto) {
                return RestMessage.newInstance(false, "【org-permission-service|启停用用户/管理员权限变化|请求服务失败！", null);
            }
        };
    }
}
