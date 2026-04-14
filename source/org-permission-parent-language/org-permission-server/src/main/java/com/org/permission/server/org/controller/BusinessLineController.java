package com.org.permission.server.org.controller;

import com.github.pagehelper.PageInfo;
import com.org.permission.server.org.dto.BusinessLineDto;
import com.org.permission.server.org.dto.param.BusinessLineParam;
import com.org.permission.server.org.service.BusinessLineService;
import com.common.util.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 0业务线管理接口文档
 */
@Api(tags = "0业务线管理接口文档")
@RestController
@RequestMapping("business-line")
public class BusinessLineController {

    @Autowired
    private BusinessLineService businessLineService;

    @ApiOperation(value = "新增业务线数据", httpMethod = "POST")
    @PostMapping(value = "/save")
    public RestMessage<Boolean> save(@RequestBody BusinessLineDto businessLineDto) {
        return RestMessage.doSuccess(businessLineService.save(businessLineDto) > 0);
    }

    @ApiOperation(value = "修改业务线数据", httpMethod = "POST")
    @PostMapping(value = "/update")
    public RestMessage<Boolean> update(@RequestBody BusinessLineDto businessLineDto) {
        return RestMessage.doSuccess(businessLineService.update(businessLineDto) > 0);
    }

    @ApiOperation(value = "修改业务线数据状态", httpMethod = "POST")
    @PostMapping(value = "/updateState")
    public RestMessage<Boolean> updateState(@RequestBody BusinessLineDto businessLineDto) {
        return RestMessage.doSuccess(businessLineService.updateState(businessLineDto) > 0);
    }

    @ApiOperation(value = "分页查询业务线数据", httpMethod = "POST")
    @PostMapping(value = "/getList")
    public RestMessage<PageInfo<BusinessLineDto>> getList(@RequestBody BusinessLineParam param) {
        return RestMessage.doSuccess(businessLineService.getList(param));
    }
}
