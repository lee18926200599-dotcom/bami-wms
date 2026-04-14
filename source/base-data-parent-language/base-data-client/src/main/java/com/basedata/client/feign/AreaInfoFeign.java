package com.basedata.client.feign;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.AreaInfoDto;
import com.basedata.common.query.AreaInfoQuery;
import com.basedata.client.hystrix.AreaInfoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = AreaInfoFallback.class)
public interface AreaInfoFeign {

    @PostMapping("/AreaInfo/queryNoPage")
    RestMessage<List<AreaInfoDto>> queryNoPage(@RequestBody AreaInfoQuery areaInfoQuery);

    @PostMapping("/AreaInfo/queryById")
    RestMessage<AreaInfoDto> queryById(@RequestBody AreaInfoQuery areaInfoQuery);
}
