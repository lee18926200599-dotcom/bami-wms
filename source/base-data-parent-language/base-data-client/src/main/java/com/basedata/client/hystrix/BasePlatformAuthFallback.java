package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformAuthDto;
import com.basedata.common.vo.BasePlatformAuthQueryVo;
import com.basedata.client.feign.BasePlatformAuthFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class BasePlatformAuthFallback implements FallbackFactory<BasePlatformAuthFeign> {

    @Override
    public BasePlatformAuthFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new BasePlatformAuthFeign() {


            @Override
            public RestMessage<BasePlatformAuthDto> queryByOwnerIdAndPlatformName(BasePlatformAuthQueryVo queryVo) {
                return RestMessage.newInstance(false, "【basedata-service|查询平台密钥失败|queryByOwnerIdAndPlatformName】服务请求失败！", null);
            }

            @Override
            public RestMessage<BasePlatformAuthDto> findOne(Long serviceProviderId, Long ownerId, String platformCode) {
                return RestMessage.newInstance(false, "【basedata-service|查询唯一平台授权信息失败|findOne】服务请求失败！", null);
            }

            @Override
            public RestMessage<Boolean> updateToken(BasePlatformAuthDto basePlatformAuthDto) {
                return RestMessage.newInstance(false, "【basedata-service|授权后更新token相关信息|updateToken】服务请求失败！", null);
            }
        };
    }
}
