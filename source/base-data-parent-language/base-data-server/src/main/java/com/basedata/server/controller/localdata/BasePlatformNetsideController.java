package com.basedata.server.controller.localdata;

import com.github.pagehelper.PageInfo;
import com.common.util.message.RestMessage;
import com.basedata.server.entity.BasePlatformNetside;
import com.basedata.server.query.BasePlatformNetsideQueryVo;
import com.basedata.server.service.BasePlatformNetsideService;
import com.basedata.server.vo.BasePlatformNetsideVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* <p>
 * 平台网点信息 前端控制器
 * </p>
*/
@Api(tags = "平台网点信息（电商平台持久化到本地的数据）")
@RestController
@RequestMapping("/base-platform-netside")
public class BasePlatformNetsideController {

    @Resource
    private BasePlatformNetsideService basePlatformNetsideService;


    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BasePlatformNetsideVo>> queryPageList(@RequestBody BasePlatformNetsideQueryVo queryVo) throws Exception {
        return RestMessage.querySuccess(basePlatformNetsideService.queryPageList(queryVo));
    }

    @ApiOperation(value = "查询列表（不分页）", notes = "查询列表")
    @PostMapping("/queryList")
    public RestMessage<List<BasePlatformNetside>> queryList(@RequestBody BasePlatformNetsideQueryVo queryVo) {
        return RestMessage.querySuccess(basePlatformNetsideService.queryList(queryVo));
    }

}
