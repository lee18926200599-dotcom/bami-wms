package com.basedata.client.feign;

import com.basedata.client.hystrix.BaseStoreFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.FacesheetConfigDto;
import com.basedata.common.dto.QueryStoreInfoDto;
import com.basedata.common.vo.FacesheetConfigForPrintVo;
import com.basedata.common.vo.FacesheetConfigVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = BaseStoreFallback.class)
public interface BaseStoreFeign {

    @ApiOperation(value = "获取店铺信息")
    @PostMapping("/facesheet/getConfig")
    RestMessage<FacesheetConfigVo> getConfig(@RequestBody @Valid FacesheetConfigDto configDto);

    @ApiOperation(value = "获取店铺信息")
    @PostMapping("/facesheet/getBatchConfig")
    RestMessage<List<FacesheetConfigForPrintVo>> getBatchConfig(@RequestBody @Valid QueryStoreInfoDto queryStoreInfoDto);

    }
