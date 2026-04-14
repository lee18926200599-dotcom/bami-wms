package com.basedata.server.rpc;

import com.basedata.server.entity.BasePlatformNetside;
import com.basedata.server.service.BasePlatformNetsideService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformNetsideReqDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "平台网点信息（外部服务）")
@RestController
@RequestMapping("/rpc/base-platform-netside")
public class BasePlatformNetsideFeignController {
    @Resource
    private BasePlatformNetsideService basePlatformNetsideService;

    @ApiOperation(value = "新增或更新平台网点信息", notes = "新增")
    @PostMapping("/saveOrUpdate")
    public RestMessage<BasePlatformNetside> saveOrUpdate(@RequestBody @Valid BasePlatformNetsideReqDTO updateVo) {
        basePlatformNetsideService.saveOrUpdate(updateVo);
        return RestMessage.doSuccess(null);
    }
}
