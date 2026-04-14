package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformNetsideReqDTO;
import com.basedata.client.feign.BasePlatformNetsideFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class BasePlatformNetsideFallback implements FallbackFactory<BasePlatformNetsideFeign> {

    @Override
    public BasePlatformNetsideFeign create(Throwable throwable) {
        if (null != throwable && StringUtils.hasLength(throwable.getMessage())) {
            log.error(throwable.getMessage(), throwable);
        }
        return new BasePlatformNetsideFeign() {
            @Override
            public RestMessage<Object> saveOrUpdate(BasePlatformNetsideReqDTO updateVo) {
                return RestMessage.newInstance(false, "【basedata-service|新增或更新平台网点信息|saveOrUpdate】服务请求失败！", null);
            }
        };
    }
}
