package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.dto.BasePlatformLogisticsDetailDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.query.LogisticsDetailQuery;
import com.basedata.common.query.LogisticsEntrustQuery;
import com.basedata.client.feign.LogisticsEntrustFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class LogisticsEntrustFallback implements FallbackFactory<LogisticsEntrustFeign> {
    @Override
    public LogisticsEntrustFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new LogisticsEntrustFeign() {
            @Override
            public RestMessage<BaseLogisticsEntrustDto> queryByCondition(LogisticsEntrustQuery logisticsEntrustQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询承运商信息|queryByCondition】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<BasePlatformLogisticsDetailDTO>> queryLogisticsByCondition(LogisticsDetailQuery query) {
                return RestMessage.newInstance(false, "【basedata-service|查询承运商编码关系信息|queryLogisticsByCondition】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<PlatformLogisticsRelationshipDTO>> queryPlatformLogistics(PlatformLogisticsRelationshipReqDTO relationshipReqDTO) {
                return RestMessage.newInstance(false, "【basedata-service|查询电商平台下的承运商编码|queryPlatformLogistics】服务请求失败！", null);
            }
        };
    }
}
