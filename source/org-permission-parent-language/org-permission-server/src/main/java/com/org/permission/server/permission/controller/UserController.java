package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.*;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.common.permission.dto.*;
import com.org.permission.common.permission.param.MenuButtonPermissionParam;
import com.org.permission.common.permission.param.PermissionUserParam;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.org.OrgDomainService;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.permission.dto.UserAndRoleOrgDto;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.entity.BasePermissionTypeDomain;
import com.org.permission.server.permission.entity.BasePermissionTypeResource;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.enums.ResourceTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.service.*;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.common.base.enums.StateEnum;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserDetail;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.common.enums.UserManagerLevelEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Api(tags = "1权限-权限(用户相关)接口文档")
@RequestMapping(value = "/permission-user")
@RestController
public class UserController {
    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private GroupConfigHelper groupConfigHelper;
    @Autowired
    private IBasePermissionRoleService basePermissionRoleService;
    @Resource
    private UserFeign userFeign;
    @Autowired
    private IBasePermissionTypeDomainService basePermissionTypeDomainService;
    @Autowired
    private IBasePermissionResourceService basePermissionResourceService;
    @Autowired
    private IBasePermissionAdminGroupService basePermissionAdminGroupService;
    @Autowired
    private IBasePermissionTypeResourceService basePermissionTypeResourceService;
    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(50));
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private OrgDomainService orgDomainService;

    @Value("${button.control.group.id:0}")
    private String buttonControlGroupId;
    @Value("${button.global.control:false}")
    private boolean buttonGlobalControl;

    // 根据用户id获取用户在所有集团中的组织权限id
    @ApiOperation(value = "根据用户id获取用户在所有集团中的组织权限id")
    @PostMapping(value = "/getOrgPermissions")
    public RestMessage<Set<Long>> getOrgPermissions(@RequestBody FplUser user) {
        Set<Long> set = Sets.newHashSet();
        try {
            List<UserAndRoleOrgDto> userOrgPermissions = groupConfigHelper.getUserAndRoleOrgs(user);
            set = userOrgPermissions.stream().map(UserAndRoleOrgDto::getOrgId).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(set);
    }

    // 根据用户id获取用户在所有集团中的组织权限id
    @ApiOperation(value = "根据用户id集合批量获取用户在所有集团中的组织权限id")
    @PostMapping(value = "/batchGetOrgPermissions")
    public RestMessage<Set<Long>> batchGetOrgPermissions(@RequestParam(value = "userIdSet") Set<Integer> userIdSet) {
        Set<Long> set = Sets.newHashSet();
        try {
            List<UserAndRoleOrgDto> userOrgPermissions = groupConfigHelper.batchGetUserAndRoleOrgs(userIdSet);
            set = userOrgPermissions.stream().map(UserAndRoleOrgDto::getOrgId).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(set);
    }

    // 根据用户Id查询,获取组织权限对应的集团信息，并且根据业务类型过滤集团信息
    @ApiOperation(value = " 根据用户Id查询,获取组织权限对应的集团信息，并且根据业务类型过滤集团信息")
    @PostMapping(value = "/getGroupsByOrgPermissions")
    public RestMessage<Set<Long>> getGroupsByPermissions(@RequestParam("userId") Long userId, @RequestParam("bussinessTypes") List<String> bussinessTypes) {
        //获取用户信息
        Set<Long> groupIdSet = Sets.newHashSet();
        RestMessage<List<UserDetail>> restMessage = userFeign.getDetailListByUserId(userId);
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        //不同的管理级别获取不同的组织权限的集团列表
        for (UserDetail user : restMessage.getData()) {
            Integer managerLevel = user.getManagerLevel();
            if (managerLevel.intValue() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
                GroupListQueryParam groupListQueryParam = new GroupListQueryParam();
                //获取所有集团
                groupListQueryParam.setBusinessType(bussinessTypes);
                List<OrgInfoDto> orgInfoDtoList = orgDomainService.queryAllGroupInfoList(groupListQueryParam).getList();
                if (CollectionUtils.isNotEmpty(orgInfoDtoList)) {
                    groupIdSet = orgInfoDtoList.stream().map(OrgInfoDto::getGroupId).collect(Collectors.toSet());
                }
            } else if (managerLevel.intValue() == UserManagerLevelEnum.GROUP_ADMINI.getCode()) {
                //获取有权限得集团列表
                BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
                basePermissionAdminGroup.setAdminId(userId);
                List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminGroupsByPOJO(basePermissionAdminGroup);
                List<Long> list = chooseGroupDtos.stream().map(BasePermissionAdminGroup::getGroupId).collect(Collectors.toList());
                List<Integer> orgTypes = Lists.newArrayList();
                orgTypes.add(OrgTypeEnum.GROUP.getIndex());
                BatchQueryParam batchQueryParam = new BatchQueryParam();
                batchQueryParam.setIds(list);
                batchQueryParam.setOrgTypes(orgTypes);
                batchQueryParam.setState(StateEnum.ENABLE.getCode());
                //需要去掉不合适的业务类型
                List<OrgInfoDto> orgInfoDtoList = orgDomainService.batchQueryOrgInfo(batchQueryParam);
                for (OrgInfoDto itm : orgInfoDtoList) {
                    String businessType = itm.getBusinessType();
                    if (businessType == null) {
                        return RestMessage.error("1001", I18nUtils.getMessage("org.common.param.grouptype.cannot.null"));
                    }
                    String[] strList = businessType.split(",");
                    for (String busType : bussinessTypes) {
                        for (String i : strList) {
                            if (i.equals(busType)) {
                                groupIdSet.add(itm.getGroupId());
                            }
                        }

                    }
                }
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
                    pesIds = new ArrayList<>(orgIdSet);
                }
                QueryGroupBUByFuncReqParam queryGroupBUByFuncReqParam = new QueryGroupBUByFuncReqParam();
                queryGroupBUByFuncReqParam.setUserId(Long.valueOf(user.getId()));
                queryGroupBUByFuncReqParam.setGroupId(user.getGroupId());
                queryGroupBUByFuncReqParam.setFlag(true);
                BatchQueryParam batchQueryParam = new BatchQueryParam();
                batchQueryParam.setIds(pesIds);
                List<Integer> orgTypes = new ArrayList<>();
                orgTypes.add(OrgTypeEnum.ORGANIZATION.getIndex());
                batchQueryParam.setOrgTypes(orgTypes);
                List<OrgInfoDto> groupInfoList = orgDomainService.queryGroupInfoByOrgIdsNoPage(batchQueryParam);
                for (OrgInfoDto itm : groupInfoList) {
                    String businessType = itm.getBusinessType();
                    String[] strList = businessType.split(",");
                    for (String i : strList) {
                        for (String j : bussinessTypes) {
                            if (j.equals(i)) {
                                groupIdSet.add(itm.getGroupId());
                            }
                        }
                    }
                }
            }

        }
        return RestMessage.doSuccess(groupIdSet);
    }


    // 根据用户id获取用户在当前切换集团中的组织权限id
    @ApiOperation(value = "根据用户id获取用户在当前切换集团中的组织权限id")
    @PostMapping(value = "/getOrgsByUidAndGroupId")
    public RestMessage<Set<Long>> getOrgsByUidAndGroupId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId) {
        Set set = Sets.newHashSet();
        try {
            List<UserAndRoleOrgDto> userOrgPermissions = groupConfigHelper.getOrgsByUidAndGroupId(userId, groupId);
            set = userOrgPermissions.stream().map(UserAndRoleOrgDto::getOrgId).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(set);
    }

    @ApiOperation(value = "根据用户id获取用户在当前集团中的组织权限id（包含管理员相关判断）")
    @PostMapping(value = "/getOrgIdListByUidAndGroupId")
    public RestMessage<Set<Long>> getOrgIdListByUidAndGroupId(@RequestBody PermissionUserParam param) {
        Set set = Sets.newHashSet();
        if (Objects.isNull(param.getUserId()) || Objects.isNull(param.getGroupId())) {
            return RestMessage.querySuccess(set);
        }
        try {
            List<UserOrgPermissionDto> userOrgPermissions = groupConfigHelper.getUserOrgPermissionByStrategy(param.getUserId(), param.getGroupId(), null);
            set = userOrgPermissions.stream().map(UserOrgPermissionDto::getPermissionId).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(set);
    }

    @ApiOperation(value = "根据用户id获取用户权限(包含功能权限，组织权限，数据权限)", httpMethod = "POST")
    @ApiImplicitParam(name = "param", value = "根据用户id获取用户权限参数", paramType = "body", required = true, dataType = "PermissionUserParam")
    @PostMapping(value = "/getPermissionByUid")
    public RestMessage<UserAllPermissionDto> getPermissionByUid(@RequestBody PermissionUserParam param) {
        Long userId = param.getUserId();
        Long groupId = param.getGroupId();
        Long domainId = param.getDomainId();
        String source = param.getSource();
        Integer sourceType = SourceTypeEnum.parser(source).getCode();
        FplUser user = param.getUser();
        LOG.info("登录传参----->" + user.getId() + "---身份类型" + user.getManagerLevel());
        Map<String, Object> map = Maps.newHashMap();
        UserAllPermissionDto userAllPermissionDto = new UserAllPermissionDto();
        ListenableFuture futurePermission;
        ListenableFuture futureOrg;
        ListenableFuture futureData;
        try {
            if (groupId.longValue() > 0) {
                //如果集团id不为空，走业务类型逻辑.
                //根据集团id获取业务类型
                String type = orgDomainService.queryGroupBusinessType(groupId);
                LOG.info("集团id获取业务类型,集团id{},业务类型{}", groupId, type);
                String[] strArray = StringUtils.split(type, ",");

                //根据业务类型获取域名id，并判断域名id是否存在，存在返回权限，否则提示无权限登录
                map.put("array", strArray);
                if (strArray.length == 0) {
                    LOG.info("集团未启用或集团没有配置业务类型", groupId);
                    return RestMessage.error("1001", I18nUtils.getMessage("permission.user.group.bussinesstype.null"));
                }
                List<BasePermissionTypeDomain> basePermissionTypeDomains = basePermissionTypeDomainService.getDomainByType(map);
                boolean anyMatch = basePermissionTypeDomains.stream().anyMatch(e -> domainId.intValue() == e.getDomainId().intValue());
                LOG.info("是否有匹配的域名{}，id值：{}", anyMatch, domainId);
                map.clear();
                if (!anyMatch) {
                    return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.match.domain"));
                } else {
                    futurePermission = service.submit(new Callable<Map<String, List<UserPermission>>>() {
                        public Map<String, List<UserPermission>> call() throws InterruptedException {
                            Map<String, List<UserPermission>> map = Maps.newHashMap();
                            List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", null, 0L, sourceType, 0L, user);
                            List<UserPermission> menuPermissions = userPermissions.stream().filter(item -> Objects.equals(item.getResourceType(), ResourceTypeEnum.MENU.getCode())).collect(Collectors.toList());
                            menuPermissions = basePermissionRoleService.getUserFuncPermissionList(menuPermissions, 0L);
                            map.put("all", userPermissions);
                            map.put("menu", menuPermissions);
                            return map;
                        }
                    });


                    futureOrg = service.submit(new Callable<List<UserOrgPermissionDto>>() {
                        public List<UserOrgPermissionDto> call() throws Exception {
                            List<UserOrgPermissionDto> userOrgPermissions = Lists.newArrayList();
                            userOrgPermissions = groupConfigHelper.getUserOrgPermissionByStrategy(userId, groupId, "", user, true);
                            userOrgPermissions = basePermissionRoleService.getUserOrgPermissionsList(userOrgPermissions);
                            return userOrgPermissions;
                        }
                    });
                    futureData = service.submit(new Callable<List<UserDataPermissionDto>>() {
                        public List<UserDataPermissionDto> call() throws InterruptedException {
                            List<UserDataPermissionDto> userDataPermissionDtos = Lists.newArrayList();
                            userDataPermissionDtos = groupConfigHelper.getLoginUserDataPermissionByStrategy(userId, groupId, 0, "", "", "", user);
                            userDataPermissionDtos = basePermissionRoleService.getUserDataPermissionsList(userDataPermissionDtos);
                            return userDataPermissionDtos;
                        }
                    });

                    final ListenableFuture allFutures = Futures.allAsList(futurePermission, futureOrg, futureData);

                    final ListenableFuture transform = Futures.transformAsync(allFutures, new AsyncFunction<List, Boolean>() {
                        @Override
                        public ListenableFuture apply(List results) throws Exception {
                            Map<String, List<UserPermission>> funMap = (Map<String, List<UserPermission>>) results.get(0);
                            List<UserPermission> funcs = funMap.get("menu");
                            List<UserPermission> allfuncs = funMap.get("all");
                            List<UserOrgPermissionDto> orgs = (List<UserOrgPermissionDto>) results.get(1);
                            List<UserDataPermissionDto> datas = (List<UserDataPermissionDto>) results.get(2);
                            userAllPermissionDto.setAllFunc(allfuncs);
                            userAllPermissionDto.setFunc(funcs);
                            userAllPermissionDto.setOrg(orgs);
                            userAllPermissionDto.setData(datas);
                            return Futures.immediateFuture(userAllPermissionDto);
                        }
                    });
                    return RestMessage.doSuccess((UserAllPermissionDto) transform.get());
                }
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.login.permission"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("登录获取权限失败");
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.login.get.permission.fail"));
        }

    }


    @ApiOperation(value = "根据token获取权限")
    @PostMapping(value = "/getPermissonsByToken")
    public RestMessage<UserAllPermissionDto> getPermissonsByToken(@RequestBody PermissionUserParam param) {
        Long userId = param.getUserId();
        Long groupId = param.getGroupId();
        Long domainId = param.getDomainId();
        SourceTypeEnum sourceTypeEnum = SourceTypeEnum.parser(param.getSource());
        Integer sourceType = sourceTypeEnum == null ? null : sourceTypeEnum.getCode();
        FplUser user = param.getUser();
        LOG.info("切换登录传参----->" + user.getId() + "---身份类型" + user.getManagerLevel());
        Map<String, Object> map = Maps.newHashMap();
        UserAllPermissionDto userAllPermissionDto = new UserAllPermissionDto();
        ListenableFuture futurePermission;
        ListenableFuture futureOrg;
        ListenableFuture futureData;
        try {
            if (groupId.longValue() > 0) {//如果集团id不为空，走业务类型逻辑.
                //根据集团id获取业务类型
                String type = orgDomainService.queryGroupBusinessType(groupId);
                String[] strArray = StringUtils.split(type, ",");
                futurePermission = service.submit(new Callable<Map<String, List<UserPermission>>>() {
                    public Map<String, List<UserPermission>> call() throws InterruptedException {
                        Map<String, List<UserPermission>> map = Maps.newHashMap();
                        List<UserPermission> userPermissions = Lists.newArrayList();
                        userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", sourceType, 0L, null, 0L, user);
                        List menuPermissions = userPermissions.stream().filter(item -> Objects.equals(item.getResourceType(), ResourceTypeEnum.MENU.getCode())).collect(Collectors.toList());
                        menuPermissions = basePermissionRoleService.getUserFuncPermissionList(menuPermissions, 0L);
                        map.put("all", userPermissions);
                        map.put("menu", menuPermissions);
                        return map;
                    }
                });

                futureOrg = service.submit(new Callable<List<UserOrgPermissionDto>>() {
                    public List<UserOrgPermissionDto> call() throws Exception {
                        List<UserOrgPermissionDto> userOrgPermissionDtos = Lists.newArrayList();
                        userOrgPermissionDtos = groupConfigHelper.getUserOrgPermissionByStrategy(userId, groupId, "", user, true);
                        userOrgPermissionDtos = basePermissionRoleService.getUserOrgPermissionsList(userOrgPermissionDtos);
                        return userOrgPermissionDtos;
                    }
                });
                futureData = service.submit(new Callable<List<UserDataPermissionDto>>() {
                    public List<UserDataPermissionDto> call() throws InterruptedException {
                        List<UserDataPermissionDto> userDataPermissionDtos = Lists.newArrayList();
                        userDataPermissionDtos = groupConfigHelper.getLoginUserDataPermissionByStrategy(userId, groupId, 0, "", "", "", user);
                        userDataPermissionDtos = basePermissionRoleService.getUserDataPermissionsList(userDataPermissionDtos);
                        return userDataPermissionDtos;
                    }
                });
                final ListenableFuture allFutures = Futures.allAsList(futurePermission, futureOrg, futureData);
                final ListenableFuture transform = Futures.transformAsync(allFutures, new AsyncFunction<List, Boolean>() {
                    @Override
                    public ListenableFuture apply(List results) throws Exception {
                        Map<String, List<UserPermission>> funMap = (Map<String, List<UserPermission>>) results.get(0);
                        List<UserPermission> funcs = funMap.get("menu");
                        List<UserPermission> allfuncs = funMap.get("all");
                        List<UserOrgPermissionDto> orgs = (List<UserOrgPermissionDto>) results.get(1);
                        List<UserDataPermissionDto> datas = (List<UserDataPermissionDto>) results.get(2);
                        userAllPermissionDto.setFunc(funcs);
                        userAllPermissionDto.setAllFunc(allfuncs);
                        userAllPermissionDto.setOrg(orgs);
                        userAllPermissionDto.setData(datas);

                        return Futures.immediateFuture(userAllPermissionDto);
                    }
                });
                return RestMessage.doSuccess((UserAllPermissionDto) transform.get());
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.login.permission"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("登录获取权限失败");
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.login.get.permission.fail"));
        }

    }


    @ApiOperation(value = "根据用户id获取用户菜单权限")
    @PostMapping(value = "/getMenus")
    public RestMessage<Map<String, Object>> getMenus(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Integer domainId) {
        Map<String, Object> map = Maps.newHashMap();
        List<UserPermission> userPermissions = Lists.newArrayList();
        try {
            if (groupId.longValue() > 0) {//如果集团id不为空，走业务类型逻辑.
                //根据集团id获取业务类型
                String type = orgDomainService.queryGroupBusinessType(groupId);
                LOG.info("集团id获取业务类型,集团id{},业务类型{}", groupId, type);
                String[] strArray = StringUtils.split(type, ",");
                //根据业务类型获取域名id，并判断域名id是否存在，存在返回权限，否则提示无权限登录
                map.put("array", strArray);
                List<BasePermissionTypeDomain> basePermissionTypeDomains = basePermissionTypeDomainService.getDomainByType(map);
                boolean anyMatch = basePermissionTypeDomains.stream().anyMatch(e -> domainId.intValue() == e.getDomainId().intValue());
                LOG.info("是否有匹配的域名{}，id值：{}", anyMatch, domainId);
                map.clear();
                if (!anyMatch) {
                    return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.match.domain"));
                } else {
                    userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", ResourceTypeEnum.MENU.getCode(), 0L, ResourceMenuTypeEnum.PC.getCode(), 0L);
                    List menuPermissions = basePermissionRoleService.getUserFuncPermissionList(userPermissions, 0L);
                    map.put("funcs", menuPermissions);
                    return RestMessage.doSuccess(map);
                }
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.login.permission"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("登录获取权限失败");
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.login.get.permission.fail"));
        }
    }

    @ApiOperation(value = "根据用户id、菜单ID再获取用户菜单下的子功能权限")
    @PostMapping(value = "/getPermissonsByMenuIdAndUid")
    public RestMessage<List<UserPermission>> getMenuPermissonsByUid(@RequestBody MenuButtonPermissionParam param) {
        List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategyMenuId(param.getUserId(), param.getGroupId(), "", null, 0L, null, param.getMenuId());
        return RestMessage.doSuccess(userPermissions);
    }

    // 根据用户id获取父级下的子菜单权限
    @ApiOperation(value = "根据用户id获取父级下的子菜单权限")
    @PostMapping(value = "/getPermissonsByParentIdAndUid")
    public RestMessage<List<UserPermission>> getPermissonsByParentIdAndUid(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Long parentId) {
        List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategy(userId, groupId, "", null, 0L, null, parentId);//代表获取子权限，不带层级结构
        userPermissions = basePermissionRoleService.getUserFuncPermissionListP(userPermissions, parentId);
        return RestMessage.doSuccess(userPermissions);
    }

    // 后台校验用户是否有权限
    @ApiOperation(value = "后台校验用户是否有权限")
    @PostMapping(value = "/checkPermissionById")
    public RestMessage checkPermissionById(@RequestParam Integer userId, @RequestParam Integer groupId, @RequestParam String token, @RequestParam Integer permissionId, @RequestParam String uri) {
        RestMessage<Map<String, Object>> permission = userFeign.getMenuPermissonsByToken(token);
        Map<String, Object> map = permission.getData();
        LOG.info("userapi get permission--->" + map);
        if (ObjectUtils.isEmpty(map)) {
            //缓存中没有，从数据库校验
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.permission"));
        } else {
            List<Map> userPermissions = (List<Map>) map.get("allfuncs");
            boolean anyMatch = userPermissions.stream().anyMatch(bean -> StringUtils.equals(uri, (bean.get("url") == null ? "" : bean.get("url").toString())) && permissionId.equals(NumberUtils.toInt(bean.get("permissionId") + "")));
            if (anyMatch) {
                return RestMessage.doSuccess(anyMatch);
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.permission"));
            }
        }
    }

    // 后台校验用户是否有权限
    @ApiOperation(value = "后台校验用户是否有权限根据权限标识")
    @PostMapping(value = "/checkPermissionByTag")
    public RestMessage checkPermissionByTag(@RequestParam Integer userId, @RequestParam Integer groupId, @RequestParam String token, @RequestParam String tag, @RequestParam String uri) {
        RestMessage<Map<String, Object>> permission = userFeign.getMenuPermissonsByToken(token);
        Map<String, Object> map = permission.getData();
        LOG.info("userapi get permission--->" + map);
        if (ObjectUtils.isEmpty(map)) {
            //缓存中没有，从数据库校验
            return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.permission"));
        } else {
            List<Map> userPermissions = (List<Map>) map.get("allfuncs");
            boolean anyMatch = userPermissions.stream().anyMatch(bean -> StringUtils.equals(uri, (bean.get("url") == null ? "" : bean.get("url").toString())) && tag.equals((bean.get("tag") == null ? "" : bean.get("tag").toString())));
            if (anyMatch) {
                return RestMessage.doSuccess(anyMatch);
            } else {
                return RestMessage.error("1001", I18nUtils.getMessage("permission.user.not.permission"));
            }
        }
    }

    /**
     * 递归获取缓存中的token对应的功能权限集合中是否
     *
     * @param flag
     * @param userPermissions
     * @param tag
     * @return
     */

    private int getPermissionTag(int flag, List<Map> userPermissions, String tag, String uri) {
        for (Map map : userPermissions) {
            String dbConfigUri = map.get("url") == null ? "" : map.get("url").toString();
            String dbConfigTag = map.get("tag") == null ? "" : map.get("tag").toString();
            if (StringUtils.equals(tag, dbConfigTag) && StringUtils.equals(uri, dbConfigUri)) {
                flag = 1;
                return flag;
            }
            List<Map> childFuncs = (List<Map>) map.get("childFuncs");
            if (!ObjectUtils.isEmpty(childFuncs)) {
                flag = getPermissionTag(flag, childFuncs, tag, uri);
            }
        }
        return flag;
    }

    // 后台根据权限tag获取权限id
    @ApiOperation(value = "后台根据权限tag获取权限id")
    @PostMapping(value = "/getPermissionByTag")
    public RestMessage getPermissionByTag(@RequestParam Integer userId, @RequestParam Integer groupId, @RequestParam String token, @RequestParam String tag) {
        //缓存中没有，从数据库校验
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setNumber(tag);
        List<BasePermissionResource> basePermissionResources = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        List list = basePermissionResources.stream().map(e -> {
            return e.getId();
        }).collect(Collectors.toList());
        return RestMessage.doSuccess(list);
    }


    // 按管理员id查集团列表
    @ApiOperation(value = "按管理员id查集团列表")
    @PostMapping(value = "/getGroupsById")
    public RestMessage<List<AdminGroupDto>> getGroupListByAdminId(@RequestParam Long userId) {
        BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
        basePermissionAdminGroup.setAdminId(userId);
        List<BasePermissionAdminGroup> chooseGroupDtos = basePermissionAdminGroupService.getListBasePermissionAdminGroupsByPOJO(basePermissionAdminGroup);
        List<Long> list = chooseGroupDtos.stream().map(BasePermissionAdminGroup::getGroupId).collect(Collectors.toList());
        List<Integer> orgTypes = Lists.newArrayList();
        List<OrgInfoDto> orgInfoDtos = Lists.newArrayList();
        orgTypes.add(OrgTypeEnum.GROUP.getIndex());
        try {
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(list);
            batchQueryParam.setOrgTypes(orgTypes);
            batchQueryParam.setState(StateEnum.ENABLE.getCode());
            orgInfoDtos = orgDomainService.batchQueryOrgInfo(batchQueryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long now = System.currentTimeMillis();
        //判断时间有效性   有效：过期时间为空，当前时间小于等于失效时间，当前时间大于等于生效时间
        List<BasePermissionAdminGroup> result = chooseGroupDtos.stream().filter(bpag -> ((ObjectUtils.isEmpty(bpag.getExpireTime())) || ((((bpag.getExpireTime() == null ? 0 : bpag.getExpireTime().getTime()) - now) >= 0) && ((now - bpag.getEffectiveTime().getTime()) >= 0)))).collect(Collectors.toList());
        //异步将失效的数据更新状态
        executorService.submit(new UpdateTask(chooseGroupDtos, result, basePermissionAdminGroupService));
        List<AdminGroupDto> groupList = Lists.newArrayList();
        for (BasePermissionAdminGroup bpag : result) {
            AdminGroupDto adminGroupDto = new AdminGroupDto();
            adminGroupDto.setGroupId(bpag.getGroupId());
            adminGroupDto.setId(bpag.getGroupId());
            if (CollectionUtils.isNotEmpty(orgInfoDtos)) {
                for (OrgInfoDto orgInfoDto : orgInfoDtos) {
                    if (bpag.getGroupId().equals(orgInfoDto.getGroupId())) {
                        adminGroupDto.setOrgCode(orgInfoDto.getOrgCode());
                        adminGroupDto.setOrgName(orgInfoDto.getOrgName());
                        adminGroupDto.setOrgType(orgInfoDto.getOrgType());//集团
                        adminGroupDto.setParentId(orgInfoDto.getParentId());//父级
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

    /**
     * 获取权限详情
     *
     * @param token
     * @param permissionId
     * @return
     */
    @ApiOperation(value = "获取权限详情")
    @PostMapping(value = "/getResourceDetail")
    public RestMessage getResourceDetail(@RequestParam String token, @RequestParam Long permissionId) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setId(permissionId);
        basePermissionResource.setState(ValidEnum.YES.getCode());
        List<BasePermissionResource> basePermissionResources = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        return RestMessage.doSuccess(basePermissionResources.get(0));
    }


    @ApiOperation(value = "根据权限ID获取业务类型域名功能权限资源数据")
    @PostMapping(value = "/getResourceBusinessType")
    public RestMessage getResourceBusinessType(@RequestParam String token, @RequestParam Long permissionId) {
        BasePermissionTypeResource basePermissbionTypeResource = new BasePermissionTypeResource();
        basePermissbionTypeResource.setPermissionId(permissionId);
        basePermissbionTypeResource.setState(ValidEnum.YES.getCode());
        List<BasePermissionTypeResource> basePermissbionTypeResources = basePermissionTypeResourceService.getListBasePermissionTypeResourcesByPOJO(basePermissbionTypeResource);
        return RestMessage.doSuccess(basePermissbionTypeResources);
    }


}
