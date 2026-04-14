package com.basedata.server.controller;

import com.basedata.server.dto.*;
import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.entity.BaseLogisticsNetside;
import com.basedata.server.query.BaseLogisticsNetsideDetailQuery;
import com.basedata.server.query.BaseLogisticsNetsideQueryVo;
import com.basedata.server.service.BaseLogisticsNetsideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 承运商网点对应关系 前端控制器
 * </p>
 */
@Api(tags = "承运商网点对应关系")
@RestController
@RequestMapping("/base-logistics-netside")
public class BaseLogisticsNetsideController {

    @Resource
    private BaseLogisticsNetsideService baseLogisticsNetsideService;

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/saveAll")
    public RestMessage<Object> saveAll(@RequestBody @Valid BaseLogisticsNetsideReqDTO reqDTO) {
        baseLogisticsNetsideService.saveAll(reqDTO);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "更新", notes = "更新")
    @PostMapping("/updateAll")
    public RestMessage<Object> updateAll(@RequestBody @Valid BaseLogisticsNetsideReqDTO reqDTO) {
        baseLogisticsNetsideService.updateAll(reqDTO);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchDelete")
    public RestMessage<Object> batchDelete(@RequestBody List<Long> ids) {
        return RestMessage.doSuccess(baseLogisticsNetsideService.deleteByIds(ids));
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @PostMapping("/batchEnable")
    public RestMessage<Boolean> batchEnableOrDisable(@RequestBody @Valid UpdateStatusDto statusDto) {
        return RestMessage.doSuccess(baseLogisticsNetsideService.batchEnableOrDisable(statusDto));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BaseLogisticsNetside>> queryPageList(@RequestBody BaseLogisticsNetsideQueryVo queryVo) {
        return RestMessage.querySuccess(baseLogisticsNetsideService.queryPageList(queryVo));
    }

    @ApiOperation(value = "查询明细列表（根据配置项主ID查）", notes = "查看详情")
    @GetMapping("/queryDetailList/{configId}")
    public RestMessage<BaseLogisticsNetsideDTO> queryDetailList(@PathVariable("configId") Long configId) throws Exception {
        return RestMessage.querySuccess(baseLogisticsNetsideService.queryDetailList(configId));
    }


    @ApiOperation(value = "查询网点信息", notes = "查询网点信息")
    @PostMapping("/queryNetsideDetail")
    public RestMessage<List<BaseLogisticsNetsideDetailDTO>> queryNetsideDetail(@RequestBody BaseLogisticsNetsideDetailQuery queryVo) {
        return RestMessage.querySuccess(baseLogisticsNetsideService.queryNetsideDetail(queryVo));
    }
    @ApiOperation(value = "查询承运商信息", notes = "查询承运商信息")
    @GetMapping("/queryLogistics")
   public RestMessage<List<BaseLogisticsDTO>> queryBaseLogisticsList(@RequestParam(required = false,value = "deliveryType") Integer deliveryType ){
        return RestMessage.querySuccess(baseLogisticsNetsideService.queryBaseLogisticsList(deliveryType));
   }

    @ApiOperation(value = "查询承运商网点信息", notes = "查询承运商网点信息")
    @GetMapping("/queryNetside/{logisticsId}")
   public RestMessage<List<BaseNetsideDTO>> queryBaseNetsideList(@PathVariable("logisticsId")Long logisticsId){
        return RestMessage.querySuccess(baseLogisticsNetsideService.queryBaseNetsideList(logisticsId));
   }
}
