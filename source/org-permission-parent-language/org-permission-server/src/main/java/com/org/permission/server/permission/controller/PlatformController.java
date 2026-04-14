package com.org.permission.server.permission.controller;

import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台管理员的接口
 */
@Api(tags = "1权限-平台管理员的接口文档")
@RequestMapping(value = "/platform")
@RestController
public class PlatformController {
    private final static Logger LOG = LoggerFactory.getLogger(PlatformController.class);

    @Autowired
    private GroupConfigHelper groupConfigHelper;


    // 管理员管理页面，平台管理员身份 获取所属组织数据源
    @ApiOperation(value = "管理员管理页面，平台管理员身份 获取所属组织数据源")
    @PostMapping(value = "/getPlatformOrgs")
    public RestMessage<List<UserOrgPermissionDto>> getPlatformOrgs(@RequestParam long userId, @RequestParam Long groupId) {
        List<UserOrgPermissionDto> userOrgPermissionDtos = null;
        try {
            userOrgPermissionDtos = groupConfigHelper.getPlatformOrgPermission(userId, groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(userOrgPermissionDtos);
    }

}
