package com.org.permission.server.permission.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.common.util.DateUtil;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.PermissionException;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.dto.param.RootBUListQueryParam;
import com.org.permission.server.org.enums.BusinessTypeEnum;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.CommonOrgMapper;
import com.org.permission.server.org.service.BusinessLineOrgService;
import com.org.permission.server.permission.dto.*;
import com.org.permission.server.permission.entity.BasePermissionGroupParam;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.enums.*;
import com.org.permission.server.permission.mapper.*;
import com.org.permission.server.utils.ListUtils;
import com.common.util.message.RestMessage;
import com.common.base.enums.StateEnum;
import com.common.framework.redis.RedisUtil;
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
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取集团策略
 */
@Service
public class GroupConfigHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupConfigHelper.class);

    @Autowired
    private BasePermissionGroupParamMapper basePermissionGroupParamMapper;
    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private BasePermissionRoleFuncMapper basePermissionRoleFuncMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonOrgMapper commonOrgMapper;
    @Autowired
    private BasePermissionGroupResourceMapper basePermissionGroupResourceMapper;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private BasePermissionResourceMapper basePermissionResourceMapper;

    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private OrgDomainService orgDomainService;
    @Autowired
    private BusinessLineOrgService businessLineOrgService;

    /*
     * 是否允许为用户直接授权
     */
    public boolean isUserAuth(Long groupId) {
        try {
            BasePermissionGroupParam basePermissionGroupParam = getBasePermissionGroupParam(groupId, GroupParamConfigEnum.USER_PARAM.value);
            String paramValue = basePermissionGroupParam.getParamValue();
            List<GroupParamValue> list = JSON.parseArray(paramValue, GroupParamValue.class);
            for (GroupParamValue groupParamValue : list) {
                if (groupParamValue.getName().equals(GroupParamEnum.USER_PARAM_YES.desc) && groupParamValue.getSelect().equals(GroupParamEnum.USER_PARAM_YES.value)) {
                    LOGGER.info("查询集团id{},允许为用户直接授权,并且使用用户的权限", groupId);
                    return true;
                }
                if (groupParamValue.getName().equals(GroupParamEnum.USER_PARAM_NO.desc) && groupParamValue.getSelect().equals(GroupParamEnum.USER_PARAM_YES.value)) {
                    LOGGER.info("查询集团id{},不允许为用户直接授权，不存在权限冲突", groupId);
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("查询集团id{},是否允许直接用户授权报错", groupId);
            e.printStackTrace();
            return true;
        }

    }

    /*
     * 根据参数编码和集团id获取策略对象
     */
    private BasePermissionGroupParam getBasePermissionGroupParam(Long groupId, String value) {
        BasePermissionGroupParam basePermissionGroupParam = new BasePermissionGroupParam();
        basePermissionGroupParam.setParamCode(GroupParamConfigEnum.USER_PARAM.value);
        basePermissionGroupParam.setGroupId(groupId);
        try {
            String key = "4pl_permission_group_param_" + groupId;
            String bpgpStr = (String) redisUtil.get(key);
            if (StringUtils.isNotBlank(bpgpStr)) {
                basePermissionGroupParam = JSON.parseObject(bpgpStr, BasePermissionGroupParam.class);
                return basePermissionGroupParam;
            } else {
                List<BasePermissionGroupParam> list = basePermissionGroupParamMapper.getListBasePermissionGroupParamsByPOJO(basePermissionGroupParam);
                if (CollectionUtils.isNotEmpty(list)) {
                    basePermissionGroupParam = list.get(0);
                    String basePermissionGroupParamStr = JSON.toJSONString(basePermissionGroupParam);
                    redisUtil.set(key, basePermissionGroupParamStr);
                } else {
                    basePermissionGroupParam.setParamValue(GroupParamEnum.USER_PARAM.getValue());
                    basePermissionGroupParam.setParamName(GroupParamConfigEnum.USER_PARAM.getName());
                }
                return basePermissionGroupParam;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return basePermissionGroupParam;
        }
    }

    /*
     * 获取用户功能权限
     */
    public List<UserPermission> getUserAuth(Long userId, Long groupId, String resourceName, Integer resourceType, Long belong, Integer type, Long menuId) {
        try {
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userId);
            userPermission.setGroupId(groupId);
            userPermission.setPermissionType(PermissionTypeEnum.FUNC.getCode());
            userPermission.setState(StateEnum.ENABLE.getCode());
            userPermission.setName(resourceName);
            userPermission.setBelong(belong);
            userPermission.setType(type);
            userPermission.setParentId(menuId);
            return basePermissionUserFuncMapper.getUserPermission(userPermission);
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},功能权限类型出错", userId, groupId);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /*
     * 获取用户组织权限
     */
    public List<UserOrgPermissionDto> getUserOrgAuth(Long userId, Long groupId, String orgName) {
        try {
            UserOrgPermissionDto userOrgPermissionDto = new UserOrgPermissionDto();
            userOrgPermissionDto.setUserId(userId);
            userOrgPermissionDto.setPermissionType(PermissionTypeEnum.ORG.getCode());
            userOrgPermissionDto.setGroupId(groupId);
            userOrgPermissionDto.setState(ValidEnum.YES.getCode());
            userOrgPermissionDto.setOrgName(orgName);
            LOGGER.info("获取用户组织权限查询条件：" + JSON.toJSONString(userOrgPermissionDto));
            List<UserOrgPermissionDto> list = basePermissionUserFuncMapper.getUserOrgPermission(userOrgPermissionDto);
            LOGGER.info("获取用户组织权限查询结果：" + JSON.toJSONString(list));
            if (!ObjectUtils.isEmpty(list)) {
                Set<Long> set = new HashSet<>();
                for (UserOrgPermissionDto item : list) {
                    set.add(item.getAuthor());
                }
                Map<Long, FplUser> infoMap = userDomainService.getUserInfoMap(set);
                for (UserOrgPermissionDto item : list) {
                    item.setAuthorUser(infoMap.get(item.getAuthor()).getUserName());
                }
            }
            LOGGER.info("获取用户组织权限返回结果：" + JSON.toJSONString(list));
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},组织权限类型出错", userId, groupId);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /*
     * 获取用户数据权限
     */
    public List<UserDataPermissionDto> getUserDataAuth(Long userId, Long groupId, Integer managementId, String distributionType, String based, String dataResource) {
        try {
            UserDataPermissionDto userDataPermissionDto = new UserDataPermissionDto();
            userDataPermissionDto.setUserId(userId);
            userDataPermissionDto.setPermissionType(PermissionTypeEnum.DATA.getCode());
            userDataPermissionDto.setGroupId(groupId);
            userDataPermissionDto.setState(ValidEnum.YES.getCode());
            userDataPermissionDto.setManagementId(managementId);
            userDataPermissionDto.setBased(based);
            userDataPermissionDto.setDistributionType(distributionType);
            userDataPermissionDto.setDataResource(dataResource);
            List<UserDataPermissionDto> list = basePermissionUserFuncMapper.getUserDataPermission(userDataPermissionDto);

            if (!ObjectUtils.isEmpty(list)) {
                Set<Long> set = new HashSet<>();
                for (UserDataPermissionDto item : list) {
                    set.add(item.getAuthor());
                }
                Map<Long, FplUser> infoMap = userDomainService.getUserInfoMap(set);
                for (UserDataPermissionDto item : list) {
                    item.setAuthorUser(infoMap.get(item.getAuthor()).getUserName());
                }
            }

            List<EnableUserDataPermission> enableList = basePermissionUserFuncMapper.getEnableUserDataPermission(userDataPermissionDto);
            for (EnableUserDataPermission enableUserDataPermission : enableList) {
                UserDataPermissionDto udp = new UserDataPermissionDto();
                udp.setBased(enableUserDataPermission.getBased());
                udp.setDataResource(enableUserDataPermission.getDataResource());
                udp.setDataResourceCode(enableUserDataPermission.getDataResourceCode());
                udp.setDistributionType(enableUserDataPermission.getDistributionType());
                udp.setGroupId(groupId);
                udp.setOrgId(enableUserDataPermission.getOrgId());
                udp.setManagementId(enableUserDataPermission.getManagementId());
                udp.setName(enableUserDataPermission.getName());
                udp.setOptionPermission(enableUserDataPermission.getOptionPermission());
                udp.setParentId(enableUserDataPermission.getParentId());
                udp.setPermissionId(enableUserDataPermission.getPermissionId());
                udp.setDataId(enableUserDataPermission.getDataId());
                udp.setGbCode(enableUserDataPermission.getGbCode());
                udp.setPermissionType(PermissionTypeEnum.DATA.getCode());
                udp.setState(enableUserDataPermission.getState());
                udp.setUserId(userId);
                udp.setFlag(0);
                list.add(udp);
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},数据权限类型出错{}", userId, groupId, e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    public List<UserDataPermissionDto> getOnlyRoleDataPermission(Long roleId, Long groupId, Integer managementId, String distributionType, String based, String dataResource) {
        try {
            Map map = Maps.newHashMap();
            map.put("roleId", roleId);
            map.put("groupId", groupId);
            map.put("managementId", managementId);
            map.put("distributionType", distributionType);
            map.put("based", based);
            map.put("dataResource", dataResource);
            map.put("permissionType", PermissionTypeEnum.DATA.getCode());
            UserDataPermissionDto userDataPermissionDto = new UserDataPermissionDto();
            userDataPermissionDto.setPermissionType(PermissionTypeEnum.DATA.getCode());
            userDataPermissionDto.setGroupId(groupId);
            userDataPermissionDto.setState(ValidEnum.YES.getCode());
            userDataPermissionDto.setManagementId(managementId);
            userDataPermissionDto.setBased(based);
            userDataPermissionDto.setDistributionType(distributionType);
            userDataPermissionDto.setDataResource(dataResource);
            List<UserDataPermissionDto> list = basePermissionUserFuncMapper.getOnlyRoleDataPermission(map);

            if (!ObjectUtils.isEmpty(list)) {
                Set<Long> set = new HashSet<>();
                for (UserDataPermissionDto item : list) {
                    set.add(item.getAuthor());
                }
                Map<Long, FplUser> infoMap = userDomainService.getUserInfoMap(set);
                for (UserDataPermissionDto item : list) {
                    item.setAuthorUser(infoMap.get(item.getAuthor()).getUserName());
                }
            }

            //站点权限数据排除用户数据权限
            if (managementId.equals(DataManagementEnum.SITE.getCode())) {
                return list;
            }
            List<EnableUserDataPermission> enableList = basePermissionUserFuncMapper.getEnableUserDataPermission(userDataPermissionDto);
            for (EnableUserDataPermission enableUserDataPermission : enableList) {
                UserDataPermissionDto udp = new UserDataPermissionDto();
                udp.setBased(enableUserDataPermission.getBased());
                udp.setDataResource(enableUserDataPermission.getDataResource());
                udp.setDistributionType(enableUserDataPermission.getDistributionType());
                udp.setGroupId(groupId);
                udp.setOrgId(enableUserDataPermission.getOrgId());
                udp.setManagementId(enableUserDataPermission.getManagementId());
                udp.setName(enableUserDataPermission.getName());
                udp.setOptionPermission(enableUserDataPermission.getOptionPermission());
                udp.setParentId(enableUserDataPermission.getParentId());
                udp.setPermissionId(enableUserDataPermission.getPermissionId());
                udp.setDataId(enableUserDataPermission.getDataId());
                udp.setGbCode(enableUserDataPermission.getGbCode());
                udp.setPermissionType(PermissionTypeEnum.DATA.getCode());
                udp.setState(enableUserDataPermission.getState());
                udp.setFlag(0);
                udp.setUserId(0L);
                list.add(udp);
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},数据权限类型出错{}", roleId, groupId, e.getMessage(), e);
            throw new PermissionException(-1, e.getMessage());
        }
    }

    /*
     * 获取角色功能权限
     */
    public List<UserPermission> getRoleAuth(Long userId, Long groupId, String resourceName, Integer resourceType, Long belong, Integer type, Long menuId) {
        try {
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userId);
            userPermission.setGroupId(groupId);
            userPermission.setPermissionType(PermissionTypeEnum.FUNC.getCode());
            userPermission.setState(ValidEnum.YES.getCode());
            userPermission.setName(resourceName);
            userPermission.setBelong(belong);
            userPermission.setType(type);
            userPermission.setParentId(menuId);
            List<UserPermission> list = basePermissionRoleFuncMapper.getRolePermission(userPermission);
            list = list.stream().filter(up -> DateUtil.validateTimeBetween(up.getEffectiveTime(), up.getExpireTime())).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},拥有角色功能权限类型出错{}", userId, groupId, e.getMessage(), e);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /*
     * 获取角色组织权限
     */
    public List<UserOrgPermissionDto> getRoleOrgAuth(Long userId, Long groupId, String orgName) {
        try {
            UserOrgPermissionDto userOrgPermissionDto = new UserOrgPermissionDto();
            userOrgPermissionDto.setUserId(userId);
            userOrgPermissionDto.setPermissionType(PermissionTypeEnum.ORG.getCode());
            userOrgPermissionDto.setGroupId(groupId);
            userOrgPermissionDto.setState(ValidEnum.YES.getCode());
            userOrgPermissionDto.setOrgName(orgName);
            List<UserOrgPermissionDto> list = basePermissionRoleFuncMapper.getRoleOrgPermission(userOrgPermissionDto);
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},拥有角色组织权限类型出错{}", userId, groupId, e.getMessage(), e);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /*
     * 获取角色数据权限
     */
    public List<UserDataPermissionDto> getRoleDataAuth(Long userId, Long groupId, Integer managementId, String distributionType, String based, String dataResource) {
        try {
            UserDataPermissionDto userDataPermissionDto = new UserDataPermissionDto();
            userDataPermissionDto.setUserId(userId);
            userDataPermissionDto.setPermissionType(PermissionTypeEnum.DATA.getCode());
            userDataPermissionDto.setGroupId(groupId);
            userDataPermissionDto.setState(ValidEnum.YES.getCode());
            userDataPermissionDto.setDataResource(dataResource);
            userDataPermissionDto.setManagementId(managementId);
            userDataPermissionDto.setBased(based);
            userDataPermissionDto.setDistributionType(distributionType);
            List<UserDataPermissionDto> list = basePermissionRoleFuncMapper.getRoleDataPermission(userDataPermissionDto);
            int siteManagementId = DataManagementEnum.SITE.getCode();
            //站点维度：获取角色的站点数据权限，暂时不用关系用户的数据权限，因为站点暂不考虑用户的数据权限
            if (siteManagementId == managementId) {
                return list;
            }
            List<EnableUserDataPermission> enableList = basePermissionUserFuncMapper.getEnableUserDataPermission(userDataPermissionDto);
            for (EnableUserDataPermission enableUserDataPermission : enableList) {
                UserDataPermissionDto udp = new UserDataPermissionDto();
                udp.setBased(enableUserDataPermission.getBased());
                udp.setDataResource(enableUserDataPermission.getDataResource());
                udp.setDataResourceCode(enableUserDataPermission.getDataResourceCode());
                udp.setDistributionType(enableUserDataPermission.getDistributionType());
                udp.setGroupId(groupId);
                udp.setManagementId(enableUserDataPermission.getManagementId());
                udp.setName(enableUserDataPermission.getName());
                udp.setOptionPermission(enableUserDataPermission.getOptionPermission());
                udp.setParentId(enableUserDataPermission.getParentId());
                udp.setPermissionId(enableUserDataPermission.getPermissionId());
                udp.setGbCode(enableUserDataPermission.getGbCode());
                udp.setDataId(enableUserDataPermission.getDataId());
                udp.setPermissionType(PermissionTypeEnum.DATA.getCode());
                udp.setState(enableUserDataPermission.getState());
                udp.setUserId(userId);
                udp.setOrgId(enableUserDataPermission.getOrgId());
                udp.setFlag(0);
                list.add(udp);
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户id{},集团id{},拥有角色数据权限类型出错{}", userId, groupId, e.getMessage(), e);
            throw new PermissionException(-1, e.getMessage());
        }
    }

    /**
     * 根据策略获取用户功能权限
     *
     * @param userId
     * @param groupId
     * @param resourceName
     * @param resourceType {@link ResourceTypeEnum}
     * @param belong
     * @param type         {@link ResourceMenuTypeEnum}
     * @param menuId
     * @param objects
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserPermission> getUserPermissionByStrategy(Long userId, Long groupId, String resourceName, Integer resourceType, Long belong, Integer type, Long menuId, Object... objects) {
        try {
            FplUser baseUser = getUserById(userId, objects);
            //权限取用户子表id
            Long userAuthId = baseUser.getId();
            if (isGroupAdmin(baseUser)) {
                //集团管理员获取整个集团下功能列表
                return buildAdminPermission(groupId, menuId, resourceName, resourceType, belong, type);
            } else if (isPlatformAdmin(baseUser)) {
                //平台管理员获取当前集团下的功能权限列表+平台运营的权限 TODO 平台运营菜单类型暂定
//                List<UserPermission> list = buildPlatformPermission(groupId);
                List<UserPermission> list = new ArrayList<>();
                List<UserPermission> adminList = buildAdminPermission(groupId, menuId, resourceName, resourceType, belong, type);
                list = ListUtils.union(list, adminList);
                return list;
            } else {//不是集团管理员，按照权限获取
                if (isUserAuth(groupId)) {
                    return getUserAuth(userAuthId, groupId, resourceName, resourceType, belong, type, menuId);
                } else {
                    return getRoleAuth(userAuthId, groupId, resourceName, resourceType, belong, type, menuId);
                }
            }

        } catch (Exception e) {
            LOGGER.error("根据策略获取功能权限出错，查询用户id{},集团id{}", userId, groupId);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /**
     * 获取用户对象
     *
     * @param userId
     * @return
     */
    public FplUser getUserById(Long userId, Object... objects) {
        FplUser baseUser = null;
        if (objects.length > 0) {
            baseUser = (FplUser) objects[0];
            LOGGER.info("用户登录传递user对象), userId------>" + baseUser.getId());
        } else {
            RestMessage<FplUser> restMessage = userFeign.getUserInfo(userId);
            if (!ObjectUtils.isEmpty(restMessage.getData())) {
                baseUser = restMessage.getData();
            } else {
                LOGGER.info("用户不存在或者调用用户服务出错userFeign.getUserInfo(userDto), userId------>" + userId);
            }
        }
        return baseUser;
    }

    /**
     * 获取平台管理员公共部分全选
     *
     * @param groupId
     * @return
     */
    private List<UserPermission> buildPlatformPermission(Long groupId) {
        List<UserPermission> result = Lists.newArrayList();
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setResourceType(1);
        basePermissionResource.setState(ValidEnum.YES.getCode());
        List<BasePermissionResource> list = basePermissionResourceMapper.getListBasePermissionResourcesByPOJO(basePermissionResource);
        for (BasePermissionResource bpr : list) {
            UserPermission userPermission = new UserPermission();
            BeanUtils.copyProperties(bpr, userPermission);
            userPermission.setGroupId(groupId);
            userPermission.setPermissionId(bpr.getId());
            result.add(userPermission);
        }

        return result;
    }

    /**
     * 获取集团管理员权限
     *
     * @param groupId
     * @param menuId
     * @param resourceName
     * @param resourceType 资源类型1=菜单 2=按钮
     * @param belong
     * @param type         0=PC 1=APP
     * @return
     */
    public List<UserPermission> buildAdminPermission(Long groupId, Long menuId, String resourceName, Integer resourceType, Long belong, Integer type) {
        List<UserPermission> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("groupId", groupId);
        map.put("parentId", menuId);
        map.put("resourceName", resourceName);
        map.put("resourceType", resourceType);
        map.put("belong", belong);
        map.put("type", type);
        map.put("state", StateEnum.ENABLE.getCode());
        List<GroupPermissionDto> groupPermissionDtos = basePermissionGroupResourceMapper.getGroupFuncTree(map);
        for (GroupPermissionDto groupPermissionDto : groupPermissionDtos) {
            //集团管理员的权限只获取集团上有效的菜单
            UserPermission userPermission = buildUserPermission(groupPermissionDto);
            list.add(userPermission);
        }
        return list;

    }

    /**
     * 根据菜单ID获取按钮权限
     *
     * @param userId
     * @param groupId
     * @param resourceName
     * @param resourceType
     * @param belong
     * @param type
     * @param menuId
     * @return
     */
    public List<UserPermission> getUserPermissionByStrategyMenuId(Long userId, Long groupId, String resourceName, Integer resourceType, Long belong, Integer type, Long menuId) {
        try {
            List<UserPermission> listAll = Lists.newArrayList();
            FplUser baseUser = getUserById(userId);
            if (isGroupAdmin(baseUser)) {//集团管理员获取整个集团下功能列表
                listAll = buildAdminPermission(groupId, menuId, resourceName, resourceType, belong, type);
            } else if (isPlatformAdmin(baseUser)) {
                //平台管理员获取当前集团下的功能权限列表+平台运营的权限
//                List<UserPermission> list_platform = buildPlatformPermission(groupId);
                listAll = buildAdminPermission(groupId, menuId, resourceName, resourceType, belong, type);
            } else {//不是集团管理员，按照权限获取
                if (isUserAuth(groupId)) {
                    listAll = getUserAuth(userId, groupId, resourceName, resourceType, belong, type, menuId);
                } else {
                    listAll = getRoleAuth(userId, groupId, resourceName, resourceType, belong, type, menuId);
                }
            }
            return listAll;
        } catch (Exception e) {
            LOGGER.error("根据策略获取功能权限出错，查询用户id{},集团id{}", userId, groupId);
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /**
     * 递归查子权限列表
     *
     * @param menuId
     * @param listAll
     * @param allResult
     * @return
     */
    private void getTreeList(Long menuId, List<UserPermission> listAll, List<UserPermission> allResult) {
        List<UserPermission> result = null;
        for (Iterator<UserPermission> it = listAll.iterator(); it.hasNext(); ) {
            UserPermission bean = it.next();
            int parentId = bean.getParentId() == null ? 0 : bean.getParentId().intValue();
            if (menuId == parentId) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(bean);
                it.remove();
            }
        }

        if (result != null) {
            allResult.addAll(result);
            for (UserPermission bean : result) {
                getTreeList(bean.getPermissionId(), listAll, allResult);
            }
        }
    }

    private UserPermission buildUserPermission(GroupPermissionDto groupPermissionDto) {
        UserPermission userPermission = new UserPermission();
        BeanUtils.copyProperties(groupPermissionDto, userPermission);
        userPermission.setControlDisplay(groupPermissionDto.getControlDisplay());
        return userPermission;
    }


    /**
     * 根据用户身份判断用户级别
     *
     * @param baseUser
     * @return
     */
    public boolean isGroupAdmin(FplUser baseUser) {
        int managerLevel = (baseUser.getManagerLevel() == null ? 0 : baseUser.getManagerLevel().intValue());
        if (managerLevel == 2) {
            return true;
        } else {
            return false;
        }
    }

    public List<UserAndRolePermissionDto> getRolePermissions(Map map) {
        return basePermissionUserFuncMapper.getRolePermissionByUidAndGroupId(map);
    }

    public List<UserAndRolePermissionDto> getUserPermissions(Map map) {
        return basePermissionUserFuncMapper.getUserPermissionsByUidAndGroupId(map);
    }

    /**
     * 是否平台管理员
     *
     * @param baseUser
     * @return
     */
    public boolean isPlatformAdmin(FplUser baseUser) {
        int managerLevel = (baseUser.getManagerLevel() == null ? 0 : baseUser.getManagerLevel().intValue());
        if (managerLevel == UserManagerLevelEnum.GLOBAL_ADMINI.getCode() || managerLevel == UserManagerLevelEnum.SUPER_ADMINI.getCode()) {
            return true;
        } else {
            return false;
        }
    }
    /*
     * 获取角色组织权限,先看用户是否集团管理员
     */

    public List<UserOrgPermissionDto> getUserOrgPermissionByStrategy(Long userId, Long groupId, String orgName, Object... objects) throws Exception {
        List<UserOrgPermissionDto> list = Lists.newArrayList();
        FplUser baseUser = getUserById(userId, objects);
        Long userAuthId = baseUser.getId();
        if (isGroupAdmin(baseUser) || isPlatformAdmin(baseUser)) {//集团管理员获取整个集团下列表
            // 根据集团id获取集团下所有组织列表
            List<OrgTreeBean> orgTreeBeans = commonOrgMapper.queryGroupNotStopOrgTreeByOrgId(groupId);
            Map<Long, List<Long>> lineMap = businessLineOrgService.getUsableLineByGroup(groupId);
            for (OrgTreeBean orgTreeBean : orgTreeBeans) {
                UserOrgPermissionDto userOrgPermission = new UserOrgPermissionDto();
                userOrgPermission.setUserId(userAuthId);
                userOrgPermission.setPermissionType(PermissionTypeEnum.ORG.getCode());
                userOrgPermission.setPermissionId(orgTreeBean.getId());
                userOrgPermission.setState(orgTreeBean.getState());
                if (orgTreeBean.getOrgType().equals(OrgTypeEnum.DEPARTMENT.getIndex())) {
                    userOrgPermission.setParentId(orgTreeBean.getParentBUId());
                } else {
                    userOrgPermission.setParentId(orgTreeBean.getParentId());
                }
                userOrgPermission.setGroupId(orgTreeBean.getGroupId());
                userOrgPermission.setOrgCode(orgTreeBean.getOrgCode());
                userOrgPermission.setOrgName(orgTreeBean.getOrgName());
                if (orgTreeBean.getOrgType().equals(OrgTypeEnum.GLOBAL.getIndex())) {//全局
                    userOrgPermission.setOrgType(OrgTypeEnum.GLOBAL.getName());
                    continue;
                } else if (orgTreeBean.getOrgType().equals(OrgTypeEnum.GROUP.getIndex())) {//集团
                    userOrgPermission.setOrgType(OrgTypeEnum.GROUP.getName());
                    continue;
                } else if (orgTreeBean.getOrgType().equals(OrgTypeEnum.ORGANIZATION.getIndex())) {
                    userOrgPermission.setOrgType(OrgTypeEnum.ORGANIZATION.getName());
                } else if (orgTreeBean.getOrgType().equals(OrgTypeEnum.DEPARTMENT.getIndex())) {
                    userOrgPermission.setOrgType(OrgTypeEnum.DEPARTMENT.getName());
                }
                userOrgPermission.setLineIdList(lineMap.get(orgTreeBean.getId()));
                list.add(userOrgPermission);
            }
            if (queryEnableData(objects)) {
                return filterEnableData(list);
            }
            return list;
        } else {//不是集团管理员，按照权限获取
            if (isUserAuth(groupId)) {
                list = getUserOrgAuth(userAuthId, groupId, orgName);
            } else {
                list = getRoleOrgAuth(userAuthId, groupId, orgName);
            }
            Map<Long, List<Long>> lineMap = businessLineOrgService.getUsableLineByGroup(groupId);
            list.forEach(org -> {
                org.setLineIdList(lineMap.get(org.getPermissionId()));
            });
            return list;
        }
    }

    private List<UserOrgPermissionDto> filterEnableData(List<UserOrgPermissionDto> userOrgPermissionDtoList) {
        if (ObjectUtils.isEmpty(userOrgPermissionDtoList)) {
            return Lists.newArrayList();
        }
        return userOrgPermissionDtoList.stream().filter(item -> item.getState() != null && OrgStateEnum.ENABLE.getCode() == item.getState()).collect(Collectors.toList());
    }

    /**
     * 是否查询启用的数据
     *
     * @return
     */
    private Boolean queryEnableData(Object... objects) {
        if (ObjectUtils.isEmpty(objects)) {
            return false;
        }
        if (objects.length < 2) {
            return false;
        }
        return (Boolean) objects[1];
    }


    public List<UserAndRoleOrgDto> getUserAndRoleOrgs(FplUser user) {
        Map map = Maps.newHashMap();
        map.put("userId", user.getId());
        return basePermissionUserFuncMapper.getUserAndRoleOrgs(map);
    }

    public List<UserAndRoleOrgDto> batchGetUserAndRoleOrgs(Set<Integer> userIdSet) {
        return basePermissionUserFuncMapper.batchGetUserAndRoleOrgs(userIdSet);
    }

    public List<UserAndRoleOrgDto> getOrgsByUidAndGroupId(Long userId, Long groupId) {

        Map map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("groupId", groupId);
        if (isUserAuth(groupId)) {
            return basePermissionUserFuncMapper.getUserOrgsByUidAndGroupId(map);
        } else {
            return basePermissionUserFuncMapper.getRoleOrgsByUidAndGroupId(map);
        }

    }

    /**
     * 页面 平台管理员身份 获取所属组织接口
     *
     * @param userId
     * @param groupId
     * @return
     * @throws Exception
     */
    public List<UserOrgPermissionDto> getPlatformOrgPermission(long userId, Long groupId) throws Exception {
        List<UserOrgPermissionDto> list = Lists.newArrayList();
        List<OrgInfoDto> orgInfoDtos = orgDomainService.queryRootBUInfoList(new RootBUListQueryParam());
        //  查询根业务单元
        for (OrgInfoDto orgInfoDto : orgInfoDtos) {
            UserOrgPermissionDto userOrgPermissionDto = new UserOrgPermissionDto();
            userOrgPermissionDto.setUserId(userId);
            userOrgPermissionDto.setPermissionType(PermissionTypeEnum.ORG.getCode());
            userOrgPermissionDto.setPermissionId(orgInfoDto.getId());
            userOrgPermissionDto.setParentId(orgInfoDto.getParentId());
            userOrgPermissionDto.setGroupId(orgInfoDto.getGroupId());
            userOrgPermissionDto.setOrgCode(orgInfoDto.getOrgCode());
            userOrgPermissionDto.setOrgName(orgInfoDto.getOrgName());
            if (orgInfoDto.getOrgType().equals(OrgTypeEnum.GLOBAL.getCode())) {//全局
                userOrgPermissionDto.setOrgType(OrgTypeEnum.GLOBAL.getName());
                continue;
            } else if (orgInfoDto.getOrgType().equals(OrgTypeEnum.GROUP.getCode())) {//集团
                userOrgPermissionDto.setOrgType(OrgTypeEnum.GROUP.getName());
                continue;
            } else if (orgInfoDto.getOrgType().equals(OrgTypeEnum.ORGANIZATION.getCode())) {
                userOrgPermissionDto.setOrgType(OrgTypeEnum.ORGANIZATION.getName());
            } else if (orgInfoDto.getOrgType().equals(OrgTypeEnum.DEPARTMENT.getCode())) {
                userOrgPermissionDto.setOrgType(OrgTypeEnum.DEPARTMENT.getName());
            }
            list.add(userOrgPermissionDto);
        }
        return list;
    }


    /*
     * 获取角色数据权限
     */
    public List<UserDataPermissionDto> getUserDataPermissionByStrategy(Long userId, Long groupId, Integer managementId, String distributionType, String based, String dataResource, Object... objects) {
        try {
            FplUser baseUser = getUserById(userId, objects);
            Long userAuthId = baseUser.getId();
            List<UserDataPermissionDto> list = Lists.newArrayList();
            if (isGroupAdmin(baseUser) || isPlatformAdmin(baseUser)) {//集团管理员获取整个集团下的数据权限
                Map<String, Object> map = Maps.newHashMap();
                map.put("managementId", managementId);
                map.put("distributionType", distributionType);
                map.put("based", based);
                map.put("dataResource", dataResource);
                int siteManagementId = DataManagementEnum.SITE.getCode();
                //站点维度：平台可以查看所有的站点权限数据 目前不需要
                if (siteManagementId == managementId && this.isGroupPlatformAdmin(groupId)) {
                    //平台可以获取所有集团的站点权限数据
                    list = basePermissionUserFuncMapper.getPlatGroupDataPermission(map);
                } else {
                    // 根据集团id获取集团下所有数据权限列表
                    map.put("groupId", groupId);
                    list = basePermissionUserFuncMapper.getGroupDataPermission(map);
                }
            } else {//不是集团管理员，按照权限获取
                if (isUserAuth(groupId)) {
                    list = getUserDataAuth(userAuthId, groupId, managementId, distributionType, based, dataResource);
                } else {
                    list = getRoleDataAuth(userAuthId, groupId, managementId, distributionType, based, dataResource);
                }
            }
            List<UserDataPermissionDto> result = Lists.newArrayList();
            List<UserDataPermissionDto> warehouseList = Lists.newArrayList();

            List<UserDataPermissionDto> warehouseResult = Lists.newArrayList();
            //仓库数据权限,需要根据组织权限过虑
            // 业务线权限，需按照组织上关联的业务线数据重新按组织和权限的交集组装数据
            for (UserDataPermissionDto udp : list) {
                if (udp.getManagementId().equals(DataManagementEnum.WAREHOUSE.getCode())) {
                    warehouseList.add(udp);
                }else {
                    result.add(udp);
                }
            }
            if (warehouseList != null && warehouseList.size() > 0) {
                List<UserOrgPermissionDto> orgPermissions = this.getUserOrgPermissionByStrategy(userId, groupId, "");
                for (UserDataPermissionDto userDataPermissionDto : warehouseList) {
                    boolean anyMatch = orgPermissions.stream().anyMatch(orgPermission -> Objects.equals(userDataPermissionDto.getOrgId(), orgPermission.getPermissionId()));
                    if (anyMatch) {
                        warehouseResult.add(userDataPermissionDto);
                    }
                }
            }
            result.addAll(warehouseResult);
            LOGGER.info("获取角色数据权限条数为{}", result.size());
            return result;
        } catch (Exception e) {
            LOGGER.error("根据策略获取数据权限出错，查询用户id{},集团id{},error{}", userId, groupId, e.getMessage(), e);
            throw new PermissionException(-1, e.getMessage());
        }
    }
    public List<UserDataPermissionDto> getLoginUserDataPermissionByStrategy(Long userId, Long groupId, Integer managementId, String distributionType, String based, String dataResource, Object... objects) {
        try {
            FplUser baseUser = getUserById(userId, objects);
            Long userAuthId = baseUser.getId();
            List<UserDataPermissionDto> list = Lists.newArrayList();
            if (isGroupAdmin(baseUser) || isPlatformAdmin(baseUser)) {//集团管理员获取整个集团下的数据权限
                Map<String, Object> map = Maps.newHashMap();
                map.put("managementId", managementId);
                map.put("distributionType", distributionType);
                map.put("based", based);
                map.put("dataResource", dataResource);
                int siteManagementId = DataManagementEnum.SITE.getCode();
                //站点维度：平台可以查看所有的站点权限数据 目前不需要
                if (siteManagementId == managementId && this.isGroupPlatformAdmin(groupId)) {
                    //平台可以获取所有集团的站点权限数据
                    list = basePermissionUserFuncMapper.getPlatGroupDataPermission(map);
                } else {
                    // 根据集团id获取集团下所有数据权限列表
                    map.put("groupId", groupId);
                    list = basePermissionUserFuncMapper.getGroupDataPermission(map);
                }
            } else {//不是集团管理员，按照权限获取
                if (isUserAuth(groupId)) {
                    list = getUserDataAuth(userAuthId, groupId, managementId, distributionType, based, dataResource);
                } else {
                    list = getRoleDataAuth(userAuthId, groupId, managementId, distributionType, based, dataResource);
                }
            }
            List<UserDataPermissionDto> result = Lists.newArrayList();
            List<UserDataPermissionDto> warehouseList = Lists.newArrayList();

            List<UserDataPermissionDto> warehouseResult = Lists.newArrayList();
            List<UserDataPermissionDto> blList = Lists.newArrayList();
            List<UserDataPermissionDto> blResultList = Lists.newArrayList();
            //仓库数据权限,需要根据组织权限过虑
            // 业务线权限，需按照组织上关联的业务线数据重新按组织和权限的交集组装数据
            for (UserDataPermissionDto udp : list) {
                if (udp.getManagementId().equals(DataManagementEnum.WAREHOUSE.getCode())) {
                    warehouseList.add(udp);
                } else if (udp.getManagementId().equals(DataManagementEnum.BUSINESS_LINE.getCode())) {
                    blList.add(udp);
                } else {
                    result.add(udp);
                }
            }
            if (warehouseList != null && warehouseList.size() > 0) {
                List<UserOrgPermissionDto> orgPermissions = this.getUserOrgPermissionByStrategy(userId, groupId, "");
                for (UserDataPermissionDto userDataPermissionDto : warehouseList) {
                    boolean anyMatch = orgPermissions.stream().anyMatch(orgPermission -> Objects.equals(userDataPermissionDto.getOrgId(), orgPermission.getPermissionId()));
                    if (anyMatch) {
                        warehouseResult.add(userDataPermissionDto);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(blList)) {
                List<UserOrgPermissionDto> orgPermissions = this.getUserOrgPermissionByStrategy(userId, groupId, "");
                Map<Long, UserDataPermissionDto> blMap = blList.stream().collect(Collectors.toMap(UserDataPermissionDto::getPermissionId, d -> d, (k1, k2) -> k2));
                for (UserOrgPermissionDto orgPermissionDto : orgPermissions) {
                    Long orgId = orgPermissionDto.getPermissionId();
                    List<Long> lineIdList = orgPermissionDto.getLineIdList();
                    if (CollectionUtils.isEmpty(lineIdList)){
                        continue;
                    }
                    for (Long lineId : lineIdList) {
                        UserDataPermissionDto userDataPermissionDto = blMap.get(lineId);
                        if (userDataPermissionDto != null) {
                            UserDataPermissionDto userData=new UserDataPermissionDto();
                            BeanUtils.copyProperties(userDataPermissionDto,userData);
                            userData.setOrgId(orgId);
                            blResultList.add(userData);
                        }
                    }

                }
            }
            result.addAll(warehouseResult);
            result.addAll(blResultList);
            LOGGER.info("获取角色数据权限条数为{}", result.size());
            return result;
        } catch (Exception e) {
            LOGGER.error("根据策略获取数据权限出错，查询用户id{},集团id{},error{}", userId, groupId, e.getMessage(), e);
            throw new PermissionException(-1, e.getMessage());
        }
    }

    /**
     * 判断集团是否是平台
     *
     * @param groupId
     * @return
     */
    private boolean isGroupPlatformAdmin(Long groupId) {
        //获取组织信息
        OrgInfoDto orgInfo = commonOrgMapper.getOrgInfoById(groupId);
        if (orgInfo == null || StringUtils.isBlank(orgInfo.getBusinessType())) {
            return false;
        }
        List<String> list = Arrays.asList(orgInfo.getBusinessType().split(","));
        for (String type : list) {
            if (BusinessTypeEnum.PLATFORM.getCode().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
