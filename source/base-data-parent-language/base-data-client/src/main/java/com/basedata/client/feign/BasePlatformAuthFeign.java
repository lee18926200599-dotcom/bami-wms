package com.basedata.client.feign;

import com.basedata.client.hystrix.BasePlatformAuthFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformAuthDto;
import com.basedata.common.vo.BasePlatformAuthQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = BasePlatformAuthFallback.class)
public interface BasePlatformAuthFeign {

    @PostMapping("/base-platform-auth/queryByOwnerIdAndPlatformName")
    RestMessage<BasePlatformAuthDto> queryByOwnerIdAndPlatformName(@RequestBody BasePlatformAuthQueryVo queryVo);

    @ApiOperation(value = "查询货主唯一的平台授权（PS：没有过滤是否是启用状态）", notes = "查询货主唯一的平台授权")
    @PostMapping("/base-platform-auth-feign/findOne")
    RestMessage<BasePlatformAuthDto> findOne(@RequestParam("serviceProviderId") Long serviceProviderId, @RequestParam("ownerId") Long ownerId, @RequestParam("platformCode") String platformCode);

    @PostMapping("/base-platform-auth-feign/updateToken")
    RestMessage<Boolean> updateToken(@RequestBody BasePlatformAuthDto basePlatformAuthDto);
}
