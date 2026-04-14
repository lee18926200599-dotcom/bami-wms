package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.enums.SourceTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户搜索末级菜单：有url和tag的菜单
 */
@Api(tags = "1权限-用户搜索末级菜单：有url和tag的菜单接口文档")
@RequestMapping(value = "permission-user-search")
@RestController
public class UserSearchController {
    private final static Logger LOG = LoggerFactory.getLogger(UserSearchController.class);
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;
    /**
     * 英文、汉字、数字匹配
     */
    private static Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\u4E00-\\u9FA5]+$");

    @ApiOperation(value = "根据用户查询功能权限")
    @PostMapping(value = "/searchMenusByUid")
    public RestMessage searchMenusByUid(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Integer managerLevel, @RequestParam String resourceName) {
        LOG.info("userid----->" + userId + "---身份类型-->" + managerLevel);
        if (illegalResourceName(resourceName)) {//如果不合法搜索，直接返回提示
            return RestMessage.error(PermissionErrorCode.SEARCH_PARAM_ERROR.getErrorCode() + "", PermissionErrorCode.SEARCH_PARAM_ERROR.getErrorReason());
        }
        try {
            FplUser user = new FplUser();
            user.setId(userId);
            user.setManagerLevel(managerLevel);
            List<UserPermission> userPermissions = Lists.newArrayList();
            userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", SourceTypeEnum.PC.getCode(), 0L, null, 0L, user);
            //模糊查询关键字的末级菜单
            List<UserPermission> lastStageMenus = userPermissions.stream().filter(item -> StringUtils.isNotBlank(item.getUrl()) && (StringUtils.contains(item.getName(), resourceName))).collect(Collectors.toList());
            //递归获取父级菜单拼接后返回
            userPermissions = basePermissionRoleService.searchMenuList(userPermissions, lastStageMenus);
            return RestMessage.doSuccess(userPermissions);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("搜索菜单失败");
            return RestMessage.error("1001", I18nUtils.getMessage("org.common.fail"));
        }

    }

    /**
     * 对搜索内容进行正则匹配，不是汉字 字母 数字判定为非法菜单名
     *
     * @param resourceName
     * @return
     */
    private boolean illegalResourceName(String resourceName) {
        Matcher match = pattern.matcher(resourceName);
        boolean result = match.find();
        return !result;
    }

}
