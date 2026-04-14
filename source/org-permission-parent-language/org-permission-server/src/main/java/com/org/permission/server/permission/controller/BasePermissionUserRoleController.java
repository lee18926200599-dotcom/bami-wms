package com.org.permission.server.permission.controller;

import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.permission.dto.*;
import com.org.permission.server.permission.dto.InputParentDto;
import com.org.permission.server.permission.dto.InputUserRoleDto;
import com.org.permission.server.permission.dto.OutPutUserRoleDto;
import com.org.permission.server.permission.dto.UserExpireDto;
import com.org.permission.server.permission.dto.req.BatchDeleteUserRoleReq;
import com.org.permission.server.permission.dto.req.BatchSaveUserRoleReq;
import com.org.permission.server.permission.dto.req.RoleDataPermissionParam;
import com.org.permission.server.permission.dto.req.UserDataPermissionParam;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import com.org.permission.server.permission.service.IBasePermissionUserRoleService;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.org.permission.server.permission.service.impl.UserHelper;
import com.common.util.message.RestMessage;
import com.usercenter.common.enums.SourceTypeEnum;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 用户角色关联表管理
 */
@Api(tags = "1权限-用户角色关联表管理接口文档")
@RequestMapping(value = "permission-user-role")
@RestController
public class BasePermissionUserRoleController {
    private Logger log = LoggerFactory.getLogger(BasePermissionUserRoleController.class);
    @Autowired
    private IBasePermissionUserRoleService basePermissionUserRoleService;
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;
    @Autowired
    private UserHelper userHelper;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    // 角色批量添加用户 弹窗 查询组织权限和用户树，查询角色下已有的组织权限用户树
    @ApiOperation(value = "角色批量添加用户 弹窗 查询组织权限和用户树，查询角色下已有的组织权限用户树")
    @GetMapping(value = "/getUserTree")
    public RestMessage<List<OrgDto>> getUserTree(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Integer roleId, @RequestParam(required = false) String userName, @RequestParam(required = false) String orgName) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("groupId", groupId);
        map.put("roleId", roleId);
        map.put("userName", userName);
        map.put("orgName", orgName);
        List<UserOrgPermissionDto> ups = null;
        try {
            ups = groupConfigHelper.getUserOrgPermissionByStrategy(userId, groupId, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("list", ups);
        List<OrgDto> allUsers = basePermissionUserRoleService.getOrgUserList(map);
        return RestMessage.doSuccess(allUsers);
    }

    // 批量分配用户
    @ApiOperation(value = "批量分配用户")
    @PostMapping(value = "/batchUserRoleCreate")
    public RestMessage<String> batchUserRoleCreate(@RequestBody InputUserRoleDto inputUserRoleDto) {
        return basePermissionUserRoleService.batchUserRoleCreate(inputUserRoleDto);
    }

    // 调整用户时效性
    @ApiOperation(value = "调整用户时效性")
    @PostMapping(value = "/selectUserExpire")
    public RestMessage<UserExpireDto> selectUserExpire(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Integer roleId) {
        return basePermissionUserRoleService.selectUserExpire(userId, roleId, groupId);
    }

    // 确定调整用户时效性
    @ApiOperation(value = "确定调整用户时效性")
    @PostMapping(value = "/adjustUserExpire")
    public RestMessage<Integer> adjustUserExpire(@RequestParam Long userId, @RequestParam Long id, @RequestParam(required = false) Date effectiveTime, @RequestParam(required = false) Date expireTime) {
        BasePermissionUserRole basePermissionUserRole = new BasePermissionUserRole();
        basePermissionUserRole.setId(id);
        basePermissionUserRole.setAuthorTime(new Date());
        basePermissionUserRole.setAuthorUser(userId);
        basePermissionUserRole.setEffectiveTime(effectiveTime);
        basePermissionUserRole.setExpireTime(expireTime);
        basePermissionUserRole.setModifiedDate(new Date());
        basePermissionUserRole.setModifiedBy(userId);
        int result = basePermissionUserRoleService.updateBasePermissionUserRole(basePermissionUserRole);
        return RestMessage.doSuccess(result);
    }

    // 查询用户下角色列表
    @ApiOperation(value = "查询用户下角色列表")
    @PostMapping(value = "/userRoleList")
    public RestMessage<List<OutPutUserRoleDto>> getUserRoleList(@RequestBody InputParentDto inputParentDto) {
        List<OutPutUserRoleDto> outPutUserRoleDtos = basePermissionUserRoleService.getUserRoleList(inputParentDto);
        long now = System.currentTimeMillis();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<OutPutUserRoleDto> result = outPutUserRoleDtos.stream().filter(outPutUserRoleDto -> ((ObjectUtils.isEmpty(outPutUserRoleDto.getExpireTime())) || ((((outPutUserRoleDto.getExpireTime() == null ? 0 : outPutUserRoleDto.getExpireTime().getTime()) - now) >= 0) && ((now - outPutUserRoleDto.getEffectiveTime().getTime()) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateTask(outPutUserRoleDtos, result, basePermissionUserRoleService));
        return RestMessage.doSuccess(result);
    }

    // 批量保存用户角色关系
    @ApiOperation(value = "批量保存用户角色关系")
    @PostMapping(value = "/batchSaveUserRole")
    public RestMessage batchSaveUserRole(@RequestBody BatchSaveUserRoleReq batchSaveUserRoleReq) {
        Assert.notNull(batchSaveUserRoleReq.getAuthUserId(), I18nUtils.getMessage("org.common.param.operateid.cannot.null"));
        Assert.notNull(batchSaveUserRoleReq.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(batchSaveUserRoleReq.getRoleId(), I18nUtils.getMessage("permission.role.id.null"));
        batchSaveUserRoleReq.setAuthUserName(FplUserUtil.getUserName());
        basePermissionUserRoleService.batchSaveUserRole(batchSaveUserRoleReq);
        return RestMessage.doSuccess(null);
    }

    // 批量保存用户角色关系
    @ApiOperation(value = "批量删除用户角色关系")
    @PostMapping(value = "/batchDeleteUserRole")
    public RestMessage batchDeleteUserRole(@RequestBody BatchDeleteUserRoleReq batchDeleteUserRoleReq) {
        Assert.notNull(batchDeleteUserRoleReq.getAuthUserId(), I18nUtils.getMessage("org.common.param.operateid.cannot.null"));
        Assert.notNull(batchDeleteUserRoleReq.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(batchDeleteUserRoleReq.getRoleId(), I18nUtils.getMessage("permission.role.id.null"));
        basePermissionUserRoleService.batchDeleteUserRole(batchDeleteUserRoleReq);
        return RestMessage.doSuccess(null);
    }


    class UpdateTask implements Callable<String> {
        private List<OutPutUserRoleDto> outPutUserRoleDtos;
        private List<OutPutUserRoleDto> result;
        private IBasePermissionUserRoleService basePermissionUserRoleService;

        public UpdateTask(List<OutPutUserRoleDto> outPutUserRoleDtos, List<OutPutUserRoleDto> result, IBasePermissionUserRoleService basePermissionUserRoleService) {
            this.outPutUserRoleDtos = outPutUserRoleDtos;
            this.result = result;
            this.basePermissionUserRoleService = basePermissionUserRoleService;
        }

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
         *
         * @return
         * @throws Exception
         */
        public String call() throws Exception {
            List<Long> unUseIds = Lists.newArrayList();
            for (OutPutUserRoleDto outPutUserRoleDto : outPutUserRoleDtos) {
                Long id = outPutUserRoleDto.getId();
                boolean anyMatch = result.stream().anyMatch(opur -> (opur.getId().intValue() == id));
                if (!anyMatch) {
                    unUseIds.add(id);
                }
            }
            Map map = Maps.newHashMap();
            map.put("list", unUseIds);
            basePermissionUserRoleService.batchUpdateUserRole(map);
            return "success";
        }
    }

    // 新增用户
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/insertUser")
    public RestMessage insertUser(@RequestBody InputUserDto inputUserDto) {
        // 保存用户信息，调用账户系统接口
        // 新增用户与角色关系
        // 新增用户与功能权限，组织权限，数据权限的关系
        RestMessage result = userHelper.insertUser(inputUserDto);
        return result;

    }

    // 修改用户
    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/updateUser")
    public RestMessage<Boolean> updateUser(@RequestBody InputUserUpdateDto inputUserUpdateDto) {
        log.info("编辑用户信息权限变化内容---->" + JSON.toJSONString(inputUserUpdateDto));
        // 新增用户与角色关系
        // 新增用户与功能权限，组织权限，数据权限的关系
        Boolean result = userHelper.updateUser(inputUserUpdateDto);
        if (result) {
            return RestMessage.doSuccess(result);
        } else {
            return RestMessage.error(PermissionErrorCode.UPDATE_USER_ERROR.getErrorCode() + "", PermissionErrorCode.UPDATE_USER_ERROR.getErrorReason());
        }
    }

    // 所属组织树
    @ApiOperation(value = "所属组织树")
    @PostMapping(value = "/getOrgTree")
    public RestMessage<List<UserOrgPermissionDto>> getOrgTree(@RequestBody InputParentDto inputParentDto) {
        return basePermissionUserRoleService.getOrgTree(inputParentDto);
    }

    // 分配角色弹窗 查询用户组织权限列表和角色列表，构造组织权限和角色的树
    @ApiOperation(value = "分配角色弹窗 查询用户组织权限列表和角色列表，构造组织权限和角色的树")
    @PostMapping(value = "/getRoleTree")
    public RestMessage<List<OrgDto>> getRoleTree(@RequestParam Long loginUserId, @RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false) String roleName, @RequestParam(required = false) String orgName, @RequestParam(required = false) String chooseRoleName, @RequestParam(required = false) String chooseOrgName) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("groupId", groupId);
        map.put("roleName", roleName);
        map.put("orgName", orgName);

        //根据登录人的id获取组织权限,如果登录人是集团管理员,则获取集团下所有组织权限
        List<UserOrgPermissionDto> ups = null;
        try {
            ups = groupConfigHelper.getUserOrgPermissionByStrategy(loginUserId, groupId, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Set<String>> chooseRoles = Maps.newHashMap();
        if (userId > 0) {
            map.put("roleName", chooseRoleName);
            map.put("orgName", chooseOrgName);
            //被编辑的用户，已经选过的角色
            chooseRoles = basePermissionUserRoleService.getUserOrgRoleList(map);
        }
        map.put("list", ups);
        map.put("orgs", chooseRoles.get("orgs"));
        map.put("roles", chooseRoles.get("roles"));
        List<OrgDto> allRoles = basePermissionUserRoleService.getOrgRoleList(map);
        return RestMessage.doSuccess(allRoles);
    }

    // 特殊授权-添加功能权限弹窗
    @ApiOperation(value = "添加功能权限弹窗")
    @PostMapping(value = "/getUserFuncTree")
    public RestMessage<Map<String, List<UserPermission>>> getUserFuncTree(@RequestParam Long LoginUserId, @RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false) String resourceName, @RequestParam(required = false) String chooseResourceName) {
        List<UserPermission> allFuncs = groupConfigHelper.getUserPermissionByStrategy(LoginUserId, groupId, resourceName, null, 0L, null, 0L);
        List<UserPermission> chooseUsers = Lists.newArrayList();
        if (userId > 0) {
            chooseUsers = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, chooseResourceName, null, 0L, null, 0L);
            for (UserPermission userPermission : allFuncs) {
                boolean anyMatch = chooseUsers.stream().anyMatch(chooseUp -> chooseUp.getPermissionId().equals(userPermission.getPermissionId()));
                log.info(anyMatch + "--->" + userPermission.getPermissionId());
                if (anyMatch) {
                    userPermission.setCheck(true);
                } else {
                    userPermission.setCheck(false);
                }
            }
        }
        allFuncs = basePermissionRoleService.getUserFuncPermissionList(allFuncs, 0L);
        Map<Integer, List<UserPermission>> grouped = allFuncs.stream().collect(Collectors.groupingBy(userPermission -> userPermission.getType()));
        Map<String, List<UserPermission>> resultMap = new HashMap<>();
        grouped.forEach((k, v) -> {
            resultMap.put(SourceTypeEnum.getEnum(k).name(), v);
        });
        return RestMessage.doSuccess(resultMap);
    }

    // 特殊授权-添加组织权限弹窗
    @ApiOperation(value = "添加组织权限弹窗")
    @PostMapping(value = "/getUserOrgTree")
    public RestMessage<List<UserOrgPermissionDto>> getUserOrgTree(@RequestParam Long loginUserId, @RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false) String orgName, @RequestParam(required = false) String chooseOrgName) {
        Map<String, Object> map = Maps.newHashMap();
        List<UserOrgPermissionDto> allOrgs = null;
        try {
            allOrgs = groupConfigHelper.getUserOrgPermissionByStrategy(loginUserId, groupId, orgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userId > 0) {
            List<UserOrgPermissionDto> chooseOrgs = groupConfigHelper.getUserOrgAuth(userId, groupId, chooseOrgName);
            for (UserOrgPermissionDto userOrgPermissionDto : allOrgs) {
                boolean anyMatch = chooseOrgs.stream().anyMatch(chooseUp -> chooseUp.getPermissionId().equals(userOrgPermissionDto.getPermissionId()));
                log.info(anyMatch + "--->" + userOrgPermissionDto.getPermissionId());
                if (anyMatch) {
                    userOrgPermissionDto.setCheck(true);
                } else {
                    userOrgPermissionDto.setCheck(false);
                }
            }
        }
        allOrgs = basePermissionRoleService.getUserOrgPermissionsList(allOrgs);
        return RestMessage.doSuccess(allOrgs);
    }

    // 特殊授权-添加数据权限弹窗
    @ApiOperation(value = "添加数据权限弹窗-用户权限")
    @PostMapping(value = "/getUserDataTree")
    public RestMessage<List<UserDataPermissionDto>> getUserDataTree(@RequestBody UserDataPermissionParam param) {
        log.info("特殊授权-添加数据权限弹窗请求参数为:{}", JSON.toJSONString(param));
        List<UserDataPermissionDto> allDatas = groupConfigHelper.getUserDataPermissionByStrategy(param.getLoginUserId(), param.getGroupId(), param.getManagementId(), param.getDistributionType(), param.getBased(), param.getDataResource());
        if (param.getUserId() > 0) {
            List<UserDataPermissionDto> chooseDatas = groupConfigHelper.getUserDataAuth(param.getUserId(), param.getGroupId(), param.getManagementId(), param.getDistributionType(), param.getBased(), param.getDataResource());
            for (UserDataPermissionDto userDataPermissionDto : allDatas) {
                boolean anyMatch = chooseDatas.stream().anyMatch(chooseUp -> chooseUp.getPermissionId().equals(userDataPermissionDto.getPermissionId()));
                log.info(anyMatch + "--->" + userDataPermissionDto.getPermissionId());
                if (anyMatch) {
                    userDataPermissionDto.setCheck(true);
                } else {
                    userDataPermissionDto.setCheck(false);
                }
            }
        }
        allDatas = basePermissionRoleService.getUserDataPermissionsList(allDatas);
        return RestMessage.doSuccess(allDatas);
    }

    // 特殊授权-添加数据权限弹窗
    @ApiOperation(value = "添加数据权限弹窗-角色权限")
    @PostMapping(value = "/getRoleDataTree")
    public RestMessage<List<UserDataPermissionDto>> getRoleDataTree(@RequestBody RoleDataPermissionParam param) {
        log.info("特殊授权-添加数据权限弹窗参数:{}", JSON.toJSONString(param));
        List<UserDataPermissionDto> allDatas = groupConfigHelper.getUserDataPermissionByStrategy(param.getLoginUserId(), param.getGroupId(), param.getManagementId(), param.getDistributionType(), param.getBased(), param.getDataResource());
        if (param.getRoleId() > 0) {
            List<UserDataPermissionDto> roleDataPermissions = groupConfigHelper.getOnlyRoleDataPermission(param.getRoleId(), param.getGroupId(), param.getManagementId(), "", "", "");
            for (UserDataPermissionDto userDataPermissionDto : allDatas) {
                boolean anyMatch = roleDataPermissions.stream().anyMatch(chooseUp -> chooseUp.getPermissionId().equals(userDataPermissionDto.getPermissionId()));
                log.info(anyMatch + "--->" + userDataPermissionDto.getPermissionId());
                if (anyMatch) {
                    userDataPermissionDto.setCheck(true);
                } else {
                    userDataPermissionDto.setCheck(false);
                }
            }
        }
        allDatas = basePermissionRoleService.getUserDataPermissionsList(allDatas);
        return RestMessage.doSuccess(allDatas);
    }

}
