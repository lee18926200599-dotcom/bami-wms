package com.basedata.server.controller.localdata;

import com.common.util.message.RestMessage;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.BasePlatformPrintTemplateReqDTO;
import com.basedata.server.service.BasePlatformPrintTemplateService;
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
 * <p>
 * 平台面单模版前端控制器
 * </p>
 */
@Api(tags = "平台面单模版（电商平台持久化到本地的数据）")
@RestController
@RequestMapping("/base-platform-print-template")
public class BasePlatformPrintTemplateController {

    @Resource
    private BasePlatformPrintTemplateService basePlatformPrintTemplateService;


    @ApiOperation(value = "根据条件获取面单模板信息", notes = "根据条件获取面单模板信息")
    @PostMapping("/queryTemplate")
    public RestMessage<List<BasePlatformPrintTemplateDto>> queryPlatformPrintTemplate(@RequestBody @Valid BasePlatformPrintTemplateReqDTO dto){
        return RestMessage.querySuccess(basePlatformPrintTemplateService.queryPlatformPrintTemplate(dto));
    }
}
