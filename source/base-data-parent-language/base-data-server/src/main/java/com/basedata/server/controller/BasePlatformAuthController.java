package com.basedata.server.controller;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.BasePlatformAuthDto;
import com.basedata.common.dto.BasePlatformAuthReqDTO;
import com.basedata.common.dto.SimpleDTO;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.vo.BasePlatformAuthQueryVo;
import com.basedata.common.vo.BasePlatformAuthVo;
import com.basedata.server.entity.BasePlatformAuth;
import com.basedata.server.service.BasePlatformAuthService;
import com.basedata.server.vo.BasePlatformAuthUpdateVo;
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
 * 货主平台授权信息表 前端控制器
 * </p>
 */
@Api(tags = "货主平台授权")
@RestController
@RequestMapping("/base-platform-auth")
public class BasePlatformAuthController {

    @Resource
    private BasePlatformAuthService basePlatformAuthService;

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/saveConfig")
    public RestMessage<Object> saveConfig(@RequestBody @Valid BasePlatformAuthUpdateVo updateVo) {
        basePlatformAuthService.save(updateVo);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "更新", notes = "更新")
    @PostMapping("/updateConfig")
    public RestMessage<Object> update(@RequestBody @Valid BasePlatformAuthUpdateVo updateVo) {
        basePlatformAuthService.update(updateVo);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchDelete")
    public RestMessage<Object> batchDelete(@RequestBody List<Long> ids) {
        return RestMessage.doSuccess(basePlatformAuthService.deleteByIds(ids));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BasePlatformAuthVo>> queryPageList(@RequestBody BasePlatformAuthQueryVo queryVo) throws Exception {
        return RestMessage.querySuccess(basePlatformAuthService.queryPageList(queryVo));
    }

    @ApiOperation(value = "查询授权列表（查启用状态的，不分页）", notes = "查询列表")
    @PostMapping("/queryVaildList")
    public RestMessage<List<BasePlatformAuth>> queryValidList(@RequestBody BasePlatformAuthReqDTO reqDTO) {
        return RestMessage.querySuccess(basePlatformAuthService.queryValidList(reqDTO));
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @PostMapping("/batchEnable")
    public RestMessage<Object> batchEnableOrDisable(@RequestBody @Valid UpdateStatusDto statusDto) {
        return RestMessage.doSuccess(basePlatformAuthService.batchEnableOrDisable(statusDto));
    }

    @ApiOperation(value = "授权/重新授权（生成授权链接）", notes = "生成授权链接")
    @PostMapping("/genPlatformAuthUrl")
    public RestMessage<String> genPlatformAuthUrl(@RequestBody @Valid SimpleDTO simpleDTO) {
        return RestMessage.querySuccess(basePlatformAuthService.genPlatformAuthUrl(simpleDTO.getId()));
    }

    @ApiOperation(value = "刷新token（从接口平台更新token）", notes = "从接口平台更新token")
    @PostMapping("/refreshToken")
    public RestMessage<Boolean> refreshToken(@RequestBody @Valid SimpleDTO simpleDTO) {
        return RestMessage.querySuccess(basePlatformAuthService.refreshToken(simpleDTO.getId()));
    }

    @ApiOperation(value = "查询平台密钥信息")
    @PostMapping("/queryByOwnerIdAndPlatformName")
    public RestMessage<BasePlatformAuthDto> queryByOwnerIdAndPlatformName(@RequestBody BasePlatformAuthQueryVo queryVo) {
        return RestMessage.doSuccess(basePlatformAuthService.queryByOwnerIdAndPlatformName(queryVo));
    }

}
