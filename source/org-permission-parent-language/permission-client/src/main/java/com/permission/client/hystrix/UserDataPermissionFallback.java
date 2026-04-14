package com.permission.client.hystrix;

import com.common.util.message.RestMessage;
import com.permission.client.feign.UserDataPermissionFeign;
import com.org.permission.common.permission.dto.DataPermissionDto;
import com.org.permission.common.permission.param.GetDataPermissionParam;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class UserDataPermissionFallback implements FallbackFactory<UserDataPermissionFeign> {
    @Override
    public UserDataPermissionFeign create(Throwable throwable) {
        return new UserDataPermissionFeign() {
            @Override
            public RestMessage<Map<String, Object>> getDataPermissonsByUid(Long userId, Long groupId) {
                return RestMessage.newInstance(false, "【org-permission-service|根据用户id和集团id获取用户的数据权限|请求服务失败！", null);
            }

            @Override
            public RestMessage<List<DataPermissionDto>> getDataPermissons(GetDataPermissionParam getDataPermissonReq) {
                return RestMessage.newInstance(false, "【org-permission-service|获取用户权限|请求服务失败！", null);
            }
        };
    }
}
