package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.func.SaleOrgFuncBean;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.SaleEntrustRelationQueryParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.SaleEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.mapper.FinanceEntrustRelationMapper;
import com.org.permission.server.org.mapper.SaleEntrustRelationMapper;
import com.org.permission.server.org.mapper.SaleOrgFuncMapper;
import com.org.permission.server.org.service.SaleEntrustRelationService;
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
 * 销售委托关系服务实现
 */
@Service("saleEntrustRelationService")
public class SaleEntrustRelationServiceImpl implements SaleEntrustRelationService {

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private SaleEntrustRelationMapper saleEntrustRelationMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;
    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private SaleOrgFuncMapper saleOrgFuncMapper;

    @Resource
    private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addSaleEntrustRelation(SaleEntrustRelationBean bean) {
        // 验证销售业务委托关系合法性
        entrustRelationVerify.saleEntrustRelationVerify(bean);

        bean.setEntrustType(EntrustTypeEnum.SALE.getIndex());
        bean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        bean.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        bean.setEntrustSourceId(Long.valueOf(EntrustSourceEnum.MANUAL.getIndex()));
        bean.setCreatedDate(new Date());
        bean.setState(StateEnum.CREATE.getCode());
        saleEntrustRelationMapper.addSaleEntrustRelation(bean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateSaleEntrustRelation(SaleEntrustRelationBean newRel) {
        final SaleEntrustRelationBean sourceRel = saleEntrustRelationMapper.querySaleEntrustRelationByIdLock(newRel.getId());
        if (Objects.isNull(sourceRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {
            if (Objects.equals(BooleanEnum.FALSE.getCode(), newRel.getDefaultFlag())) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.purchase.org.create.cannot.undefault"));
            }
        }

        final Integer oldStatus = sourceRel.getState();
        newRel.setState(oldStatus);
        entrustRelationVerify.updateSaleEntrustRelationVerify(newRel);

        final Date updateTime = new Date();

        newRel.setModifiedDate(updateTime);
        saleEntrustRelationMapper.updateSaleEntrustRelation(newRel);

        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {// 级联更新组织职能
            SaleOrgFuncBean saleOrgFuncBean = new SaleOrgFuncBean();
            saleOrgFuncBean.setId(sourceRel.getEntrustSourceId());
            saleOrgFuncBean.setModifiedBy(newRel.getModifiedBy());
            saleOrgFuncBean.setModifiedDate(updateTime);
            saleOrgFuncBean.setStockOrgId(newRel.getStockOrgId());
            saleOrgFuncBean.setReceiveOrgId(newRel.getReceiveOrgId());
            saleOrgFuncBean.setSettleOrgId(newRel.getSettleOrgId());
            saleOrgFuncMapper.entrustCascadeUpdateSaleFunc(saleOrgFuncBean);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void buFuncCascadeUpdateSaleEntrustRelation(SaleEntrustRelationBean saleEntrustRel, StateEnum stateEnum, Long groupId) {
        final Long entrusId = commonEntrustRelationMapper.queryBUProductEntrustRelationId(saleEntrustRel);
        if (Objects.isNull(entrusId)) {
            return;
        }

        saleEntrustRel.setId(entrusId);
        entrustRelationVerify.updateSaleEntrustRelationVerify(saleEntrustRel);

        saleEntrustRel.setModifiedDate(new Date());
        saleEntrustRel.setGroupId(groupId);
        saleEntrustRelationMapper.updateBUProductSaleEntrustRelation(saleEntrustRel);

        // 级联更新财务委托关系
        cascadeUpdateFinanceEntrustRelation(saleEntrustRel, stateEnum, groupId, I18nUtils.getMessage("org.service.impl.purchase.update.sale"));
    }

    /**
     * 级联更新财务委托关系
     *
     * @param saleEntrustRel 委托关系
     */
    private void cascadeUpdateFinanceEntrustRelation(SaleEntrustRelationBean saleEntrustRel, StateEnum stateEnum, Long groupId, String financeNote) {
        FinanceEntrustRelationBean financeEntrustRelationship = FinanceEntrustRelationBean.buildeDefaultValue();
        financeEntrustRelationship.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        financeEntrustRelationship.setEntrustSourceId(0L);
        financeEntrustRelationship.setGroupId(groupId);
        financeEntrustRelationship.setState(stateEnum.getCode());

        financeEntrustRelationship.setAccountOrgId(saleEntrustRel.getReceiveOrgId());
        financeEntrustRelationship.setSettleOrgId(saleEntrustRel.getSettleOrgId());
        financeEntrustRelationship.setBizOrgId(saleEntrustRel.getSaleOrgId());

        financeEntrustRelationship.setCreatedBy(saleEntrustRel.getModifiedBy());
        financeEntrustRelationship.setCreatedDate(new Date());
        financeEntrustRelationship.setRemark(financeNote);
        cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationship);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableSaleEntrustRelation(EnableOperateParam reqParam) {

        //SaleEntrustRelationBean oldEntrustRel  = entrustRelationVerify.enableSaleEntrustRelationVerify(reqParam);
        SaleEntrustRelationBean oldEntrustRel = saleEntrustRelationMapper.querySaleEntrustRelationByIdLock(reqParam.getId());
        final Date enableTime = new Date();
        commonEntrustRelationMapper.enableEntrustRelation(reqParam, enableTime);

        if (reqParam.getState() == StateEnum.DISABLE.getCode()) {
            return;
        }

        // 级联新增财务委托关系
        FinanceEntrustRelationBean newFinanceEntrustRel = FinanceEntrustRelationBean.buildeDefaultValue();
        newFinanceEntrustRel.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        newFinanceEntrustRel.setEntrustSourceId(0L);
        newFinanceEntrustRel.setGroupId(oldEntrustRel.getGroupId());
        newFinanceEntrustRel.setCreatedBy(reqParam.getUserId());
        newFinanceEntrustRel.setCreatedDate(enableTime);
        newFinanceEntrustRel.setState(StateEnum.ENABLE.getCode());
        newFinanceEntrustRel.setAccountOrgId(oldEntrustRel.getReceiveOrgId());
        newFinanceEntrustRel.setSettleOrgId(oldEntrustRel.getSettleOrgId());
        newFinanceEntrustRel.setBizOrgId(oldEntrustRel.getSaleOrgId());
        newFinanceEntrustRel.setRemark(I18nUtils.getMessage("org.service.impl.purchase.sale.enable.create.finance.relation"));
        cascadeCreateFinanceEntrustRelEvent.entrustCreate(newFinanceEntrustRel);
    }

    @Override
    public PageInfo<SaleEntrustRelationInfoBean> querySaleEntrustRelationList(SaleEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<SaleEntrustRelationInfoBean> logisticsBeans = saleEntrustRelationMapper.querySaleEntrustRelationList(reqParam);
        PageInfo<SaleEntrustRelationInfoBean> pageInfo = new PageInfo(logisticsBeans);
        return pageInfo;
    }

    @Override
    public List<SaleEntrustRelationInfoBean> querySaleEntrustRelation(SaleEntrustRelationQueryParam reqParam) {
        return saleEntrustRelationMapper.querySaleEntrustRelation(reqParam);
    }
}
