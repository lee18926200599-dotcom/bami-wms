package com.org.permission.server.permission.controller;

import com.google.common.collect.Lists;
import com.org.permission.server.permission.dto.BasePermissionManagementDto;
import com.org.permission.server.permission.dto.GroupManageMentDto;
import com.org.permission.server.permission.enums.ValidEnum;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.entity.BasePermissionGroupManagement;
import com.org.permission.server.permission.mapper.BasePermissionGroupManagementMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 管理维度表管理
 */
@Api(tags = "1权限-管理维度表管理接口文档")
@RequestMapping(value = "permission-management")
@RestController
public class BasePermissionManagementController {
    @Autowired
    private IBasePermissionGroupManagementService basePermissionGroupManagementService;
    @Autowired
    private BasePermissionGroupManagementMapper basePermissionGroupManagementMapper;


    // 修改管理维度表
    @ApiOperation(value = "修改管理维度表")
    @PostMapping(value = "/updateBasePermissionManagement")
    public RestMessage updateBasePermissionManagement(@RequestBody List<BasePermissionManagementDto> basePermissionManagements) {
        int result = 0;
        for (BasePermissionManagementDto basePermissionManagementDto : basePermissionManagements) {
            BasePermissionGroupManagement bpgm = new BasePermissionGroupManagement();
            bpgm.setId(basePermissionManagementDto.getId());
            if (basePermissionManagementDto.getState() == ValidEnum.YES.getCode()) {
                bpgm.setState(ValidEnum.YES.getCode());
            } else {
                bpgm.setState(ValidEnum.NO.getCode());
            }
            bpgm.setModifiedDate(new Date());
            result = basePermissionGroupManagementService.updateBasePermissionGroupManagement(bpgm);
        }
        return RestMessage.doSuccess(result);
    }

    // 不分页查询管理维度表
    @ApiOperation(value = "不分页查询管理维度表")
    @PostMapping(value = "/getListBasePermissionManagementsByPOJO")
    public RestMessage getManageMentByGroupId(@RequestBody GroupManageMentDto groupManageMentDto) {
        List<BasePermissionManagementDto> result = basePermissionGroupManagementMapper.getManageMentByGroupId(groupManageMentDto);
        List<BasePermissionManagementDto> list = Lists.newArrayList();
        for (BasePermissionManagementDto basePermissionManagementDto : result) {
            BasePermissionManagementDto bm = new BasePermissionManagementDto();
            bm.setId(basePermissionManagementDto.getId());
            bm.setManagementId(basePermissionManagementDto.getManagementId());
            bm.setName(basePermissionManagementDto.getName());
            bm.setDes(basePermissionManagementDto.getDes());
            bm.setRemark(basePermissionManagementDto.getRemark());
            bm.setState(basePermissionManagementDto.getState());
            list.add(bm);
        }
        return RestMessage.doSuccess(list);
    }

    // 不分页查询管理维度表
    @ApiOperation(value = "不分页查询管理维度表")
    @PostMapping(value = "/getQyManageMentByGroupId")
    public RestMessage getQyManageMentByGroupId(@RequestBody GroupManageMentDto groupManageMentDto) {
        List<BasePermissionManagementDto> result = basePermissionGroupManagementMapper.getManageMentByGroupId(groupManageMentDto);
        List<BasePermissionManagementDto> list = Lists.newArrayList();
        for (BasePermissionManagementDto basePermissionManagementDto : result) {
            if (basePermissionManagementDto.getState() != ValidEnum.YES.getCode()) {
                continue;
            }
            BasePermissionManagementDto bm = new BasePermissionManagementDto();
            bm.setId(basePermissionManagementDto.getId());
            bm.setManagementId(basePermissionManagementDto.getManagementId());
            bm.setName(basePermissionManagementDto.getName());
            bm.setDes(basePermissionManagementDto.getDes());
            bm.setRemark(basePermissionManagementDto.getRemark());
            bm.setState(basePermissionManagementDto.getState());
            list.add(bm);
        }
        return RestMessage.doSuccess(list);
    }
}
