package com.basedata.client.feign;

import com.basedata.client.hystrix.BasePlatformNetsideFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformNetsideReqDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = BasePlatformNetsideFallback.class)
public interface BasePlatformNetsideFeign {

    @ApiOperation(value = "新增或更新平台网点信息", notes = "新增")
    @PostMapping("/rpc/base-platform-netside/saveOrUpdate")
    RestMessage<Object> saveOrUpdate(@RequestBody @Valid BasePlatformNetsideReqDTO updateVo);

}
