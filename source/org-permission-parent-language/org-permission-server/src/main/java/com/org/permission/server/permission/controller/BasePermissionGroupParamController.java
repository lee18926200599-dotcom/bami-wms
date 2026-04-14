package com.org.permission.server.permission.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.language.util.I18nUtils;
import com.org.permission.server.permission.entity.BasePermissionGroupParam;
import com.org.permission.server.permission.enums.GroupParamConfigEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.common.permission.dto.BasePermissionGroupParamDto;
import com.org.permission.server.permission.service.IBasePermissionGroupParamService;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 集团参数配置管理
 */
@Api(tags = "1权限-集团参数配置管理接口文档")
@RequestMapping(value = "permission-group-param")
@RestController
public class BasePermissionGroupParamController {
    private final static Logger logger = LoggerFactory.getLogger(BasePermissionGroupParamController.class);

    @Autowired
    private IBasePermissionGroupParamService basePermissionGroupParamService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private GroupConfigHelper groupConfigHelper;

    // 修改集团参数配置
    @ApiOperation(value = "修改集团参数配置")
    @PostMapping(value = "/updateBasePermissionGroupParam")
    public RestMessage updateBasePermissionGroupParam(@RequestBody List<BasePermissionGroupParam> basePermissionGroupParams) {
        int result = 0;
        Long userId = FplUserUtil.getUserId();
        String userName = FplUserUtil.getUserName();
        for (BasePermissionGroupParam basePermissionGroupParam : basePermissionGroupParams) {
            BasePermissionGroupParam existParam = basePermissionGroupParamService.getBasePermissionGroupParamById(basePermissionGroupParam.getId());
            if (existParam!=null){
                redisUtil.del("4pl_permission_group_param_" + existParam.getGroupId());
            }
            basePermissionGroupParam.setModifiedBy(userId);
            basePermissionGroupParam.setModifiedName(userName);
            basePermissionGroupParam.setModifiedDate(new Date());
            result = basePermissionGroupParamService.updateBasePermissionGroupParam(basePermissionGroupParam);
        }
        return RestMessage.doSuccess(result);
    }


    // 不分页查询集团参数配置
    @ApiOperation(value = "不分页查询集团参数配置")
    @PostMapping(value = "/getListBasePermissionGroupParamsByGroupId")
    public RestMessage<List<BasePermissionGroupParam>> getListBasePermissionGroupParamsByPOJO(@RequestParam Long groupId) {
        BasePermissionGroupParam basePermissionGroupParam = new BasePermissionGroupParam();
        basePermissionGroupParam.setGroupId(groupId);
        List<BasePermissionGroupParam> result = basePermissionGroupParamService.getListBasePermissionGroupParamsByPOJO(basePermissionGroupParam);
        return RestMessage.doSuccess(result);
    }

    @ApiOperation(value = "获取集团策略")
    @GetMapping(value = "/getGroupStrategy")
    public RestMessage<Boolean> getGroupStrategy(@RequestParam Long groupId) {
        Boolean result = groupConfigHelper.isUserAuth(groupId);
        return RestMessage.querySuccess(result);
    }


    /**
     * 根据集团ID和参数code获取集团参数设置
     *
     * @param groupId   集团ID
     * @param paramCode 参数code
     */
    @ApiOperation(value = "根据集团ID和参数code获取集团参数设置")
    @PostMapping(value = "/getBasePermissionGroupParamsByGroupIdAndCode")
    public RestMessage<BasePermissionGroupParamDto> getBasePermissionGroupParamsByGroupIdAndCode(@RequestParam(name = "groupId", required = true) Long groupId, @RequestParam(name = "paramCode", required = true) String paramCode) {
        logger.info("根据集团ID和参数code获取集团参数设置接口入参:groupId=" + groupId + "---paramCode=" + paramCode);
        GroupParamConfigEnum config = GroupParamConfigEnum.getEnumByValue(paramCode);
        if (config == null) {
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", I18nUtils.getMessage("org.common.param.cannot.null"));
        }
        BasePermissionGroupParam basePermissionGroupParam = new BasePermissionGroupParam();
        basePermissionGroupParam.setGroupId(groupId);
        basePermissionGroupParam.setParamCode(paramCode);
        basePermissionGroupParam.setState(ValidEnum.YES.getCode());
        RestMessage<BasePermissionGroupParamDto> RestMessage = new RestMessage<BasePermissionGroupParamDto>();
        RestMessage.setData(new BasePermissionGroupParamDto());
        List<BasePermissionGroupParam> result = basePermissionGroupParamService.getListBasePermissionGroupParamsByPOJO(basePermissionGroupParam);
        if (CollectionUtils.isNotEmpty(result)) {
            BasePermissionGroupParam groupParam = result.get(0);
            BasePermissionGroupParamDto permissionGroupParamDto = new BasePermissionGroupParamDto();
            BeanUtils.copyProperties(groupParam, permissionGroupParamDto);
            permissionGroupParamDto.setParamValue("1");
            if (groupParam.getParamValue() == null || "".equals(groupParam.getParamValue())) {
                return RestMessage;
            }
            JSONArray jsonArray = JSON.parseArray(groupParam.getParamValue());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if (name.equals(I18nUtils.getMessage("org.common.yes"))) {
                    permissionGroupParamDto.setParamValue(jsonObject.getString("select"));
                }
            }
            RestMessage.setData(permissionGroupParamDto);
            return RestMessage;
        }
        return RestMessage;
    }
}
