package com.org.permission.server.org.service.impl;


import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.func.PurchaseOrgFuncBean;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.PurchaseEntrustRelationQueryParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.mapper.FinanceEntrustRelationMapper;
import com.org.permission.server.org.mapper.PurchaseEntrustRelationMapper;
import com.org.permission.server.org.mapper.PurchaseOrgFuncMapper;
import com.org.permission.server.org.service.PurchaseEntrustRelationService;
import com.org.permission.server.org.service.event.CascadeCreateFinanceEntrustRelEvent;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 采购委托关系服务实现
 */
@Service("purchaseEntrustRelationService")
public class PurchaseEntrustRelationServiceImpl implements PurchaseEntrustRelationService {

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private PurchaseEntrustRelationMapper purchaseEntrustRelationMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;

    @Resource
    private PurchaseOrgFuncMapper purchaseOrgFuncMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addPurchaseEntrustRelation(PurchaseEntrustRelationBean bean) {
        entrustRelationVerify.purchaseEntrustRelationVerify(bean);

        bean.setEntrustType(EntrustTypeEnum.PURCHASE.getIndex());
        bean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        bean.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        bean.setEntrustSourceId(Long.valueOf(EntrustSourceEnum.MANUAL.getIndex()));
        bean.setState(OrgStateEnum.CREATE.getCode());
        bean.setCreatedDate(new Date());

        purchaseEntrustRelationMapper.addPurchaseEntrustRelation(bean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updatePurchaseEntrustRelation(PurchaseEntrustRelationBean newRel) {
        // 读取原业务委托关系
        final PurchaseEntrustRelationBean sourceRel = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationByIdLock(newRel.getId());
        if (Objects.isNull(sourceRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }


        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {
            if (BooleanEnum.FALSE.getCode().equals(newRel.getDeletedFlag())) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.purchase.org.create.cannot.undefault"));
            }
        }

        final Integer oldStatus = sourceRel.getState();
        newRel.setState(oldStatus);
        entrustRelationVerify.updatePurchaseEntrustRelationVerify(newRel);

        final Date updateTime = new Date();

        newRel.setModifiedDate(updateTime);
        purchaseEntrustRelationMapper.updatePurchaseEntrustRelation(newRel);

        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {// 级联更新组织职能
            PurchaseOrgFuncBean purchaseOrgFuncBean = new PurchaseOrgFuncBean();
            purchaseOrgFuncBean.setId(sourceRel.getEntrustSourceId());
            purchaseOrgFuncBean.setModifiedDate(updateTime);
            purchaseOrgFuncBean.setModifiedBy(newRel.getModifiedBy());
            purchaseOrgFuncBean.setStockOrgId(newRel.getStockOrgId());
            purchaseOrgFuncBean.setPayOrgId(newRel.getPayOrgId());
            purchaseOrgFuncBean.setSettleOrgId(newRel.getSettleOrgId());
            purchaseOrgFuncMapper.entrustCascadeUpdatePurchaseFunc(purchaseOrgFuncBean);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void buFuncCascadeUpdatePurchaseEntrustRelation(PurchaseEntrustRelationBean purchaseEntrustRel, StateEnum stateEnum, Long groupId) {

        // 获取有职能产生的委托关系 ID
        final Long entrusId = commonEntrustRelationMapper.queryBUProductEntrustRelationId(purchaseEntrustRel);
        if (Objects.isNull(entrusId)) {
            return;
        }

        purchaseEntrustRel.setId(entrusId);
        entrustRelationVerify.updatePurchaseEntrustRelationVerify(purchaseEntrustRel);

        purchaseEntrustRel.setModifiedDate(new Date());
        purchaseEntrustRelationMapper.updateBUProductPurchaseEntrustRelation(purchaseEntrustRel);

        // 级联更新财务委托关系
        cascadeUpdateFinanceEntrustRelation(purchaseEntrustRel, stateEnum, groupId, I18nUtils.getMessage("org.service.impl.purchase.update.finance"));
    }

    /**
     * 级联更新财务委托关系
     *
     * @param purchaseEntrustRel 采购委托关系
     */
    private void cascadeUpdateFinanceEntrustRelation(PurchaseEntrustRelationBean purchaseEntrustRel, StateEnum stateEnum, Long groupId, String financeNote) {
        Long entrustId = purchaseEntrustRel.getId();
        FinanceEntrustRelationBean financeEntrustRelationship = FinanceEntrustRelationBean.buildeDefaultValue();
        financeEntrustRelationship.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        financeEntrustRelationship.setEntrustSourceId(0L);

        financeEntrustRelationship.setGroupId(groupId);
        financeEntrustRelationship.setAccountOrgId(purchaseEntrustRel.getPayOrgId());
        financeEntrustRelationship.setSettleOrgId(purchaseEntrustRel.getSettleOrgId());
        financeEntrustRelationship.setBizOrgId(purchaseEntrustRel.getPurchaseOrgId());

        financeEntrustRelationship.setState(stateEnum.getCode());
        financeEntrustRelationship.setCreatedBy(purchaseEntrustRel.getModifiedBy());
        financeEntrustRelationship.setCreatedDate(new Date());
        financeEntrustRelationship.setRemark(financeNote);

        cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationship);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enablePurchaseEntrustRelation(EnableOperateParam reqParam) {

        final Date enableTime = new Date();
        PurchaseEntrustRelationBean oldEntrustRel = entrustRelationVerify.enablePurchaseEntrustRelationVerify(reqParam);

        commonEntrustRelationMapper.enableEntrustRelation(reqParam, enableTime);

        if (reqParam.getState() == StateEnum.DISABLE.getCode()) {
            return;
        }

        // 级联新增财务委托关系
        FinanceEntrustRelationBean newFinanceEntrustRel = FinanceEntrustRelationBean.buildeDefaultValue();
        newFinanceEntrustRel.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        newFinanceEntrustRel.setEntrustSourceId(0L);

        newFinanceEntrustRel.setGroupId(oldEntrustRel.getGroupId());
        newFinanceEntrustRel.setState(StateEnum.ENABLE.getCode());
        newFinanceEntrustRel.setAccountOrgId(oldEntrustRel.getPayOrgId());
        newFinanceEntrustRel.setSettleOrgId(oldEntrustRel.getSettleOrgId());
        newFinanceEntrustRel.setBizOrgId(oldEntrustRel.getPurchaseOrgId());

        newFinanceEntrustRel.setCreatedBy(reqParam.getUserId());
        newFinanceEntrustRel.setCreatedDate(enableTime);
        newFinanceEntrustRel.setRemark(I18nUtils.getMessage("org.service.impl.purchase.enable.create.finance.relation"));
        cascadeCreateFinanceEntrustRelEvent.entrustCreate(newFinanceEntrustRel);
    }

    @Override
    public PageInfo<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelationList(PurchaseEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<PurchaseEntrustRelationInfoBean> logisticsBeans = purchaseEntrustRelationMapper.queryPurchaseEntrustRelationList(reqParam);
        PageInfo<PurchaseEntrustRelationInfoBean> pageInfo = new PageInfo<>(logisticsBeans);
        return pageInfo;
    }

    @Override
    public List<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelation(PurchaseEntrustRelationQueryParam reqParam) {
        return purchaseEntrustRelationMapper.queryPurchaseEntrustRelation(reqParam);
    }
}
