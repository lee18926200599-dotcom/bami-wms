package com.basedata.client.hystrix;

import com.basedata.client.feign.AreaInfoFeign;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.AreaInfoDto;
import com.basedata.common.query.AreaInfoQuery;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AreaInfoFallback implements FallbackFactory<AreaInfoFeign> {
    @Override
    public AreaInfoFeign create(Throwable throwable) {
        return new AreaInfoFeign() {
            @Override
            public RestMessage<List<AreaInfoDto>> queryNoPage(AreaInfoQuery areaInfoQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询地址集合|queryNoPage】服务请求失败！", null);
            }

            @Override
            public RestMessage<AreaInfoDto> queryById(AreaInfoQuery areaInfoQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询地址|queryById】服务请求失败！", null);
            }
        };
    }
}
