package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.server.permission.dto.InputRoleDto;
import com.org.permission.server.permission.dto.InputRoleInsertOrUpdateDto;
import com.org.permission.server.permission.dto.OutPutRoleDto;
import com.org.permission.server.permission.dto.req.GetRoleUserListReq;
import com.org.permission.server.permission.dto.req.RolePermissionParam;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import com.org.permission.server.permission.service.IBasePermissionUserRoleService;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.org.permission.server.permission.vo.BasePermissionRoleVo;
import com.common.util.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 用户角色表管理
 */
@Api(tags = "1权限-用户角色表管理接口文档")
@RequestMapping(value = "permission-role")
@RestController
public class BasePermissionRoleController {
    private Logger log = LoggerFactory.getLogger(BasePermissionRoleController.class);
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;
    @Autowired
    private GroupConfigHelper groupConfigHelper;

    @Autowired
    private IBasePermissionUserRoleService basePermissionUserRoleService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    // 删除用户角色表,若角色关联有一个或多个状态为“启用”的用户，则该角色不允许删除，只有该角色下无
    // 关联用户，或者关联的用户状态都为“停用”时，该角色才允许删除，并会删除两者之间
    // 的关联关系
    @ApiOperation(value = "删除用户角色表")
    @GetMapping(value = "/delBasePermissionRole/{roleId}/{groupId}")
    public RestMessage<Integer> delBasePermissionRole(@PathVariable Long roleId, @PathVariable Long groupId) throws ExecutionException, InterruptedException {
        log.info("删除角色---->角色id:{},集团id：{}" + roleId, groupId);
        // 查角色下是否有启用中用户
        List<RoleUser> roleUsers = basePermissionRoleService.getUserByRoleId(roleId, groupId);
        if (roleUsers.size() > 0) {
            return RestMessage.error(PermissionErrorCode.DEL_ROLE_ERROR.getErrorCode() + "", PermissionErrorCode.DEL_ROLE_ERROR.getErrorReason());
        } else {
            int result = basePermissionRoleService.delRole(roleId, groupId);
            return RestMessage.doSuccess(result);
        }
    }


    // 新增或修改角色
    @ApiOperation(value = "新增或修改角色")
    @PostMapping(value = "/updateBasePermissionRole")
    public RestMessage<BasePermissionRoleVo> inputRoleInsertOrUpdateDto(@RequestBody InputRoleInsertOrUpdateDto inputRoleInsertOrUpdateDto) {
        inputRoleInsertOrUpdateDto.setRoleId(inputRoleInsertOrUpdateDto.getId());
        return basePermissionRoleService.insertOrupdateBasePermissionRole(inputRoleInsertOrUpdateDto);
    }


    // 查角色的功能权限或者组织权限
    @ApiOperation(value = "查角色的功能权限或者组织权限")
    @PostMapping(value = "/getRolePermission")
    public RestMessage<?> getRolePermission(@RequestBody RolePermissionParam rolePermissionParam) {
        return basePermissionRoleService.getRolePermission(rolePermissionParam);
    }

    // 根据操作人的组织权限，查询相关角色
    @ApiOperation(value = "根据操作人的组织权限，查询相关角色")
    @PostMapping(value = "/getRoleByOrg")
    public RestMessage<PageInfo<OutPutRoleDto>> getRoleByOrg(@RequestBody InputRoleDto inputRoleDto) {
        try {
            List<UserOrgPermissionDto> list = groupConfigHelper.getUserOrgPermissionByStrategy(inputRoleDto.getUserId(), inputRoleDto.getGroupId(), "");
            PageHelper.startPage(inputRoleDto.getPageNum(), inputRoleDto.getPageSize());
            Map<String, Object> mapParam = Maps.newHashMap();
            mapParam.put("pojo", inputRoleDto);
            mapParam.put("list", list);
            List<OutPutRoleDto> result = basePermissionRoleService.getRoleByOrgPermission(mapParam);
            for (int i = 0; i <result.size() ; i++) {
                List<RoleUser> roleUsers = basePermissionRoleService.getUserByRoleId(result.get(i).getId(), inputRoleDto.getGroupId());
                if (roleUsers.size() > 0) {
                    result.get(i).setHasEnableUser(1);
                }else{
                    result.get(i).setHasEnableUser(0);
                }
            }

            PageInfo<OutPutRoleDto> page = new PageInfo<OutPutRoleDto>(result);
            return RestMessage.doSuccess(page);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error("5400", e.getMessage());
        }
    }

    /*
     * 获取角色下关联的所有用户列表
     */
    @ApiOperation(value = "获取角色下关联的所有用户列表")
    @GetMapping(value = "/getRoleUsers")
    public RestMessage<List<RoleUser>> getRoleUsers(@RequestParam Long roleId, @RequestParam Long groupId) {
        List<RoleUser> list = null;
        try {
            list = basePermissionRoleService.getUserByRoleId(roleId, groupId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<RoleUser> result = list.stream().filter(roleUser -> ((ObjectUtils.isEmpty(roleUser.getExpireTime())) || ((((roleUser.getExpireTime() == null ? 0 : roleUser.getExpireTime().getTime()) - now) >= 0) && ((now - roleUser.getEffectiveTime().getTime()) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateTask(list, result, basePermissionUserRoleService));
        return RestMessage.doSuccess(result);
    }

    /**
     * 获取角色下的所有用户
     *
     * @param
     * @return
     **/
    @ApiOperation(value = "获取角色下的所有用户")
    @PostMapping(value = "/getRoleUserList")
    public RestMessage getRoleUserList(@RequestBody GetRoleUserListReq getAssignRoleUserReq) {
        Assert.notNull(getAssignRoleUserReq.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(getAssignRoleUserReq.getCurrentUserId(), I18nUtils.getMessage("org.common.param.userid.cannot.null"));
        Assert.notNull(getAssignRoleUserReq.getRoleId(), I18nUtils.getMessage("permission.role.id.null"));
        return RestMessage.doSuccess(basePermissionRoleService.getAssignRoleUser(getAssignRoleUserReq));
    }


    class UpdateTask implements Callable<String> {
        private List<RoleUser> list;
        private List<RoleUser> result;
        private IBasePermissionUserRoleService basePermissionUserRoleService;

        public UpdateTask(List<RoleUser> list, List<RoleUser> result, IBasePermissionUserRoleService basePermissionUserRoleService) {
            this.list = list;
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
            List<Integer> unUseIds = Lists.newArrayList();
            for (RoleUser roleUser : list) {
                Integer id = roleUser.getId();
                boolean anyMatch = result.stream().anyMatch(ru -> (ru.getId() == id));
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


}
