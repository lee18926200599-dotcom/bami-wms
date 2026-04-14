package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.LogisticsEntrustRelationBean;
import com.org.permission.server.org.bean.LogisticsEntrustRelationInfoBean;
import com.org.permission.server.org.dto.LogisticEntrustRelationDto;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.LogisticsEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.mapper.LogisticsEntrustRelationMapper;
import com.org.permission.server.org.service.LogisticsEntrustRelationService;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.common.base.enums.BooleanEnum;
import com.common.framework.user.FplUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 物流委托关系服务实现
 */
@Service("logisticsEntrustRelationService")
public class LogisticsEntrustRelationServiceImpl implements LogisticsEntrustRelationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsEntrustRelationServiceImpl.class);

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private LogisticsEntrustRelationMapper logisticsEntrustRelationMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private CustDomainService custDomainService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addLogisticsEntrustRelation(LogisticsEntrustRelationBean reqParam) {
        entrustRelationVerify.logisticEntrustRelationVerify(reqParam);

        reqParam.setEntrustType(EntrustTypeEnum.LOGISTICS.getIndex());
        reqParam.setEntrustRange(EntrustRangeEnum.INTER_GROUP.getIndex());
        reqParam.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        reqParam.setState(OrgStateEnum.CREATE.getCode());
        reqParam.setCreatedDate(new Date());
        reqParam.setCreatedBy(FplUserUtil.getUserId());
        reqParam.setCreatedName(FplUserUtil.getUserName());
        logisticsEntrustRelationMapper.addLogisticsEntrustRelation(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateLogisticsEntrustRelation(LogisticsEntrustRelationBean newRel) {
        final LogisticsEntrustRelationBean sourceRel = logisticsEntrustRelationMapper.queryEntrustRelationByIdLock(newRel.getId());
        if (Objects.isNull(sourceRel)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }
        //状态校验
        if (BooleanEnum.TRUE.getCode().equals(sourceRel.getDefaultFlag()) && BooleanEnum.FALSE.getCode().equals(newRel.getDefaultFlag())) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.logistics.only.default.cannot.modify"));
        }
        entrustRelationVerify.updateLogisticEntrustRelationVerify(newRel);
        newRel.setCreatedDate(new Date());
        logisticsEntrustRelationMapper.updateLogisticsEntrustRelation(newRel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableLogisticsEntrustRelation(EnableOperateParam reqParam) {
        entrustRelationVerify.enableLogisticEntrustRelationVerify(reqParam);
        commonEntrustRelationMapper.enableEntrustRelation(reqParam, new Date());
    }

    @Override
    public PageInfo<LogisticsEntrustRelationInfoBean> queryLogisticsEntrustRelationList(LogisticsEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<LogisticsEntrustRelationInfoBean> logisticsBeans = logisticsEntrustRelationMapper.queryLogisticsEntrustRelationList(reqParam);
        PageInfo<LogisticsEntrustRelationInfoBean> pageInfo = new PageInfo<>(logisticsBeans);
        return pageInfo;
    }

    @Override
    public List<LogisticEntrustRelationDto> queryLogisticsEntrustRelation(LogisticEntrustRelationQueryParam reqParam) {
        return logisticsEntrustRelationMapper.queryLogisticsEntrustRelation(reqParam);
    }
}
