package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.MarketEntrustRelationDto;
import com.org.permission.common.org.dto.WarehouseEnterOwnerRankingListDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.MarketEntrustRelationQueryParam;
import com.org.permission.common.org.param.WarehouseEnterOwnerRankingListParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.MarketEntrustRelationBean;
import com.org.permission.server.org.bean.MarketEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.InitCustEntrustRelParam;
import com.org.permission.server.org.dto.param.MarketEntrustRelationReqParam;
import com.org.permission.server.org.enums.*;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.mapper.MarketEntrustRelationMapper;
import com.org.permission.server.org.service.MarketEntrustRelationshipService;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.common.base.enums.BooleanEnum;
import com.common.framework.user.FplUserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 采销委托关系实现
 */
@Service("marketEntrustRelationshipService")
public class MarketEntrustRelationshipServiceImpl implements MarketEntrustRelationshipService {

    @Resource
    private MarketEntrustRelationMapper marketEntrustRelationMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private CustDomainService custDomainService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addMarketEntrustRelation(MarketEntrustRelationBean reqParam) {

        entrustRelationVerify.marketEntrustRelationVerify(reqParam);

        reqParam.setEntrustType(EntrustTypeEnum.PURCHASEANDSALE.getIndex());
        reqParam.setEntrustRange(EntrustRangeEnum.INTER_GROUP.getIndex());
        reqParam.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        reqParam.setCreatedDate(new Date());
        reqParam.setCreatedBy(FplUserUtil.getUserId());
        reqParam.setCreatedName(FplUserUtil.getUserName());
        reqParam.setState(OrgStateEnum.CREATE.getCode());
        marketEntrustRelationMapper.addMarketEntrustRelation(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateMarketEntrustRelation(MarketEntrustRelationBean newRel) {
        final MarketEntrustRelationBean sourceRel = marketEntrustRelationMapper.queryEntrustRelationById(newRel.getId());
        if (Objects.isNull(sourceRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }
        if (BooleanEnum.TRUE.getCode().equals(sourceRel.getDefaultFlag()) && BooleanEnum.TRUE.getCode().equals(newRel.getDefaultFlag())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.logistics.only.default.cannot.modify"));
        }
        entrustRelationVerify.updateMarketEntrustRelationVerify(newRel);// 设置默认委托关系 ID
        newRel.setModifiedDate(new Date());
        marketEntrustRelationMapper.updateMarketEntrustRelation(newRel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableMarketEntrustRelation(EnableOperateParam reqParam) {
        //启用状态生成全局委托关系
        entrustRelationVerify.enableMarketEntrustRelationVerify(reqParam);
        commonEntrustRelationMapper.enableEntrustRelation(reqParam, new Date());
        final MarketEntrustRelationBean enableRelation = marketEntrustRelationMapper.queryEntrustRelationById(reqParam.getId());
        InitCustEntrustRelParam initCustEntrustRelParam = new InitCustEntrustRelParam();
        initCustEntrustRelParam.setDelegationType(CustEntrustTypeEnum.MARKET.getType());
        initCustEntrustRelParam.setOperateUser(reqParam.getUserId());
        initCustEntrustRelParam.setOperateUserName(reqParam.getUserName());
        initCustEntrustRelParam.setMainCustId(enableRelation.getOwnerId());
        initCustEntrustRelParam.setMainBUId(enableRelation.getMarketOrgId());
        initCustEntrustRelParam.setEntrustBUId(enableRelation.getStockOrgId());
        initCustEntrustRelParam.setEntrustCustId(enableRelation.getWarehouseProviderId());
        initCustEntrustRelParam.setState(reqParam.getState());
        custDomainService.initCustEntrustRel(initCustEntrustRelParam);
    }

    @Override
    public PageInfo<MarketEntrustRelationInfoBean> queryMarketEntrustRelationList(MarketEntrustRelationReqParam reqParam) {

        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<MarketEntrustRelationInfoBean> marketEntrustRelationDetailBeans = marketEntrustRelationMapper.queryMarketEntrustRelationList(reqParam);
        PageInfo<MarketEntrustRelationInfoBean> pageInfo = new PageInfo<>(marketEntrustRelationDetailBeans);
        return pageInfo;
    }

    @Override
    public List<MarketEntrustRelationDto> queryMarketEntrustRelation(MarketEntrustRelationQueryParam reqParam) {
        return marketEntrustRelationMapper.queryMarketEntrustRelation(reqParam);
    }

    @Override
    public List<WarehouseEnterOwnerRankingListDto> warehouseEnterOwnerRankingList(WarehouseEnterOwnerRankingListParam reqParam) {
        return marketEntrustRelationMapper.warehouseEnterOwnerRankingList(reqParam);
    }
}
