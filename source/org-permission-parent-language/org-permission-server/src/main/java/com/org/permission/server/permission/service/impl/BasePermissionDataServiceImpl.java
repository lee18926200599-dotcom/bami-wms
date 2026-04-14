package com.org.permission.server.permission.service.impl;


import com.common.language.util.I18nUtils;
import com.org.permission.server.permission.entity.BasePermissionGroupData;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.enums.DataManagementEnum;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.vo.BasePermissionDataVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.exception.PermissionException;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.server.permission.entity.BasePermissionData;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.mapper.BasePermissionDataMapper;
import com.org.permission.server.permission.mapper.BasePermissionGroupDataMapper;
import com.org.permission.server.permission.mapper.BasePermissionRoleFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.org.permission.server.permission.service.IBasePermissionDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据权限资源表管理
 */
@Slf4j
@Service
public class BasePermissionDataServiceImpl implements IBasePermissionDataService {
    @Autowired
    private BasePermissionDataMapper dao;
    @Autowired
    private BasePermissionGroupDataMapper basePermissionGroupDataMapper;
    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private BasePermissionRoleFuncMapper basePermissionRoleFuncMapper;


    public int addBasePermissionData(BasePermissionData base_permission_data) {
        return dao.addBasePermissionData(base_permission_data);
    }

    public int delBasePermissionData(Integer Id) {
        return dao.delBasePermissionData(Id);
    }

    public int delBasePermissionDataTrue(Integer Id) {
        return dao.delBasePermissionDataTrue(Id);
    }

    public int updateBasePermissionData(BasePermissionData base_permission_data) {
        return dao.updateBasePermissionData(base_permission_data);
    }

    public BasePermissionData getBasePermissionDataById(Integer Id) {
        return dao.getBasePermissionDataByID(Id);
    }

    public int getBasePermissionDataCount() {
        return dao.getBasePermissionDataCount();
    }

    public int getBasePermissionDataCountAll() {
        return dao.getBasePermissionDataCountAll();
    }

    public List<BasePermissionData> getListBasePermissionDatasByPage(BasePermissionDataVo basePermissionDataVo) {
        return dao.getListBasePermissionDatasByPage(basePermissionDataVo);
    }

    public List<BasePermissionData> getListBasePermissionDatasByPOJO(BasePermissionData base_permission_data) {
        return dao.getListBasePermissionDatasByPOJO(base_permission_data);
    }

    public List<BasePermissionData> getListBasePermissionDatasByPojoPage(BasePermissionData base_permission_data) {
        Map map = new HashMap();
        map.put("pojo", base_permission_data);
        return dao.getListBasePermissionDatasByPojoPage(map);
    }

    @Override
    public RestMessage syncwarehouse(SyncDataPermissionParam param) {
        int managementId = DataManagementEnum.WAREHOUSE.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(String.valueOf(param.getWarehouseId()));
        bpd.setManagementId(managementId);
        List<BasePermissionData> list = dao.getListBasePermissionDatasByPOJO(bpd);
        param.setUserId(param.getUserId() == null ? 0L : param.getUserId());
        BasePermissionData basePermissionData = new BasePermissionData();
        basePermissionData.setManagementId(managementId);
        basePermissionData.setDistributionType(distributionType);
        basePermissionData.setOrgId(param.getOrgId());
        basePermissionData.setGroupId(param.getGroupId());
        basePermissionData.setDataResourceId(String.valueOf(param.getWarehouseId()));
        basePermissionData.setDataResource(param.getWarehouseName());
        basePermissionData.setDataResourceCode(param.getWarehouseCode());
        basePermissionData.setOptionPermission(2);//1:查询 2:查询和维护
        basePermissionData.setParentId(-1L);//仓库 没有层级关系 默认都是根节点
        basePermissionData.setCreatedDate(new Date());
        basePermissionData.setCreatedBy(param.getUserId());//
        basePermissionData.setModifiedDate(new Date());
        basePermissionData.setModifiedBy(param.getUserId());
        basePermissionData.setState(param.getState());
        if (list.size() > 0) {
            basePermissionData.setId(list.get(0).getId());
            dao.updateBasePermissionData(basePermissionData);
        } else {
            dao.addBasePermissionData(basePermissionData);
        }
        if (param.getState() > 0) {
            createBasePermissionGroupData(basePermissionData, param.getUserId(), param.getGroupId(), null);
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    @Override
    public RestMessage site(Long userId, Long groupId, String siteId, String siteName, Long newGroupId) {

        int managementId = DataManagementEnum.SITE.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(siteId);
        bpd.setManagementId(managementId);
        bpd.setGroupId(groupId);
        bpd.setDistributionType(distributionType);
        List<BasePermissionData> list = dao.getListBasePermissionDatasByPOJO(bpd);
        BasePermissionData basePermissionData = new BasePermissionData();
        basePermissionData.setManagementId(managementId);
        basePermissionData.setDistributionType(distributionType);
        basePermissionData.setGroupId(groupId);
        basePermissionData.setDataResourceId(siteId);
        basePermissionData.setDataResource(siteName);
        basePermissionData.setOptionPermission(2);//1:查询 2:查询和维护
        basePermissionData.setParentId(-1L);//仓库 没有层级关系 默认都是根节点
        basePermissionData.setState(ValidEnum.YES.getCode());
        if ((newGroupId != null && newGroupId > 0 && CollectionUtils.isNotEmpty(list)) || CollectionUtils.isNotEmpty(list)) {
            if (newGroupId == null || newGroupId <= 0) {
                newGroupId = groupId;
            }
            basePermissionData.setId(list.get(0).getId());
            basePermissionData.setGroupId(newGroupId);
            basePermissionData.setModifiedDate(new Date());
            basePermissionData.setModifiedBy(NumberUtils.createLong(userId + ""));
            //删除角色站点的权限数据
            BasePermissionRoleFunc basePermRoleFunc = new BasePermissionRoleFunc();
            basePermRoleFunc.setPermissionId(basePermissionData.getId());
            basePermRoleFunc.setPermissionType(PermissionTypeEnum.DATA.getCode());
            basePermissionRoleFuncMapper.delDataByCondition(basePermRoleFunc);
            //删除用户站点的权限数据
            BasePermissionUserFunc basePermUserFunc = new BasePermissionUserFunc();
            basePermUserFunc.setPermissionId(basePermissionData.getId());
            basePermUserFunc.setPermissionType(PermissionTypeEnum.DATA.getCode());
            basePermissionUserFuncMapper.delDataByCondition(basePermUserFunc);
            boolean isUpdate = dao.updateBasePermissionData(basePermissionData) > 0;
            if (!isUpdate) {
                log.info("更新站点权限数据失败！站点siteId={}", siteId);
                throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
            }
        } else {
            basePermissionData.setCreatedDate(new Date());
            basePermissionData.setCreatedBy(NumberUtils.createLong(userId + ""));//
            basePermissionData.setModifiedDate(new Date());
            basePermissionData.setModifiedBy(NumberUtils.createLong(userId + ""));
            boolean isSave = dao.addBasePermissionData(basePermissionData) > 0;
            if (!isSave) {
                log.info("添加站点权限数据失败！站点siteId={}", siteId);
                throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
            }
        }
        createBasePermissionGroupData(basePermissionData, userId, groupId, newGroupId);

        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    @Override
    public RestMessage syncCustomer(Long userId, Long groupId, String dataId, String dataName, Long parentId, Integer type) {

        return RestMessage.doSuccess("客户维度数据权限，目前只包括业务类型，按个体和按区域屏蔽，接口暂时不处理数据");
    }

    @Override
    public RestMessage syncSupplier(Long userId, Long groupId, String supplierId, String supplierName) {
        int managementId = DataManagementEnum.SUPPLIER.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(supplierId);
        bpd.setManagementId(managementId);
        List<BasePermissionData> list = dao.getListBasePermissionDatasByPOJO(bpd);

        BasePermissionData basePermissionData = new BasePermissionData();
        basePermissionData.setManagementId(managementId);
        basePermissionData.setDistributionType(distributionType);
        basePermissionData.setGroupId(groupId);
        basePermissionData.setDataResourceId(supplierId);
        basePermissionData.setDataResource(supplierName);
        basePermissionData.setOptionPermission(2);//1:查询 2:查询和维护
        basePermissionData.setParentId(-1L);//仓库 没有层级关系 默认都是根节点
        basePermissionData.setCreatedDate(new Date());
        basePermissionData.setCreatedBy(NumberUtils.createLong(userId + ""));//
        basePermissionData.setModifiedDate(new Date());
        basePermissionData.setModifiedBy(NumberUtils.createLong(userId + ""));
        basePermissionData.setState(ValidEnum.YES.getCode());
        if (list.size() > 0) {
            basePermissionData.setId(list.get(0).getId());
            dao.updateBasePermissionData(basePermissionData);
        } else {
            dao.addBasePermissionData(basePermissionData);
        }
        createBasePermissionGroupData(basePermissionData, userId, groupId, null);
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    private void createBasePermissionGroupData(BasePermissionData basePermissionData, Long userId, Long groupId, Long newGroupId) {
        Long dataPermissionId = basePermissionData.getId();
        BasePermissionGroupData basePermissionGroupData = new BasePermissionGroupData();
        basePermissionGroupData.setGroupId(groupId);
        basePermissionGroupData.setDataId(dataPermissionId);
        List<BasePermissionGroupData> basePermissionGroupDatas = basePermissionGroupDataMapper.getListBasePermissionGroupDatasByPOJO(basePermissionGroupData);
        if (CollectionUtils.isEmpty(basePermissionGroupDatas)) {
            basePermissionGroupData.setCreatedDate(new Date());
            basePermissionGroupData.setCreatedBy(userId);
            basePermissionGroupData.setModifiedDate(new Date());
            basePermissionGroupData.setModifiedBy(userId);
            basePermissionGroupData.setState(ValidEnum.YES.getCode());
            boolean isAdd = basePermissionGroupDataMapper.addBasePermissionGroupData(basePermissionGroupData) > 0;
            if (!isAdd) {
                log.info("新增集团权限数据失败！各个业务线数据权限id={}", dataPermissionId);
                throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
            }

        } else if (newGroupId != null || basePermissionGroupDatas != null) {
            basePermissionGroupData.setId(basePermissionGroupDatas.get(0).getId());
            basePermissionGroupData.setGroupId(newGroupId);
            basePermissionGroupData.setModifiedDate(new Date());
            basePermissionGroupData.setModifiedBy(userId);
            basePermissionGroupData.setState(ValidEnum.YES.getCode());
            boolean isUpdate = basePermissionGroupDataMapper.updateBasePermissionGroupData(basePermissionGroupData) > 0;
            if (!isUpdate) {
                log.info("更新集团权限数据失败！各个业务线数据权限id={}", dataPermissionId);
                throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
            }
        }
    }

    @Override
    public RestMessage siteName(Long userId, Long groupId, String siteId, String siteName) {
        int managementId = DataManagementEnum.SITE.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(siteId);
        bpd.setManagementId(managementId);
        bpd.setGroupId(groupId);
        bpd.setDistributionType(distributionType);
        List<BasePermissionData> list = dao.getListBasePermissionDatasByPOJO(bpd);
        if (CollectionUtils.isEmpty(list)) {
            log.info("同步更新站点权限数据名称失败！站点在数据权限中没有数据。站点siteId={}", siteId);
            throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
        }
        BasePermissionData basePermissionData = list.get(0);
        BasePermissionData basePermissionDataUpdate = new BasePermissionData();
        basePermissionDataUpdate.setId(basePermissionData.getId());
        basePermissionDataUpdate.setDataResource(siteName);
        basePermissionDataUpdate.setModifiedDate(new Date());
        basePermissionDataUpdate.setModifiedBy(NumberUtils.createLong(userId + ""));
        boolean isUpdate = dao.updateBasePermissionData(basePermissionDataUpdate) > 0;
        if (!isUpdate) {
            log.info("更新站点权限数据失败！站点siteId={}", siteId);
            throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    /**
     * 同步业务线数据权限
     * @param param
     * @return
     */
    public RestMessage syncBusinessLine(SyncDataPermissionParam param) {
        int managementId = DataManagementEnum.BUSINESS_LINE.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(String.valueOf(param.getLineId()));
        bpd.setManagementId(managementId);
        bpd.setGroupId(param.getGroupId());
        List<BasePermissionData> list = dao.getListBasePermissionDatasByPOJO(bpd);

        BasePermissionData basePermissionData = new BasePermissionData();
        basePermissionData.setManagementId(managementId);
        basePermissionData.setDistributionType(distributionType);
//        basePermissionData.setOrgId(param.getOrgId());
        basePermissionData.setGroupId(param.getGroupId());
        basePermissionData.setDataResourceId(String.valueOf(param.getLineId()));
        basePermissionData.setDataResource(param.getLineName());
        basePermissionData.setDataResourceCode(param.getLineCode());
        basePermissionData.setOptionPermission(2);//1:查询 2:查询和维护
        basePermissionData.setParentId(0L);
        basePermissionData.setCreatedDate(new Date());
        basePermissionData.setCreatedBy(param.getUserId());//
        basePermissionData.setModifiedDate(new Date());
        basePermissionData.setModifiedBy(param.getUserId());
        basePermissionData.setState(param.getState());
        if (list.size() > 0) {
            basePermissionData.setId(list.get(0).getId());
            dao.updateBasePermissionData(basePermissionData);
        } else {
            dao.addBasePermissionData(basePermissionData);
        }
        if (param.getState() > 0) {
            createBasePermissionGroupData(basePermissionData, param.getUserId(), param.getGroupId(), null);
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

}

