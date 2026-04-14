package com.basedata.server.controller;

import com.basedata.server.query.BaseLogisticsEntrustQuery;
import com.basedata.server.service.BaseLogisticsEntrustService;
import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.vo.BaseLogisticsEntrustDetailVo;
import com.basedata.common.vo.BaseLogisticsEntrustVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "仓储承运商委托关系")
@RestController
@RequestMapping("/logistics-entrust")
public class BaseLogisticsEntrustController {

    @Autowired
    private BaseLogisticsEntrustService logisticsEntrustService;

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public RestMessage saveLogisticsEntrust(@RequestBody @Valid BaseLogisticsEntrustDto logisticsEntrustDto) {
        logisticsEntrustService.save(logisticsEntrustDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public RestMessage updateLogisticsEntrust(@RequestBody @Valid BaseLogisticsEntrustDto logisticsEntrustDto) {
        logisticsEntrustService.update(logisticsEntrustDto);
        return RestMessage.doSuccess(null);
    }


    @ApiOperation(value = "分页列表")
    @PostMapping("/page")
    public RestMessage<PageInfo<BaseLogisticsEntrustVo>> selectPage(@RequestBody @Valid BaseLogisticsEntrustQuery query) {
        PageInfo<BaseLogisticsEntrustVo> page = logisticsEntrustService.page(query);
        return RestMessage.querySuccess(page);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/detail/{id}")
    public RestMessage<BaseLogisticsEntrustDetailVo> detail(@PathVariable Long id) {
        BaseLogisticsEntrustDetailVo detail = logisticsEntrustService.detail(id);
        return RestMessage.querySuccess(detail);
    }


    @ApiOperation(value = "启用禁用")
    @PostMapping("/status")
    public RestMessage updateStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        logisticsEntrustService.updateStatus(updateStatusDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public RestMessage delete(@RequestBody DeleteDto deleteDto) {
        logisticsEntrustService.delete(deleteDto);
        return RestMessage.doSuccess(null);
    }


}
