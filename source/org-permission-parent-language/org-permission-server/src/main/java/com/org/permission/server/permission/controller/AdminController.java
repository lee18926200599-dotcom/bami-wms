package com.org.permission.server.permission.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.permission.dto.AdminGroupDto;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.server.permission.service.IBasePermissionAdminGroupService;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.enums.UserManagerLevelEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * 集团权限查询
 */
@RequestMapping(value = "permission-admin")
@Api(tags = "1权限-集团权限接口文档")
@RestController
public class AdminController {
    private final static Logger LOG = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private GroupConfigHelper groupConfigHelper;

    @Autowired
    private IBasePermissionAdminGroupService basePermissionAdminGroupService;

    @Autowired
    private OrgDomainService orgDomainService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 集团权限策略，true取用户权限，false取角色权限
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "是否允许为用户直接授权")
    @GetMapping(value = "/isUserAuth")
    public RestMessage isUserAuth(@RequestParam("groupId") Long groupId) {
        return RestMessage.doSuccess(groupConfigHelper.isUserAuth(groupId));
    }

    /**
     * 是否真正的管理员，
     * 所在集团策略有分配权限，就相当于普通员工，没有分配就是真正的管理员
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "是否真正的管理员")
    @GetMapping(value = "/isAdmin")
    public RestMessage isGroupAdmin(@RequestParam("userId") Long userId) {
        FplUser baseUser = new FplUser();
        baseUser.setId(userId);
        baseUser.setManagerLevel(UserManagerLevelEnum.GROUP_ADMINI.getCode());
        return RestMessage.doSuccess(groupConfigHelper.isGroupAdmin(baseUser));
    }

    /**
     * 获取用户所在集团的用户特殊权限
     *
     * @param userId
     * @param groupId
     * @return
     */
    @ApiOperation(value = "获取用户所在集团的用户特殊权限", httpMethod = "POST")
    @PostMapping(value = "/getUserPermissions")
    public RestMessage getUserPermissions(Long userId, Long groupId) {
        Map map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("groupId", groupId);
        return RestMessage.doSuccess(groupConfigHelper.getUserPermissions(map));
    }

    /**
     * 获取用户所在集团角色上的权限
     *
     * @param userId
     * @param groupId
     * @return
     */
    @ApiOperation(value = "获取用户所在集团角色上的权限", httpMethod = "POST")
    @PostMapping(value = "/getRolePermissions")
    public RestMessage getRolePermissions(Long userId, Long groupId) {
        Map map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("groupId", groupId);
        return RestMessage.doSuccess(groupConfigHelper.getRolePermissions(map));
    }


    // 按管理员id查集团列表
    @ApiOperation(value = "按管理员id查集团列表", httpMethod = "POST")
    @PostMapping(value = "/getGroupsByIdSet")
    public RestMessage<List<AdminGroupDto>> getGroupsByIdSet(@RequestBody BatchQueryParam query) {
        AssertUtils.isNotEmpty(query.getIds(), "参数不能为空");
        List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminByUserIdSet(new HashSet<>(query.getIds()));
        List<Long> groupIds = chooseGroupDtos.stream().map(BasePermissionAdminGroup::getGroupId).collect(Collectors.toList());
        List<Integer> orgTypes = Lists.newArrayList();
        List<OrgInfoDto> orgInfoDtos = Lists.newArrayList();
        orgTypes.add(2);
        try {
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(groupIds);
            batchQueryParam.setOrgTypes(orgTypes);
            orgInfoDtos = orgDomainService.batchQueryOrgInfo(batchQueryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<BasePermissionAdminGroup> result = chooseGroupDtos.stream().filter(bpag -> (ObjectUtils.isEmpty(bpag.getExpireTime()) || ((((ObjectUtils.isEmpty(bpag.getExpireTime()) ? 0 : bpag.getExpireTime().getTime()) - now) >= 0) && ((now - bpag.getEffectiveTime().getTime()) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateTask(chooseGroupDtos, result, basePermissionAdminGroupService));
        List<AdminGroupDto> groupList = Lists.newArrayList();
        for (BasePermissionAdminGroup bpag : result) {
            AdminGroupDto adminGroupDto = new AdminGroupDto();
            adminGroupDto.setGroupId(bpag.getGroupId());
            adminGroupDto.setId(bpag.getGroupId());
            if (!ObjectUtils.isEmpty(orgInfoDtos)) {
                for (OrgInfoDto orgInfoDto : orgInfoDtos) {
                    if (bpag.getGroupId().equals(orgInfoDto.getGroupId())) {
                        adminGroupDto.setOrgCode(orgInfoDto.getOrgCode());
                        adminGroupDto.setOrgName(orgInfoDto.getOrgName());
                        adminGroupDto.setOrgType(orgInfoDto.getOrgType());//集团
                        adminGroupDto.setParentId(orgInfoDto.getParentId());//父级
                        if (!ObjectUtils.isEmpty(orgInfoDto.getBusinessType())) {
                            adminGroupDto.setBusinessType(orgInfoDto.getBusinessType());
                        }

                    }
                }
            }
            groupList.add(adminGroupDto);
        }
        return RestMessage.doSuccess(groupList);
    }


    class UpdateTask implements Callable<String> {
        private List<BasePermissionAdminGroup> chooseGroupDtos;
        private List<BasePermissionAdminGroup> result;
        private IBasePermissionAdminGroupService basePermissionAdminGroupService;

        public UpdateTask(List<BasePermissionAdminGroup> chooseGroupDtos, List<BasePermissionAdminGroup> result, IBasePermissionAdminGroupService basePermissionAdminGroupService) {
            this.chooseGroupDtos = chooseGroupDtos;
            this.result = result;
            this.basePermissionAdminGroupService = basePermissionAdminGroupService;
        }

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
         *
         * @return
         * @throws Exception
         */
        public String call() throws Exception {
            List<Long> unUseIds = Lists.newArrayList();
            for (BasePermissionAdminGroup basePermissionAdminGroup : chooseGroupDtos) {
                Long id = basePermissionAdminGroup.getId();
                boolean anyMatch = result.stream().anyMatch(gd -> (gd.getId() == id));
                if (!anyMatch) {
                    unUseIds.add(id);
                }
            }
            Map map = Maps.newHashMap();
            map.put("list", unUseIds);
            basePermissionAdminGroupService.batchUpdateGroupAdmin(map);
            return "success";
        }
    }

}
