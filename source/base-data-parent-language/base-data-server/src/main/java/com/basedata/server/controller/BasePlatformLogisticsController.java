package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.dto.BasePlatformLogisticsDTO;
import com.basedata.server.dto.BasePlatformLogisticsUpdateReqDTO;
import com.basedata.server.query.BasePlatformLogisticsQueryVo;
import com.basedata.server.service.BasePlatformLogisticsService;
import com.basedata.server.vo.BasePlatformLogisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 承运商编码对照关系表 前端控制器
 * </p>
 */
@Api(tags = "承运商编码对应关系")
@RestController
@RequestMapping("/base-platform-logistics")
public class BasePlatformLogisticsController {

    @Resource
    private BasePlatformLogisticsService basePlatformLogisticsService;

    @ApiOperation(value = "批量保存配置", notes = "批量保存配置")
    @PostMapping("/batchSaveConfig")
    public RestMessage<Object> batchSaveConfig(@RequestBody @Valid BasePlatformLogisticsUpdateReqDTO reqDTO) {
        basePlatformLogisticsService.batchSaveConfig(reqDTO);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchDelete")
    public RestMessage<Object> batchDelete(@RequestBody List<Long> ids) {
        return RestMessage.doSuccess(basePlatformLogisticsService.deleteByIds(ids));
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @PostMapping("/batchEnable")
    public RestMessage<Boolean> batchEnableOrDisable(@RequestBody @Valid UpdateStatusDto statusDto) {
        return RestMessage.doSuccess(basePlatformLogisticsService.batchEnableOrDisable(statusDto));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BasePlatformLogisticsVo>> queryPageList(@RequestBody BasePlatformLogisticsQueryVo queryVo) throws Exception {
        return RestMessage.querySuccess(basePlatformLogisticsService.queryPageList(queryVo));
    }

    @ApiOperation(value = "查询明细列表（根据配置项主ID查）", notes = "明细查询")
    @GetMapping("/queryDetailList/{configId}")
    public RestMessage<BasePlatformLogisticsDTO> queryDetailList(@PathVariable("configId") Long configId) throws Exception {
        return RestMessage.querySuccess(basePlatformLogisticsService.queryDetailList(configId));
    }

}
