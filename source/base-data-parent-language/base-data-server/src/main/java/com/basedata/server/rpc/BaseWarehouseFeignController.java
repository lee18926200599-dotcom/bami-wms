package com.basedata.server.rpc;

import com.basedata.server.service.BaseWarehouseService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 *  仓库管理-Feign接口
 */
@Api(tags = "仓库管理-Feign接口")
@RestController
@RequestMapping("/rpc/warehouse")
public class BaseWarehouseFeignController {
    @Autowired
    private BaseWarehouseService warehouseService;

    @ApiOperation(value = "根据条件查询仓库列表")
    @PostMapping("/getWarehouseList")
    public RestMessage<List<BaseWarehouseDto>> getWarehouseList(@RequestBody WarehouseQuery warehouseQuery) {
        try {
            List<BaseWarehouseDto> list = warehouseService.getWarehouseList(warehouseQuery);
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            return RestMessage.error("查询仓库信息异常");
        }
    }


    @ApiOperation(value = "启用禁用仓库")
    @PostMapping("/updateStatus")
    public RestMessage updateStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        warehouseService.updateStatus(updateStatusDto);
        return RestMessage.doSuccess(null);
    }
}
