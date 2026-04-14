package com.basedata.client.feign;

import com.basedata.client.hystrix.BaseWarehouseCategroyFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.query.WarehouseCategoryQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",fallbackFactory = BaseWarehouseCategroyFallback.class)
public interface BaseWarehouseCategroyFeign {
    @GetMapping("/v1/warehouseCategory/queryCategoryListByParentId")
    RestMessage<List<Long>> queryCategoryListByParentId(@RequestParam("id") Long id);
    @GetMapping("/v1/warehouseCategory/queryCategoryById")
    RestMessage<BaseWarehouseCategoryDto> queryCategoryById(@RequestParam("id") Long id);
    @PostMapping("/v1/warehouseCategory/queryCategoryList")
    RestMessage<List<BaseWarehouseCategoryDto>> queryCategoryList(@RequestBody WarehouseCategoryQuery query);

    @PostMapping("/v1/warehouseCategory/enableOrDisable")
    RestMessage enableOrDisable(@RequestBody BaseWarehouseCategoryDto baseWarehouseCategoryDto);

}
