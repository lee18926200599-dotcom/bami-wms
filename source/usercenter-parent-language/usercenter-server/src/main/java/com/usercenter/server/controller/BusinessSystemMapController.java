package com.usercenter.server.controller;
import com.common.util.message.RestMessage;
import com.usercenter.server.entity.BaseUserBusinessSystemMap;
import com.usercenter.server.service.IBaseUserBusinessSystemMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Description: 系统-身份对应关系 api controller
 */
@Api(tags = "系统-身份对应关系", description = "系统-身份对应关系")
@RestController
@RequestMapping("/businessSystemMap")
public class BusinessSystemMapController{

    @Autowired
    private IBaseUserBusinessSystemMapService identitySystemService;


    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestMessage add(@RequestBody BaseUserBusinessSystemMap baseUserBusinessSystemMap) {
        identitySystemService.add(baseUserBusinessSystemMap);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestMessage update(@RequestBody BaseUserBusinessSystemMap baseUserBusinessSystemMap) {
        identitySystemService.update(baseUserBusinessSystemMap);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestMessage<List<BaseUserBusinessSystemMap>> list(@RequestBody BaseUserBusinessSystemMap listReq) {
        List<BaseUserBusinessSystemMap> page = identitySystemService.getList(listReq);
        return RestMessage.doSuccess(page);
    }

}
