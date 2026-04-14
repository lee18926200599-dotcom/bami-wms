package com.basedata.server.controller;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.AreaInfoDto;
import com.basedata.common.query.AreaInfoQuery;
import com.basedata.server.service.AreaInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "行政区划")
@RestController
@RequestMapping("/AreaInfo")
public class AreaInfoController {

    @Resource
    private AreaInfoService areaInfoService;


    /**
     * 地区查询
     * @param  areaInfoQuery
     * @return
     */
    @ApiOperation(value = "地区查询")
    @PostMapping("/queryNoPage")
    public RestMessage<List<AreaInfoDto>> queryNoPage(@RequestBody AreaInfoQuery areaInfoQuery) {
        try {
            List<AreaInfoDto> list = areaInfoService.queryNoPage(areaInfoQuery);
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 地区查询
     * @param  areaInfoQuery
     * @return
     */
    @ApiOperation(value = "地区查询")
    @PostMapping("/queryById")
    public RestMessage<AreaInfoDto> queryById(@RequestBody AreaInfoQuery areaInfoQuery) {
        try {
            AreaInfoDto areaInfoDto = areaInfoService.queryById(areaInfoQuery.getAreaId());
            return RestMessage.doSuccess(areaInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }
}
