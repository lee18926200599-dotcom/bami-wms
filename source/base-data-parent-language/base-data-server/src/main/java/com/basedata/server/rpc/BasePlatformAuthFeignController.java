package com.basedata.server.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.basedata.server.entity.BasePlatformAuth;
import com.basedata.server.service.BasePlatformAuthService;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformAuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "货主平台授权-feign服务")
@RestController
@RequestMapping("/base-platform-auth-feign")
public class BasePlatformAuthFeignController {
    @Resource
    private BasePlatformAuthService basePlatformAuthService;

    @ApiOperation(value = "查询货主唯一的平台授权（PS：没有过滤是否是启用状态）", notes = "查询货主唯一的平台授权")
    @PostMapping("/findOne")
    public RestMessage<BasePlatformAuthDto> findOne(@RequestParam("serviceProviderId") Long serviceProviderId, @RequestParam("ownerId") Long ownerId, @RequestParam("platformCode") String platformCode) {
        BasePlatformAuth one = basePlatformAuthService.findOne(serviceProviderId, ownerId, platformCode);
        return RestMessage.querySuccess(one == null ? null : BeanUtil.toBean(one, BasePlatformAuthDto.class));
    }

    /**
     * 授权后更新token相关信息
     *
     * @param basePlatformAuthDto
     */
    @ApiOperation(value = "授权后更新token相关信息", notes = "授权后更新token相关信息")
    @PostMapping("/updateToken")
    public RestMessage<Boolean> updateToken(@RequestBody BasePlatformAuthDto basePlatformAuthDto) {
        return RestMessage.doSuccess(basePlatformAuthService.updateToken(basePlatformAuthDto));
    }
}
