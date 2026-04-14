package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.vo.BasePlatformStoreVo;
import com.basedata.common.vo.BaseStoreDetailVo;
import com.basedata.common.vo.BaseStorePageVo;
import com.basedata.common.vo.BaseStoreVo;
import com.basedata.server.dto.BaseStoreDto;
import com.basedata.server.query.BaseStoreMultiQuery;
import com.basedata.server.query.BaseStoreQuery;
import com.basedata.server.query.StoreQuery;
import com.basedata.server.service.BaseStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "店铺管理")
@RestController
@RequestMapping("/store")
public class BaseStoreController {

    @Autowired
    private BaseStoreService baseStoreService;

    @ApiOperation(value = "新增店铺")
    @PostMapping("/save")
    public RestMessage saveBaseStore(@RequestBody @Valid BaseStoreDto baseStoreDto) {
        baseStoreService.save(baseStoreDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "修改店铺")
    @PostMapping("/update")
    public RestMessage updateBaseStore(@RequestBody @Valid BaseStoreDto baseStoreDto) {
        baseStoreService.update(baseStoreDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "根据相关条件查询店铺")
    @PostMapping("/getStoreList")
    public RestMessage<List<BaseStoreVo>> getStoreList(@RequestBody BaseStoreQuery baseStoreQuery) {
        List<BaseStoreVo> list = baseStoreService.getStoreList(baseStoreQuery);
        return RestMessage.doSuccess(list);
    }


    @ApiOperation(value = "多选多条件查询店铺")
    @PostMapping("/multi/getStoreList")
    public RestMessage<List<BaseStoreVo>> getMultiStoreList(@RequestBody BaseStoreMultiQuery baseStoreQuery) {
        List<BaseStoreVo> list = baseStoreService.getMultiStoreList(baseStoreQuery);
        return RestMessage.doSuccess(list);
    }

    @ApiOperation(value = "店铺分页列表")
    @PostMapping("/page")
    public RestMessage<PageInfo<BaseStorePageVo>> selectPage(@RequestBody @Valid StoreQuery storeQuery) {
        PageInfo<BaseStorePageVo> page = baseStoreService.page(storeQuery);
        return RestMessage.querySuccess(page);
    }

    @ApiOperation(value = "店铺详情")
    @GetMapping("/detail/{id}")
    public RestMessage<BaseStoreDetailVo> detail(@PathVariable Long id) {
        BaseStoreDetailVo detail = baseStoreService.detail(id);
        return RestMessage.querySuccess(detail);
    }


    @ApiOperation(value = "启用禁用仓库")
    @PostMapping("/status")
    public RestMessage updateStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        baseStoreService.updateStatus(updateStatusDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "删除仓库")
    @PostMapping("/delete")
    public RestMessage delete(@RequestBody DeleteDto deleteDto) {
        baseStoreService.delete(deleteDto);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "根据平台获取店铺及所属货主")
    @GetMapping("/platform/{platformCode}")
    public RestMessage<List<BasePlatformStoreVo>> platformStore(@PathVariable("platformCode") String platformCode) {
        return RestMessage.querySuccess(baseStoreService.platformStore(platformCode));
    }
}
