package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.dto.req.GetDataPermissionReq;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.DataPermissionDto;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 获取用户数据权限<br>
 */
@Api(tags = "1权限-获取用户数据权限接口文档")
@RequestMapping(value = "permission-user-data")
@RestController
public class UserDataController {
    private final static Logger LOG = LoggerFactory.getLogger(UserDataController.class);
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;

    @ApiOperation(value = "根据用户id、集团ID获取用户数据权限")
    @PostMapping(value = "/getDataPermissonsByUid")
    public RestMessage<Map<String, Object>> getDataPermissonsByUid(@RequestParam Long userId, @RequestParam Long groupId) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (groupId > 0) {//如果集团id不为空，走业务类型逻辑.
                List<UserDataPermissionDto> userDataPermissionDtos = Lists.newArrayList();
                long begin6 = System.currentTimeMillis();
                userDataPermissionDtos = groupConfigHelper.getUserDataPermissionByStrategy(userId, groupId, 0, "", "", "");
                long end6 = System.currentTimeMillis();
                LOG.info("数据权限耗时间--->" + (end6 - begin6));
                long begin7 = System.currentTimeMillis();
                userDataPermissionDtos = basePermissionRoleService.getUserDataPermissionsList(userDataPermissionDtos);
                long end7 = System.currentTimeMillis();
                LOG.info("数据权限构造树结构耗时间--->" + (end7 - begin7));
                map.put("datas", userDataPermissionDtos);
                return RestMessage.doSuccess(map);
            } else {//如果groupId为空，只能b2b app 登录.
                return RestMessage.error("1001", I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("获取用户数据权限失败");
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.get.user.data.permission.fail"));
        }

    }


    @ApiOperation(value = "根据用户id、集团ID、数据权限维度获取用户数据权限")
    @PostMapping(value = "/getDataPermissons")
    public RestMessage<List<DataPermissionDto>> getDataPermissons(@RequestBody GetDataPermissionReq getDataPermissionReq) {
        Long groupId = getDataPermissionReq.getGroupId();
        Long userId = getDataPermissionReq.getUserId();
        Integer managementId = getDataPermissionReq.getManagementId();
        LOG.info("根据用户id获取用户数据权限,请求参数为groupId={},userId={},managementId={}", groupId, userId, managementId);
        try {
            if (groupId > 0) {//如果集团id不为空，走业务类型逻辑.
                List<UserDataPermissionDto> userDataPermissionDtos = groupConfigHelper.getUserDataPermissionByStrategy(userId, groupId, managementId, "", "", "");
                List<DataPermissionDto> dataPermissionDtos = new DataPermissionDto().convertListFrom(userDataPermissionDtos);
                return RestMessage.doSuccess(dataPermissionDtos);
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
            }
        } catch (Exception e) {
            LOG.error("查询用户id{},集团id{},维度managementId{}获取用户数据权限失败{}", userId, groupId, managementId, e.getMessage(), e);
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.get.user.data.permission.fail"));
        }
    }


}
