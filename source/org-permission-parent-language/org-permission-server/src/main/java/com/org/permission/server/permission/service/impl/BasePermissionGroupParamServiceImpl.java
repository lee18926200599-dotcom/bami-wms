package com.org.permission.server.permission.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import com.org.permission.server.permission.dto.ResourceDto;
import com.org.permission.server.permission.entity.*;
import com.org.permission.server.permission.enums.*;
import com.org.permission.server.permission.mapper.*;
import com.org.permission.server.permission.vo.BasePermissionGroupParamVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.IBasePermissionGroupParamService;
import com.org.permission.server.permission.service.IBasePermissionManagementService;
import com.org.permission.server.permission.service.IBasePermissionTypeResourceService;
import com.common.base.enums.StateEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * base_permission_group_paramServiceImpl类 集团参数配置管理
 */
@Service
public class BasePermissionGroupParamServiceImpl implements IBasePermissionGroupParamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePermissionGroupParamServiceImpl.class);

    @Autowired
    private BasePermissionGroupParamMapper dao;
    @Autowired
    private BasePermissionDataMapper basePermissionDataMapper;
    @Autowired
    private BasePermissionGroupDataMapper basePermissionGroupDataMapper;
    @Autowired
    private IBasePermissionTypeResourceService basePermissionTypeResourceService;
    @Autowired
    private BasePermissionGroupResourceMapper basePermissionGroupResourceMapper;
    @Autowired
    private BasePermissionGroupManagementMapper basePermissionGroupManagementMapper;
    @Autowired
    private IBasePermissionManagementService bsePermissionManagementService;
    @Autowired
    private BasePermissionAdminGroupMapper basePermissionAdminGroupMapper;

    final static ListeningExecutorService initService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(50));

    public int addBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam) {
        return dao.addBasePermissionGroupParam(basePermissionGroupParam);
    }

    public int delBasePermissionGroupParam(Long id) {
        return dao.delBasePermissionGroupParam(id);
    }

    public int delBasePermissionGroupParamTrue(Long id) {
        return dao.delBasePermissionGroupParamTrue(id);
    }

    public int updateBasePermissionGroupParam(BasePermissionGroupParam basePermissionGroupParam) {
        return dao.updateBasePermissionGroupParam(basePermissionGroupParam);
    }

    public BasePermissionGroupParam getBasePermissionGroupParamById(Long id) {
        return dao.getBasePermissionGroupParamByID(id);
    }

    public int getBasePermissionGroupParamCount() {
        return dao.getBasePermissionGroupParamCount();
    }

    public int getBasePermissionGroupParamCountAll() {
        return dao.getBasePermissionGroupParamCountAll();
    }

    public List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPage(BasePermissionGroupParamVo basePermissionGroupParam) {
        return dao.getListBasePermissionGroupParamsByPage(basePermissionGroupParam);
    }

    public List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPOJO(BasePermissionGroupParam basePermissionGroupParam) {
        List<BasePermissionGroupParam> listBasePermissionGroupParamsByPOJO = dao.getListBasePermissionGroupParamsByPOJO(basePermissionGroupParam);
        return listBasePermissionGroupParamsByPOJO;
    }

    public List<BasePermissionGroupParam> getListBasePermissionGroupParamsByPojoPage(BasePermissionGroupParam basePermissionGroupParam) {
        Map map = new HashMap();
        map.put("pojo", basePermissionGroupParam);
        return dao.getListBasePermissionGroupParamsByPojoPage(map);
    }

    /*
     * 初始化集团时，需要初始化集团参数策略和数据权限
     */
    @Override
    public RestMessage initGroupPermission(Long userId, String userName, Long groupId, String... type) {
        long begin = System.currentTimeMillis();
        ListenableFuture initGroupParam;
        ListenableFuture initGroupDataPermission;
        ListenableFuture initGroupFuncPermission;
        ListenableFuture initGroupManagement;
        ListenableFuture initAdminGroup;
        try {
            initGroupParam = initService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    // 初始化集团参数策略
                    initGroupParam(userId,userName, groupId);
                    return 1;
                }
            });
            initGroupDataPermission = initService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    // 初始化数据权限
                    initGroupDataPermission(userId,userName, groupId);
                    return 1;
                }
            });
            initGroupFuncPermission = initService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    //初始化功能权限
                    initGroupFuncPermission(userId,userName, groupId, type[0]);
                    return 1;
                }
            });
            initGroupManagement = initService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    //初始化管理维度
                    initGroupManagement(userId,userName, groupId);
                    return 1;
                }
            });
            initAdminGroup = initService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    //初始化管理员和集团id的关系
                    //initGroupPermission(Long userId,Long groupId,String 集团的业务类型,String 集团管理员id,String 集团管理员名称,String 集团编码,String 集团名称)
                    initAdminGroup(userId,userName, groupId, type[1], type[2], type[3], type[4]);
                    return 1;
                }
            });
            final ListenableFuture allFutures = Futures.allAsList(initGroupParam, initGroupDataPermission, initGroupFuncPermission, initGroupManagement, initAdminGroup);
            final ListenableFuture transform = Futures.transformAsync(allFutures, new AsyncFunction<List, Boolean>() {
                @Override
                public ListenableFuture apply(List results) throws Exception {
                    Integer initGroupParamResult = (Integer) results.get(0);
                    Integer initGroupDataPermissionResult = (Integer) results.get(1);
                    Integer initGroupFuncPermissionResult = (Integer) results.get(2);
                    Integer initGroupManagementResult = (Integer) results.get(3);
                    Integer initAdminGroupResult = (Integer) results.get(4);
                    Integer result = initGroupParamResult + initGroupDataPermissionResult + initGroupFuncPermissionResult + initGroupManagementResult + initAdminGroupResult;
                    return Futures.immediateFuture(result);
                }
            });
            Integer dataResult = (Integer) transform.get();
            if (dataResult == 5) {
                RestMessage.doSuccess("初始化成功");
            } else {
                LOGGER.error("初始化集团参数和权限失败,集团id-->" + groupId);
                throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
            }
        } catch (Exception e) {
            LOGGER.error("初始化集团参数和权限异常,集团id-->" + groupId, e);
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }
        long end = System.currentTimeMillis();
        LOGGER.info("集团初始化总耗时---->" + (end - begin));
        return RestMessage.doSuccess("init success");
    }

    /**
     * @param groupId
     * @param s       集团管理员id
     * @param s1      集团管理员名称
     * @param s2      集团编码
     * @param s3      集团名称
     */
    private void initAdminGroup(Long userId,String userName, Long groupId, String s, String s1, String s2, String s3) {

        BasePermissionAdminGroup basePermissionAdminGroup = null;
        try {
            basePermissionAdminGroup = new BasePermissionAdminGroup();
            basePermissionAdminGroup.setGroupId(groupId);
            basePermissionAdminGroup.setAdminId(NumberUtils.toLong(s));
            basePermissionAdminGroup.setAdminName(s1);
            basePermissionAdminGroup.setGroupCode(s2);
            basePermissionAdminGroup.setGroupName(s3);
            basePermissionAdminGroup.setEffectiveTime(new Date());
            basePermissionAdminGroup.setCreatedDate(new Date());
            basePermissionAdminGroup.setCreatedBy(userId);
            basePermissionAdminGroup.setCreatedName(userName);
            basePermissionAdminGroup.setModifiedDate(new Date());
            basePermissionAdminGroup.setModifiedBy(userId);
            basePermissionAdminGroup.setModifiedName(userName);
            basePermissionAdminGroup.setState(StateEnum.ENABLE.getCode());
            if (groupId > 0 && (NumberUtils.toInt(s)) > 0) {
                basePermissionAdminGroupMapper.addBasePermissionAdminGroup(basePermissionAdminGroup);
            } else {
                LOGGER.info("无数据，初始化集团参数和集团管理员关系---->集团id::" + groupId + " 集团管理员id::" + s + " 集团管理员名称::" + s1 + " 集团编码::" + s2 + " 集团名称::" + s3);
            }
            LOGGER.info("初始化集团参数和集团管理员关系完成，集团id---->" + groupId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("初始化集团参数和集团管理员关系异常,集团id-->" + groupId, e);
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }


    }

    private void initGroupFuncPermission(Long userId,String userName, Long groupId, String type) {
        Map map = null;
        try {
            map = Maps.newHashMap();
            LOGGER.info("集团id获取业务类型,集团id{},业务类型{}", groupId, type);
            String[] strArray = StringUtils.split(type, ",");
            map.put("array", strArray);
            List<ResourceDto> funcs = basePermissionTypeResourceService.getPermissionByType(map);
            List<BasePermissionGroupResource> permissionGroupResourceList = new ArrayList<>();
            for (ResourceDto resourceDto : funcs) {
                BasePermissionGroupResource basePermissionGroupResource = new BasePermissionGroupResource();
                basePermissionGroupResource.setGroupId(groupId);
                basePermissionGroupResource.setPermissionId(resourceDto.getId());
                basePermissionGroupResource.setState(StateEnum.ENABLE.getCode());
                basePermissionGroupResource.setCreatedBy(userId);
                basePermissionGroupResource.setCreatedDate(new Date());
                basePermissionGroupResource.setCreatedName(userName);
                permissionGroupResourceList.add(basePermissionGroupResource);
            }
            if (!CollectionUtils.isEmpty(permissionGroupResourceList)) {
                basePermissionGroupResourceMapper.batchAddBasePermissionGroupResource(permissionGroupResourceList);
            } else {
                LOGGER.info("无数据，初始化集团功能权限", groupId, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("初始化集团功能权限失败，集团id---->" + groupId);
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }

    }


    private void initGroupManagement(Long userId,String userName, Long groupId) {
        BasePermissionManagement basePermissionManagement = null;
        try {
            basePermissionManagement = new BasePermissionManagement();
            basePermissionManagement.setState(ValidEnum.YES.getCode());
            List<BasePermissionManagement> basePermissionManagements = bsePermissionManagementService.getListBasePermissionManagementsByPOJO(basePermissionManagement);
            List<BasePermissionGroupManagement> permissionGroupManagementList = new ArrayList<>();
            for (BasePermissionManagement permissionManagement : basePermissionManagements) {
                BasePermissionGroupManagement permissionGroupManagement = new BasePermissionGroupManagement();
                permissionGroupManagement.setManagementId(permissionManagement.getId());
                permissionGroupManagement.setState(StateEnum.CREATE.getCode());
                permissionGroupManagement.setGroupId(groupId);
                permissionGroupManagement.setCreatedBy(userId);
                permissionGroupManagement.setCreatedDate(new Date());
                permissionGroupManagement.setCreatedName(userName);
                permissionGroupManagementList.add(permissionGroupManagement);
                LOGGER.info("无数据，初始化集团管理维度，集团id:{},维度:{}",groupId,permissionManagement.getName());
            }
            if (!CollectionUtils.isEmpty(permissionGroupManagementList)) {
                basePermissionGroupManagementMapper.insertList(permissionGroupManagementList);
            } else {
                LOGGER.info("无数据，初始化集团管理维度，集团id---->" + groupId);
            }
            LOGGER.info("初始化集团管理维度完成，集团id---->" + groupId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("初始化集团管理维度异常，集团id---->" + groupId);
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }
    }

    private void initGroupDataPermission(Long userId,String userName, Long groupId) {
        List<Integer> list = null;
        try {
            list = Lists.newArrayList();
            list.add(DataManagementEnum.REGION.getCode());//区域 所有集团共享
            list.add(DataManagementEnum.CUSTOMER.getCode());//客户 按集团
            list.add(DataManagementEnum.SUPPLIER.getCode());//供应商 按集团
            list.add(DataManagementEnum.WAREHOUSE.getCode());//客户 按集团
            list.add(DataManagementEnum.CRM_CUSTOMER.getCode());//CRM客户查看权限 所有集团共享
            list.add(DataManagementEnum.BUSINESS_LINE.getCode());//按业务线
            for (int i : list) {
                BasePermissionData basePermissionDataParam = new BasePermissionData();
                basePermissionDataParam.setState(ValidEnum.YES.getCode());
                basePermissionDataParam.setManagementId(i);
                if (i != DataManagementEnum.REGION.getCode() && i != DataManagementEnum.CRM_CUSTOMER.getCode()) {
                    basePermissionDataParam.setGroupId(groupId);
                }
                List<BasePermissionData> basePermissionDatas = basePermissionDataMapper.getListBasePermissionDatasByPOJO(basePermissionDataParam);
                List<BasePermissionGroupData> basePermissionGroupDataList = new ArrayList<>();
                for (BasePermissionData basePermissionData : basePermissionDatas) {
                    BasePermissionGroupData basePermissionGroupData = new BasePermissionGroupData();
                    basePermissionGroupData.setGroupId(groupId);
                    basePermissionGroupData.setState(StateEnum.ENABLE.getCode());
                    basePermissionGroupData.setCreatedBy(userId);
                    basePermissionGroupData.setCreatedDate(new Date());
                    basePermissionGroupData.setCreatedName(userName);
                    basePermissionGroupData.setDataId(basePermissionData.getId());
                    basePermissionGroupDataList.add(basePermissionGroupData);
                }
                if (!CollectionUtils.isEmpty(basePermissionGroupDataList)) {
                    basePermissionGroupDataMapper.batchAddBasePermissionGroupData(basePermissionGroupDataList);
                } else {
                    LOGGER.info("无数据，初始化集团数据权限，集团id---->" + groupId);
                }
            }
            LOGGER.info("初始化集团数据权限结束，集团id---->" + groupId);
        } catch (Exception e) {
            LOGGER.error("初始化集团数据权限异常，集团id---->" + groupId);
            e.printStackTrace();
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }
    }

    // 初始化参数
    private void initGroupParam(Long userId,String userName, Long groupId) {
        try {
            List<BasePermissionGroupParam> params = this.assmbleGroupParam(userId,userName, groupId);
            for (BasePermissionGroupParam groupParam : params) {
                dao.addBasePermissionGroupParam(groupParam);
            }
            LOGGER.info("初始化集团参数，集团id---->" + groupId);
        } catch (Exception e) {
            LOGGER.error("初始化集团参数失败，集团id---->" + groupId);
            e.printStackTrace();
            BasePermissionGroupParam userParam = null;
            userParam = new BasePermissionGroupParam();
            userParam.setGroupId(groupId);
            throw PermissionErrorCode.GROUP_INIT_ERROR.throwError();
        }
    }

    /**
     * 组装集团参数默认值
     *
     * @param userId
     * @param groupId
     */
    private List<BasePermissionGroupParam> assmbleGroupParam(Long userId,String userName, Long groupId) {
        List<BasePermissionGroupParam> params = new ArrayList<>();
        // 允许为用户授权
        BasePermissionGroupParam groupParam = null;
        groupParam = new BasePermissionGroupParam();
        groupParam.setGroupId(groupId);
        groupParam.setParamCode(GroupParamConfigEnum.USER_PARAM.value);
        groupParam.setParamName(GroupParamConfigEnum.USER_PARAM.name);
        groupParam.setRemark(GroupParamConfigEnum.USER_PARAM.remark);
        groupParam.setParamValue(GroupParamEnum.USER_PARAM.value);
        groupParam.setCreatedDate(new Date());
        groupParam.setCreatedBy(userId);
        groupParam.setCreatedName(userName);
        groupParam.setModifiedDate(new Date());
        groupParam.setModifiedBy(userId);
        groupParam.setState(ValidEnum.YES.getCode());
        params.add(groupParam);
        return params;
    }
}
