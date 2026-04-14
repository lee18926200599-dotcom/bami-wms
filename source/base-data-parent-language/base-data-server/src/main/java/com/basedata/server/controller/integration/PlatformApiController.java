package com.basedata.server.controller.integration;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressReqDto;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressRespDto;
import com.basedata.server.dto.integration.GetPlatformNetsideRespDto;
import com.basedata.server.service.integration.PlatformApiService;
import com.basedata.server.vo.integration.GetPlatformNetsideReqVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Description: wms请求 接口平台获取 平台店铺、面单 等信息
 */
@Api(tags = "请求接口平台获取面单、网点等信息")
@RestController
@RequestMapping("/platform-api")
public class PlatformApiController {
    @Resource
    private PlatformApiService platformApiService;

    @ApiOperation(value = "获取快递网点信息", notes = "获取快递网点信息")
    @PostMapping("/getNetsideAddress")
    public RestMessage<List<GetPlatformNetsideRespDto>> getNetsideAddress(@RequestBody @Valid GetPlatformNetsideReqVo reqVo) throws Exception {
        return RestMessage.querySuccess(platformApiService.getNetsideAddress(reqVo));
    }

    // 给 承运商网点对应关系 用
    @ApiOperation(value = "获取授权店铺下的快递网点信息", notes = "获取授权店铺下的快递网点信息")
    @PostMapping("/getAuthStoreNetsideAddress")
    public RestMessage<PageInfo<GetPlatformNetsideAddressRespDto>> getAuthStoreNetsideAddress(@RequestBody @Valid GetPlatformNetsideAddressReqDto reqDto) throws Exception {
        return RestMessage.querySuccess(platformApiService.getAuthStoreNetsideAddress(reqDto));
    }

    @ApiOperation(value = "获取电商平台面单模板并同步到本地库", notes = "获取电商平台面单模板")
    @PostMapping("/syncFaceOrderTemplate")
    public RestMessage<String> syncFaceOrderTemplate(@RequestBody @Valid SyncFaceOrderTemplateReqVo reqVo) {
        return RestMessage.querySuccess(platformApiService.syncFaceOrderTemplate(reqVo));
    }

}
