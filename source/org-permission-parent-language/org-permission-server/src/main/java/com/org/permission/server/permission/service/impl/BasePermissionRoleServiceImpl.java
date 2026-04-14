package com.org.permission.server.permission.service.impl;


import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.permission.dto.*;
import com.org.permission.server.permission.dto.req.GetRoleUserListReq;
import com.org.permission.server.permission.dto.req.RolePermissionParam;
import com.org.permission.server.permission.entity.BasePermissionRole;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.mapper.BasePermissionRoleFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionRoleMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserRoleMapper;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import com.org.permission.server.permission.vo.BasePermissionRoleVo;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserDto;
import com.usercenter.common.enums.UserStateEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * base_permission_roleServiceImpl类 用户角色关联表管理
 */
@Service
public class BasePermissionRoleServiceImpl implements IBasePermissionRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePermissionRoleServiceImpl.class);

    @Autowired
    private BasePermissionRoleMapper dao;
    @Autowired
    private BasePermissionUserRoleMapper basePermissionUserRoleMapper;
    @Autowired
    private BasePermissionRoleFuncMapper basePermissionRoleFuncMapper;
    @Autowired
    private RoleManagerHelper roleManagerHelper;
    @Autowired
    private GroupConfigHelper groupConfigHelper;


    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private OrgDomainService orgDomainService;
    @Autowired
    private CodeDomainService codeDomainService;


    @Resource
    private UserFeign userFeign;


    public static Integer[] FUNC_GROUP_PC = new Integer[]{ResourceMenuTypeEnum.PC.getCode()};
    public static Integer[] FUNC_GROUP_APPS = new Integer[]{ResourceMenuTypeEnum.APP.getCode(), ResourceMenuTypeEnum.RF.getCode()};

    public int addBasePermissionRole(BasePermissionRole base_permission_role) {
        return dao.addBasePermissionRole(base_permission_role);
    }

    public int delBasePermissionRole(Integer Id) {
        return dao.delBasePermissionRole(Id);
    }

    public int updateBasePermissionRole(BasePermissionRole base_permission_role) {
        return dao.updateBasePermissionRole(base_permission_role);
    }

    public BasePermissionRole getBasePermissionRoleById(Long Id) {
        return dao.getBasePermissionRoleByID(Id);
    }

    public int getBasePermissionRoleCount() {
        return dao.getBasePermissionRoleCount();
    }

    public int getBasePermissionRoleCountAll() {
        return dao.getBasePermissionRoleCountAll();
    }

    public List<BasePermissionRole> getListBasePermissionRolesByPage(BasePermissionRoleVo base_permission_role) {
        return dao.getListBasePermissionRolesByPage(base_permission_role);
    }

    public List<BasePermissionRole> getListBasePermissionRolesByPOJO(BasePermissionRole base_permission_role) {
        return dao.getListBasePermissionRolesByPOJO(base_permission_role);
    }

    public List<BasePermissionRole> getListBasePermissionRolesByPojoPage(BasePermissionRole base_permission_role) {
        Map map = new HashMap();
        map.put("pojo", base_permission_role);
        return dao.getListBasePermissionRolesByPojoPage(map);
    }

    @Override
    public List<OutPutRoleDto> getRoleByOrgPermission(Map<String, Object> mapParam) {
        return dao.getRoleByOrgPermission(mapParam);
    }

    @Override
    public List<RoleUser> getUserByRoleId(Long roleId, Long groupId) {
        Map<String, Long> map = Maps.newHashMap();
        map.put("roleId", roleId);
        map.put("groupId", groupId);
        //userName是userName和realname的拼装，authoruser取的userName
        List<RoleUser> roleUsers = dao.getUserByRoleId(map);
        List<RoleUser> roleUsersResult = new ArrayList();
        if (CollectionUtils.isNotEmpty(roleUsers)) {
            //获取授权人
            Set<Long> userSet = new HashSet<>();
            List<Long> orgIds = new ArrayList<>();
            for (RoleUser item : roleUsers) {
                userSet.add(item.getUserId());
                userSet.add(item.getAuthor());
            }
            Map<Long, FplUser> userMap = userDomainService.getUserInfoMap(userSet);
            Map<Long, OrgInfoDto> orgMap = new HashMap<>();

            Iterator<Map.Entry<Long, FplUser>> iterator = userMap.entrySet().iterator();
            if (!org.springframework.util.ObjectUtils.isEmpty(iterator)) {
                while (iterator.hasNext()) {
                    Long orgId = iterator.next().getValue().getOrgId();
                    if (orgId != null && orgId > 0) {
                        orgIds.add(orgId);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(orgIds)) {
                orgMap = findOrgInfoByIds(orgIds);
            }
            //获取用户id对应的名字
            for (RoleUser item : roleUsers) {
                RoleUser roleUser = new RoleUser();
                BeanUtils.copyProperties(item, roleUser);
                FplUser authorTemp = userMap.get(item.getAuthor());
                if (authorTemp != null) {
                    String authorUserName = authorTemp.getUserName();
                    if (!StringUtils.isBlank(authorUserName)) {
                        roleUser.setAuthorUser(authorUserName);
                    }
                }
                //获取用户名
                FplUser userTemp = userMap.get(item.getUserId());
                if (userTemp != null) {
                    orgIds.add(userTemp.getOrgId());
                    if (!StringUtils.isBlank(userTemp.getRealName())) {
                        roleUser.setUserName(userTemp.getUserName() + "(" + userTemp.getRealName() + ")");
                    } else {
                        roleUser.setUserName(userTemp.getUserName());
                    }
                    roleUser.setUserCode(userTemp.getUserCode());
                    //获取组织
                    roleUser.setOrgId(userTemp.getOrgId());
                    roleUser.setOrgName(orgMap.get(userTemp.getOrgId()).getOrgName());
                }
                //获取用户编码
                roleUsersResult.add(roleUser);
            }
            return roleUsersResult;
        }
        return roleUsersResult;
    }

    @Override
    public PageInfo<RoleUser> getAssignRoleUser(GetRoleUserListReq getAssignRoleUserReq) {
        List<RoleUser> roleUserList;
        PageInfo<RoleUser> pageInfo = new PageInfo<>();
        Map<String, Long> map = Maps.newHashMap();
        map.put("roleId", getAssignRoleUserReq.getRoleId());
        map.put("groupId", getAssignRoleUserReq.getGroupId() != null ? getAssignRoleUserReq.getGroupId() : null);
        if ("notAssigned".equals(getAssignRoleUserReq.getQueryType())) {
            roleUserList = dao.getUserByRoleId(map);
            //分页过滤掉已经分配的用户
            Set<Integer> userIdSet = roleUserList.stream().map(roleUser -> (ObjectUtils.isEmpty(roleUser.getUserId()) ? 0 : roleUser.getUserId().intValue())).collect(Collectors.toSet());
            UserDto findDto = getFindDto(getAssignRoleUserReq);
            //设置需要根据权限过滤用户
            findDto.setFilterByUserPermission(true);
            if (CollectionUtils.isNotEmpty(userIdSet)) {
                findDto.setIdNotInSet(userIdSet);
            }
            RestMessage<PageInfo<FplUser>> findResult = userFeign.findUserListPage(findDto, getAssignRoleUserReq.getPageNum(), getAssignRoleUserReq.getPageSize());
            if (!findResult.isSuccess() || ObjectUtils.isEmpty(findResult.getData())) {
                return new PageInfo<>();
            }
            roleUserList = convert(findResult.getData().getList());
            pageInfo.setPageSize(findResult.getData().getPageSize());
            pageInfo.setPageNum(findResult.getData().getPageNum());
            pageInfo.setTotal(findResult.getData().getTotal());
        } else {
            UserDto findDto = getFindDto(getAssignRoleUserReq);
            RestMessage<List<FplUser>> findResult = userFeign.findUserList(findDto);
            if (!findResult.isSuccess() || ObjectUtils.isEmpty(findResult.getData())) {
                return new PageInfo<>();
            }
            Set<Long> userIdSet = findResult.getData().stream().map(user -> user.getId()).collect(Collectors.toSet());
            Map<String, Object> queryMap = Maps.newHashMap();
            queryMap.put("roleId", getAssignRoleUserReq.getRoleId());
            queryMap.put("groupId", getAssignRoleUserReq.getGroupId());
            if (getAssignRoleUserReq.getUserId() != null) {
                queryMap.put("userId", getAssignRoleUserReq.getUserId());
            }
            if (CollectionUtils.isNotEmpty(userIdSet)) {
                queryMap.put("userIdSet", userIdSet);
            }
            PageHelper.startPage(getAssignRoleUserReq.getPageNum(), getAssignRoleUserReq.getPageSize());
            roleUserList = dao.getRoleUserList(queryMap);
            pageInfo = new PageInfo<>(roleUserList);
        }
        translate(roleUserList);
        pageInfo.setList(roleUserList);
        return pageInfo;
    }

    /**
     * 获取查询条件
     *
     * @param getAssignRoleUserReq
     * @return
     */
    private UserDto getFindDto(GetRoleUserListReq getAssignRoleUserReq) {
        UserDto findDto = new UserDto();
        findDto.setGroupId(getAssignRoleUserReq.getGroupId());
        findDto.setQueryUserId(getAssignRoleUserReq.getCurrentUserId());
        findDto.setState(UserStateEnum.ENABLE.getCode());
        if (CollectionUtils.isNotEmpty(getAssignRoleUserReq.getOrgIdList())) {
            findDto.setOrgIdList(getAssignRoleUserReq.getOrgIdList());
        }
        if (!StringUtils.isBlank(getAssignRoleUserReq.getUserName())) {
            findDto.setUserName(getAssignRoleUserReq.getUserName());
        }
        if (!StringUtils.isBlank(getAssignRoleUserReq.getPhone())) {
            findDto.setPhone(getAssignRoleUserReq.getPhone());
        }
        if (getAssignRoleUserReq.getUserId() != null) {
            findDto.setId(getAssignRoleUserReq.getUserId());
        }
        return findDto;
    }

    /**
     * 翻译
     *
     * @param roleUserList
     * @return
     */
    private List<RoleUser> translate(List<RoleUser> roleUserList) {
        //获取到用户map
        Set<Long> userSet = roleUserList.stream().map(roleUser -> roleUser.getUserId()).collect(Collectors.toSet());
        Set<Long> authUserSet = roleUserList.stream().map(roleUser -> roleUser.getAuthor() == null ? null : roleUser.getAuthor()).collect(Collectors.toSet());
        Map<Long, FplUser> userMap = userDomainService.getUserInfoMap(userSet);
        Map<Long, FplUser> authUserMap = null;
        if (CollectionUtils.isNotEmpty(authUserSet)) {
            authUserMap = userDomainService.getUserInfoMap(authUserSet);
        }
        Map<Long, OrgInfoDto> orgMap = null;
        //获取组织map
        if (!ObjectUtils.isEmpty(userMap)) {
            List<Long> orgIdList = userMap.values().stream().map(user -> user.getOrgId()).collect(Collectors.toList());
            orgMap = findOrgInfoByIds(orgIdList);
        }

        for (RoleUser roleUser : roleUserList) {
            if (!ObjectUtils.isEmpty(userMap)) {
                FplUser user = userMap.get(roleUser.getUserId());
                if (!ObjectUtils.isEmpty(user)) {
                    roleUser.setUserName(user.getUserName());
                    roleUser.setUserCode(user.getUserCode());
                    roleUser.setRealName(user.getRealName());
                    roleUser.setPhone(user.getPhone());
                    if (!ObjectUtils.isEmpty(orgMap)) {
                        OrgInfoDto orgInfoDto = orgMap.get(user.getOrgId());
                        if (!ObjectUtils.isEmpty(orgInfoDto)) {
                            roleUser.setOrgName(orgInfoDto.getOrgName());
                        }
                    }
                }
            }
            if (!ObjectUtils.isEmpty(authUserMap)) {
                FplUser user = authUserMap.get(roleUser.getAuthor());
                if (!ObjectUtils.isEmpty(user)) {
                    roleUser.setAuthorUser(user.getUserName());
                }
            }
        }
        return roleUserList;
    }

    private List<RoleUser> convert(List<FplUser> userList) {
        List<RoleUser> roleUserList = Lists.newArrayList();
        for (FplUser user : userList) {
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            roleUser.setUserName(user.getUserName());
            roleUser.setOrgId(user.getOrgId());
            roleUser.setPhone(user.getPhone());
            roleUserList.add(roleUser);
        }
        return roleUserList;
    }

    @Override
    public int delRole(Long roleId, Long groupId) {
        try {
            Map<String, Long> map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId == null ? null : groupId);
            // 删除角色
            dao.delBasePermissionRoleExtendTrue(map);
            // 删除用户角色关系表
            basePermissionUserRoleMapper.delBasePermissionUserRoleTrueByRoleId(map);
            // 删除角色权限关系表
            basePermissionRoleFuncMapper.delBasePermissionRoleFuncTrueByRoleId(map);
        } catch (Exception e) {
            LOGGER.error("删除角色失败，角色id-->" + roleId + ",集团id-->" + groupId, e);
            throw e;
        }
        return 1;
    }

    @Override
    public RestMessage<BasePermissionRoleVo> insertOrupdateBasePermissionRole(InputRoleInsertOrUpdateDto inputRoleInsertOrUpdateDto) {
        // 判断是新增还是更新
        try {
            if (inputRoleInsertOrUpdateDto.getRoleId() != null) {
                BasePermissionRole basePermissionRole = dao.getBasePermissionRoleByID(inputRoleInsertOrUpdateDto.getRoleId());
                AssertUtils.isNotNull(basePermissionRole, "修改的数据不存在");
                return updateRole(inputRoleInsertOrUpdateDto,basePermissionRole);
            } else {
                return insertRole(inputRoleInsertOrUpdateDto);
            }
        } catch (Exception e) {
            if (inputRoleInsertOrUpdateDto.getRoleId() == null) {
                LOGGER.error("新增角色失败，角色id-->" + inputRoleInsertOrUpdateDto.getRoleId() + "，角色编码-->" + inputRoleInsertOrUpdateDto.getRoleCode() + ",角色名称-->" + inputRoleInsertOrUpdateDto.getRoleName(), e);
                throw PermissionErrorCode.INSERT_UPDATE_ROLE_ERROR.throwError();
            } else {
                LOGGER.error("更新角色失败，角色id-->" + inputRoleInsertOrUpdateDto.getRoleId() + "，角色编码-->" + inputRoleInsertOrUpdateDto.getRoleCode() + ",角色名称-->" + inputRoleInsertOrUpdateDto.getRoleName(), e);
                throw PermissionErrorCode.INSERT_UPDATE_ROLE_ERROR.throwError();
            }

        }
    }

    /*
     * 更新角色，更新角色功能权限，更新角色组织权限
     */
    public RestMessage<BasePermissionRoleVo> updateRole(InputRoleInsertOrUpdateDto inputRoleInsertOrUpdateDto, BasePermissionRole basePermissionRole) {
        RestMessage<String> chekcResult = roleManagerHelper.checkRoleName(inputRoleInsertOrUpdateDto.getRoleName(), inputRoleInsertOrUpdateDto.getGroupId(), inputRoleInsertOrUpdateDto.getRoleId());
        if (!chekcResult.isSuccess()) {
            return RestMessage.error(chekcResult.getCode(), chekcResult.getMessage());
        }
        basePermissionRole.setRoleName(inputRoleInsertOrUpdateDto.getRoleName());
        basePermissionRole.setRemark(inputRoleInsertOrUpdateDto.getRemark());
        basePermissionRole.setOrgId(inputRoleInsertOrUpdateDto.getOrgId());
        basePermissionRole.setGroupId(inputRoleInsertOrUpdateDto.getGroupId());
        basePermissionRole.setCreatedBy(inputRoleInsertOrUpdateDto.getUserId());
        basePermissionRole.setModifiedBy(inputRoleInsertOrUpdateDto.getUserId());
        basePermissionRole.setCreatedDate(new Date());
        basePermissionRole.setModifiedDate(new Date());
        basePermissionRole.setState(StateEnum.ENABLE.getCode());
        dao.updateBasePermissionRole(basePermissionRole);
        BasePermissionRoleVo result = new BasePermissionRoleVo();
        BeanUtils.copyProperties(basePermissionRole, result);
        return RestMessage.doSuccess(result);
    }


    /*
     * 新增角色，新增角色功能权限，新增角色组织权限
     */
    public RestMessage<BasePermissionRoleVo> insertRole(InputRoleInsertOrUpdateDto inputRoleInsertOrUpdateDto) {
        RestMessage<String> chekcResult = roleManagerHelper.checkRoleName(inputRoleInsertOrUpdateDto.getRoleName(), inputRoleInsertOrUpdateDto.getGroupId(), null);
        if (!chekcResult.isSuccess()) {
            return RestMessage.error(chekcResult.getCode(), chekcResult.getMessage());
        }

        // 新增角色
        BasePermissionRole basePermissionRoleInsert = new BasePermissionRole();
        //获取角色编码

        String roleCode = codeDomainService.getRoleCode(inputRoleInsertOrUpdateDto.getGroupId());
        basePermissionRoleInsert.setRoleCode(roleCode);
        basePermissionRoleInsert.setRoleName(inputRoleInsertOrUpdateDto.getRoleName());
        basePermissionRoleInsert.setRemark(inputRoleInsertOrUpdateDto.getRemark());
        basePermissionRoleInsert.setOrgId(inputRoleInsertOrUpdateDto.getOrgId());
        basePermissionRoleInsert.setGroupId(inputRoleInsertOrUpdateDto.getGroupId());
        basePermissionRoleInsert.setCreatedBy(inputRoleInsertOrUpdateDto.getUserId());
        basePermissionRoleInsert.setModifiedBy(inputRoleInsertOrUpdateDto.getUserId());
        basePermissionRoleInsert.setCreatedDate(new Date());
        basePermissionRoleInsert.setCreatedName(FplUserUtil.getUserName());
        basePermissionRoleInsert.setModifiedName("System");
        basePermissionRoleInsert.setModifiedDate(new Date());
        basePermissionRoleInsert.setState(ValidEnum.YES.getCode());
        dao.addBasePermissionRole(basePermissionRoleInsert);
        BasePermissionRoleVo result = new BasePermissionRoleVo();
        BeanUtils.copyProperties(basePermissionRoleInsert, result);
        return RestMessage.doSuccess(result);
    }

    public RestMessage getRolePermission(RolePermissionParam rolePermissionParam) {
        Long userId = rolePermissionParam.getUserId();
        Long roleId = rolePermissionParam.getRoleId();
        Long groupId = rolePermissionParam.getGroupId();
        String permissionType = rolePermissionParam.getPermissionType();
        Integer showSelected = rolePermissionParam.getShowSelected();
        if (roleId > 0 && userId == 0) {
            if (StringUtils.equals(permissionType, PermissionTypeEnum.ORG.getCode())) {
                return buildOrgOutPut(roleId, groupId, permissionType);
            }
            if (StringUtils.equals(permissionType, PermissionTypeEnum.FUNC.getCode())) {
                return buildFuncOutPut(roleId, groupId, permissionType);
            }
        } else if (userId > 0 && roleId == 0) {// 新增角色,只查询登录人的权限
            if (StringUtils.equals(permissionType, PermissionTypeEnum.ORG.getCode())) {
                return buildLoginUserOrgOutPut(userId, groupId, permissionType);
            }
            if (StringUtils.equals(permissionType, PermissionTypeEnum.FUNC.getCode())) {
                return buildLoginUserFuncOutPut(userId, groupId, permissionType);
            }
        } else if (userId > 0 && roleId > 0) {
            if (StringUtils.equals(permissionType, PermissionTypeEnum.ORG.getCode())) {
                return buildUpdateOrgOutPut(userId, roleId, groupId, permissionType, showSelected);
            }
            if (StringUtils.equals(permissionType, PermissionTypeEnum.FUNC.getCode())) {
                return buildUpdateFuncOutPut(userId, roleId, groupId, permissionType, showSelected);
            }
        }
        return null;
    }


    private RestMessage<Map<String, Object>> buildUpdateFuncOutPut(Long userId, Long roleId, Long groupId, String permissionType, Integer showSelected) {
        try {
            Map<String, Object> outPutMap = Maps.newHashMap();
            String roleCode = codeDomainService.getRoleCode(groupId);
            //当前登录用户上的功能权限
            List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", null, 0L, null, 0L);
            //获取角色上的功能权限,与用户上的组织权限进行匹配，已经选中的打勾，没选中的不打勾TBD
            Map<String, Object> map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId);
            OutPutRoleManagerDto outPutRoleManagerDto = buildOutPutRoleManagerDto(map);
            map.put("permissionType", permissionType);
            List<RolePermission> funcPermissions = dao.getRoleFuncPermissions(map);
            for (UserPermission up : userPermissions) {
                Long permissionId = up.getPermissionId();
                if (CollectionUtils.isNotEmpty(funcPermissions)) {
                    boolean choose = funcPermissions.stream().anyMatch(rolePermission -> rolePermission.getPermissionId().equals(permissionId));
                    if (choose) {
                        up.setCheck(true);
                    }
                }

            }
            //只获取选中的
            if (new Integer(1).equals(showSelected)) {
                userPermissions = userPermissions.stream().filter(resp -> resp.isCheck()).collect(Collectors.toList());
            }
            userPermissions = getUserFuncPermissionList(userPermissions, 0L);
            Map<Integer, List<UserPermission>> grouped = userPermissions.stream().collect(Collectors.groupingBy(userPermission -> userPermission.getType()));
            outPutMap.put("funcs", reGroup(grouped));
            return RestMessage.doSuccess(outPutMap);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("构造当前用户功能权限树出错,用户id {},所属集团id {},权限类型 {}", userId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorReason());
        }

    }

    private RestMessage<Map<String, Object>> buildLoginUserFuncOutPut(Long userId, Long groupId, String permissionType) {
        try {
            Map<String, Object> outPutMap = Maps.newHashMap();
            String roleCode = codeDomainService.getRoleCode(groupId);
            outPutMap.put("roleCode", roleCode);
            List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", null, 0L, null, 0L);
            userPermissions = getUserFuncPermissionList(userPermissions, 0L);
            Map<Integer, List<UserPermission>> grouped = userPermissions.stream().collect(Collectors.groupingBy(userPermission -> userPermission.getType()));
            outPutMap.put("funcs", reGroup(grouped));
            return RestMessage.doSuccess(outPutMap);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("构造当前用户功能权限树出错,用户id {},所属集团id {},权限类型 {}", userId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorReason());
        }

    }

    /**
     * 重新分组
     *
     * @param grouped
     * @return
     */
    private Map<String, List<UserPermission>> reGroup(Map<Integer, List<UserPermission>> grouped) {
        Map<String, List<UserPermission>> result = Maps.newHashMap();
        List<UserPermission> pcPermissions = Lists.newArrayList();
        List<UserPermission> appPermissions = Lists.newArrayList();
        for (Integer key : grouped.keySet()) {
            for (Integer appName : FUNC_GROUP_PC) {
                if (key.equals(appName)) {
                    pcPermissions.addAll(grouped.get(appName));
                }
            }
            for (Integer appName : FUNC_GROUP_APPS) {
                if (key.equals(appName)) {
                    appPermissions.addAll(grouped.get(appName));
                }
            }
        }
        result.put("PC", pcPermissions);
        result.put("APP", appPermissions);
        return result;
    }

    private RestMessage<OutPutRoleManagerDto> buildUpdateOrgOutPut(Long userId, Long roleId, Long groupId, String permissionType, Integer showSelected) {
        try {
            //当前登录用户上的组织权限
            List<UserOrgPermissionDto> userOrgPermissionDtos = groupConfigHelper.getUserOrgPermissionByStrategy(userId, groupId, "");
            //过滤掉部门
            userOrgPermissionDtos = userOrgPermissionDtos.stream().filter(userOrgPermission -> !OrgTypeEnum.DEPARTMENT.getName().equals(userOrgPermission.getOrgType())).collect(Collectors.toList());
            //获取角色上的组织权限,与用户上的组织权限进行匹配，已经选中的打勾，没选中的不打勾TBD
            Map<String, Object> map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId);
            OutPutRoleManagerDto outPutRoleManagerDto = buildOutPutRoleManagerDto(map);
            map.put("permissionType", permissionType);
            List<RoleOrgPermission> userRolePermissions = Lists.newArrayList();
            List<RoleOrgPermission> roleOrgPermissions = dao.getRoleOrgPermissions(map);

            for (UserOrgPermissionDto up : userOrgPermissionDtos) {
                RoleOrgPermission rop = new RoleOrgPermission();
                Long permissionId = up.getPermissionId();
                boolean choose = false;
                if (CollectionUtils.isNotEmpty(roleOrgPermissions)) {
                    choose = roleOrgPermissions.stream().anyMatch(roleOrgPermission -> roleOrgPermission.getId().equals(permissionId));
                }
                rop.setGroupId(groupId);
                rop.setId(up.getPermissionId());
                rop.setOrgCode(up.getOrgCode());
                rop.setOrgName(up.getOrgName());
                rop.setParentId(up.getParentId());
                if (choose) {
                    rop.setCheck(true);
                }
                rop.setOrgType(up.getOrgType());
                userRolePermissions.add(rop);
            }
            //只显示选中的数据
            if (new Integer(1).equals(showSelected)) {
                userRolePermissions = userRolePermissions.stream().filter(resp -> resp.isCheck()).collect(Collectors.toList());
            }
            userRolePermissions = getRoleOrgPermissionsList(userRolePermissions);
            //过滤掉部门
            if (CollectionUtils.isNotEmpty(userOrgPermissionDtos)) {
                userRolePermissions = userRolePermissions.stream().filter(userOrgPermission -> !OrgTypeEnum.DEPARTMENT.getName().equals(userOrgPermission.getOrgType())).collect(Collectors.toList());
            }
            outPutRoleManagerDto.setOrgPermissions(userRolePermissions);
            return RestMessage.doSuccess(outPutRoleManagerDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("getRolePermission--当前用户修改角色上组织权限树出错,用户id {},所属集团id {},权限类型 {}", userId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorReason());
        }
    }

    private RestMessage<Map<String, List<UserOrgPermissionDto>>> buildLoginUserOrgOutPut(Long userId, Long groupId, String permissionType) {
        try {
            Map<String, List<UserOrgPermissionDto>> outPutMap = Maps.newHashMap();
            List<UserOrgPermissionDto> userOrgPermissionDtos = groupConfigHelper.getUserOrgPermissionByStrategy(userId, groupId, "");
            //过滤掉部门
            if (CollectionUtils.isNotEmpty(userOrgPermissionDtos)) {
                userOrgPermissionDtos = userOrgPermissionDtos.stream().filter(userOrgPermission -> !OrgTypeEnum.DEPARTMENT.getName().equals(userOrgPermission.getOrgType())).collect(Collectors.toList());
                userOrgPermissionDtos = getUserOrgPermissionsList(userOrgPermissionDtos);
            }
            outPutMap.put("orgs", userOrgPermissionDtos);
            return RestMessage.doSuccess(outPutMap);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("构造当前用户组织权限树出错,用户id {},所属集团id {},权限类型 {}", userId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorReason());
        }
    }

    /*
     * 用户--获取当前用户的功能权限树
     */
    public List<UserPermission> getUserFuncPermissionList(List<UserPermission> userPermissions, Long menuId) {
        List<UserPermission> datas = Lists.newArrayList();
        for (UserPermission userPermission : userPermissions) {
            if (menuId > 0) {
                if (userPermission.getParentId().equals(menuId)) {
                    UserPermission data = new UserPermission();
                    BeanUtils.copyProperties(userPermission, data);
                    build(userPermissions, data);
                    datas.add(data);
                }
            } else {
                if (0 == userPermission.getParentId()) {
                    UserPermission data = new UserPermission();
                    BeanUtils.copyProperties(userPermission, data);
                    build(userPermissions, data);
                    datas.add(data);
                }
            }

        }

        return datas;
    }

    /*
     * 用户--构造功能树
     */
    public void build(List<UserPermission> userPermissions, UserPermission data) {
        List<UserPermission> childFuncs = getChildren(userPermissions, data);
        data.setChildFuncs(childFuncs);
        for (UserPermission userPermission : childFuncs) {
            build(userPermissions, userPermission);
        }
    }

    /*
     * 用户--获取每个根节点下的列表
     */
    private List<UserPermission> getChildren(List<UserPermission> userPermissions, UserPermission data) {
        List<UserPermission> children = new ArrayList<UserPermission>();
        for (UserPermission child : userPermissions) {
            if (Objects.equals(child.getParentId(), data.getPermissionId())) {
                UserPermission userPermission = new UserPermission();
                BeanUtils.copyProperties(child, userPermission);
                children.add(userPermission);
            }

        }
        return children;
    }

    /*
     * 获取当前用户的组织权限
     */
    public List<UserOrgPermissionDto> getUserOrgPermissionsList(List<UserOrgPermissionDto> userOrgPermissionDtos) {
        List<UserOrgPermissionDto> datas = Lists.newArrayList();
        for (UserOrgPermissionDto userOrgPermissionDto : userOrgPermissionDtos) {
            // 找组织的起始节点，如果组织的pid不在集合的id中或者pid为0，那么这个权限是起始组织权限
            boolean isRoot = userCheckIsRoot(userOrgPermissionDtos, userOrgPermissionDto.getParentId());
            LOGGER.info("是否是root：" + isRoot);
            if (userOrgPermissionDto.getParentId() == null || isRoot) {
                UserOrgPermissionDto data = new UserOrgPermissionDto();
                data.setPermissionId(userOrgPermissionDto.getPermissionId());
                data.setOrgCode(userOrgPermissionDto.getOrgCode());
                data.setOrgName(userOrgPermissionDto.getOrgName());
                data.setParentId(userOrgPermissionDto.getParentId());
                data.setGroupId(userOrgPermissionDto.getGroupId());
                data.setCheck(userOrgPermissionDto.isCheck());
                data.setOrgType(userOrgPermissionDto.getOrgType());
                build(userOrgPermissionDtos, data);
                datas.add(data);
            }
        }
        return datas;
    }

    /*
     * 用户组织权限-是否起始节点，不是完整的树
     */
    private boolean userCheckIsRoot(List<UserOrgPermissionDto> userOrgPermissionDtos, Long parentId) {
        for (UserOrgPermissionDto userOrgPermissionDto : userOrgPermissionDtos) {
            if ((userOrgPermissionDto.getPermissionId() == null ? 0 : userOrgPermissionDto.getPermissionId().intValue()) == (parentId == null ? 0 : parentId.intValue())) {
                return false;
            }
        }
        return true;
    }

    /*
     * 用户组织权限--构造组织树
     */
    public void build(List<UserOrgPermissionDto> userOrgPermissionDtos, UserOrgPermissionDto data) {
        List<UserOrgPermissionDto> childOrgs = getChildren(userOrgPermissionDtos, data);
        data.setChildOrgs(childOrgs);
        for (UserOrgPermissionDto userOrgPermissionDto : childOrgs) {
            build(userOrgPermissionDtos, userOrgPermissionDto);
        }
    }

    /*
     * 角色--获取每个根节点下的列表
     */
    private List<UserOrgPermissionDto> getChildren(List<UserOrgPermissionDto> userOrgPermissionDtos, UserOrgPermissionDto data) {
        List<UserOrgPermissionDto> children = new ArrayList<UserOrgPermissionDto>();
        for (UserOrgPermissionDto child : userOrgPermissionDtos) {
            Long parentId = child.getParentId() == null ? 0L : child.getParentId();
            if ((parentId == null ? 0L : parentId) == (data.getPermissionId() == null ? 0 : data.getPermissionId().longValue())) {
                UserOrgPermissionDto userOrgPermissionDto = new UserOrgPermissionDto();
                userOrgPermissionDto.setPermissionId(child.getPermissionId());
                userOrgPermissionDto.setOrgCode(child.getOrgCode());
                userOrgPermissionDto.setOrgName(child.getOrgName());
                userOrgPermissionDto.setParentId(child.getParentId());
                userOrgPermissionDto.setGroupId(child.getGroupId());
                userOrgPermissionDto.setCheck(child.isCheck());
                userOrgPermissionDto.setOrgType(child.getOrgType());
                children.add(userOrgPermissionDto);
            }
        }
        return children;
    }


    /*
     * 返回功能权限
     */
    private RestMessage<OutPutRoleManagerDto> buildFuncOutPut(Long roleId, Long groupId, String permissionType) {
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId);
            OutPutRoleManagerDto outPutRoleManagerDto = buildOutPutRoleManagerDto(map);
            map.put("permissionType", permissionType);
            List<RolePermission> funcPermissions = dao.getRoleFuncPermissions(map);
            if (CollectionUtils.isNotEmpty(funcPermissions)) {
                funcPermissions = getRoleFuncPermissionList(funcPermissions);
            }
            Map<Integer, List<RolePermission>> grouped = funcPermissions.stream().collect(Collectors.groupingBy(rolePermission -> rolePermission.getType()));

            Map<String, List<RolePermission>> result = Maps.newHashMap();
            List<RolePermission> pcPermissions = Lists.newArrayList();
            List<RolePermission> appPermissions = Lists.newArrayList();
            for (Integer key : grouped.keySet()) {
                for (Integer appName : FUNC_GROUP_PC) {
                    if (key.equals(appName)) {
                        pcPermissions.addAll(grouped.get(appName));
                    }
                }
                for (Integer appName : FUNC_GROUP_APPS) {
                    if (key.equals(appName)) {
                        appPermissions.addAll(grouped.get(appName));
                    }
                }
            }
            result.put("PC", pcPermissions);
            result.put("APP", appPermissions);
            outPutRoleManagerDto.setFuncPermissions(result);
            return RestMessage.doSuccess(outPutRoleManagerDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("构造功能权限树出错,角色id {},所属集团id {},权限类型 {}", roleId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_TREE_ERROR.getErrorReason());
        }
    }

    /*
     * 构造返回结构角色公共部分
     */
    private OutPutRoleManagerDto buildOutPutRoleManagerDto(Map<String, Object> map) {
        OutPutRoleManagerDto outPutRoleManagerDto = new OutPutRoleManagerDto();
        try {
            OutPutRoleDto outPutRoleDto = dao.getRoleMessageByRoleIdAndGroupId(map);
            if (!ObjectUtils.isEmpty(outPutRoleDto)) {
                outPutRoleManagerDto.setId(outPutRoleDto.getId());
                outPutRoleManagerDto.setOrgId(outPutRoleDto.getOrgId());
                outPutRoleManagerDto.setOrgName(outPutRoleDto.getOrgName());
                outPutRoleManagerDto.setRoleCode(outPutRoleDto.getRoleCode());
                outPutRoleManagerDto.setRemark(outPutRoleDto.getRemark());
                outPutRoleManagerDto.setRoleName(outPutRoleDto.getRoleName());
            }
            return outPutRoleManagerDto;
        } catch (Exception e) {
            e.printStackTrace();
            return outPutRoleManagerDto;
        }
    }

    /*
     * 角色-返回构造的功能权限树列表
     */
    public List<RolePermission> getRoleFuncPermissionList(List<RolePermission> rolePermissions) {
        List<RolePermission> datas = Lists.newArrayList();
        for (RolePermission rolePermission : rolePermissions) {
            if (-1 == rolePermission.getParentId()) {
                RolePermission data = new RolePermission();
                BeanUtils.copyProperties(rolePermission, data);
                build(rolePermissions, data);
                datas.add(data);
            }
        }
        return datas;
    }

    /*
     * 角色-构造功能树
     */
    public void build(List<RolePermission> rolePermissions, RolePermission data) {
        List<RolePermission> childFuncs = getChildren(rolePermissions, data);
        data.setChildFuncs(childFuncs);
        for (RolePermission rolePermission : childFuncs) {
            build(rolePermissions, rolePermission);
        }
    }

    /*
     * 角色-获取每个根节点下的列表
     */
    private List<RolePermission> getChildren(List<RolePermission> rolePermissions, RolePermission data) {
        List<RolePermission> children = new ArrayList<RolePermission>();
        for (RolePermission child : rolePermissions) {
            if (Objects.equals(child.getParentId(), data.getPermissionId())) {
                RolePermission rolePermission = new RolePermission();
                BeanUtils.copyProperties(child, rolePermission);
                children.add(rolePermission);
            }

        }
        return children;
    }

    /*
     * 角色-返回组织权限
     */
    private RestMessage<OutPutRoleManagerDto> buildOrgOutPut(Long roleId, Long groupId, String permissionType) {
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId);
            OutPutRoleManagerDto outPutRoleManagerDto = buildOutPutRoleManagerDto(map);
            map.put("permissionType", permissionType);
            List<RoleOrgPermission> roleOrgPermissions = dao.getRoleOrgPermissions(map);
            if (CollectionUtils.isNotEmpty(roleOrgPermissions)) {
                roleOrgPermissions = getRoleOrgPermissionsList(roleOrgPermissions);
            }
            outPutRoleManagerDto.setOrgPermissions(roleOrgPermissions);
            return RestMessage.doSuccess(outPutRoleManagerDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("构造组织权限树出错,角色id {},所属集团id {},权限类型 {}", roleId, groupId, permissionType);
            return RestMessage.error(PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorCode() + "", PermissionErrorCode.BUILD_ROLE_ORG_TREE_ERROR.getErrorReason());
        }
    }

    /*
     * 角色--返回构造的组织权限树列表
     */
    public List<RoleOrgPermission> getRoleOrgPermissionsList(List<RoleOrgPermission> roleOrgPermissions) {
        List<RoleOrgPermission> datas = Lists.newArrayList();
        for (RoleOrgPermission roleOrgPermission : roleOrgPermissions) {
            // 找组织的起始节点，与产品确定，如果组织的pid不在集合的id中或者pid为0，那么这个权限是起始组织权限
            boolean isRoot = checkIsRoot(roleOrgPermissions, roleOrgPermission.getParentId());
            if (roleOrgPermission.getParentId() == null || isRoot) {
                RoleOrgPermission data = new RoleOrgPermission();
                data.setId(roleOrgPermission.getId());
                data.setOrgCode(roleOrgPermission.getOrgCode());
                data.setOrgName(roleOrgPermission.getOrgName());
                data.setParentId(roleOrgPermission.getParentId());
                data.setGroupId(roleOrgPermission.getGroupId());
                data.setCheck(roleOrgPermission.isCheck());
                data.setOrgType(roleOrgPermission.getOrgType());
                build(roleOrgPermissions, data);
                datas.add(data);
            }
        }
        return datas;
    }

    /*
     * 角色-是否起始节点，不是完整的树
     */
    private boolean checkIsRoot(List<RoleOrgPermission> roleOrgPermissions, Long parentId) {

        for (RoleOrgPermission roleOrgPermission : roleOrgPermissions) {
            if ((roleOrgPermission.getId() == null ? 0L : roleOrgPermission.getId().longValue()) == (parentId == null ? 0L : parentId.longValue())) {
                return false;
            }
        }
        return true;
    }

    /*
     * 角色--构造组织树
     */
    public void build(List<RoleOrgPermission> roleOrgPermissions, RoleOrgPermission data) {
        List<RoleOrgPermission> childOrgs = getChildren(roleOrgPermissions, data);
        data.setChildOrgs(childOrgs);
        for (RoleOrgPermission roleOrgPermission : childOrgs) {
            build(roleOrgPermissions, roleOrgPermission);
        }
    }

    /*
     * 角色--获取每个根节点下的列表
     */
    private List<RoleOrgPermission> getChildren(List<RoleOrgPermission> roleOrgPermissions, RoleOrgPermission data) {
        List<RoleOrgPermission> children = new ArrayList<RoleOrgPermission>();
        for (RoleOrgPermission child : roleOrgPermissions) {
            if ((child.getParentId() == null ? 0L : child.getParentId().longValue()) == data.getId().longValue()) {
                RoleOrgPermission roleOrgPermission = new RoleOrgPermission();
                roleOrgPermission.setId(child.getId());
                roleOrgPermission.setOrgCode(child.getOrgCode());
                roleOrgPermission.setOrgName(child.getOrgName());
                roleOrgPermission.setParentId(child.getParentId());
                roleOrgPermission.setGroupId(child.getGroupId());
                roleOrgPermission.setCheck(child.isCheck());
                roleOrgPermission.setOrgType(child.getOrgType());
                children.add(roleOrgPermission);
            }

        }
        return children;
    }

    @Override
    public List<UserDataPermissionDto> getUserDataPermissionsList(List<UserDataPermissionDto> userDataPermissionDtos) {
        List<UserDataPermissionDto> datas = Lists.newArrayList();
        for (UserDataPermissionDto userDataPermissionDto : userDataPermissionDtos) {
            if (0 == userDataPermissionDto.getParentId() || userCheckDataIsRoot(userDataPermissionDtos, userDataPermissionDto.getParentId())) {
                UserDataPermissionDto data = new UserDataPermissionDto();
                data.setUserId(userDataPermissionDto.getUserId());
                data.setPermissionType(userDataPermissionDto.getPermissionType());
                data.setPermissionId(userDataPermissionDto.getPermissionId());
                data.setDataId(userDataPermissionDto.getDataId());
                data.setGbCode(userDataPermissionDto.getGbCode());
                data.setAuthorUser(userDataPermissionDto.getAuthorUser());
                data.setAuthorTime(userDataPermissionDto.getAuthorTime());
                data.setState(userDataPermissionDto.getState());
                data.setGroupId(userDataPermissionDto.getGroupId());
                data.setOrgId(userDataPermissionDto.getOrgId());
                data.setName(userDataPermissionDto.getName());
                data.setManagementId(userDataPermissionDto.getManagementId());
                data.setDistributionType(userDataPermissionDto.getDistributionType());
                data.setBased(userDataPermissionDto.getBased());
                data.setDataResource(userDataPermissionDto.getDataResource());
                data.setDataResourceCode(userDataPermissionDto.getDataResourceCode());
                data.setParentId(userDataPermissionDto.getParentId());
                data.setOptionPermission(userDataPermissionDto.getOptionPermission());
                data.setCheck(userDataPermissionDto.isCheck());
                build(userDataPermissionDtos, data);
                datas.add(data);
            }
        }
        return datas;
    }

    private boolean userCheckDataIsRoot(List<UserDataPermissionDto> userDataPermissionDtos, Long parentId) {
        for (UserDataPermissionDto userDataPermissionDto : userDataPermissionDtos) {
            if (userDataPermissionDto.getDataId().equals(parentId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<UserPermission> getUserFuncPermissionListP(List<UserPermission> userPermissions, Long parentId) {
        List<UserPermission> list = Lists.newArrayList();
        for (UserPermission up : userPermissions) {
            if (parentId > 0) {
                if (up.getParentId().equals(parentId)) {
                    UserPermission upn = new UserPermission();
                    BeanUtils.copyProperties(up, upn);
                    list.add(upn);
                }
            }
        }
        return list;
    }

    /*
     * 用户--构造数据权限树
     */
    public void build(List<UserDataPermissionDto> userDataPermissionDtos, UserDataPermissionDto data) {
        List<UserDataPermissionDto> childDatas = getChildren(userDataPermissionDtos, data);
        data.setChildDatas(childDatas);
        for (UserDataPermissionDto userDataPermissionDto : childDatas) {
            build(userDataPermissionDtos, userDataPermissionDto);
        }
    }

    /*
     * 用户--获取每个根节点下的列表
     */
    private List<UserDataPermissionDto> getChildren(List<UserDataPermissionDto> userDataPermissionDtos, UserDataPermissionDto data) {
        List<UserDataPermissionDto> children = new ArrayList<UserDataPermissionDto>();
        for (UserDataPermissionDto child : userDataPermissionDtos) {
            if (child.getParentId().equals(data.getDataId())) {
                UserDataPermissionDto userDataPermissionDto = new UserDataPermissionDto();
                userDataPermissionDto.setUserId(child.getUserId());
                userDataPermissionDto.setPermissionType(child.getPermissionType());
                userDataPermissionDto.setPermissionId(child.getPermissionId());
                userDataPermissionDto.setDataId(child.getDataId());
                userDataPermissionDto.setGbCode(child.getGbCode());
                userDataPermissionDto.setAuthorUser(child.getAuthorUser());
                userDataPermissionDto.setAuthorTime(child.getAuthorTime());
                userDataPermissionDto.setState(child.getState());
                userDataPermissionDto.setGroupId(child.getGroupId());
                userDataPermissionDto.setOrgId(child.getOrgId());
                userDataPermissionDto.setName(child.getName());
                userDataPermissionDto.setManagementId(child.getManagementId());
                userDataPermissionDto.setDistributionType(child.getDistributionType());
                userDataPermissionDto.setBased(child.getBased());
                userDataPermissionDto.setDataResource(child.getDataResource());
                userDataPermissionDto.setDataResourceCode(child.getDataResourceCode());
                userDataPermissionDto.setParentId(child.getParentId());
                userDataPermissionDto.setOptionPermission(child.getOptionPermission());
                userDataPermissionDto.setCheck(child.isCheck());
                children.add(userDataPermissionDto);
            }

        }
        return children;
    }


    @Override
    public List<UserPermission> searchMenuList(List<UserPermission> userPermissions, List<UserPermission> lastStageMenus) {
        List<UserPermission> allResult = new ArrayList<>();
        Map map = Maps.newHashMap();
        for (UserPermission userPermission : lastStageMenus) {
            getParentList(userPermissions, userPermission, allResult, map);
            if (!map.containsKey(userPermission.getPermissionId())) allResult.add(userPermission);
        }
        return getUserFuncPermissionList(allResult, 0L);

    }

    /**
     * 递归获取父级菜单，添加到list
     *
     * @param userPermissions
     * @param userPermission
     * @param allResult
     */
    private void getParentList(List<UserPermission> userPermissions, UserPermission userPermission, List<UserPermission> allResult, Map map) {
        List<UserPermission> result = null;
        for (Iterator<UserPermission> it = userPermissions.iterator(); it.hasNext(); ) {
            UserPermission bean = it.next();
            Long parentId = userPermission.getParentId() == null ? 0L : userPermission.getParentId();
            Long id = bean.getPermissionId() == null ? 0 : bean.getPermissionId();
            if (id.equals(parentId)) {
                if (result == null) {
                    result = new ArrayList<UserPermission>();
                }
                result.add(bean);
                map.put(bean.getPermissionId(), bean.getName());
                it.remove();
            }
        }
        if (result != null) {
            allResult.addAll(result);
            for (UserPermission bean : result) {
                getParentList(userPermissions, bean, allResult, map);
            }
        }
    }

    private Map<Long, OrgInfoDto> findOrgInfoByIds(List<Long> list) {
        BatchQueryParam param = new BatchQueryParam();
        param.setIds(list);
        List<OrgInfoDto> orgInfoDtos = orgDomainService.batchQueryOrgInfo(param);
        if (CollectionUtils.isNotEmpty(orgInfoDtos)) {
            return orgInfoDtos.stream().collect(Collectors.toMap(OrgInfoDto::getId, org -> org, (v1, v2) -> v1));
        }
        return null;
    }

}
