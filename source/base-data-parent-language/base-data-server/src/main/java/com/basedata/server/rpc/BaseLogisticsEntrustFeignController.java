package com.basedata.server.rpc;

import com.alibaba.fastjson.JSON;
import com.basedata.server.service.BaseLogisticsEntrustService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.query.LogisticsEntrustQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "仓储承运商委托关系")
@RestController
@RequestMapping("/logistics-entrust-feign")
@Slf4j
public class BaseLogisticsEntrustFeignController {

    @Autowired
    private BaseLogisticsEntrustService logisticsEntrustService;

    @ApiOperation(value = "查询承运商")
    @PostMapping("/queryByCondition")
    public RestMessage<BaseLogisticsEntrustDto> queryByCondition(@RequestBody LogisticsEntrustQuery logisticsEntrustQuery) {
        log.info("打印请求日志, logisticsEntrustQuery:{}", JSON.toJSONString(logisticsEntrustQuery));
        BaseLogisticsEntrustDto baseLogisticsEntrustDto = logisticsEntrustService.queryByCondition(logisticsEntrustQuery);
        return RestMessage.doSuccess(baseLogisticsEntrustDto);
    }

}
