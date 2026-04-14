package com.org.permission.server.org.builder;

import com.common.language.util.I18nUtils;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.StorageGroupEntrustRelationBean;
import com.org.permission.common.org.dto.func.*;
import com.org.permission.server.org.dto.param.AddBizUnitReqParam;
import com.org.permission.server.org.dto.param.UpdateBizUnitReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.mapper.FinanceEntrustRelationMapper;
import com.org.permission.server.org.mapper.PurchaseEntrustRelationMapper;
import com.org.permission.server.org.mapper.SaleEntrustRelationMapper;
import com.org.permission.server.org.mapper.StorageGroupEntrustRelationMapper;
import com.common.base.enums.BooleanEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 根业务委托关系构建器
 */
@Component(value = "bizUnitEntrustRelationshipBuilder")
public class BizUnitEntrustRelationshipBuilder {

    @Resource
    private PurchaseEntrustRelationMapper purchaseEntrustRelationMapper;

    @Resource
    private SaleEntrustRelationMapper saleEntrustRelationMapper;

    @Resource
    private StorageGroupEntrustRelationMapper storageGroupEntrustRelationMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;

    /**
     * 由业务单元组织职能生成委托关系
     * <p>
     * 注意：一个业务单元只生成一条财务委托关系
     *
     * @param reqParam 业务单元参数
     */
    public void buGenerateEntrustRelation(AddBizUnitReqParam reqParam, Long[] orgFuncIds, Integer status) {

        Set<FinanceEntrustRelationBean> financeEntrustRelationBeans = new HashSet<>(4);
        final Long groupId = reqParam.getGroupId();
        if (reqParam.hasPurchaseFunc()) {
            final FinanceEntrustRelationBean purchaseFinanceEntrustRelationBean = writePurchaseEntrustRelation(reqParam, groupId, orgFuncIds[0], status, Boolean.TRUE, "新增BU,采购级联财务");
            financeEntrustRelationBeans.add(purchaseFinanceEntrustRelationBean);
        }

        if (reqParam.hasSaleFunc()) {
            final FinanceEntrustRelationBean saleFinanceEntrustRelationBean = writeSaleEntrustRelation(reqParam, groupId, orgFuncIds[1], status, Boolean.TRUE, "新增BU,销售级联财务");
            financeEntrustRelationBeans.add(saleFinanceEntrustRelationBean);
        }

        if (reqParam.hasStorageFunc()) {
            final FinanceEntrustRelationBean storageFinanceEntrustRelationBean = writeGroupStorageEntrustRelation(reqParam, groupId, orgFuncIds[2], status, Boolean.TRUE, "新增BU,仓储级联财务");
            financeEntrustRelationBeans.add(storageFinanceEntrustRelationBean);
        }

        if (reqParam.hasLogisticFunc()) {
            final FinanceEntrustRelationBean logisticFinanceEntrustRelationBean = writeLogisticsEntrustRelation(reqParam, groupId, orgFuncIds[3], status, Boolean.TRUE, "新增BU,运输级联财务");
            financeEntrustRelationBeans.add(logisticFinanceEntrustRelationBean);
        }
        //金融
        if (reqParam.hasBankingFunc()) {
            FinanceEntrustRelationBean bankEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, status, reqParam.getId(), reqParam.getId(), groupId);
            bankEntrustRelationBean.setRemark(I18nUtils.getMessage("org.builder.BizUnitEntrustRelationship.bank.add"));
            financeEntrustRelationBeans.add(bankEntrustRelationBean);
        }

        if (!ObjectUtils.isEmpty(financeEntrustRelationBeans)) {
            if (financeEntrustRelationBeans.size() > 0) {
                financeEntrustRelationMapper.batchAddFinanceEntrustRelation(new ArrayList<>(financeEntrustRelationBeans));
            }
        }
    }

    /**
     * 写采购委托关系
     *
     * @param reqParam          业务单眼新增请求参数
     * @param addOp             true新增操作（财务取默认值）;false更新操作（取传递值）
     * @param purchaseOrgFuncId 采购职能
     */
    public FinanceEntrustRelationBean writePurchaseEntrustRelation(UpdateBizUnitReqParam reqParam, Long groupId, Long purchaseOrgFuncId, Integer state, boolean addOp, String financeNote) {
        final PurchaseOrgFuncBean purchaseFunc = reqParam.getPurchase();
        Long accountOrgId = addOp ? reqParam.hasFinanceFunc() ? reqParam.getId() : purchaseFunc.getPayOrgId() : purchaseFunc.getPayOrgId();
        Long settleOrgId = addOp ? reqParam.hasFinanceFunc() ? reqParam.getId() : purchaseFunc.getSettleOrgId() : purchaseFunc.getSettleOrgId();
        Long stockOrgId = reqParam.hasStorageFunc() ? reqParam.getId() : purchaseFunc.getStockOrgId();

        PurchaseEntrustRelationBean purchaseEntrustRelationBean = new PurchaseEntrustRelationBean();
        purchaseEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
        purchaseEntrustRelationBean.setEntrustSourceId(purchaseOrgFuncId);

        purchaseEntrustRelationBean.setGroupId(groupId);
        purchaseEntrustRelationBean.setPurchaseOrgId(reqParam.getId());
        purchaseEntrustRelationBean.setCreatedBy(reqParam.getCreatedBy());
        purchaseEntrustRelationBean.setCreatedName(reqParam.getCreatedName());
        purchaseEntrustRelationBean.setCreatedDate(new Date());
        purchaseEntrustRelationBean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        purchaseEntrustRelationBean.setEntrustType(EntrustTypeEnum.PURCHASE.getIndex());
        purchaseEntrustRelationBean.setState(state);
        purchaseEntrustRelationBean.setDefaultFlag(BooleanEnum.TRUE.getCode());
        purchaseEntrustRelationBean.setPayOrgId(accountOrgId);
        purchaseEntrustRelationBean.setSettleOrgId(settleOrgId);
        purchaseEntrustRelationBean.setStockOrgId(stockOrgId);
        purchaseEntrustRelationBean.setRemark(I18nUtils.getMessage("org.builder.BizUnitEntrustRelationship.purchase.add"));

        purchaseEntrustRelationMapper.addPurchaseEntrustRelation(purchaseEntrustRelationBean);
        // 级联财务委托关系
        FinanceEntrustRelationBean financeEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, state, accountOrgId, settleOrgId, groupId);
        financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.CASCADE.getIndex());
        financeEntrustRelationBean.setEntrustSourceId(purchaseEntrustRelationBean.getId());

        financeEntrustRelationBean.setRemark(financeNote);
        return financeEntrustRelationBean;
    }

    /**
     * 写销售委托关系
     *
     * @param reqParam      业务单眼新增请求参数
     * @param saleOrgFuncId 销售职能
     */
    public FinanceEntrustRelationBean writeSaleEntrustRelation(UpdateBizUnitReqParam reqParam, Long groupId, Long saleOrgFuncId, Integer status, boolean addOp, String financeNote) {
        final SaleOrgFuncBean saleFunc = reqParam.getSale();
        Long buId = reqParam.getId();
        Long accountOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : saleFunc.getReceiveOrgId() : saleFunc.getReceiveOrgId();
        Long settleOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : saleFunc.getSettleOrgId() : saleFunc.getSettleOrgId();
        Long stockOrgId = reqParam.hasStorageFunc() ? buId : saleFunc.getStockOrgId();

        SaleEntrustRelationBean saleEntrustRelationBean = new SaleEntrustRelationBean();
        saleEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
        saleEntrustRelationBean.setEntrustSourceId(saleOrgFuncId);

        saleEntrustRelationBean.setGroupId(groupId);
        saleEntrustRelationBean.setSaleOrgId(buId);
        saleEntrustRelationBean.setCreatedBy(reqParam.getCreatedBy());
        saleEntrustRelationBean.setCreatedName(reqParam.getCreatedName());
        saleEntrustRelationBean.setCreatedDate(new Date());
        saleEntrustRelationBean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        saleEntrustRelationBean.setEntrustType(EntrustTypeEnum.SALE.getIndex());
        saleEntrustRelationBean.setState(status);
        saleEntrustRelationBean.setDefaultFlag(BooleanEnum.TRUE.getCode());
        saleEntrustRelationBean.setReceiveOrgId(accountOrgId);
        saleEntrustRelationBean.setSettleOrgId(settleOrgId);
        saleEntrustRelationBean.setStockOrgId(stockOrgId);
        saleEntrustRelationBean.setRemark(I18nUtils.getMessage("org.builder.BizUnitEntrustRelationship.sale.add"));

        saleEntrustRelationMapper.addSaleEntrustRelation(saleEntrustRelationBean);

        // 级联财务委托关系
        FinanceEntrustRelationBean financeEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, status, accountOrgId, settleOrgId, groupId);

        financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.CASCADE.getIndex());
        financeEntrustRelationBean.setEntrustSourceId(saleEntrustRelationBean.getId());

        financeEntrustRelationBean.setRemark(financeNote);
        return financeEntrustRelationBean;
    }

    /**
     * 写集团内的仓储委托关系
     *
     * @param reqParam         业务单眼新增请求参数
     * @param addOp            true新增操作（财务取默认值）;false更新操作（取传递值）
     * @param storageOrgFuncId 仓储职能
     */
    public FinanceEntrustRelationBean writeGroupStorageEntrustRelation(UpdateBizUnitReqParam reqParam, Long groupId, Long storageOrgFuncId, Integer state, boolean addOp, String financeNote) {
        final StorageOrgFuncBean storageFunc = reqParam.getStorage();
        Long accountOrgId = addOp ? reqParam.hasFinanceFunc() ? reqParam.getId() : storageFunc.getAccountOrgId() : storageFunc.getAccountOrgId();
        Long settleOrgId = addOp ? reqParam.hasFinanceFunc() ? reqParam.getId() : storageFunc.getSettleOrgId() : storageFunc.getSettleOrgId();
        Long logisticsOrgId = reqParam.hasLogisticFunc() ? reqParam.getId() : storageFunc.getLogisticsOrgId();

        StorageGroupEntrustRelationBean storageGroupEntrustRelationBean = new StorageGroupEntrustRelationBean();
        storageGroupEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
        storageGroupEntrustRelationBean.setEntrustSourceId(storageOrgFuncId);

        storageGroupEntrustRelationBean.setGroupId(groupId);
        storageGroupEntrustRelationBean.setStockOrgId(reqParam.getId());
        storageGroupEntrustRelationBean.setCreatedBy(reqParam.getCreatedBy());
        storageGroupEntrustRelationBean.setCreatedName(reqParam.getCreatedName());
        storageGroupEntrustRelationBean.setCreatedDate(new Date());
        storageGroupEntrustRelationBean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        storageGroupEntrustRelationBean.setEntrustType(EntrustTypeEnum.STORAGE.getIndex());
        storageGroupEntrustRelationBean.setState(state);
        storageGroupEntrustRelationBean.setDefaultFlag(BooleanEnum.TRUE.getCode());
        storageGroupEntrustRelationBean.setAccountOrgId(accountOrgId);
        storageGroupEntrustRelationBean.setSettleOrgId(settleOrgId);
        storageGroupEntrustRelationBean.setLogisticsOrgId(logisticsOrgId);
        storageGroupEntrustRelationBean.setRemark(financeNote);
        storageGroupEntrustRelationMapper.addStorageEntrustRelation(storageGroupEntrustRelationBean);

        // 级联财务委托关系
        FinanceEntrustRelationBean financeEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, state, accountOrgId, settleOrgId, groupId);

        financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.CASCADE.getIndex());
        financeEntrustRelationBean.setEntrustSourceId(storageGroupEntrustRelationBean.getId());
        financeEntrustRelationBean.setRemark(financeNote);

        return financeEntrustRelationBean;
    }

    /**
     * 写物流委托关系
     *
     * @param reqParam           业务单元新增请求参数
     * @param addOp              true新增操作（财务取默认值）;false更新操作（取传递值）
     * @param logisticsOrgFuncId 物流职能
     */
    public FinanceEntrustRelationBean writeLogisticsEntrustRelation(UpdateBizUnitReqParam reqParam, Long groupId, Long logisticsOrgFuncId, Integer status, boolean addOp, String financeNote) {
        // 级联财务委托关系
        final LogisticsOrgFuncBean logisticsFunc = reqParam.getLogistics();
        final Long buId = reqParam.getId();
        Long accountOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : logisticsFunc.getAccountOrgId() : logisticsFunc.getAccountOrgId();
        Long settleOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : logisticsFunc.getSettleOrgId() : logisticsFunc.getSettleOrgId();
        FinanceEntrustRelationBean financeEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, status, accountOrgId, settleOrgId, groupId);

        financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
        financeEntrustRelationBean.setEntrustSourceId(logisticsOrgFuncId);

        financeEntrustRelationBean.setRemark(financeNote);
        return financeEntrustRelationBean;
    }

    /**
     * 写物流委托关系
     *
     * @param reqParam      业务单眼新增请求参数
     * @param addOp         true新增操作（财务取默认值）;false更新操作（取传递值）
     * @param bankOrgFuncId 物流职能
     */
    public FinanceEntrustRelationBean writeBankingEntrustRelation(UpdateBizUnitReqParam reqParam, Long groupId, Long bankOrgFuncId, Integer status, boolean addOp, String financeNote) {
        // 级联财务委托关系
        final BankingOrgFuncBean bankingOrgFuncBean = reqParam.getBanking();
        final Long buId = reqParam.getId();
        Long accountOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : bankingOrgFuncBean.getAccountOrgId() : bankingOrgFuncBean.getAccountOrgId();
        Long settleOrgId = addOp ? reqParam.hasFinanceFunc() ? buId : bankingOrgFuncBean.getSettleOrgId() : bankingOrgFuncBean.getSettleOrgId();
        FinanceEntrustRelationBean financeEntrustRelationBean = buildeFinanceEntrustRelationBean(reqParam, status, accountOrgId, settleOrgId, groupId);

        financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
        financeEntrustRelationBean.setEntrustSourceId(bankOrgFuncId);

        financeEntrustRelationBean.setRemark(financeNote);
        return financeEntrustRelationBean;
    }

    /**
     * 构建财务委托关系
     *
     * @param status       状态
     * @param accountOrgId 核算组织ID
     * @param settleOrgId  结算组织ID
     * @return 财务委托关系
     */
    private FinanceEntrustRelationBean buildeFinanceEntrustRelationBean(UpdateBizUnitReqParam reqParam, Integer status, Long accountOrgId, Long settleOrgId, Long groupId) {
        FinanceEntrustRelationBean financeEntrustRelationBean = new FinanceEntrustRelationBean();
        financeEntrustRelationBean.setGroupId(groupId);
        financeEntrustRelationBean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        financeEntrustRelationBean.setEntrustType(EntrustTypeEnum.FINANCE.getIndex());
        financeEntrustRelationBean.setDefaultFlag(BooleanEnum.TRUE.getCode());
        financeEntrustRelationBean.setState(status);

        financeEntrustRelationBean.setBizOrgId(reqParam.getId());
        financeEntrustRelationBean.setAccountOrgId(accountOrgId);
        financeEntrustRelationBean.setSettleOrgId(settleOrgId);

        financeEntrustRelationBean.setCreatedBy(reqParam.getCreatedBy());
        financeEntrustRelationBean.setCreatedName(reqParam.getCreatedName());
        financeEntrustRelationBean.setCreatedDate(new Date());

        return financeEntrustRelationBean;
    }
}
