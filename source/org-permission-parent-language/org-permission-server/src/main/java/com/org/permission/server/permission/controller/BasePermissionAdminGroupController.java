package com.org.permission.server.permission.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.QueryOrgListInfoReqParam;
import com.org.permission.common.permission.dto.AdminGroupDto;
import com.org.permission.common.permission.dto.InputAdminDto;
import com.org.permission.common.permission.dto.InputAdminUpdateDto;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.org.bean.OrgListBean;
import com.org.permission.server.org.dto.GroupDto;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.server.permission.service.IBasePermissionAdminGroupService;
import com.common.util.message.RestMessage;
import com.usercenter.common.dto.FplUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 用户表中的管理员和集团关系表管理
 */
@Api(tags = "1权限-管理员和集团关系表管理接口文档")
@RequestMapping(value = "permission-admin-group")
@RestController
public class BasePermissionAdminGroupController {
    @Autowired
    private IBasePermissionAdminGroupService basePermissionAdminGroupService;

    @Autowired
    private OrgDomainService orgDomainService;

    private ExecutorService executorService = Executors.newCachedThreadPool();


    // 查询所有集团列表和已经有的集团列表，支持条件查询
    @ApiOperation(value = "查询所有集团列表和已经有的集团列表，支持条件查询", httpMethod = "POST")
    @PostMapping(value = "/getAdminGroupList")
    public RestMessage getAdminGroupList(@RequestParam Long userId, @RequestParam(required = false, defaultValue = "0") Long adminId, @RequestParam(required = false, defaultValue = "") String allGroupName, @RequestParam(required = false, defaultValue = "") String chooseGroupName) {
        Map<String, List<GroupDto>> map = Maps.newHashMap();
        List<GroupDto> groupDtos = Lists.newArrayList();
        QueryOrgListInfoReqParam reqParam = new QueryOrgListInfoReqParam();
        reqParam.setOrgName(allGroupName);
        List<OrgListBean> orgListBeanList = orgDomainService.queryGroupList(reqParam);
        if (CollectionUtils.isNotEmpty(orgListBeanList)) {
            for (OrgListBean olb : orgListBeanList) {
                GroupDto groupDto = new GroupDto();
                groupDto.setGroupId(olb.getId());
                groupDto.setGroupCode(olb.getOrgCode());
                groupDto.setGroupName(olb.getOrgName());
                groupDtos.add(groupDto);
            }
        }
        BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
        basePermissionAdminGroup.setAdminId(adminId);
        basePermissionAdminGroup.setGroupName(chooseGroupName);
        List<BasePermissionAdminGroup> basePermissionAdminGroupList = basePermissionAdminGroupService.getChooseList(basePermissionAdminGroup);
        List<GroupDto> chooseGroupDtos = new ArrayList<>();
        basePermissionAdminGroupList.stream().forEach(item -> {
            GroupDto groupDto = new GroupDto();
            groupDto.setGroupId(item.getGroupId());
            groupDto.setGroupName(item.getGroupName());
            groupDto.setGroupCode(item.getGroupCode());
            groupDto.setId(Long.valueOf(item.getId()));
            groupDto.setExpireTime(item.getExpireTime());
            groupDto.setEffectiveTime(item.getEffectiveTime());
            chooseGroupDtos.add(groupDto);
        });
        Date now = new Date();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<GroupDto> result = chooseGroupDtos.stream().filter(groupDto -> (ObjectUtils.isEmpty(groupDto.getExpireTime()) || ((((groupDto.getExpireTime() == null ? null : groupDto.getExpireTime()).compareTo(now)) >= 0) && ((now.compareTo(groupDto.getEffectiveTime())) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateGroupAdminRunnable(chooseGroupDtos, result, basePermissionAdminGroupService));
        map.put("all", groupDtos);
        map.put("choose", result);
        return RestMessage.doSuccess(map);
    }

    class UpdateGroupAdminRunnable implements Callable<String> {
        private List<GroupDto> chooseGroupDtos;
        private List<GroupDto> result;
        private IBasePermissionAdminGroupService basePermissionAdminGroupService;

        public UpdateGroupAdminRunnable(List<GroupDto> chooseGroupDtos, List<GroupDto> result, IBasePermissionAdminGroupService basePermissionAdminGroupService) {
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
            for (GroupDto groupDto : chooseGroupDtos) {
                Long id = groupDto.getId();
                boolean anyMatch = result.stream().anyMatch(gd -> (gd.getId().equals(id)));
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

    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping(value = "/insertAdminGroup")
    public RestMessage insertAdminGroup(@RequestBody InputAdminDto inputAdminDto) {
        RestMessage<FplUser> result = basePermissionAdminGroupService.insertAdminGroup(inputAdminDto);
        return result;
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/updateAdminGroup")
    public RestMessage updateAdminGroup(@RequestBody InputAdminUpdateDto inputAdminUpdateDto) {
        RestMessage<FplUser> result = basePermissionAdminGroupService.updateAdminGroup(inputAdminUpdateDto);
        return result;
    }

    @ApiOperation(value = "按管理员id查集团列表", httpMethod = "GET")
    @GetMapping(value = "/getGroupListByAdminId")
    public RestMessage<List<BasePermissionAdminGroup>> getGroupListByAdminId(@RequestParam Long adminId) {
        BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
        basePermissionAdminGroup.setAdminId(adminId);
        List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminGroupsByPOJO(basePermissionAdminGroup);
        List<Long> list = chooseGroupDtos.stream().map(bean -> {
            return bean.getGroupId();
        }).collect(Collectors.toList());
        List<Integer> orgTypes = Lists.newArrayList();
        List<OrgInfoDto> orgInfoDtos = Lists.newArrayList();
        orgTypes.add(2);
        try {
            BatchQueryParam reqParam = new BatchQueryParam();
            reqParam.setIds(list);
            reqParam.setOrgTypes(orgTypes);
            orgInfoDtos = orgDomainService.batchQueryOrgInfo(reqParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<BasePermissionAdminGroup> result = chooseGroupDtos.stream().filter(bpag -> (ObjectUtils.isEmpty(bpag.getExpireTime()) || ((((bpag.getExpireTime() == null ? 0 : bpag.getExpireTime().getTime()) - now) >= 0) && ((now - bpag.getEffectiveTime().getTime()) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateTask(chooseGroupDtos, result, basePermissionAdminGroupService));
        List<BasePermissionAdminGroup> groupList = Lists.newArrayList();
        for (BasePermissionAdminGroup bpag : result) {
            if (CollectionUtils.isNotEmpty(orgInfoDtos)) {
                for (OrgInfoDto orgInfoDto : orgInfoDtos) {
                    if (bpag.getGroupId().equals(orgInfoDto.getGroupId())) {
                        bpag.setGroupCode(orgInfoDto.getOrgCode());
                        bpag.setGroupName(orgInfoDto.getOrgName());
                    }
                }
            }
            groupList.add(bpag);
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

    @ApiOperation(value = "调整集团管理员管理集团时效", httpMethod = "POST")
    @PostMapping(value = "/adjustAdminGroupExpire")
    public RestMessage<Integer> adjustAdminGroupExpire(@RequestBody AdminGroupDto adminGroupDto) {
        BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
        basePermissionAdminGroup.setId(adminGroupDto.getId());
        basePermissionAdminGroup.setEffectiveTime(adminGroupDto.getEffectiveTime());
        basePermissionAdminGroup.setExpireTime(adminGroupDto.getExpireTime());
        basePermissionAdminGroup.setModifiedBy(adminGroupDto.getUserId());
        basePermissionAdminGroup.setModifiedDate(new Date());
        int result = basePermissionAdminGroupService.update(basePermissionAdminGroup);
        return RestMessage.doSuccess(result);
    }
}
