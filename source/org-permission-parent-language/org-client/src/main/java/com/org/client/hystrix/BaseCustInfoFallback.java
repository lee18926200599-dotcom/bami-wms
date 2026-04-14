package com.org.client.hystrix;

import com.common.util.message.RestMessage;
import com.org.permission.common.dto.BaseCustInfoDto;
import com.org.client.feign.BaseCustInfoFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
@Slf4j
public class BaseCustInfoFallback implements FallbackFactory<BaseCustInfoFeign> {
    @Override
    public BaseCustInfoFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new BaseCustInfoFeign() {

            @Override
            public RestMessage<BaseCustInfoDto> queryById(Long id) {
                return RestMessage.newInstance(false, "【org-permission-service|根据id全局客户|queryById】请求服务失败！", null);
            }

            @Override
            public RestMessage<BaseCustInfoDto> queryByCustCode(String code) {
                return RestMessage.newInstance(false, "【org-permission-service|根据code全局客户|queryByCustCode】请求服务失败！", null);
            }
        };
    }
}
