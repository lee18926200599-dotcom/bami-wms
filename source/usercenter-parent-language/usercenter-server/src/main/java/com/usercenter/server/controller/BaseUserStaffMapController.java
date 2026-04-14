package com.usercenter.server.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.common.util.message.RestMessage;
import com.usercenter.common.dto.UserStaffMapDto;
import com.usercenter.common.dto.request.UserStaffMapFindListReq;
import com.usercenter.common.dto.request.UserStaffMapFindOneReq;
import com.usercenter.common.dto.request.UserStaffMapSaveReq;
import com.usercenter.server.entity.BaseUserStaffMap;
import com.usercenter.server.service.IBaseUserStaffMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/user-staff")
@Api(tags = "用户人员关系映射", description = "用户人员关系映射")
public class BaseUserStaffMapController {
    @Autowired
    private IBaseUserStaffMapService userStaffMapService;


    @ApiOperation(value = "保存用户人员映射", httpMethod = "POST")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestMessage<UserStaffMapDto> save(@RequestBody UserStaffMapSaveReq userStaffMapSaveReq) {
        //参数转换
        BaseUserStaffMap baseUserStaffMap = new BaseUserStaffMap();
        BeanUtils.copyProperties(userStaffMapSaveReq, baseUserStaffMap);
        baseUserStaffMap.setCreatedBy(userStaffMapSaveReq.getUserId());
        BaseUserStaffMap saveResult = userStaffMapService.save(baseUserStaffMap);
        //结果转换
        UserStaffMapDto result = new UserStaffMapDto();
        BeanUtils.copyProperties(saveResult, result);
        return RestMessage.doSuccess(result);
    }





    @ApiOperation(value = "根据条件查询映射关系", httpMethod = "POST")
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public RestMessage<List<UserStaffMapDto>> findList(@RequestBody UserStaffMapFindListReq userStaffMapFindListReq) {
        List<BaseUserStaffMap> findResult = userStaffMapService.findListByCondition(userStaffMapFindListReq.getUserIds(),
                userStaffMapFindListReq.getStaffIds(), userStaffMapFindListReq.getState(), userStaffMapFindListReq.getDeletedFlag());
        //结果转换
        List<UserStaffMapDto> result = Lists.newArrayList();
        if (ObjectUtils.isEmpty(findResult)) {
            return RestMessage.doSuccess(result);
        }
        findResult.forEach(baseUserStaffMap -> {
                    UserStaffMapDto userStaffMapDTO = new UserStaffMapDto();
                    BeanUtils.copyProperties(baseUserStaffMap, userStaffMapDTO);
                    result.add(userStaffMapDTO);
                }
        );
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "根据条件查询映射关系", httpMethod = "POST")
    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    public RestMessage<UserStaffMapDto> findOne(@RequestBody UserStaffMapFindOneReq userStaffMapFindOneReq) {
        BaseUserStaffMap query = new BaseUserStaffMap();
        BeanUtils.copyProperties(userStaffMapFindOneReq,query);
        BaseUserStaffMap findResult = userStaffMapService.findOneByCondition(query);
        UserStaffMapDto result = new UserStaffMapDto();
        if(ObjectUtils.isEmpty(findResult)){
            return RestMessage.doSuccess(result);
        }
        BeanUtils.copyProperties(findResult,result);
        return RestMessage.doSuccess(result);
    }




    @RequestMapping(value = "/syncUserStaffMap")
    @ApiOperation(value = "同步人员和用户的映射", httpMethod = "POST")
    public RestMessage syncUserStaffMap(String password) {
        if(ObjectUtils.isEmpty(password)){
            return RestMessage.doSuccess(I18nUtils.getMessage("user.check.pwd.null"));
        }
        userStaffMapService.syncUserStaffMap();
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.sync.success"));
    }

}
