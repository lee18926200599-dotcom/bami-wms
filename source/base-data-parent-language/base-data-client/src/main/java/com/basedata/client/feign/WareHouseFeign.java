package com.basedata.client.feign;

import com.basedata.client.hystrix.WareHouseFallback;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = WareHouseFallback.class)
public interface WareHouseFeign {

    @GetMapping("/warehouse/getWarehouseByOrgId")
    RestMessage<List<BaseWarehouseDto>> getWarehouseByOrgId(@RequestParam("orgId")Long orgId);

    @GetMapping("/warehouse/getWarehouseByCode")
    RestMessage<BaseWarehouseDto> getWarehouseByCode(@RequestParam("warehouseCode") String warehouseCode);

    @GetMapping("/warehouse/getWarehouseById")
    RestMessage<BaseWarehouseDto> getWarehouseById(@RequestParam("id") Long id);

    @PostMapping("/rpc/warehouse/getWarehouseList")
    RestMessage<List<BaseWarehouseDto>> getWarehouseList(@RequestBody WarehouseQuery warehouseQuery);

    @PostMapping("/rpc/warehouse/updateStatus")
    RestMessage updateStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto);
}
