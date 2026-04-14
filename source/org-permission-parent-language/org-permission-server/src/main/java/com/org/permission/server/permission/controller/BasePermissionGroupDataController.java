package com.org.permission.server.permission.controller;

import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.dto.*;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.enums.OptionPermissionEnum;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.common.permission.dto.RoleDto;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.common.base.enums.StateEnum;

import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.mapper.BasePermissionGroupDataMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupDataService;
import com.org.permission.server.permission.service.IBasePermissionRoleFuncService;
import com.org.permission.server.permission.service.IBasePermissionUserFuncService;
import com.usercenter.common.dto.FplUser;

import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集团的数据权限关系表管理
 */
@Api(tags = "1权限-集团的数据权限关系表管理接口文档")
@RequestMapping(value = "permission-group-data")
@RestController
public class BasePermissionGroupDataController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BasePermissionGroupDataController.class);
    @Autowired
    private IBasePermissionGroupDataService basePermissionGroupDataService;
    @Autowired
    private BasePermissionGroupDataMapper basePermissionGroupDataMapper;
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleFuncService basePermissionRoleFuncService;
    @Autowired
    private IBasePermissionUserFuncService basePermissionUserFuncService;

    @Autowired
    private UserDomainService userDomainService;

    //根据角色和管理维度查询数据权限列表
    @ApiOperation(value = "根据角色和管理维度查询数据权限列表")
    @PostMapping(value = "/getDataByRoleAndManageMent")
    public RestMessage getDataByRoleAndManageMent(@RequestBody InputRoleManageMentDto inputRoleManageMentDto) {
        if (Objects.isNull(inputRoleManageMentDto) || Objects.isNull(inputRoleManageMentDto.getRoleId()) || Objects.isNull(inputRoleManageMentDto.getManagementId())) {
            return RestMessage.doSuccess(Lists.newArrayList());
        }
        List<OutputRoleManageMentDto> outputRoleManageMentDtos = basePermissionGroupDataMapper.getDataByRoleAndManageMent(inputRoleManageMentDto);
        if (CollectionUtils.isEmpty(outputRoleManageMentDtos)) {
            return RestMessage.doSuccess(Lists.newArrayList());
        }
        //根据批量的用户author_user给userName赋值
        Set<Long> userIdSet = outputRoleManageMentDtos.stream().map(OutputRoleManageMentDto::getAuthorUser).collect(Collectors.toSet());
        Map<Long, FplUser> userInfoMap = userDomainService.getUserInfoMap(userIdSet);
        List<OutputRoleManageMentDto> list = Lists.newArrayList();
        for (OutputRoleManageMentDto outputRoleManageMentDto : outputRoleManageMentDtos) {
            if (outputRoleManageMentDto.getOptionPermission() == OptionPermissionEnum.OPERATE.getType()) {
                outputRoleManageMentDto.setQuery(true);
                outputRoleManageMentDto.setEdit(true);
            } else if (outputRoleManageMentDto.getOptionPermission() == OptionPermissionEnum.QUERY.getType()) {
                outputRoleManageMentDto.setQuery(true);
                outputRoleManageMentDto.setEdit(false);
            } else if (outputRoleManageMentDto.getOptionPermission() == OptionPermissionEnum.NO.getType()) {
                outputRoleManageMentDto.setQuery(false);
                outputRoleManageMentDto.setEdit(false);
            }
            outputRoleManageMentDto.setUserName(userInfoMap.get(outputRoleManageMentDto.getAuthorUser()).getUserName());
            list.add(outputRoleManageMentDto);
        }
        return RestMessage.doSuccess(list);
    }

    //根据用户和管理维度查询数据权限列表
    @ApiOperation(value = "根据用户和管理维度查询数据权限列表")
    @PostMapping(value = "/getDataByUserAndManageMent")
    public RestMessage getDataByUserAndManageMent(@RequestBody InputUserManageMentDto inputUserManageMentDto) {
        if (Objects.isNull(inputUserManageMentDto) || Objects.isNull(inputUserManageMentDto.getUserId()) || Objects.isNull(inputUserManageMentDto.getManagementId())) {
            return RestMessage.doSuccess(Lists.newArrayList());
        }
        List<OutputRoleManageMentDto> ormms = basePermissionGroupDataMapper.getDataByUserAndManageMent(inputUserManageMentDto);
        if (CollectionUtils.isEmpty(ormms)) {
            return RestMessage.doSuccess(Lists.newArrayList());
        }
        Set<Long> userIdSet = ormms.stream().map(OutputRoleManageMentDto::getAuthorUser).collect(Collectors.toSet());
        Map<Long, FplUser> userInfoMap = userDomainService.getUserInfoMap(userIdSet);
        List<OutputRoleManageMentDto> list = Lists.newArrayList();
        for (OutputRoleManageMentDto omm : ormms) {
            if (omm.getOptionPermission() == OptionPermissionEnum.OPERATE.getType()) {
                omm.setQuery(true);
                omm.setEdit(true);
            } else if (omm.getOptionPermission() == OptionPermissionEnum.QUERY.getType()) {
                omm.setQuery(true);
                omm.setEdit(false);
            } else if (omm.getOptionPermission() == OptionPermissionEnum.NO.getType()) {
                omm.setQuery(false);
                omm.setEdit(false);
            }
            omm.setUserName(userInfoMap.get(omm.getAuthorUser()).getUserName());
            list.add(omm);
        }
        return RestMessage.doSuccess(list);
    }

    //根据当前用户组织权限，获取组织权限下的角色列表
    @ApiOperation(value = "根据当前用户组织权限，获取组织权限下的角色列表")
    @PostMapping(value = "/getRoleListByOrgPermission")
    public RestMessage getRoleListByOrgPermission(@RequestBody InputDto inputDto) {

        List<UserOrgPermissionDto> list = null;
        List<RoleDto> roleDtos = null;
        try {
            list = groupConfigHelper.getUserOrgPermissionByStrategy(inputDto.getUserId(), inputDto.getGroupId(), "");
            roleDtos = basePermissionGroupDataMapper.getRoleListByOrgPermission(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestMessage.doSuccess(roleDtos);
    }

    //根据当前用户组织权限，获取组织权限下的用户列表
    @ApiOperation(value = "根据当前用户组织权限，获取组织权限下的用户列表")
    @PostMapping(value = "/getUserListByOrgPermission")
    public RestMessage getUserListByOrgPermission(@RequestBody InputDto inputDto) {

        List<UserOrgPermissionDto> list = null;
        List<UserDto> userDtos = Lists.newArrayList();
        try {
            list = groupConfigHelper.getUserOrgPermissionByStrategy(inputDto.getUserId(), inputDto.getGroupId(), "");
            com.usercenter.common.dto.UserDto userQueryDTO = new com.usercenter.common.dto.UserDto();
            userQueryDTO.setManagerLevel(3);
            userQueryDTO.setState(StateEnum.ENABLE.getCode());
            if (!org.springframework.util.ObjectUtils.isEmpty(list)) {
                Set<Long> set = new HashSet();
                for (UserOrgPermissionDto item : list) {
                    if (item.getPermissionId() != null) {
                        set.add(Long.valueOf(item.getPermissionId()));
                    }
                }
                if (set.size() > 0) {
                    userQueryDTO.setOrgIdList(new ArrayList<>(set));
                }
            }
            List<FplUser> userList = userDomainService.findUserList(userQueryDTO).getData();
            if (list.size() > 0) {
                for (FplUser user : userList) {
                    UserDto userDto = new UserDto();
                    userDto.setUserId(user.getId().longValue());
                    userDto.setOrgId(user.getOrgId());
                    userDto.setUserCode(user.getUserCode());
                    userDto.setUserName(user.getUserName());
                    userDto.setRealName(user.getRealName());
                    userDtos.add(userDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(userDtos);
    }


    //根据id更新用户数据权限 操作权限
    @ApiOperation(value = "根据id更新用户数据权限 操作权限")
    @PostMapping(value = "/updateRoleData")
    public RestMessage updateUserData(@RequestBody List<InputDataPermission> inputDataPermissions) {

        try {
            inputDataPermissions.stream().forEach(bean -> {
                BasePermissionRoleFunc basePermissionRoleFunc = new BasePermissionRoleFunc();
                basePermissionRoleFunc.setId(bean.getId());
                basePermissionRoleFunc.setPermissionType("data");
                boolean query = bean.isQuery();
                boolean edit = bean.isEdit();
                if (edit) {
                    basePermissionRoleFunc.setOptionPermission(OptionPermissionEnum.OPERATE.getType());
                }
                if (query) {//只能查询，不能编辑
                    if (!edit) {
                        basePermissionRoleFunc.setOptionPermission(OptionPermissionEnum.QUERY.getType());
                    }
                }
                if (!query && !edit) {//不能查询，不能编辑
                    basePermissionRoleFunc.setOptionPermission(OptionPermissionEnum.NO.getType());
                }
                basePermissionRoleFuncService.updateBasePermissionRoleFunc(basePermissionRoleFunc);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error("5500", I18nUtils.getMessage("org.common.fail"));
        }

        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    //根据id更新角色数据权限 操作权限
    @ApiOperation(value = "根据id更新角色数据权限 操作权限")
    @PostMapping(value = "/updateUserData")
    public RestMessage updateRoleData(@RequestBody List<InputDataPermission> inputDataPermissions) {

        try {
            inputDataPermissions.stream().forEach(bean -> {
                BasePermissionUserFunc basePermissionUserFunc = new BasePermissionUserFunc();
                basePermissionUserFunc.setId(bean.getId());
                basePermissionUserFunc.setPermissionType(PermissionTypeEnum.DATA.getCode());
                boolean query = bean.isQuery();
                boolean edit = bean.isEdit();
                if (edit) {
                    basePermissionUserFunc.setOptionPermission(OptionPermissionEnum.OPERATE.getType());
                }
                if (query) {//只能查询，不能编辑
                    if (!edit) {
                        basePermissionUserFunc.setOptionPermission(OptionPermissionEnum.QUERY.getType());
                    }
                }
                if (!query && !edit) {//没有设置
                    basePermissionUserFunc.setOptionPermission(OptionPermissionEnum.NO.getType());
                }
                basePermissionUserFuncService.updateBasePermissionUserFunc(basePermissionUserFunc);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(PermissionErrorCode.UPDATE_USER_ERROR.getErrorCode()+"", I18nUtils.getMessage("org.common.fail"));
        }

        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    //用户数据权限调整
    @ApiOperation(value = "用户数据权限调整")
    @PostMapping(value = "/adjustUserData")
    public RestMessage adjustUserData(@RequestBody AdjustDataDto adjustDataDto) {
        List<UserDataPermissionDto> userDataPermissionDtos = groupConfigHelper.getUserDataAuth(adjustDataDto.getUserId(), adjustDataDto.getGroupId(), 0, "", "", "");
        List<Long> dataIds = adjustDataDto.getDataIds();
        List<Long> delList = Lists.newArrayList();
        List<Long> addList = Lists.newArrayList();
        adjustDataDto.setLoginUserName(FplUserUtil.getUserName());
        try {
            for (UserDataPermissionDto userDataPermissionDto : userDataPermissionDtos) {
                boolean anyMatch = dataIds.stream().anyMatch(e -> e.equals(userDataPermissionDto.getDataId()));
                LOGGER.info("新选中没有原来的id" + anyMatch);
                if (!anyMatch) {
                    delList.add(userDataPermissionDto.getDataId());
                }
            }
            for (Long dataId : dataIds) {
                boolean anyMatch = userDataPermissionDtos.stream().anyMatch(e -> e.getDataId().equals(dataId));
                System.out.println("原来里面没有新选中的" + anyMatch);
                if (!anyMatch) {
                    addList.add(dataId);
                }
            }
            HashMap<String, Object> immutableMap = Maps.newHashMap(new ImmutableMap.Builder<String, Object>().put("delList", delList).put("addList", addList)
                    .put("userId", adjustDataDto.getUserId()).put("groupId", adjustDataDto.getGroupId())
                    .put("authorUser", adjustDataDto.getLoginUserId()).put("authorTime", new Date())
                    .put("createdBy", adjustDataDto.getLoginUserId()).put("createdDate", new Date())
                    .put("createdName", adjustDataDto.getLoginUserName()).put("modifiedBy", adjustDataDto.getLoginUserId())
                    .put("modifiedDate", new Date()).put("modifiedName", adjustDataDto.getLoginUserName()).build());

            basePermissionGroupDataService.adjustUserData(immutableMap);
        } catch (Exception e) {
            LOGGER.error("userId{},groupId{},authorUser{},dataIds{}调整用户数据权限失败e{}", adjustDataDto.getUserId(), adjustDataDto.getGroupId(), adjustDataDto.getLoginUserId(), dataIds, e.getMessage(), e);
            return RestMessage.error("5500", I18nUtils.getMessage("org.common.fail"));
        }

        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    //角色数据权限调整
    @ApiOperation(value = "角色数据权限调整")
    @PostMapping(value = "/adjustRoleData")
    public RestMessage adjustRoleData(@RequestBody AdjustDataDto adjustDataDto) {
        LOGGER.info("调整角色数据权限请求参数为{}", JSON.toJSONString(adjustDataDto));
        adjustDataDto.setLoginUserName(FplUserUtil.getCurrentUser().getUserName());
        List<UserDataPermissionDto> roleDataPermissions = groupConfigHelper.getOnlyRoleDataPermission(adjustDataDto.getRoleId(), adjustDataDto.getGroupId(), adjustDataDto.getManagementId(), "", "", "");
        List<Long> dataIds = adjustDataDto.getDataIds();
        List<Long> delList = Lists.newArrayList();
        List<Long> addList = Lists.newArrayList();
        try {
            for (UserDataPermissionDto userDataPermissionDto : roleDataPermissions) {
                boolean anyMatch = dataIds.stream().anyMatch(e -> e.equals(userDataPermissionDto.getDataId()));
                LOGGER.info("新选中没有原来的id" + anyMatch);
                if (!anyMatch) {
                    delList.add(userDataPermissionDto.getDataId());
                }
            }
            for (Long dataId : dataIds) {
                boolean anyMatch = roleDataPermissions.stream().anyMatch(e -> e.getDataId().equals(dataId));
                System.out.println("原来里面没有新选中的" + anyMatch);
                if (!anyMatch) {
                    addList.add(dataId);
                }
            }
            HashMap<String, Object> immutableMap = Maps.newHashMap(new ImmutableMap.Builder<String, Object>()
                    .put("delList", delList).put("addList", addList).put("roleId", adjustDataDto.getRoleId())
                    .put("groupId", adjustDataDto.getGroupId()).put("authorUser", adjustDataDto.getLoginUserId())
                    .put("authorTime", new Date()).put("createdBy", adjustDataDto.getLoginUserId())
                    .put("createdDate", new Date()).put("createdName", adjustDataDto.getLoginUserName()).build());

            basePermissionGroupDataService.adjustRoleData(immutableMap);
        } catch (Exception e) {
            LOGGER.error("roleId{},groupId{},authorUser{},dataIds{}调整用户数据权限失败e{}", adjustDataDto.getRoleId(), adjustDataDto.getGroupId(), adjustDataDto.getLoginUserId(), dataIds, e.getMessage(), e);
            return RestMessage.error("5500", I18nUtils.getMessage("org.common.fail"));
        }

        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

}
