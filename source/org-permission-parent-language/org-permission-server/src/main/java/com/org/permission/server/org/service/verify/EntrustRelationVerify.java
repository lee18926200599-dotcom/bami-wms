package com.org.permission.server.org.service.verify;


import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.mapper.*;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 委托关系重复验证
 */
@Service(value = "entrustRelationVerify")
public class EntrustRelationVerify {

    @Resource
    private MarketEntrustRelationMapper marketEntrustRelationMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;

    @Resource
    private LogisticsEntrustRelationMapper logisticsEntrustRelationMapper;

    @Resource
    private StoragePlatformEntrustRelationMapper storagePlatformEntrustRelationMapper;

    @Resource
    private StorageGroupEntrustRelationMapper storageGroupEntrustRelationMapper;

    @Resource
    private PurchaseEntrustRelationMapper purchaseEntrustRelationMapper;

    @Resource
    private SaleEntrustRelationMapper saleEntrustRelationMapper;

    /**
     * 采销委托关系重复验证
     *
     * @param newRel 新增采销委托关系
     */
    public void marketEntrustRelationVerify(final MarketEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<MarketEntrustRelationBean> oldRels = marketEntrustRelationMapper.queryMarketEntrustRelationByOwerLock(newRel.getOwnerId());
        if (CollectionUtils.isEmpty(oldRels)) {
            return;
        }

        addMarketEntrustRelationDefaultVerify(oldRels, newRel);

        marketEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 启用采销委托关系重复验证
     *
     * @param param 启用参数
     */
    public void enableMarketEntrustRelationVerify(final EnableOperateParam param) {
        final MarketEntrustRelationBean enableRelation = marketEntrustRelationMapper.queryEntrustRelationById(param.getId());
        if (Objects.isNull(enableRelation)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {
            return;
        }

        final List<MarketEntrustRelationBean> ownerAllRels = marketEntrustRelationMapper.queryMarketEntrustRelationByOwerLock(enableRelation.getOwnerId());

        if (ownerAllRels.size() == 1) {
            return;
        }

        if (Objects.equals(enableRelation.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.market.state.same"));
        }

        enableRelation.setState(param.getState());
        marketEntrustRelationStateVerify(ownerAllRels, enableRelation);
    }

    /**
     * 更新采销委托关系重复验证
     *
     * @param newRel 新增采销委托关系
     */
    public void updateMarketEntrustRelationVerify(final MarketEntrustRelationBean newRel) {
        final List<MarketEntrustRelationBean> oldRels = marketEntrustRelationMapper.queryMarketEntrustRelationByOwerLock(newRel.getOwnerId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前货主无任何委托关系
            return;
        }

        addMarketEntrustRelationDefaultVerify(oldRels, newRel);

        marketEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 采销委托关系默认状态验证
     *
     * @param oldRels 原采销委托关系
     * @param newRel  新增采销委托关系
     */
    private void addMarketEntrustRelationDefaultVerify(final List<MarketEntrustRelationBean> oldRels, final MarketEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        //货主+ 采销组织+仓储服务商，同时相同时只允许存在一条默认记录
        final Long newOwnerId = newRel.getOwnerId();
        final Long newMarketOrg = newRel.getMarketOrgId();
        final Long newWarehouseProvider = newRel.getWarehouseProviderId();
        final Long newId = newRel.getId();
        for (MarketEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            final Long oldOwnerId = oldRelation.getOwnerId();
            if (!Objects.equals(oldOwnerId, newOwnerId)) {
                continue;
            }

            final Long marketOrg = oldRelation.getMarketOrgId();
            if (!Objects.equals(newMarketOrg, marketOrg)) {
                continue;
            }

            final Long oldWarehouseProvider = oldRelation.getWarehouseProviderId();
            if (!Objects.equals(newWarehouseProvider, oldWarehouseProvider)) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.market.warehouse.default.state.same"));
        }
    }

    /**
     * 采销委托关系默认状态验证
     *
     * @param oldRels 原采销委托关系
     * @param newRel  新增采销委托关系
     */
    private void fillDefaultMarketEntrustRel(final List<MarketEntrustRelationBean> oldRels, final MarketEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        //货主+ 采销组织+仓储服务商，同时相同时只允许存在一条默认记录
        final Long newOwnerId = newRel.getOwnerId();
        final Long newMarketOrg = newRel.getMarketOrgId();
        final Long newWarehouseProvider = newRel.getWarehouseProviderId();
        final Long newId = newRel.getId();
        for (MarketEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            final Long oldOwnerId = oldRelation.getOwnerId();
            if (!Objects.equals(oldOwnerId, newOwnerId)) {
                continue;
            }

            final Long marketOrg = oldRelation.getMarketOrgId();
            if (!Objects.equals(newMarketOrg, marketOrg)) {
                continue;
            }

            final Long oldWarehouseProvider = oldRelation.getWarehouseProviderId();
            if (!Objects.equals(newWarehouseProvider, oldWarehouseProvider)) {
                continue;
            }

            newRel.setDefaultEntrustId(oldRelation.getId());

            break;
        }
    }

    /**
     * 采销启停状态验证
     *
     * @param oldRels 原采销委托关系
     * @param newRel  新增采销委托关系
     */
    private void marketEntrustRelationStateVerify(final List<MarketEntrustRelationBean> oldRels, final MarketEntrustRelationBean newRel) {
        if (newRel.getState() != StateEnum.ENABLE.getCode()) {
            return;
        }

        final Long newMarketOrg = newRel.getMarketOrgId();
        final Long newWarehouseProvider = newRel.getWarehouseProviderId();
        final Long newStockOrg = newRel.getStockOrgId();
        final Long newWarehouseId = newRel.getWarehouseId();
        final Long newId = newRel.getId();
        for (MarketEntrustRelationBean oldRelation : oldRels) {
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            final Integer oldStatus = oldRelation.getState();
            if (!Objects.equals(oldStatus, StateEnum.ENABLE.getCode())) {
                continue;
            }

            if (!Objects.equals(newMarketOrg, oldRelation.getMarketOrgId())) {
                continue;
            }

            if (!Objects.equals(newWarehouseProvider, oldRelation.getWarehouseProviderId())) {
                continue;
            }

            if (!Objects.equals(newStockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newWarehouseId, oldRelation.getWarehouseId())) {
                continue;
            }
        }
    }

    /**
     * 集团间仓储物流委托关系验证
     *
     * @param newRel 新增集团间仓储委托关系
     */
    public void platformStorageEntrustRelationVerify(final StoragePlatformEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<StoragePlatformEntrustRelationBean> oldRels = storagePlatformEntrustRelationMapper.queryStorageEntrustRelationByWarehouseProviderLock(newRel.getWarehouseProviderId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 仓储服务商无任何委托关系
            return;
        }

        // 默认状态校验
        addPlatformStorageEntrustRelationDefaultVerify(oldRels, newRel);

        // 启用状态校验
        platformStorageEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 集团间仓储物流委托关系验证
     *
     * @param param 启用参数
     */
    public void enablePlatformStorageEntrustRelationVerify(final EnableOperateParam param) {
        final StoragePlatformEntrustRelationBean enableRel = storagePlatformEntrustRelationMapper.queryEntrustRelationByIdLock(param.getId());
        if (Objects.isNull(enableRel)) {// 该委托关系不存在
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {
            return;
        }

        final List<StoragePlatformEntrustRelationBean> allWarehouseRels = storagePlatformEntrustRelationMapper.queryStorageEntrustRelationByWarehouseProviderLock(enableRel.getWarehouseProviderId());
        if (allWarehouseRels.size() == 1) {
            return;
        }

        if (Objects.equals(enableRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        enableRel.setState(param.getState());
        platformStorageEntrustRelationStateVerify(allWarehouseRels, enableRel);
    }

    /**
     * 集团间仓储物流委托关系验证
     *
     * @param newRel 新增集团间仓储委托关系
     */
    public void updatePlatformStorageEntrustRelationVerify(final StoragePlatformEntrustRelationBean newRel) {
        final List<StoragePlatformEntrustRelationBean> oldRels = storagePlatformEntrustRelationMapper.queryStorageEntrustRelationByWarehouseProviderLock(newRel.getWarehouseProviderId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前仓储服务商务任何委托关系
            return;
        }

        addPlatformStorageEntrustRelationDefaultVerify(oldRels, newRel);

        platformStorageEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 集团间仓储委托关系默认状态验证
     *
     * @param oldRels 原仓储委托关系
     * @param newRel  新增仓储委托关系
     */
    private void addPlatformStorageEntrustRelationDefaultVerify(final List<StoragePlatformEntrustRelationBean> oldRels, final StoragePlatformEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 仓储服务商+库存组织+仓库+物流服务商，同时相同时只允许存在一条默认记录
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newWarehouse = newRel.getWarehouseId();
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newId = newRel.getId();
        for (StoragePlatformEntrustRelationBean oldRelation : oldRels) {
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newWarehouse, oldRelation.getWarehouseId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.service.stock.warehouse.logistics.default.state.same"));
        }
    }

    /**
     * 集团间仓储委托关系默认状态验证
     *
     * @param oldRels 原仓储委托关系
     * @param newRel  新增仓储委托关系
     */
    private void fillPlatformDefaultStorageEntrustRel(final List<StoragePlatformEntrustRelationBean> oldRels, final StoragePlatformEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 仓储服务商+库存组织+仓库+物流服务商，同时相同时只允许存在一条默认记录
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newWarehouse = newRel.getWarehouseId();
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newId = newRel.getId();
        for (StoragePlatformEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newWarehouse, oldRelation.getWarehouseId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }

            newRel.setDefaultEntrustId(oldRelationId);

            break;
        }
    }

    /**
     * 集团间仓储启停状态验证
     *
     * @param oldRels 原集团间仓储委托关系
     * @param newRel  新增集团间仓储委托关系
     */
    private void platformStorageEntrustRelationStateVerify(final List<StoragePlatformEntrustRelationBean> oldRels, final StoragePlatformEntrustRelationBean newRel) {
        if (!Objects.equals(newRel.getState(), StateEnum.ENABLE.getCode())) {
            return;
        }

        // 仓储服务商+库存组织+仓库+物流服务商+物流组织+已启用，同时只允许存在一条都相同的记录
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newWarehouse = newRel.getWarehouseId();
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newId = newRel.getId();
        for (StoragePlatformEntrustRelationBean oldRelation : oldRels) {
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }
            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }
            if (!Objects.equals(newWarehouse, oldRelation.getWarehouseId())) {
                continue;
            }
            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }
            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.service.stock.warehouse.logistics.enable.state.same"));
        }
    }

    /**
     * 物流委托关系验证
     *
     * @param newRel 新增物流委托关系
     */
    public void logisticEntrustRelationVerify(final LogisticsEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<LogisticsEntrustRelationBean> oldRels = logisticsEntrustRelationMapper.queryLogisticEntrustRelationByLogisticProviderLock(newRel.getLogisticsProviderId());
        if (CollectionUtils.isEmpty(oldRels)) {
            return;
        }
        // 默认状态校验
        addLogisticEntrustRelationDefaultVerify(oldRels, newRel);
        // 启用状态校验
        logisticEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 物流委托关系验证
     *
     * @param newRel 新增物流委托关系
     */
    public void updateLogisticEntrustRelationVerify(final LogisticsEntrustRelationBean newRel) {
        final List<LogisticsEntrustRelationBean> oldRels = logisticsEntrustRelationMapper.queryLogisticEntrustRelationByLogisticProviderLock(newRel.getLogisticsProviderId());

        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前物流服务商务任何委托关系
            return;
        }

        // 默认状态校验
        addLogisticEntrustRelationDefaultVerify(oldRels, newRel);

        logisticEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 启用物流委托关系验证
     *
     * @param param 启用参数
     */
    public void enableLogisticEntrustRelationVerify(final EnableOperateParam param) {
        final LogisticsEntrustRelationBean enableRel = logisticsEntrustRelationMapper.queryEntrustRelationByIdLock(param.getId());
        if (Objects.isNull(enableRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {// 是否需要验证
            return;
        }

        final List<LogisticsEntrustRelationBean> allLogisticRels = logisticsEntrustRelationMapper.queryLogisticEntrustRelationByLogisticProviderLock(enableRel.getLogisticsProviderId());

        if (allLogisticRels.size() == 1) {
            return;
        }

        if (Objects.equals(enableRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        enableRel.setState(param.getState());
        logisticEntrustRelationStateVerify(allLogisticRels, enableRel);
    }

    /**
     * 物流默认状态验证
     *
     * @param oldRels 原采物流托关系
     * @param newRel  新物流委托关系
     */
    private void addLogisticEntrustRelationDefaultVerify(final List<LogisticsEntrustRelationBean> oldRels, final LogisticsEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 物流服务商+物流组织+物流服务商，同时相同时只允许存在一条默认记录
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newRelevanceLogisticsProvider = newRel.getRelevanceLogisticsProviderId();
        final Long newId = newRel.getId();
        for (LogisticsEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            if (!Objects.equals(newRelevanceLogisticsProvider, oldRelation.getRelevanceLogisticsProviderId())) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.logistics.org.default.state.same"));
        }
    }

    /**
     * 物流默认状态验证
     *
     * @param oldRels 原采物流托关系
     * @param newRel  新物流委托关系
     */
    private void fillDefaultLogisticEntrustRel(final List<LogisticsEntrustRelationBean> oldRels, final LogisticsEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 物流服务商+物流组织+物流服务商，同时相同时只允许存在一条默认记录
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newRelevanceLogisticsProvider = newRel.getRelevanceLogisticsProviderId();

        final Long newId = newRel.getId();
        for (LogisticsEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            if (!Objects.equals(newRelevanceLogisticsProvider, oldRelation.getRelevanceLogisticsProviderId())) {
                continue;
            }

            newRel.setDefaultEntrustId(oldRelation.getId());

            break;
        }
    }

    /**
     * 物流启停状态验证
     *
     * @param oldRels 原采物流托关系
     * @param newRel  新物流委托关系
     */
    private void logisticEntrustRelationStateVerify(final List<LogisticsEntrustRelationBean> oldRels, final LogisticsEntrustRelationBean newRel) {
        if (!Objects.equals(newRel.getState(), StateEnum.ENABLE.getCode())) {
            return;
        }
        // 物流服务商+物流组织+物流服务商+物流组织+已启用，同时只允许存在一条都相同的记录
        final Long newLogisticsProvider = newRel.getLogisticsProviderId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newRelevanceLogisticsProvider = newRel.getRelevanceLogisticsProviderId();
        final Long newRelevanceLogisticsOrg = newRel.getRelevanceLogisticsOrgId();
        final Long newId = newRel.getId();
        for (LogisticsEntrustRelationBean oldRelation : oldRels) {

            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (!Objects.equals(oldRelation.getState(), StateEnum.ENABLE.getCode())) {
                continue;
            }

            if (!Objects.equals(newLogisticsProvider, oldRelation.getLogisticsProviderId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            if (!Objects.equals(newRelevanceLogisticsProvider, oldRelation.getRelevanceLogisticsProviderId())) {
                continue;
            }
            if (!Objects.equals(newRelevanceLogisticsOrg, oldRelation.getRelevanceLogisticsOrgId())) {
                continue;
            }
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.logistics.org.enable.state.same"));
        }
    }

    /**
     * 采购委托关系重复验证
     *
     * @param newRel 新采购委托关系
     */
    public void purchaseEntrustRelationVerify(final PurchaseEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<PurchaseEntrustRelationBean> oldRels = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationByPurchaseOrgIdLock(newRel.getPurchaseOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前采购组织无任何委托关系
            return;
        }

        addPurchaseEntrustRelationDefaultVerify(oldRels, newRel);

        purchaseEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 采购委托关系重复验证
     *
     * @param param 启用参数
     */
    public PurchaseEntrustRelationBean enablePurchaseEntrustRelationVerify(final EnableOperateParam param) {
        final PurchaseEntrustRelationBean enableRel = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationByIdLock(param.getId());

        if (Objects.isNull(enableRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {
            return enableRel;
        }

        final List<PurchaseEntrustRelationBean> allPurchaseRels = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationByPurchaseOrgIdLock(enableRel.getPurchaseOrgId());
        if (allPurchaseRels.size() == 1) {
            return enableRel;
        }

        if (Objects.equals(enableRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        enableRel.setState(param.getState());
        purchaseEntrustRelationStateVerify(allPurchaseRels, enableRel);
        return enableRel;
    }

    /**
     * 采购委托关系重复验证
     *
     * @param newRel 新采购委托关系
     */
    public void updatePurchaseEntrustRelationVerify(final PurchaseEntrustRelationBean newRel) {
        final List<PurchaseEntrustRelationBean> oldRels = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationByPurchaseOrgIdLock(newRel.getPurchaseOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前采购组织无任何委托关系
            return;
        }

        addPurchaseEntrustRelationDefaultVerify(oldRels, newRel);

        purchaseEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 采购委托关系默认状态验证
     * <p>
     * 返回默认委托关系 ID(用于更新时使用)
     *
     * @param oldRels 原采销委托关系
     * @param newRel  新增采销委托关系
     */
    private void addPurchaseEntrustRelationDefaultVerify(final List<PurchaseEntrustRelationBean> oldRels, final PurchaseEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 采销组织+库存组织，同时相同时只允许存在一条默认记录
        final Long newPurchaseOrg = newRel.getPurchaseOrgId();
        final Long newStockOrgId = newRel.getStockOrgId();
        final Long newId = newRel.getId();
        for (PurchaseEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newPurchaseOrg, oldRelation.getPurchaseOrgId())) {
                continue;
            }

            final Long stockOrgId = oldRelation.getStockOrgId();
            if (!Objects.equals(newStockOrgId, stockOrgId)) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.market.stock.default.state.same"));
        }
    }

    private void fillDefaultPurchaseEntrustRel(final List<PurchaseEntrustRelationBean> oldRels, final PurchaseEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 采销组织+库存组织，同时相同时只允许存在一条默认记录
        final Long newPurchaseOrg = newRel.getPurchaseOrgId();
        final Long newStockOrgId = newRel.getStockOrgId();
        final Long newId = newRel.getId();
        for (PurchaseEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newPurchaseOrg, oldRelation.getPurchaseOrgId())) {
                continue;
            }

            if (!Objects.equals(newStockOrgId, oldRelation.getStockOrgId())) {
                continue;
            }
            newRel.setDefaultEntrustId(oldRelationId);
            break;
        }
    }

    /**
     * 采购启停状态验证
     *
     * @param oldRels 原采购委托关系
     * @param newRel  新采购委托关系
     */
    private void purchaseEntrustRelationStateVerify(final List<PurchaseEntrustRelationBean> oldRels, final PurchaseEntrustRelationBean newRel) {
        if (newRel.getState() != StateEnum.ENABLE.getCode()) {
            return;
        }

        // 采购组织+库存组织+已启用，同时只允许存在一条都相同的记录
        final Long newPurchaseOrg = newRel.getPurchaseOrgId();
        final Long newStockOrg = newRel.getStockOrgId();
        final Long newId = newRel.getId();
        for (PurchaseEntrustRelationBean oldRelation : oldRels) {
            final Integer oldStatus = oldRelation.getState();
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (!Objects.equals(oldStatus, StateEnum.ENABLE.getCode())) {
                continue;
            }

            if (!Objects.equals(newPurchaseOrg, oldRelation.getPurchaseOrgId())) {
                continue;
            }

            if (!Objects.equals(newStockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.market.stock.enable.state.same"));
        }
    }

    /**
     * 销售委托关系重复验证
     *
     * @param newRel 新销售委托关系
     */
    public void saleEntrustRelationVerify(final SaleEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {// 非默认 非启用状态 无需验证
            return;
        }

        final List<SaleEntrustRelationBean> oldRels = saleEntrustRelationMapper.querySaleEntrustRelationBySaleOrgIdLock(newRel.getSaleOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 该销售组织无任何委托关系
            return;
        }

        addSaleEntrustRelationDefaultVerify(oldRels, newRel);

        saleEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 销售委托关系重复验证
     *
     * @param param 启用参数
     */
    public SaleEntrustRelationBean enableSaleEntrustRelationVerify(final EnableOperateParam param) {
        final SaleEntrustRelationBean enableRel = saleEntrustRelationMapper.querySaleEntrustRelationByIdLock(param.getId());
        if (Objects.isNull(enableRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {
            return enableRel;
        }

        final List<SaleEntrustRelationBean> allSaleRels = saleEntrustRelationMapper.querySaleEntrustRelationBySaleOrgIdLock(enableRel.getSaleOrgId());
        if (allSaleRels.size() == 1) {
            return enableRel;
        }

        if (Objects.equals(enableRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        enableRel.setState(param.getState());
        saleEntrustRelationStateVerify(allSaleRels, enableRel);
        return enableRel;
    }

    /**
     * 更新销售委托关系重复验证
     *
     * @param newRel 新销售委托关系
     */
    public void updateSaleEntrustRelationVerify(final SaleEntrustRelationBean newRel) {
        final List<SaleEntrustRelationBean> oldRels = saleEntrustRelationMapper.querySaleEntrustRelationBySaleOrgIdLock(newRel.getSaleOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前销售组织任何委托关系
            return;
        }

        addSaleEntrustRelationDefaultVerify(oldRels, newRel);

        saleEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 销售委托关系默认状态验证
     *
     * @param oldRels 原销售委托关系集合
     * @param newRel  新销售委托关系
     */
    private void addSaleEntrustRelationDefaultVerify(final List<SaleEntrustRelationBean> oldRels, final SaleEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 销售组织+库存组织，同时相同时只允许存在一条默认记录；
        final Long newPurchaseSaleOrg = newRel.getSaleOrgId();
        final Long newStockOrg = newRel.getStockOrgId();
        final Long newId = newRel.getId();
        for (SaleEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {//
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newPurchaseSaleOrg, oldRelation.getSaleOrgId())) {
                continue;
            }
            if (!Objects.equals(newStockOrg, oldRelation.getStockOrgId())) {
                continue;
            }
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.sale.stock.default.state.same"));
        }
    }

    private void fillDefaultSaleEntrustRel(final List<SaleEntrustRelationBean> oldRels, final SaleEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 销售组织+库存组织，同时相同时只允许存在一条默认记录；
        final Long newPurchaseSaleOrg = newRel.getSaleOrgId();
        final Long newStockOrg = newRel.getStockOrgId();
        final Long newId = newRel.getId();
        for (SaleEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {//
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newPurchaseSaleOrg, oldRelation.getSaleOrgId())) {
                continue;
            }
            if (!Objects.equals(newStockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            newRel.setDefaultEntrustId(oldRelationId);

            break;
        }
    }

    /**
     * 销售启停状态验证
     *
     * @param oldSRelations 原销售委托关系
     * @param newRel        新销售委托关系
     */
    private void saleEntrustRelationStateVerify(final List<SaleEntrustRelationBean> oldSRelations, final SaleEntrustRelationBean newRel) {
        if (Objects.equals(newRel.getState(), StateEnum.CREATE.getCode())) {
            return;
        }

        // 销售组织+库存组织+已启用，同时只允许存在一条都相同的记录；
        final Long newSaleOrg = newRel.getSaleOrgId();
        final Long newStockOrg = newRel.getStockOrgId();// 非必填字段
        final Long newId = newRel.getId();
        for (SaleEntrustRelationBean oldRelation : oldSRelations) {

            if (oldRelation.getId().equals(newId)) {
                continue;
            }

            if (oldRelation.getState() != StateEnum.ENABLE.getCode()) {
                continue;
            }

            if (!newSaleOrg.equals(oldRelation.getSaleOrgId())) {
                continue;
            }

            final Long stockOrgId = oldRelation.getStockOrgId();
            if (!Objects.equals(newStockOrg, stockOrgId)) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.sale.stock.enable.state.same"));
        }
    }

    /**
     * 财务委托关系重复验证
     *
     * @param newRel 新财务委托关系
     */
    public void addFinanceEntrustRelationVerify(final FinanceEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<FinanceEntrustRelationBean> oldRels = financeEntrustRelationMapper.queryFinanceEntrustRelationByOrgIdLock(newRel.getBizOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            return;
        }

        financeEntrustRelationDefaultVerify(oldRels, newRel);
    }

    /**
     * 启用财务委托关系重复验证
     *
     * @param param 启用参数
     */
    public void enableFinanceEntrustRelationVerify(final EnableOperateParam param) {
        final List<FinanceEntrustRelationBean> oldRels = financeEntrustRelationMapper.queryBizOrgFinanceEntrustRelByEntrustIdLock(param.getId());

        if (CollectionUtils.isEmpty(oldRels)) {// 该组织无任何委托关系
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        FinanceEntrustRelationBean newRel = null;
        for (FinanceEntrustRelationBean oldRel : oldRels) {
            if (oldRel.getId().equals(param.getId())) {
                newRel = oldRel;
            }
        }
        if (Objects.isNull(newRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!OrgStateEnum.canOp(newRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        if (oldRels.size() == 1) {// 改委托关系对应的业务组织只有一个财务委托关系，随便玩
            return;
        }

        newRel.setState(param.getState());// 设置下一次状态
        financeEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 财务委托关系状态验证
     *
     * @param oldRels 原财务委托关系集合
     * @param newRel  新财务委托关系
     */
    private void financeEntrustRelationStateVerify(final List<FinanceEntrustRelationBean> oldRels, final FinanceEntrustRelationBean newRel) {
        if (newRel.getState() != StateEnum.ENABLE.getCode()) {
            return;
        }
        // 业务单元+核算组织+结算组织+已启用，手动新增时，就不要再增相同的记录
        final Long newBusinessOrg = newRel.getBizOrgId();
        final Long newAccountOrg = newRel.getAccountOrgId();
        final Long newSettmentOrg = newRel.getSettleOrgId();
        final Long newId = newRel.getId();
        for (FinanceEntrustRelationBean oldRelation : oldRels) {
            final Integer oldStatus = oldRelation.getState();
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (!Objects.equals(oldStatus, StateEnum.ENABLE.getCode())) {
                continue;
            }

            if (!Objects.equals(newBusinessOrg, oldRelation.getBizOrgId())) {
                continue;
            }

            if (!Objects.equals(newAccountOrg, oldRelation.getAccountOrgId())) {
                continue;
            }

            if (!Objects.equals(newSettmentOrg, oldRelation.getSettleOrgId())) {
                continue;
            }
        }
    }

    /**
     * 财务委托关系默认状态验证
     *
     * @param oldRels 原财务委托关系集合
     * @param newRel  新财务委托关系
     */
    private void financeEntrustRelationDefaultVerify(final List<FinanceEntrustRelationBean> oldRels, final FinanceEntrustRelationBean newRel) {
        // 业务单元+核算组织+结算组织+已启用，手动新增时，就不要再增相同的记录
        final Long newBusinessOrg = newRel.getBizOrgId();
        final Long newAccountOrg = newRel.getAccountOrgId();
        final Long newSettmentOrg = newRel.getSettleOrgId();
        final Long newId = newRel.getId();
        for (FinanceEntrustRelationBean oldRelation : oldRels) {
            final Integer oldStatus = oldRelation.getState();
            if (Objects.equals(oldRelation.getId(), newId)) {
                continue;
            }

            if (!Objects.equals(newBusinessOrg, oldRelation.getBizOrgId())) {
                continue;
            }

            if (!Objects.equals(newAccountOrg, oldRelation.getAccountOrgId())) {
                continue;
            }

            if (!Objects.equals(newSettmentOrg, oldRelation.getSettleOrgId())) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.account.settle.default.state.same"));
        }
    }

    /**
     * 集团内仓储物流委托关系验证
     *
     * @param newRel 新增集团内仓储委托关系
     */
    public void groupStorageEntrustRelationVerify(final StorageGroupEntrustRelationBean newRel) {
        if (!newRel.needVerify()) {
            return;
        }

        final List<StorageGroupEntrustRelationBean> oldRels = storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelationByStockOrgIdLock(newRel.getStockOrgId());
        if (CollectionUtils.isEmpty(oldRels)) {
            return;
        }

        addGroupStorageEntrustRelationDefaultVerify(oldRels, newRel);

        groupStorageEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 启用集团内仓储物流委托关系验证
     *
     * @param param 启用参数
     */
    public StorageGroupEntrustRelationBean enableGroupStorageEntrustRelationVerify(final EnableOperateParam param) {
        final StorageGroupEntrustRelationBean enableRel = storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelationByIdLock(param.getId());
        if (Objects.isNull(enableRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (!param.needVerify()) {
            return enableRel;
        }

        final List<StorageGroupEntrustRelationBean> allStockRels = storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelationByStockOrgIdLock(enableRel.getStockOrgId());
        if (allStockRels.size() == 1) {
            return enableRel;
        }

        if (Objects.equals(enableRel.getState(), param.getState())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.entrust.state.same"));
        }

        enableRel.setState(param.getState());
        groupStorageEntrustRelationStateVerify(allStockRels, enableRel);
        return enableRel;
    }

    /**
     * 更新集团内仓储物流委托关系验证
     *
     * @param newRel 新集团内仓储委托关系
     */
    public void updateGroupStorageEntrustRelationVerify(final StorageGroupEntrustRelationBean newRel) {
        final List<StorageGroupEntrustRelationBean> oldRels = storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelationByStockOrgIdLock(newRel.getStockOrgId());

        if (CollectionUtils.isEmpty(oldRels)) {
            // 当前库存组织无任何委托关系
            return;
        }

        addGroupStorageEntrustRelationDefaultVerify(oldRels, newRel);

        groupStorageEntrustRelationStateVerify(oldRels, newRel);
    }

    /**
     * 集团内仓储委托关系默认状态验证
     *
     * @param oldRels 原集团间仓储委托关系
     * @param newRel  新增集团间仓储委托关系
     */
    private void addGroupStorageEntrustRelationDefaultVerify(final List<StorageGroupEntrustRelationBean> oldRels, final StorageGroupEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 库存组织+物流组织，同时相同时只允许存在一条默认记录
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newId = newRel.getId();
        for (StorageGroupEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }

            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.stock.logistics.default.state.same"));
        }
    }

    private void fillDefaultGroupStorageEntrustRel(final List<StorageGroupEntrustRelationBean> oldRels, final StorageGroupEntrustRelationBean newRel) {
        if (BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) { // 新增 且 非默认 状态直接返回
            return;
        }

        // 库存组织+物流组织，同时相同时只允许存在一条默认记录
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newId = newRel.getId();
        for (StorageGroupEntrustRelationBean oldRelation : oldRels) {
            final Long oldRelationId = oldRelation.getId();
            if (Objects.equals(oldRelationId, newId)) {
                continue;
            }

            if (BooleanEnum.FALSE.getCode().equals(oldRelation.getDefaultFlag())) {
                continue;
            }
            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            newRel.setDefaultEntrustId(oldRelationId);
            break;
        }
    }

    /**
     * 集团内仓储启停状态验证
     *
     * @param oldRels 原集团内仓储委托关系
     * @param newRel  新增集团内仓储委托关系
     */
    private void groupStorageEntrustRelationStateVerify(final List<StorageGroupEntrustRelationBean> oldRels, final StorageGroupEntrustRelationBean newRel) {
        if (!Objects.equals(newRel.getState(), StateEnum.ENABLE.getCode())) {
            return;
        }
        // 库存组织+物流组织+已启用，同时只允许存在一条都相同的记录；
        final Long newstockOrg = newRel.getStockOrgId();
        final Long newLogisticsOrg = newRel.getLogisticsOrgId();
        final Long newId = newRel.getId();
        for (StorageGroupEntrustRelationBean oldRelation : oldRels) {
            if (oldRelation.getId().equals(newId)) {
                continue;
            }

            if (!Objects.equals(oldRelation.getState(), StateEnum.ENABLE.getCode())) {
                continue;
            }

            if (!Objects.equals(newstockOrg, oldRelation.getStockOrgId())) {
                continue;
            }

            if (!Objects.equals(newLogisticsOrg, oldRelation.getLogisticsOrgId())) {
                continue;
            }

            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.stock.logistics.enable.state.same"));
        }
    }
}
