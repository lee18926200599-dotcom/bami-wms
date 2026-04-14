package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import com.basedata.common.vo.BaseWarehouseDetailVo;
import com.basedata.common.vo.BaseWarehousePageVo;
import com.basedata.common.vo.WarehouseVo;
import com.basedata.server.service.BaseWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(tags = "仓库管理")
@RestController
@RequestMapping("/warehouse")
public class BaseWarehouseController {

    @Autowired
    private BaseWarehouseService baseWarehouseService;

    @ApiOperation(value = "新增仓库")
    @PostMapping("/save")
    public RestMessage saveBaseWarehouseInfo(@RequestBody @Valid BaseWarehouseDto baseWarehouseDto) {
        baseWarehouseService.save(baseWarehouseDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "修改仓库")
    @PostMapping("/update")
    public RestMessage updateBaseWarehouseInfo(@RequestBody @Valid BaseWarehouseDto baseWarehouseDto) {
        baseWarehouseService.update(baseWarehouseDto);
        return RestMessage.doSuccess(null);
    }
    @ApiOperation(value = "根据仓库编码查询仓库")
    @GetMapping("/getWarehouseByCode")
    public RestMessage<BaseWarehouseDto> getWarehouseByCode(@RequestParam("warehouseCode") String warehouseCode) {
        BaseWarehouseDto baseWarehouseDto = baseWarehouseService.getWarehouseByCode(warehouseCode);
        return RestMessage.doSuccess(baseWarehouseDto);
    }

    @ApiOperation(value = "根据仓库编码查询仓库")
    @GetMapping("/getWarehouseById")
    public RestMessage<BaseWarehouseDto> getWarehouseById(@RequestParam("id") Long id) {
        BaseWarehouseDto baseWarehouseDto = baseWarehouseService.getWarehouseById(id);
        return RestMessage.doSuccess(baseWarehouseDto);
    }

    @ApiOperation(value = "仓库分页列表")
    @PostMapping("/page")
    public RestMessage<PageInfo<BaseWarehousePageVo>> selectPage(@RequestBody @Valid WarehouseQuery warehouseQuery) {
        PageInfo<BaseWarehousePageVo> page = baseWarehouseService.page(warehouseQuery);
        return RestMessage.querySuccess(page);
    }

    @ApiOperation(value = "仓库详情")
    @GetMapping("/detail/{id}")
    public RestMessage<BaseWarehouseDetailVo> detail(@PathVariable Long id) {
        BaseWarehouseDetailVo detail = baseWarehouseService.detail(id);
        return RestMessage.querySuccess(detail);
    }

    @ApiOperation(value = "仓库列表")
    @GetMapping("/all")
    public RestMessage<List<WarehouseVo>> selectWarehouse() {
        List<WarehouseVo> list = baseWarehouseService.getWarehouse();
        return RestMessage.querySuccess(list);
    }

    @ApiOperation(value = "启用禁用仓库")
    @PostMapping("/status")
    public RestMessage updateStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        baseWarehouseService.updateStatus(updateStatusDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "删除仓库")
    @PostMapping("/delete")
    public RestMessage delete(@RequestBody DeleteDto deleteDto) {
        baseWarehouseService.delete(deleteDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "根据组织id查询仓库列表")
    @GetMapping("/getWarehouseByOrgId")
    public RestMessage<List<BaseWarehouseDto>> getWarehouseByOrgId(@RequestParam("orgId")Long orgId) throws Exception {
        List<BaseWarehouseDto> list = baseWarehouseService.getWarehouseByOrgId(orgId);
        return RestMessage.querySuccess(list);
    }


}
