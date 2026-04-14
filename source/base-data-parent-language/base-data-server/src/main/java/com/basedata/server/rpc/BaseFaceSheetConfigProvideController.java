package com.basedata.server.rpc;

import com.basedata.server.service.BaseFaceSheetConfigService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.FacesheetConfigDto;
import com.basedata.common.dto.QueryStoreInfoDto;
import com.basedata.common.vo.FacesheetConfigForPrintVo;
import com.basedata.common.vo.FacesheetConfigVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/facesheet")
public class BaseFaceSheetConfigProvideController {

    @Autowired
    private BaseFaceSheetConfigService baseFaceSheetConfigService;

    @ApiOperation(value = "获取")
    @PostMapping("/getConfig")
    public RestMessage<FacesheetConfigVo> getConfig(@RequestBody @Valid FacesheetConfigDto configDto) {
        FacesheetConfigVo facesheetConfigVo = baseFaceSheetConfigService.getConfig(configDto);
        return RestMessage.querySuccess(facesheetConfigVo);
    }

    @ApiOperation(value = "获取")
    @PostMapping("/getBatchConfig")
    public RestMessage<List<FacesheetConfigForPrintVo>> getBatchConfig(@RequestBody @Valid QueryStoreInfoDto queryStoreInfoDto) {
        List<FacesheetConfigForPrintVo> list = baseFaceSheetConfigService.getBatchConfig(queryStoreInfoDto);
        return RestMessage.querySuccess(list);
    }

}
