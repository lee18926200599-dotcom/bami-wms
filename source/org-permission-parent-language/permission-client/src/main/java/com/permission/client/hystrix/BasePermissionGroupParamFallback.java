package com.permission.client.hystrix;

import com.permission.client.feign.BasePermissionGroupParamFeign;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.BasePermissionGroupParamDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class BasePermissionGroupParamFallback implements FallbackFactory<BasePermissionGroupParamFeign> {
    @Override
    public BasePermissionGroupParamFeign create(Throwable throwable) {

        return new BasePermissionGroupParamFeign() {
            @Override
            public RestMessage<BasePermissionGroupParamDto> getBasePermissionGroupParamsByGroupIdAndCode(Long groupId, String paramCode) {
                return RestMessage.newInstance(false, "【org-permission-service|根据集团ID和参数code获取集团参数设置|groupId|paramCode】请求服务失败！", null);
            }
        };
    }
}
