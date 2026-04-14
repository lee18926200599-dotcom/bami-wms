package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.entity.BasePlatformStore;
import com.basedata.server.query.BasePlatformStoreQueryVo;
import com.basedata.server.service.BasePlatformStoreService;
import com.basedata.server.vo.BasePlatformStoreUpdateVo;
import com.basedata.server.vo.BasePlatformStoreVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 快递面单模板授权店铺 前端控制器
 * </p>
 */
@Api(tags = "面单模板维护授权店铺")
@RestController
@RequestMapping("/base-platform-store")
public class BasePlatformStoreController {

    @Resource
    private BasePlatformStoreService basePlatformStoreService;
    @Autowired
    private BasePlatformStoreController BasePlatformStore;

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/save")
    public RestMessage<Object> save(@RequestBody @Valid BasePlatformStoreUpdateVo updateVo) {
        basePlatformStoreService.save(updateVo);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "更新", notes = "更新")
    @PostMapping("/update")
    public RestMessage<Object> update(@RequestBody @Valid BasePlatformStoreUpdateVo updateVo) {
        basePlatformStoreService.update(updateVo);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchDelete")
    public RestMessage<Object> batchDelete(@RequestBody DeleteDto deleteDto) {
        return RestMessage.doSuccess(basePlatformStoreService.deleteByIds(deleteDto));
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @PostMapping("/batchEnable")
    public RestMessage<Boolean> batchEnableOrDisable(@RequestBody @Valid UpdateStatusDto statusDto) {
        return RestMessage.doSuccess(basePlatformStoreService.batchEnableOrDisable(statusDto));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BasePlatformStoreVo>> queryPageList(@RequestBody BasePlatformStoreQueryVo queryVo) throws Exception {
        return RestMessage.querySuccess(basePlatformStoreService.queryPageList(queryVo));
    }

    @ApiOperation(value = "根据电商平台查找有效授权店铺列表", notes = "根据电商平台查找有效授权店铺")
    @GetMapping("/getAuthStoreByPlatformCode")
    public RestMessage<List<BasePlatformStore>> getAuthStoreByPlatformCode(@RequestParam("platformCode") String platformCode) throws Exception {
        return RestMessage.querySuccess(basePlatformStoreService.getAuthStoreByPlatformCode(platformCode));
    }
}
