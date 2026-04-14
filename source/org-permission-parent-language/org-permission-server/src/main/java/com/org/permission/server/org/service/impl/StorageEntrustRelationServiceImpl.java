package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.dto.func.StorageOrgFuncBean;
import com.org.permission.common.org.param.*;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationReqParam;
import com.org.permission.server.org.dto.param.InitCustEntrustRelParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationReqParam;
import com.org.permission.server.org.enums.CustEntrustTypeEnum;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.mapper.*;
import com.org.permission.server.org.service.CommonEntrustRelationService;
import com.org.permission.server.org.service.StorageEntrustRelationService;
import com.org.permission.server.org.service.event.CascadeCreateFinanceEntrustRelEvent;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 仓储委托关系服务实现
 */
@Service("storageEntrustRelationService")
public class StorageEntrustRelationServiceImpl implements StorageEntrustRelationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageEntrustRelationServiceImpl.class);

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private StoragePlatformEntrustRelationMapper storagePlatformEntrustRelationMapper;

    @Resource
    private StorageGroupEntrustRelationMapper storageGroupEntrustRelationMapper;

    @Resource
    private StorageEntrustRelationMapper storageEntrustRelationMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;

    @Resource
    private BizUnitMapper bizUnitMapper;

    @Resource
    private StorageOrgFuncMapper storageOrgFuncMapper;

    @Resource
    private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;

    @Resource
    private CommonEntrustRelationService commonEntrustRelationService;

    @Resource
    private CustDomainService custDomainService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addPlatformStorageEntrustRelation(StoragePlatformEntrustRelationBean reqParam) {
        entrustRelationVerify.platformStorageEntrustRelationVerify(reqParam);

        reqParam.setEntrustType(EntrustTypeEnum.STORAGE.getIndex());
        reqParam.setEntrustRange(EntrustRangeEnum.INTER_GROUP.getIndex());
        reqParam.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        reqParam.setState(StateEnum.CREATE.getCode());
        reqParam.setCreatedBy(FplUserUtil.getUserId());
        reqParam.setCreatedName(FplUserUtil.getUserName());
        reqParam.setCreatedDate(new Date());
        storagePlatformEntrustRelationMapper.addStorageEntrustRelation(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updatePlatformStorageEntrustRelation(StoragePlatformEntrustRelationBean reqParam) {
        final StoragePlatformEntrustRelationBean sourceEntrust = storagePlatformEntrustRelationMapper.queryEntrustRelationByIdLock(reqParam.getId());
        if (Objects.isNull(sourceEntrust)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (Objects.equals(BooleanEnum.TRUE.getCode(),sourceEntrust.getDefaultFlag())  && Objects.equals(BooleanEnum.FALSE.getCode(),reqParam.getDefaultFlag())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.logistics.only.default.cannot.modify"));
        }
        entrustRelationVerify.updatePlatformStorageEntrustRelationVerify(reqParam);
        reqParam.setModifiedDate(new Date());
        storagePlatformEntrustRelationMapper.updateStorageEntrustRelation(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enablePlatformStorageEntrustRelation(EnableOperateParam reqParam) {

        entrustRelationVerify.enablePlatformStorageEntrustRelationVerify(reqParam);

        final Date enableTime = new Date();
        commonEntrustRelationMapper.enableEntrustRelation(reqParam, enableTime);

        financeEntrustRelationMapper.cascadeEnableEntrustRelation(reqParam, enableTime);

        // 启用生成客商关系
        final StoragePlatformEntrustRelationBean enableRel = storagePlatformEntrustRelationMapper.queryEntrustRelationByIdLock(reqParam.getId());

        InitCustEntrustRelParam initCustEntrustRelParam = new InitCustEntrustRelParam();
        initCustEntrustRelParam.setDelegationType(CustEntrustTypeEnum.STORAGE.getType());
        initCustEntrustRelParam.setLogisticsOrgId(enableRel.getLogisticsOrgId());
        initCustEntrustRelParam.setLogisticsProviderId(enableRel.getLogisticsProviderId());
        initCustEntrustRelParam.setWarehouseProviderId(enableRel.getWarehouseProviderId());
        initCustEntrustRelParam.setStockOrgId(enableRel.getStockOrgId());
        initCustEntrustRelParam.setState(reqParam.getState());
        initCustEntrustRelParam.setOperateUserName(reqParam.getUserName());
        initCustEntrustRelParam.setOperateUser(reqParam.getUserId());
        custDomainService.initCustEntrustRel(initCustEntrustRelParam);
    }

    @Override
    public PageInfo<StoragePlatformEntrustRelationInfoBean> queryPlatformStorageEntrustRelationList(PlatformStorageEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize()).setOrderBy("created_date desc");
        //查询仓库委托关系
        final List<StoragePlatformEntrustRelationInfoBean> marketEntrustRelationDetailBeans = storagePlatformEntrustRelationMapper.queryStorageEntrustRelationList(reqParam);
        PageInfo<StoragePlatformEntrustRelationInfoBean> pageInfo = new PageInfo(marketEntrustRelationDetailBeans);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addGroupStorageEntrustRelation(StorageGroupEntrustRelationBean bean) {
        entrustRelationVerify.groupStorageEntrustRelationVerify(bean);

        bean.setEntrustType(EntrustTypeEnum.STORAGE.getIndex());
        bean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
        bean.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        bean.setEntrustSourceId(Long.valueOf(EntrustSourceEnum.MANUAL.getIndex()));
        bean.setState(StateEnum.CREATE.getCode());
        bean.setCreatedDate(new Date());
        storageGroupEntrustRelationMapper.addStorageEntrustRelation(bean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateGroupStorageEntrustRelation(StorageGroupEntrustRelationBean newRel) {
        final StorageGroupEntrustRelationBean sourceRel = storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelationByIdLock(newRel.getId());
        if (Objects.isNull(sourceRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {
            if (Objects.equals(BooleanEnum.FALSE.getCode(),newRel.getDefaultFlag())) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.purchase.org.create.cannot.undefault"));
            }
        }
        final Integer oldStatus = sourceRel.getState();
        newRel.setState(oldStatus);
        entrustRelationVerify.updateGroupStorageEntrustRelationVerify(newRel);

        final Date updateTime = new Date();
        newRel.setModifiedDate(updateTime);
        storageGroupEntrustRelationMapper.updateStorageEntrustRelation(newRel);
        if (sourceRel.getEntrustSource() == EntrustSourceEnum.BUSINESS_UNIT.getIndex()) {// 级联更新组织职能
            StorageOrgFuncBean storageOrgFuncBean = new StorageOrgFuncBean();
            storageOrgFuncBean.setId(sourceRel.getEntrustSourceId());
            storageOrgFuncBean.setLogisticsOrgId(newRel.getLogisticsOrgId());
            storageOrgFuncBean.setAccountOrgId(newRel.getAccountOrgId());
            storageOrgFuncBean.setSettleOrgId(newRel.getSettleOrgId());
            storageOrgFuncBean.setModifiedDate(updateTime);
            storageOrgFuncBean.setModifiedBy(newRel.getModifiedBy());
            storageOrgFuncMapper.entrustCascadeUpdateStorageFunc(storageOrgFuncBean);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void buFuncCascadeUpdateGroupStorageEntrustRelation(StorageGroupEntrustRelationBean entrustRel, StateEnum stateEnum, Long groupId) {
        final Long entrusId = commonEntrustRelationMapper.queryBUProductEntrustRelationId(entrustRel);
        if (Objects.isNull(entrusId)) {
            return;
        }

        entrustRel.setId(entrusId);
        entrustRelationVerify.updateGroupStorageEntrustRelationVerify(entrustRel);

        entrustRel.setModifiedDate(new Date());
        entrustRel.setGroupId(groupId);
        storageGroupEntrustRelationMapper.updateBUProductStorageEntrustRelation(entrustRel);
    }

    /**
     * 级联更新财务委托关系
     *
     * @param entrustRel 委托关系
     */
    private void cascadeUpdateFinanceEntrustRelation(StorageGroupEntrustRelationBean entrustRel, StateEnum stateEnum, Long groupId, String financeNote) {
        Long entrustId = entrustRel.getId();
        FinanceEntrustRelationBean financeEntrustRelationship = FinanceEntrustRelationBean.buildeDefaultValue();
        financeEntrustRelationship.setEntrustSource(EntrustSourceEnum.CASCADE.getIndex());
        financeEntrustRelationship.setEntrustSourceId(entrustId);
        financeEntrustRelationship.setGroupId(groupId);
        financeEntrustRelationship.setState(stateEnum.getCode());

        financeEntrustRelationship.setBizOrgId(entrustRel.getStockOrgId());
        financeEntrustRelationship.setAccountOrgId(entrustRel.getAccountOrgId());
        financeEntrustRelationship.setSettleOrgId(entrustRel.getSettleOrgId());

        financeEntrustRelationship.setCreatedBy(entrustRel.getModifiedBy());
        financeEntrustRelationship.setCreatedDate(new Date());
        financeEntrustRelationship.setRemark(financeNote);
        cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationship);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableGroupStorageEntrustRelation(EnableOperateParam reqParam) {

        StorageGroupEntrustRelationBean oldEntrustRel = entrustRelationVerify.enableGroupStorageEntrustRelationVerify(reqParam);

        final Date enableTime = new Date();
        commonEntrustRelationMapper.enableEntrustRelation(reqParam, enableTime);

        if (reqParam.getState() == StateEnum.DISABLE.getCode()) {
            return;
        }

        // 级联新增财务委托关系
        FinanceEntrustRelationBean newFinanceEntrustRel = FinanceEntrustRelationBean.buildeDefaultValue();
        newFinanceEntrustRel.setEntrustSource(EntrustSourceEnum.CASCADE.getIndex());
        newFinanceEntrustRel.setEntrustSourceId(oldEntrustRel.getId());

        newFinanceEntrustRel.setGroupId(oldEntrustRel.getGroupId());
        newFinanceEntrustRel.setCreatedBy(reqParam.getUserId());
        newFinanceEntrustRel.setCreatedDate(enableTime);
        newFinanceEntrustRel.setState(StateEnum.ENABLE.getCode());

        newFinanceEntrustRel.setAccountOrgId(oldEntrustRel.getAccountOrgId());
        newFinanceEntrustRel.setSettleOrgId(oldEntrustRel.getSettleOrgId());
        newFinanceEntrustRel.setBizOrgId(oldEntrustRel.getStockOrgId());
        newFinanceEntrustRel.setRemark(I18nUtils.getMessage("org.service.impl.storage.enable.create.relation.finance"));
        cascadeCreateFinanceEntrustRelEvent.entrustCreate(newFinanceEntrustRel);

    }

    @Override
    public PageInfo<StorageGroupEntrustRelationInfoBean> queryGroupStorageEntrustRelationList(GroupStorageEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<StorageGroupEntrustRelationInfoBean> marketEntrustRelationDetailBeans = storageGroupEntrustRelationMapper.queryStorageEntrustRelationList(reqParam);
        PageInfo<StorageGroupEntrustRelationInfoBean> pageInfo = new PageInfo<>(marketEntrustRelationDetailBeans);
        return pageInfo;
    }

    @Override
    public List<StoragePlatformEntrustRelationDto> queryPlatformStorageEntrustRelation(StoragePlatformEntrustRelationQueryParam reqParam) {
        return storagePlatformEntrustRelationMapper.queryPlatformStorageEntrustRelation(reqParam);
    }

    @Override
    public List<StorageGroupEntrustRelationDto> queryGroupStorageEntrustRelation(StorageGroupEntrustRelationQueryParam reqParam) {
        return storageGroupEntrustRelationMapper.queryGroupStorageEntrustRelation(reqParam);
    }

    /**
     * 查询具有仓储委托关系的物流组织
     * @param reqParam
     * @return
     */
    @Override
    public MultipleOrgTreeDto queryHasStorageErLogisticsOrg(QueryStorageEntrustRelationLogisticsOrgParam reqParam) {
        List<Long> logisticOrgIds;
        final Integer entrustRange = reqParam.getEntrustRange();
        if (EntrustRangeEnum.WITHIN_GROUP.getIndex() == entrustRange) {
            logisticOrgIds = storageGroupEntrustRelationMapper.queryInGroupLogisticOrg(reqParam);
        } else {
            logisticOrgIds = storagePlatformEntrustRelationMapper.queryOutGroupLogisticOrg(reqParam);
        }
        LOGGER.info("storage entrust logistics orgIds :{}.", logisticOrgIds);
        MultipleOrgTreeDto result = new MultipleOrgTreeDto();
        if (CollectionUtils.isEmpty(logisticOrgIds)) {
            return result;
        }
        final List<OrgTreeBean> orgListBeans = bizUnitMapper.queryGroupBUByOrgIdAndFunc(logisticOrgIds, FunctionTypeEnum.STORAGE.getIndex());

        result.setMatchOrgIds(logisticOrgIds);
        result.setGroupAllOrgs(orgListBeans);
        return result;
    }

    @Override
    public PageInfo<OrgInfoDto> queryHasStorageEntrustRelationLogisticsOrg(EntrustRelationQueryParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<OrgInfoDto> orgList = storageEntrustRelationMapper.queryHasStorageEntrustRelationLogisticsOrg(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>(orgList);
        return pageInfo;
    }

    @Override
    public List<EntrustRelationOrgInfoDto> queryStorageErLogisticsOrgByWarehouse(QueryStorageEntrustRelationLogisticsOrgParam queryParam) {
        return storageEntrustRelationMapper.queryStorageEntrustRelationLogisticsOrgByWarehouse(queryParam);
    }

    @Override
    public List<OrgListDto> queryLogisticOrgAndHasSettleableChildren(QueryStorageEntrustRelationLogisticsOrgParam reqParam) {
        return null;
    }
}
