package com.org.client.hystrix;

import com.common.util.message.RestMessage;
import com.org.permission.common.dto.BaseOrganizationDto;
import com.org.client.feign.BaseOrganizationFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
@Slf4j
public class BaseOrganizationFallback implements FallbackFactory<BaseOrganizationFeign> {
    @Override
    public BaseOrganizationFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new BaseOrganizationFeign() {
            @Override
            public RestMessage<BaseOrganizationDto> queryById(Long id) {
                return RestMessage.newInstance(false, "【org-permission-service|根据id查询组织名称|queryById】请求服务失败！", null);
            }

            @Override
            public RestMessage<BaseOrganizationDto> queryByCode(String code) {
                return RestMessage.newInstance(false, "【org-permission-service|根据code查询组织名称|queryByCode】请求服务失败！", null);
            }
        };
    }
}
