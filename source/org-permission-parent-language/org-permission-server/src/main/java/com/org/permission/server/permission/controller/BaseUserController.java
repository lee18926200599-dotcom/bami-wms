package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.permission.dto.UserAndRoleOrgDto;
import com.org.permission.server.permission.dto.req.GetGroupListReq;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.server.permission.enums.OptionPermissionEnum;
import com.org.permission.server.permission.service.IBasePermissionAdminGroupService;
import com.org.permission.server.permission.service.IBaseUserService;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserDetail;
import com.usercenter.common.enums.UserManagerLevelEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户基本信息表管理
 */
@Api(tags = "1权限-用户基本信息表管理接口文档")
@RequestMapping(value = "permission-baseuser")
@RestController
public class BaseUserController {

    private final static Logger LOG = LoggerFactory.getLogger(BaseUserController.class);


    @Autowired
    private UserFeign userFeign;

    @Autowired
    private IBasePermissionAdminGroupService basePermissionAdminGroupService;


    @Autowired
    private GroupConfigHelper groupConfigHelper;

    @Autowired
    private IBaseUserService baseUserService;
    @Autowired
    private OrgDomainService orgDomainService;


    // 特殊授权-功能权限
    @ApiOperation(value = "特殊授权-功能权限")
    @PostMapping(value = "/getUserFuncList")
    public RestMessage<Map<String, List<UserPermission>>> getUserFuncList(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false) String resourceName) {
        List<UserPermission> chooseUsers = groupConfigHelper.getUserAuth(userId, groupId, resourceName, null, 0L, null, 0L);
        Map<String, List<UserPermission>> grouped = baseUserService.getUserFuncPermissionList(chooseUsers, 0);
        return RestMessage.doSuccess(grouped);
    }

    // 特殊授权-组织权限
    @ApiOperation(value = "特殊授权-组织权限")
    @PostMapping(value = "/getUserOrgList")
    public RestMessage<List<UserOrgPermissionDto>> getUserOrgList(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false) String orgName) {
        List<UserOrgPermissionDto> chooseOrgs = groupConfigHelper.getUserOrgAuth(userId, groupId, orgName);
        return RestMessage.doSuccess(chooseOrgs);
    }

    // 特殊授权-数据权限
    @ApiOperation(value = "特殊授权-数据权限")
    @PostMapping(value = "/getUserDataList")
    public RestMessage<List<UserDataPermissionDto>> getUserDataList(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam(required = false, defaultValue = "0") Integer managementId, @RequestParam(required = false) String distributionType, @RequestParam(required = false) String based, @RequestParam(required = false) String dataResource) {
        List<UserDataPermissionDto> chooseDatas = Lists.newArrayList();
        chooseDatas = groupConfigHelper.getUserDataAuth(userId, groupId, managementId, distributionType, based, dataResource);
        if (!ObjectUtils.isEmpty(chooseDatas)) {
            chooseDatas.stream().forEach(bean -> {
                if (bean.getOptionPermission() == OptionPermissionEnum.OPERATE.getType()) {
                    bean.setEdit(true);
                    bean.setQuery(true);
                } else if (bean.getOptionPermission() == OptionPermissionEnum.QUERY.getType()) {
                    bean.setEdit(false);
                    bean.setQuery(true);
                } else {
                    bean.setEdit(false);
                    bean.setQuery(false);
                }
            });
        }
        return RestMessage.doSuccess(chooseDatas);
    }

    @ApiOperation("根据用户Id查询,获取组织权限对应的集团信息，并且根据业务类型过滤集团信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", dataType = "int"), @ApiImplicitParam(name = "bussinessTypes", value = "业务类型", paramType = "query", dataType = "List")})
    @PostMapping(value = "base/getGroupsByOrgPermissions")
    public RestMessage<List<OrgInfoDto>> getGroupsByPermissions(@RequestParam("userId") Long userId, @RequestParam(value = "bussinessTypes", required = false) List<String> bussinessTypes) {
        RestMessage<List<UserDetail>> restMessage = userFeign.getDetailListByUserId(userId);
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        //不同的管理级别获取不同的组织权限的集团列表
        List<OrgInfoDto> result = Lists.newArrayList();
        for (UserDetail user : restMessage.getData()) {
            Integer managerLevel = user.getManagerLevel();
            if (managerLevel.intValue() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
                GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
                //获取所有集团
                if (!ObjectUtils.isEmpty(bussinessTypes)) {
                    groupListQueryParam.setBusinessType(bussinessTypes);
                }
                List<OrgInfoDto> orgInfoDtoList = orgDomainService.queryAllGroupInfoList(groupListQueryParam).getList();
                result.addAll(orgInfoDtoList);
            } else if (managerLevel.intValue() == UserManagerLevelEnum.GROUP_ADMINI.getCode()) {
                //获取有权限得集团列表
                BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
                basePermissionAdminGroup.setAdminId(userId);
                List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminGroupsByPOJO(basePermissionAdminGroup);
                List<Long> list = chooseGroupDtos.stream().map(BasePermissionAdminGroup::getGroupId).collect(Collectors.toList());
                GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
                //获取所有集团
                if (!ObjectUtils.isEmpty(bussinessTypes)) {
                    groupListQueryParam.setBusinessType(bussinessTypes);
                }
                groupListQueryParam.setIds(list);
                List<OrgInfoDto> orgInfoDtoList = orgDomainService.queryAllGroupInfoList(groupListQueryParam).getList();
                result.addAll(orgInfoDtoList);
            } else if (managerLevel.intValue() == UserManagerLevelEnum.USER.getCode()) {
                Set<Long> orgIdSet = Sets.newHashSet();
                try {
                    FplUser userCondition = new FplUser();
                    userCondition.setId(user.getId());
                    userCondition.setManagerLevel(user.getManagerLevel());
                    List<UserAndRoleOrgDto> userOrgPermissions = groupConfigHelper.getUserAndRoleOrgs(userCondition);
                    if (userOrgPermissions.size() == 0) {
                        return RestMessage.error("1001", I18nUtils.getMessage("permission.baseuser.orgpermission.null"));
                    }
                    orgIdSet = userOrgPermissions.stream().map(UserAndRoleOrgDto::getOrgId).collect(Collectors.toSet());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<Long> pesIds = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(orgIdSet)) {
                    pesIds=new ArrayList<>(orgIdSet);
                }
                BatchQueryParam batchQueryParam = new BatchQueryParam();
                batchQueryParam.setIds(pesIds);
                List<Integer> orgTypes = new ArrayList<>();
                orgTypes.add(OrgTypeEnum.ORGANIZATION.getIndex());
                batchQueryParam.setOrgTypes(orgTypes);
                if (CollectionUtils.isNotEmpty(bussinessTypes)) {
                    batchQueryParam.setBusinessType(bussinessTypes);
                }
                List<OrgInfoDto> groupInfoList = orgDomainService.queryGroupInfoByOrgIdsNoPage(batchQueryParam);
                result.addAll(groupInfoList);
            }
        }
        return RestMessage.doSuccess(result);
    }


    @ApiOperation("查询集团列表")
    @PostMapping(value = "base/getGroupList")
    public RestMessage<PageInfo<OrgInfoDto>> getGroupList(@RequestBody GetGroupListReq getGroupListReq) {
        Assert.notNull(getGroupListReq.getCurrentUserId(), I18nUtils.getMessage("org.common.param.userid.cannot.null"));
        RestMessage<FplUser> restMessage = userFeign.getUserInfo(getGroupListReq.getCurrentUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        FplUser user = restMessage.getData();
        //不同的管理级别获取不同的组织权限的集团列表
        //如果是全局管理员
        Integer managerLevel = user.getManagerLevel();
        if (managerLevel.intValue() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
            GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
            //获取所有集团
            if (!ObjectUtils.isEmpty(getGroupListReq.getBussinessTypes())) {
                groupListQueryParam.setBusinessType(getGroupListReq.getBussinessTypes());
            }
            groupListQueryParam.setOrgName(getGroupListReq.getGroupName());
            groupListQueryParam.setOrgShortName(getGroupListReq.getGroupShortName());
            groupListQueryParam.setCreatedDateStart(getGroupListReq.getCreatedDateStart());
            groupListQueryParam.setCreatedDateEnd(getGroupListReq.getCreatedDateEnd());
            groupListQueryParam.setPageNum(getGroupListReq.getPageNum());
            groupListQueryParam.setPageSize(getGroupListReq.getPageSize());
            groupListQueryParam.setFlag(true);
            PageInfo<OrgInfoDto> page = orgDomainService.queryAllGroupInfoList(groupListQueryParam);
            return RestMessage.doSuccess(page);
            //如果是集团管理员
        } else if (managerLevel.intValue() == UserManagerLevelEnum.GROUP_ADMINI.getCode()) {
            //获取有权限得集团列表
            BasePermissionAdminGroup query = new BasePermissionAdminGroup();
            query.setAdminId(getGroupListReq.getCurrentUserId().longValue());
            query.setGroupName(getGroupListReq.getGroupName());
            query.setGroupShortName(getGroupListReq.getGroupShortName());
            query.setCreatedDateStart(getGroupListReq.getCreatedDateStart());
            query.setCreatedDateEnd(getGroupListReq.getCreatedDateEnd());
            List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminGroupsByPOJO(query);
            List<Long> list = chooseGroupDtos.stream().map(bean -> bean.getGroupId()).collect(Collectors.toList());
            if (ObjectUtils.isEmpty(list)) {
                return RestMessage.doSuccess(new PageInfo<>());
            }
            GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
            //获取所有集团
            if (!ObjectUtils.isEmpty(getGroupListReq.getBussinessTypes())) {
                groupListQueryParam.setBusinessType(getGroupListReq.getBussinessTypes());
            }
            groupListQueryParam.setIds(list);
            groupListQueryParam.setPageSize(getGroupListReq.getPageSize());
            groupListQueryParam.setPageNum(getGroupListReq.getPageNum());
            groupListQueryParam.setFlag(true);
            PageInfo<OrgInfoDto> page = orgDomainService.queryAllGroupInfoList(groupListQueryParam);
            return RestMessage.doSuccess(page);
            //如果是普通用户
        } else if (managerLevel.intValue() == UserManagerLevelEnum.USER.getCode()) {
            Set<Long> orgIdSet = Sets.newHashSet();
            try {
                FplUser userCondition = new FplUser();
                userCondition.setId(user.getId());
                userCondition.setManagerLevel(user.getManagerLevel());
                List<UserAndRoleOrgDto> userOrgPermissions = groupConfigHelper.getUserAndRoleOrgs(userCondition);
                if (userOrgPermissions.size() == 0) {
                    return RestMessage.error("1001", I18nUtils.getMessage("permission.baseuser.orgpermission.null"));
                }
                orgIdSet = userOrgPermissions.stream().map(UserAndRoleOrgDto::getOrgId).collect(Collectors.toSet());
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Long> pesIds = new ArrayList<>();
            if (orgIdSet != null && orgIdSet.size() > 0) {
                orgIdSet.forEach(permissionId -> {
                    pesIds.add(permissionId);
                });
            }
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(pesIds);
            List<Integer> orgTypes = new ArrayList<>();
            orgTypes.add(OrgTypeEnum.ORGANIZATION.getIndex());
            batchQueryParam.setOrgTypes(orgTypes);
            batchQueryParam.setOrgName(getGroupListReq.getGroupName());
            batchQueryParam.setOrgShortName(getGroupListReq.getGroupShortName());
            batchQueryParam.setCreatedDateStart(getGroupListReq.getCreatedDateStart());
            batchQueryParam.setCreatedDateEnd(getGroupListReq.getCreatedDateEnd());
            batchQueryParam.setPageNum(getGroupListReq.getPageNum());
            batchQueryParam.setPageSize(getGroupListReq.getPageSize());
            if (!ObjectUtils.isEmpty(getGroupListReq.getBussinessTypes())) {
                batchQueryParam.setBusinessType(getGroupListReq.getBussinessTypes());
            }
            PageInfo<OrgInfoDto> page = orgDomainService.queryGroupInfoByOrgIds(batchQueryParam);
            return RestMessage.doSuccess(page);
        }

        return RestMessage.doSuccess(new PageInfo<>());
    }
}
