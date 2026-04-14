package com.org.permission.server.permission.service.impl;


import com.alibaba.fastjson.JSON;
import com.common.base.enums.StateEnum;
import com.common.framework.execption.BizException;
import com.common.util.message.RestMessage;
import com.common.util.util.HuToolUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.org.permission.common.dto.BaseStaffDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.permission.dto.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.org.bean.StaffInfoBean;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.CommonOrgMapper;
import com.org.permission.server.org.mapper.StaffMapper;
import com.org.permission.server.permission.dto.*;
import com.org.permission.server.permission.dto.req.BatchDeleteUserRoleReq;
import com.org.permission.server.permission.dto.req.BatchSaveUserRoleReq;
import com.org.permission.server.permission.dto.req.SaveUserRoleReq;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.enums.PermissionErrorCode;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.mapper.BasePermissionRoleFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserRoleMapper;
import com.org.permission.server.permission.service.IBasePermissionRoleService;
import com.org.permission.server.permission.service.IBasePermissionUserRoleService;
import com.org.permission.server.permission.vo.BasePermissionUserRoleVo;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.enums.UserManagerLevelEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * base_permission_user_roleServiceImpl类 用户角色关联表管理
 */
@Service
public class BasePermissionUserRoleServiceImpl implements IBasePermissionUserRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePermissionUserRoleServiceImpl.class);

    @Autowired
    private BasePermissionUserRoleMapper dao;
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;
    @Autowired
    private BasePermissionRoleFuncMapper basePermissionRoleFuncMapper;
    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Resource
    private UserFeign userFeign;

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private CommonOrgMapper commonOrgMapper;

    public int addBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole) {
        return dao.addBasePermissionUserRole(basePermissionUserRole);
    }

    public int delBasePermissionUserRole(Integer id) {
        return dao.delBasePermissionUserRole(id);
    }

    public int delBasePermissionUserRoleTrue(Integer id) {
        return dao.delBasePermissionUserRoleTrue(id);
    }

    public int updateBasePermissionUserRole(BasePermissionUserRole basePermissionUserRole) {
        return dao.updateBasePermissionUserRole(basePermissionUserRole);
    }

    public BasePermissionUserRole getBasePermissionUserRoleById(Integer id) {
        return dao.getBasePermissionUserRoleByID(id);
    }

    public int getBasePermissionUserRoleCount() {
        return dao.getBasePermissionUserRoleCount();
    }

    public int getBasePermissionUserRoleCountAll() {
        return dao.getBasePermissionUserRoleCountAll();
    }

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPage(
            BasePermissionUserRoleVo basePermissionUserRole) {
        return dao.getListBasePermissionUserRolesByPage(basePermissionUserRole);
    }

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPOJO(
            BasePermissionUserRole basePermissionUserRole) {
        return dao.getListBasePermissionUserRolesByPOJO(basePermissionUserRole);
    }

    public List<BasePermissionUserRole> getListBasePermissionUserRolesByPojoPage(
            BasePermissionUserRole basePermissionUserRole) {
        Map map = new HashMap();
        map.put("pojo", basePermissionUserRole);
        return dao.getListBasePermissionUserRolesByPojoPage(map);
    }

    /*
     * 当前操作人组织权限下有哪些角色，树状结构列出
     */
    @Override
    public List<OrgDto> getOrgRoleList(Map<String, Object> map) {
        List<RoleDto> roles = Lists.newArrayList();
        List<OrgDto> orgDtos = Lists.newArrayList();
        Set<String> orgSet = (Set<String>) map.get("orgs");
        Set<String> roleSet = (Set<String>) map.get("roles");
        List<OrgRoleDto> orgRoleDtos = dao.getOrgRoleList(map);
        Map<String, Object> orgMap = Maps.newHashMap();
        for (OrgRoleDto orgRoleDto : orgRoleDtos) {
            if (!orgMap.containsKey("org" + orgRoleDto.getOrgId())) {
                OrgDto orgDto = new OrgDto();
                orgDto.setOrgCode(orgRoleDto.getOrgCode());
                orgDto.setOrgId(orgRoleDto.getOrgId());
                orgDto.setParentId(orgRoleDto.getParentId());
                orgDto.setOrgName(orgRoleDto.getOrgName());
                orgDto.setName(orgRoleDto.getOrgName());
                orgDto.setId("org" + orgRoleDto.getOrgId());
                orgDtos.add(orgDto);
                orgMap.put("org" + orgRoleDto.getOrgId(), orgRoleDto.getOrgId() + "");
            }
            if (!orgMap.containsKey("role" + orgRoleDto.getRoleId())) {
                RoleDto roleDto = new RoleDto();
                roleDto.setRoleId(orgRoleDto.getRoleId());
                roleDto.setOrgId(orgRoleDto.getOrgId());
                roleDto.setRoleCode(orgRoleDto.getRoleCode());
                roleDto.setRoleName(orgRoleDto.getRoleName());
                roleDto.setName(orgRoleDto.getRoleName());
                roleDto.setOrgName(orgRoleDto.getOrgName());
                roleDto.setId("role" + orgRoleDto.getRoleId());
                if (!ObjectUtils.isEmpty(roleSet)) {
                    boolean anyMatch = roleSet.stream().anyMatch(roleId -> roleId.equals(roleDto.getId()));
                    if (anyMatch) {
                        roleDto.setCheck(true);
                    } else {
                        roleDto.setCheck(false);
                    }
                }
                roles.add(roleDto);
                orgMap.put("role" + orgRoleDto.getRoleId(), orgRoleDto.getRoleId() + "");
            }

        }
        return getRoleOrgDtos(orgDtos, roles);
    }

    /*
     * 当前操作人有哪些角色，列出角色和所属组织的树状
     */
    @Override
    public Map<String, Set<String>> getUserOrgRoleList(Map<String, Object> map) {
        Set<String> roles = Sets.newHashSet();
        Set<String> orgs = Sets.newHashSet();
        Map<String, Set<String>> mapResult = Maps.newHashMap();
        List<OrgRoleDto> orgRoleDtos = dao.getUserOrgRoleList(map);
        for (OrgRoleDto orgRoleDto : orgRoleDtos) {
            orgs.add("org" + orgRoleDto.getOrgId());
            roles.add("role" + orgRoleDto.getRoleId());
        }
        mapResult.put("orgs", orgs);
        mapResult.put("roles", roles);
        return mapResult;
    }

    @Override
    public void batchUpdateUserRole(Map map) {
        dao.batchUpdateUserRole(map);
    }

    private List<OrgDto> getRoleOrgDtos(List<OrgDto> orgDtos, List<RoleDto> roles) {
        List<OrgDto> datas = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        for (OrgDto orgDto : orgDtos) {
            if ((orgDto.getParentId() == null ? 0 : orgDto.getParentId()) == 0 || IsRoot(orgDtos, orgDto.getParentId())) {
                OrgDto data = new OrgDto();
                data.setOrgId(orgDto.getOrgId());
                data.setOrgCode(orgDto.getOrgCode());
                data.setOrgName(orgDto.getOrgName());
                data.setParentId(orgDto.getParentId());
                data.setId("org" + orgDto.getOrgId());
                data.setName(orgDto.getOrgName());
                data.setCheck(orgDto.isCheck());
                buildRole(orgDtos, roles, data);
                datas.add(data);
            }
        }
        return datas;
    }

    @SuppressWarnings("unchecked")
    public void buildRole(List<OrgDto> orgDtos, List<RoleDto> roleList, OrgDto data) {
        Map<String, Object> map = getRoleChildren(orgDtos, roleList, data);
        List<RoleDto> roles = (List<RoleDto>) map.get("roles");
        List<RoleDto> lostroles = (List<RoleDto>) map.get("lostroles");
        List<OrgDto> childOrgs = (List<OrgDto>) map.get("children");
        Map<String, Object> childrenMap = Maps.newHashMap();
        childrenMap.put("childOrgs", childOrgs);
        childrenMap.put("roleList", roles);
        data.setChildren(childrenMap);
        for (OrgDto orgDto : childOrgs) {
            buildRole(orgDtos, lostroles, orgDto);
        }
    }

    private static Map<String, Object> getRoleChildren(List<OrgDto> orgDtos, List<RoleDto> roleList, OrgDto data) {
        List<OrgDto> children = new ArrayList<OrgDto>();
        List<RoleDto> roles = new ArrayList<RoleDto>();
        List<RoleDto> lostroles = new ArrayList<RoleDto>();
        Map<String, Object> map = Maps.newHashMap();
        for (OrgDto child : orgDtos) {
            if ((child.getParentId() == null ? 0 : child.getParentId().intValue()) == data.getOrgId().intValue()) {
                OrgDto orgDto = new OrgDto();
                orgDto.setOrgId(child.getOrgId());
                orgDto.setOrgCode(child.getOrgCode());
                orgDto.setOrgName(child.getOrgName());
                orgDto.setParentId(child.getParentId());
                orgDto.setId("org" + child.getOrgId());
                orgDto.setName(child.getOrgName());
                orgDto.setCheck(child.isCheck());
                children.add(orgDto);
            }
        }
        for (RoleDto roleDto : roleList) {
            if (data.getOrgId().equals(roleDto.getOrgId())) {
                roles.add(roleDto);
            } else {
                lostroles.add(roleDto);
            }
        }
        map.put("roles", roles);
        map.put("lostroles", lostroles);
        map.put("children", children);

        return map;
    }

    @Override
    public List<OrgDto> getOrgUserList(Map<String, Object> map) {
        List<UserDto> users = Lists.newArrayList();
        List<OrgDto> orgDtos = Lists.newArrayList();
        //调用两个接口，一个组织接口，一个用户接口
        com.usercenter.common.dto.UserDto dto = new com.usercenter.common.dto.UserDto();
        Map<Long, OrgInfoDto> orgInfoMap = new HashMap<>();
        if (!ObjectUtils.isEmpty(map.get("list"))) {
            List<UserOrgPermissionDto> list = (List<UserOrgPermissionDto>) map.get("list");
            Set<Long> set = new HashSet<>();
            for (UserOrgPermissionDto item : list) {
                if (item.getPermissionId() != null) {
                    set.add(Long.valueOf(item.getPermissionId()));
                }
            }
            dto.setOrgIdList(new ArrayList<>(set));
            BatchQueryParam param = new BatchQueryParam();
            param.setIds(set.stream().map(Long::valueOf).collect(Collectors.toList()));
            if (!ObjectUtils.isEmpty(map.get("orgName"))) {
                param.setOrgName((String) map.get("orgName"));
            }
            orgInfoMap = getOrgInfo(param);
        }
        dto.setState(StateEnum.ENABLE.getCode());
        dto.setManagerLevel(UserManagerLevelEnum.USER.getCode());
        if (!ObjectUtils.isEmpty(map.get("userName"))) {
            dto.setUserName(map.get("userName").toString());
        }
        List<FplUser> userList = userDomainService.findUserList(dto).getData();
        List<OrgUserDto> orgUserDtos = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userList)) {
            for (FplUser item : userList) {
                OrgUserDto orgUserDto = new OrgUserDto();
                orgUserDto.setOrgId(item.getOrgId());
                orgUserDto.setRealName(item.getRealName());
                orgUserDto.setUserCode(item.getUserCode());
                orgUserDto.setUserId(item.getId().longValue());
                orgUserDto.setUserName(item.getUserName());
                orgUserDto.setOrgCode(orgInfoMap.get(item.getOrgId()).getOrgCode());
                orgUserDto.setOrgName(orgInfoMap.get(item.getOrgId()).getOrgName());
                if (orgInfoMap.get(item.getOrgId()).getOrgType() == 4) {
                    orgUserDto.setParentId(orgInfoMap.get(item.getOrgId()).getParentBuId());
                } else {
                    orgUserDto.setParentId(orgInfoMap.get(item.getOrgId()).getParentId());
                }
                orgUserDtos.add(orgUserDto);
            }
        }

        List<OrgUserDto> chooseOrgUserDtos = dao.getRoleOrgUserList(map);
        //1.查询用户，查询组织
        List<Long> userIdList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(chooseOrgUserDtos)) {
            for (OrgUserDto item : chooseOrgUserDtos) {
                userIdList.add(item.getUserId());
            }
            Map<Integer, OrgUserDto> orgMap = getUserAndOrg(userIdList);
            List<OrgUserDto> tempList = new ArrayList<>();
            for (OrgUserDto item : chooseOrgUserDtos) {
                OrgUserDto orgUserDto = new OrgUserDto();
                orgUserDto = orgMap.get(item.getUserId().intValue());
                if (orgUserDto != null) {
                    tempList.add(orgUserDto);
                }
            }
            chooseOrgUserDtos.clear();
            chooseOrgUserDtos.addAll(tempList);
        }
        Map<String, Object> orgMap = Maps.newHashMap();
        List<BaseStaffDto> staffInfoDtos = Lists.newArrayList();
        try {
            BatchQueryParam queryParam = new BatchQueryParam();
            queryParam.setState(StateEnum.ENABLE.getCode());
            Set<Long> userIds = Sets.newHashSet();
            if (!ObjectUtils.isEmpty(orgUserDtos)) {
                orgUserDtos.stream().forEach(orgUserDto -> userIds.add(orgUserDto.getUserId()));
            }
            queryParam.setUserIds(new ArrayList<Long>(userIds));
            List<StaffInfoBean> staffInfoBeanList = staffMapper.batchQueryStaffInfo(queryParam, null, null);
            if (CollectionUtils.isNotEmpty(staffInfoBeanList)) {
                staffInfoDtos = HuToolUtil.exchange(staffInfoBeanList, BaseStaffDto.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        for (OrgUserDto orgUserDto : orgUserDtos) {
            if (!orgMap.containsKey("org" + orgUserDto.getOrgId())) {
                OrgDto orgDto = new OrgDto();
                orgDto.setOrgCode(orgUserDto.getOrgCode());
                orgDto.setOrgId(orgUserDto.getOrgId());
                orgDto.setParentId(orgUserDto.getParentId());
                orgDto.setOrgName(orgUserDto.getOrgName());
                orgDto.setKey("org" + orgUserDto.getOrgId());
                orgDto.setLabel(orgUserDto.getOrgName());
                Long orgId = orgUserDto.getOrgId();
                orgDtos.add(orgDto);
                orgMap.put("org" + orgUserDto.getOrgId(), orgUserDto.getOrgId());
            }
            if (!orgMap.containsKey("user" + orgUserDto.getUserId())) {
                UserDto userDto = new UserDto();
                Long userId = orgUserDto.getUserId() == null ? 0 : orgUserDto.getUserId();
                userDto.setUserId(orgUserDto.getUserId());
                userDto.setOrgId(orgUserDto.getOrgId());
                String realName = orgUserDto.getRealName();
                String departMentName = "";
                if (!ObjectUtils.isEmpty(staffInfoDtos)) {
                    for (BaseStaffDto staffInfoDto : staffInfoDtos) {
                        if (userId.equals(staffInfoDto.getUserId())) {
                            departMentName = staffInfoDto.getDepName();
                        }
                    }
                }

                if (StringUtils.isNotBlank(realName)) {
                    userDto.setUserName(orgUserDto.getUserName());
                    userDto.setLabel(orgUserDto.getUserName() + "(" + orgUserDto.getRealName() + ")");
                    if (StringUtils.isNotBlank(departMentName)) {
                        userDto.setLabel(orgUserDto.getUserName() + "(" + orgUserDto.getRealName() + "_" + departMentName + ")");
                    }
                } else {
                    userDto.setUserName(orgUserDto.getUserName());
                    userDto.setLabel(orgUserDto.getUserName());
                }
                userDto.setRealName(orgUserDto.getRealName());
                userDto.setKey("user" + orgUserDto.getUserId());

                long uId = orgUserDto.getUserId();
                boolean anyMatch = chooseOrgUserDtos.stream().anyMatch(item -> item.getUserId() == uId);
                LOGGER.info("已选中用户id={},是否匹配={}", orgUserDto.getUserId(), anyMatch);
                if (anyMatch) {
                    userDto.setCheck(true);
                } else {
                    userDto.setCheck(false);
                }
                users.add(userDto);
                orgMap.put("user" + orgUserDto.getUserId(), orgUserDto.getUserId());
            }
        }
        return getOrgDtos(orgDtos, users);
    }

    @Override
    public List<OrgDto> getRoleOrgUserList(Map<String, Object> map) {
        List<OrgUserDto> orgUserDtos = dao.getRoleOrgUserList(map);
        List<UserDto> users = Lists.newArrayList();
        List<OrgDto> orgDtos = Lists.newArrayList();
        Map<String, Object> orgMap = Maps.newHashMap();
        for (OrgUserDto orgUserDto : orgUserDtos) {
            if (!orgMap.containsKey(orgUserDto.getOrgId() + "")) {
                OrgDto orgDto = new OrgDto();
                orgDto.setOrgCode(orgUserDto.getOrgCode());
                orgDto.setOrgId(orgUserDto.getOrgId());
                orgDto.setParentId(orgUserDto.getParentId());
                orgDto.setOrgName(orgUserDto.getOrgName());
                orgDtos.add(orgDto);
            }
            UserDto userDto = new UserDto();
            userDto.setUserId(orgUserDto.getUserId());
            userDto.setOrgId(orgUserDto.getOrgId());
            userDto.setUserName(orgUserDto.getUserName());
            userDto.setRealName(orgUserDto.getRealName());
            users.add(userDto);
        }
        return getOrgDtos(orgDtos, users);
    }

    private List<OrgDto> getOrgDtos(List<OrgDto> orgDtos, List<UserDto> users) {
        List<OrgDto> datas = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        for (OrgDto orgDto : orgDtos) {
            if ((orgDto.getParentId() == null ? 0 : orgDto.getParentId()) == 0 || IsRoot(orgDtos, orgDto.getParentId())) {
                OrgDto data = new OrgDto();
                data.setOrgId(orgDto.getOrgId());
                data.setOrgCode(orgDto.getOrgCode());
                data.setOrgName(orgDto.getOrgName());
                data.setParentId(orgDto.getParentId());
                data.setKey("org" + orgDto.getOrgId());
                data.setLabel(orgDto.getOrgName());
                data.setCheck(orgDto.isCheck());
                build(orgDtos, users, data);
                datas.add(data);
            }
        }
        return datas;
    }

    /*
     * 是否起始节点
     */
    private boolean IsRoot(List<OrgDto> orgDtos, Long parentId) {

        for (OrgDto orgDto : orgDtos) {
            if ((orgDto.getOrgId() == null ? 0 : orgDto.getOrgId().intValue()) == (parentId == null ? 0 : parentId.intValue())) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public void build(List<OrgDto> orgDtos, List<UserDto> userList, OrgDto data) {
        Map<String, Object> map = getChildren(orgDtos, userList, data);
        List<UserDto> users = (List<UserDto>) map.get("users");
        List<UserDto> lostusers = (List<UserDto>) map.get("lostusers");
        List<OrgDto> childOrgs = (List<OrgDto>) map.get("children");
        Map<String, Object> childrenMap = Maps.newHashMap();
        childrenMap.put("childOrgs", childOrgs);
        childrenMap.put("userList", users);
        data.setChildren(childrenMap);
        for (OrgDto orgDto : childOrgs) {
            build(orgDtos, lostusers, orgDto);
        }
    }

    private static Map<String, Object> getChildren(List<OrgDto> orgDtos, List<UserDto> userList, OrgDto data) {
        List<OrgDto> children = new ArrayList<OrgDto>();
        List<UserDto> users = new ArrayList<UserDto>();
        List<UserDto> lostusers = new ArrayList<UserDto>();
        Map<String, Object> map = Maps.newHashMap();
        for (OrgDto child : orgDtos) {
            if ((child.getParentId() == null ? 0 : child.getParentId().intValue()) == data.getOrgId().intValue()) {
                OrgDto orgDto = new OrgDto();
                orgDto.setOrgId(child.getOrgId());
                orgDto.setOrgCode(child.getOrgCode());
                orgDto.setOrgName(child.getOrgName());
                orgDto.setParentId(child.getParentId());
                orgDto.setKey("org" + child.getOrgId());
                orgDto.setLabel(child.getOrgName());
                orgDto.setCheck(child.isCheck());
                children.add(orgDto);
            }

        }
        for (UserDto userDto : userList) {
            if (data.getOrgId().equals(userDto.getOrgId())) {
                users.add(userDto);
            } else {
                lostusers.add(userDto);
            }
        }
        map.put("users", users);
        map.put("lostusers", lostusers);
        map.put("children", children);
        return map;
    }

    @Override
    public RestMessage<String> batchUserRoleCreate(InputUserRoleDto inputUserRoleDto) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("userId", inputUserRoleDto.getUserId());
            paramMap.put("roleId", inputUserRoleDto.getRoleId());
            paramMap.put("groupId", inputUserRoleDto.getGroupId());
            paramMap.put("authorUser", inputUserRoleDto.getUserId());
            paramMap.put("authorTime", new Date());
            paramMap.put("state", ValidEnum.YES.getCode());
            List<UserListDto> list = inputUserRoleDto.getUserlist();
            paramMap.put("list", list);

            dao.deleteUserRoleCreate(paramMap);
            if (!ObjectUtils.isEmpty(list)) {
                dao.batchUserRoleCreate(paramMap);
            }
            //判断用户是否继承角色权限
            List<BatchUserPermissionDto> funcList = Lists.newArrayList();
            List<BatchUserPermissionDto> orgList = Lists.newArrayList();
            List<BatchUserPermissionDto> dataList = Lists.newArrayList();
            UserRoleDto userRoleDto = new UserRoleDto();
            userRoleDto.setRoleId(inputUserRoleDto.getRoleId());
            List<UserRoleDto> userRoleDtos = Lists.newArrayList();
            userRoleDtos.add(userRoleDto);
            Map map = Maps.newHashMap();
            map.put("list", userRoleDtos);
            for (UserListDto userListDto : list) {
                BasePermissionUserFunc basePermissionUserFunc = new BasePermissionUserFunc();
                basePermissionUserFunc.setUserId(userListDto.getUserId());
                basePermissionUserFunc.setState(ValidEnum.YES.getCode());
                List<BasePermissionUserFunc> basePermissionUserFuncs = basePermissionUserFuncMapper.getListBasePermissionUserFuncsByPOJO(basePermissionUserFunc);
                boolean isUserAuth = groupConfigHelper.isUserAuth(inputUserRoleDto.getGroupId());//是否取用户权限
                if (ObjectUtils.isEmpty(basePermissionUserFuncs) && !isUserAuth) {//只有取角色权限并且没有特殊权限时候，用户才继承当前角色权限
                    List<PermissionDto> permissionDtos = basePermissionRoleFuncMapper.findPermissionIdsByRole(map);
                    for (PermissionDto permissionDto : permissionDtos) {
                        if (PermissionTypeEnum.FUNC.getCode().equals(permissionDto.getPermissionType())) {
                            BatchUserPermissionDto batchUserPermissionDto = buildBatchUserPermissionDto(userListDto, permissionDto);
                            funcList.add(batchUserPermissionDto);
                        }
                        if (PermissionTypeEnum.ORG.getCode().equals(permissionDto.getPermissionType())) {
                            BatchUserPermissionDto batchUserPermissionDto = buildBatchUserPermissionDto(userListDto, permissionDto);
                            orgList.add(batchUserPermissionDto);
                        }
                        if (PermissionTypeEnum.DATA.getCode().equals(permissionDto.getPermissionType())) {
                            BatchUserPermissionDto batchUserPermissionDto = buildBatchUserPermissionDto(userListDto, permissionDto);
                            dataList.add(batchUserPermissionDto);
                        }

                    }

                }
            }
            map.clear();
            map.put("funcList", funcList);
            map.put("orgList", orgList);
            map.put("dataList", dataList);
            map.put("groupId", inputUserRoleDto.getGroupId());
            map.put("modifiedBy", inputUserRoleDto.getUserId());
            map.put("modifiedDate", new Date());
            map.put("createdBy", inputUserRoleDto.getUserId());
            map.put("createdDate", new Date());
            map.put("state", StateEnum.ENABLE.getCode());
            if (funcList.size() > 0 && orgList.size() > 0 && dataList.size() > 0) {
                basePermissionUserFuncMapper.batchUserFuncInsert(map);
            }
            return RestMessage.doSuccess(null);
        } catch (Exception e) {
            LOGGER.error("角色上批量分配用户出错，角色id-->" + inputUserRoleDto.getRoleId() + "},用户ids-->" + JSON.toJSONString(inputUserRoleDto.getUserlist()), e);
            throw PermissionErrorCode.ROLE_BATCH_USER_ERROR.throwError();
        }

    }


    @Transactional
    @Override
    public RestMessage batchSaveUserRole(BatchSaveUserRoleReq batchSaveUserRoleReq) {
        //保存
        List<BasePermissionUserRole> basePermissionUserRoleList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(batchSaveUserRoleReq.getUserRoleList())) {
            for (SaveUserRoleReq saveUserRoleReq : batchSaveUserRoleReq.getUserRoleList()) {
                //如果已经存在不再处理
                BasePermissionUserRole query = new BasePermissionUserRole();
                query.setGroupId(batchSaveUserRoleReq.getGroupId());
                query.setRoleId(batchSaveUserRoleReq.getRoleId());
                query.setUserId(saveUserRoleReq.getUserId());
                query.setOrgId(saveUserRoleReq.getOrgId());
                List<BasePermissionUserRole> roleUsers = dao.getListBasePermissionUserRolesByPOJO(query);
                if (!ObjectUtils.isEmpty(roleUsers)) {
                    continue;
                }
                BasePermissionUserRole basePermissionUserRole = new BasePermissionUserRole();
                BeanUtils.copyProperties(saveUserRoleReq, basePermissionUserRole);
                basePermissionUserRole.setRoleId(batchSaveUserRoleReq.getRoleId());
                basePermissionUserRole.setState(ValidEnum.YES.getCode());
                basePermissionUserRole.setGroupId(batchSaveUserRoleReq.getGroupId());
                basePermissionUserRole.setCreatedBy(batchSaveUserRoleReq.getAuthUserId());
                basePermissionUserRole.setCreatedName(batchSaveUserRoleReq.getAuthUserName());
                basePermissionUserRole.setCreatedDate(new Date());
                basePermissionUserRole.setModifiedBy(batchSaveUserRoleReq.getAuthUserId());
                basePermissionUserRole.setModifiedName(batchSaveUserRoleReq.getAuthUserName());
                basePermissionUserRole.setModifiedDate(new Date());
                basePermissionUserRole.setAuthorUser(batchSaveUserRoleReq.getAuthUserId());
                basePermissionUserRole.setAuthorTime(new Date());
                basePermissionUserRole.setRoleId(batchSaveUserRoleReq.getRoleId());
                basePermissionUserRoleList.add(basePermissionUserRole);
            }
        }
        dao.batchSaveUserRole(basePermissionUserRoleList);
        return RestMessage.doSuccess(null);
    }


    @Transactional
    @Override
    public RestMessage batchDeleteUserRole(BatchDeleteUserRoleReq batchDeleteUserRoleReq) {
        //保存
        dao.batchDeleteUserRole(batchDeleteUserRoleReq);
        return RestMessage.doSuccess(null);
    }


    private BatchUserPermissionDto buildBatchUserPermissionDto(UserListDto userListDto, PermissionDto permissionDto) {
        BatchUserPermissionDto batchUserPermissionDto = new BatchUserPermissionDto();
        batchUserPermissionDto.setUserId(userListDto.getUserId());
        batchUserPermissionDto.setAuthorTime(permissionDto.getAuthTime());
        batchUserPermissionDto.setAuthorUser(permissionDto.getAuthor());
        batchUserPermissionDto.setPermissionId(permissionDto.getPermissionId());
        return batchUserPermissionDto;
    }

    @Override
    public RestMessage<UserExpireDto> selectUserExpire(Long userId, Integer roleId, Long groupId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("roleId", roleId);
        map.put("groupId", groupId);
        UserExpireDto userExpireDto = dao.selectUserExpire(map);

        List<Long> userIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userExpireDto)) {
            userIds.add(userExpireDto.getUserId());
            Map<Integer, OrgUserDto> userAndOrg = getUserAndOrg(userIds);
            if (!ObjectUtils.isEmpty(userAndOrg)) {
                OrgUserDto userDto = userAndOrg.get(userId.intValue());
                userExpireDto.setOrgId(userDto.getOrgId());
                userExpireDto.setOrgName(userDto.getOrgName());
                userExpireDto.setUserName(userDto.getUserName());
                userExpireDto.setUserNumber(userDto.getUserCode());
            }
        }
        return RestMessage.doSuccess(userExpireDto);
    }

    /*
     * 获取用户下的角色列表
     */
    @Override
    public List<OutPutUserRoleDto> getUserRoleList(InputParentDto inputParentDto) {
        //获取用户角色
        List<OutPutUserRoleDto> userRoleList = dao.getUserRoleList(inputParentDto);
        //翻译
        if (!ObjectUtils.isEmpty(userRoleList)) {
            //用户id
            Long userId = userRoleList.get(0).getUserId();
            //角色组织id集合
            List<Long> orgIds = userRoleList.stream().map(userRole -> userRole.getOrgId()).collect(Collectors.toList());
            //授权人id集合
            Set<Long> authorIds = userRoleList.stream().map(userRole -> userRole.getAuthor()).collect(Collectors.toSet());
            //获取用户信息
            RestMessage<FplUser> userInfoRestMessage = userFeign.getUserInfo(userId);
            if (!userInfoRestMessage.isSuccess()) {
                LOGGER.error("获取用户信息失败，用户id{}，失败原因", userId, userInfoRestMessage.getMessage());
                throw new BizException(userInfoRestMessage.getMessage());
            }
            //授权人map
            RestMessage<Map<Long, FplUser>> userInfoMapRestMessage = userFeign.getUserInfoMap(authorIds);
            if (!userInfoMapRestMessage.isSuccess()) {
                LOGGER.error("获取用户map信息失败，用户id集合{}，失败原因", authorIds, userInfoMapRestMessage.getMessage());
                throw new BizException(userInfoMapRestMessage.getMessage());
            }
            //获取组织map
            BatchQueryParam param = new BatchQueryParam();
            param.setIds(orgIds);
            List<OrgInfoDto> orgInfoDtos = commonOrgMapper.batchQueryOrgInfo(param);
            Map<Long, OrgInfoDto> orgMap = Maps.newHashMap();
            orgInfoDtos.forEach(orgInfoDto -> orgMap.put(orgInfoDto.getId(), orgInfoDto));
            //翻译
            FplUser user = userInfoRestMessage.getData();
            Map<Long, FplUser> userInfoMap = userInfoMapRestMessage.getData();
            for (OutPutUserRoleDto outPutUserRoleDto : userRoleList) {
                if (user != null) {
                    outPutUserRoleDto.setUserName(user.getUserName());
                }
                FplUser author = userInfoMap.get(outPutUserRoleDto.getAuthor().intValue());
                if (author != null) {
                    outPutUserRoleDto.setAuthorUser(author.getUserName());
                }
                OrgInfoDto orgInfoDto = orgMap.get(outPutUserRoleDto.getOrgId());
                if (orgInfoDto != null) {
                    outPutUserRoleDto.setOrgName(orgInfoDto.getOrgName());
                }
            }
        }
        return userRoleList;
    }

    @Override
    public RestMessage<List<UserOrgPermissionDto>> getOrgTree(InputParentDto inputParentDto) {

        List<UserOrgPermissionDto> userOrgPermissionDtos = null;
        RestMessage<List<UserOrgPermissionDto>> result = new RestMessage<>();
        try {
            userOrgPermissionDtos = groupConfigHelper
                    .getUserOrgPermissionByStrategy(inputParentDto.getUserId(), inputParentDto.getGroupId(), "");
            userOrgPermissionDtos = basePermissionRoleService.getUserOrgPermissionsList(userOrgPermissionDtos);
            result.setData(userOrgPermissionDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<Long, OrgInfoDto> getOrgInfo(BatchQueryParam batchQueryParam) {
        List<OrgInfoDto> dtos = commonOrgMapper.batchQueryOrgInfo(batchQueryParam);
        if (CollectionUtils.isNotEmpty(dtos)) {
            Map<Long, OrgInfoDto> map = new HashMap<>();
            for (OrgInfoDto item : dtos) {
                map.put(item.getId(), item);
            }
            return map;
        } else {
            return null;
        }
    }

    private Map<Integer, OrgUserDto> getUserAndOrg(List<Long> userIds) {
        if (!ObjectUtils.isEmpty(userIds)) {
            Map<Integer, OrgUserDto> map = new HashMap<>();
            Set<Long> set = new HashSet<>();
            set.addAll(userIds);
            com.usercenter.common.dto.UserDto userQueryDTO = new com.usercenter.common.dto.UserDto();
            userQueryDTO.setIdSet(set);
            userQueryDTO.setManagerLevel(UserManagerLevelEnum.USER.getCode());
            RestMessage<List<FplUser>> userList = userDomainService.findUserList(userQueryDTO);
            List<FplUser> userDTOS = userList.getData();
            if (!ObjectUtils.isEmpty(userDTOS)) {
                Map<Long, OrgInfoDto> orgsMap = new HashMap<>();
                List<Long> orgIds = new ArrayList<>();
                for (FplUser item : userDTOS) {
                    orgIds.add(item.getOrgId());
                }
                if (orgIds.size() > 0) {
                    BatchQueryParam param = new BatchQueryParam();
                    param.setIds(orgIds);
                    List<OrgInfoDto> orgInfoDtos = commonOrgMapper.batchQueryOrgInfo(param);
                    if (!ObjectUtils.isEmpty(orgInfoDtos)) {
                        for (OrgInfoDto item : orgInfoDtos) {
                            orgsMap.put(item.getId(), item);
                        }
                    }
                }
                for (FplUser item : userDTOS) {
                    OrgUserDto orgUserDto = new OrgUserDto();
                    orgUserDto.setUserName(item.getUserName());
                    orgUserDto.setRealName(item.getRealName());
                    orgUserDto.setUserId(item.getId());
                    orgUserDto.setUserCode(item.getUserCode());
                    orgUserDto.setOrgId(item.getOrgId());
                    orgUserDto.setOrgName(orgsMap.get(item.getOrgId()).getOrgName());
                    orgUserDto.setOrgCode(orgsMap.get(item.getOrgId()).getOrgCode());
                    if (orgsMap.get(item.getOrgId()).getOrgType() == OrgTypeEnum.DEPARTMENT.getIndex()) {
                        orgUserDto.setParentId(orgsMap.get(item.getOrgId()).getParentBuId());
                    } else {
                        orgUserDto.setParentId(orgsMap.get(item.getOrgId()).getParentId());
                    }
                    map.put(orgUserDto.getUserId().intValue(), orgUserDto);

                }
                return map;
            }
        }
        return null;
    }

}
