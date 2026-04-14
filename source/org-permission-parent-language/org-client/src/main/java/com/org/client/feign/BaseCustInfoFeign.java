package com.org.client.feign;

import com.common.util.message.RestMessage;
import com.org.permission.common.dto.BaseCustInfoDto;
import com.org.permission.common.util.Constant;
import com.org.client.hystrix.BaseCustInfoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = Constant.ORG_SERVER_NAME,fallbackFactory = BaseCustInfoFallback.class)
public interface BaseCustInfoFeign {

    @GetMapping("/custInfo/queryById")
    RestMessage<BaseCustInfoDto> queryById(@RequestParam("id") Long id);

    @GetMapping("/custInfo/queryByCustCode")
    RestMessage<BaseCustInfoDto> queryByCustCode(@RequestParam("code") String code);

}
