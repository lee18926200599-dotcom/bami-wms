package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.dto.BaseStorePrintTemplateDTO;
import com.basedata.server.dto.BaseStorePrintTemplateDetailDTO;
import com.basedata.server.dto.BaseStorePrintTemplateUpdateReqDTO;
import com.basedata.server.entity.BaseStorePrintTemplate;
import com.basedata.server.query.BaseStorePrintTemplateQueryVo;
import com.basedata.server.service.BaseStorePrintTemplateService;
import com.basedata.server.vo.BaseStorePrintTemplateReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 快递面单模版配置 前端控制器
 * </p>
 */
@Api(tags = "快递面单模版配置")
@RestController
@RequestMapping("/base-store-print-template")
public class BaseStorePrintTemplateController {

    @Resource
    private BaseStorePrintTemplateService baseStorePrintTemplateService;

    @ApiOperation(value = "批量保存配置", notes = "新增")
    @PostMapping("/batchSaveConfig")
    public RestMessage<Object> batchSaveConfig(@RequestBody @Valid BaseStorePrintTemplateUpdateReqDTO reqDTO) {
        baseStorePrintTemplateService.batchSaveConfig(reqDTO);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchDelete")
    public RestMessage<Object> batchDelete(@RequestBody List<Long> ids) {
        return RestMessage.doSuccess(baseStorePrintTemplateService.deleteByIds(ids));
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @PostMapping("/batchEnable")
    public RestMessage<Boolean> batchEnableOrDisable(@RequestBody @Valid UpdateStatusDto statusDto) {
        return RestMessage.doSuccess(baseStorePrintTemplateService.batchEnableOrDisable(statusDto));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BaseStorePrintTemplate>> queryPageList(@RequestBody BaseStorePrintTemplateQueryVo queryVo) {
        return RestMessage.querySuccess(baseStorePrintTemplateService.queryPageList(queryVo));
    }

    @ApiOperation(value = "查询明细列表（根据配置项主ID查）", notes = "明细查询")
    @GetMapping("/queryDetailList/{configId}")
    public RestMessage<BaseStorePrintTemplateDTO> queryDetailList(@PathVariable("configId") Long configId) throws Exception {
        return RestMessage.querySuccess(baseStorePrintTemplateService.queryDetailList(configId));
    }

    @ApiOperation(value = "查询面单模板列表（标准、自定义）", notes = "查询面单模板列表")
    @PostMapping("/queryTemplateList")
    public RestMessage<List<BaseStorePrintTemplateDetailDTO>> queryTemplateList(@RequestBody BaseStorePrintTemplateReqVo reqVo) {
        return RestMessage.querySuccess(baseStorePrintTemplateService.queryTemplateDetailList(reqVo));
    }
}
