package com.basedata.client.feign;

import com.basedata.client.hystrix.LogisticsEntrustFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.dto.BasePlatformLogisticsDetailDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.query.LogisticsDetailQuery;
import com.basedata.common.query.LogisticsEntrustQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = LogisticsEntrustFallback.class)
public interface LogisticsEntrustFeign {

    @PostMapping("/logistics-entrust-feign/queryByCondition")
    RestMessage<BaseLogisticsEntrustDto> queryByCondition(@RequestBody LogisticsEntrustQuery logisticsEntrustQuery);

    @ApiOperation(value = "查询外部系统承运商编码-系统承运商编码对照关系")
    @PostMapping("/rpc/logistics-detail-feign/queryByCondition")
    RestMessage<List<BasePlatformLogisticsDetailDTO>> queryLogisticsByCondition(@RequestBody LogisticsDetailQuery query);

    @ApiOperation(value = "查询电商平台承运商编码-系统承运商编码对照关系")
    @PostMapping("/rpc/logistics-detail-feign/queryPlatformLogistics")
    RestMessage<List<PlatformLogisticsRelationshipDTO>> queryPlatformLogistics(@RequestBody PlatformLogisticsRelationshipReqDTO relationshipReqDTO);

}
