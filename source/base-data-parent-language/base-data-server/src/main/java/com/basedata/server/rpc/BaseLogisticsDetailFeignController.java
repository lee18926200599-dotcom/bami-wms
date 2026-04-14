package com.basedata.server.rpc;

import com.basedata.server.service.BasePlatformLogisticsService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformLogisticsDetailDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.query.LogisticsDetailQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "承运商编码对照关系")
@RestController
@RequestMapping("/rpc/logistics-detail-feign")
public class BaseLogisticsDetailFeignController {

    @Autowired
    private BasePlatformLogisticsService basePlatformLogisticsService;

    @ApiOperation(value = "查询外部系统承运商编码-系统承运商编码对照关系")
    @PostMapping("/queryByCondition")
    public RestMessage<List<BasePlatformLogisticsDetailDTO>> queryLogisticsByCondition(@RequestBody LogisticsDetailQuery query) {
        List<BasePlatformLogisticsDetailDTO> list = basePlatformLogisticsService.queryLogisticsByCondition(query);
        return RestMessage.doSuccess(list);
    }

    @ApiOperation(value = "查询电商平台承运商编码-系统承运商编码对照关系")
    @PostMapping("/queryPlatformLogistics")
    public RestMessage<List<PlatformLogisticsRelationshipDTO>> queryPlatformLogistics(@RequestBody PlatformLogisticsRelationshipReqDTO relationshipReqDTO) {
        return RestMessage.querySuccess(basePlatformLogisticsService.queryPlatformLogistics(relationshipReqDTO));
    }

}
