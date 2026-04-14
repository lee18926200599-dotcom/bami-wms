package com.org.client.feign;

import com.common.util.message.RestMessage;
import com.org.permission.common.dto.BaseOrganizationDto;
import com.org.permission.common.util.Constant;
import com.org.client.hystrix.BaseOrganizationFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = Constant.ORG_SERVER_NAME,fallbackFactory = BaseOrganizationFallback.class)
public interface BaseOrganizationFeign {

    @GetMapping("/organization/queryById")
    RestMessage<BaseOrganizationDto> queryById(@RequestParam("id") Long id);

    @GetMapping("/organization/queryByCode")
    RestMessage<BaseOrganizationDto> queryByCode(@RequestParam("code") String code);

}
