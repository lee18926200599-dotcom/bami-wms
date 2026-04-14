package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.FinanceEntrustRelationInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationByUserPermissionParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.FinanceEntrustRelationInfoBean;
import com.org.permission.server.org.bean.StateBean;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.BizUnitMapper;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.mapper.FinanceEntrustRelationMapper;
import com.org.permission.server.org.service.FinanceEntrustRelationService;
import com.org.permission.server.org.service.event.CascadeCreateFinanceEntrustRelEvent;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.org.permission.server.org.util.HuToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 财务委托关系服务实现
 */
@Service("financeEntrustRelationService")
public class FinanceEntrustRelationServiceImpl implements FinanceEntrustRelationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceEntrustRelationServiceImpl.class);

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private BizUnitMapper bizUnitMapper;

    @Resource
    private EntrustRelationVerify entrustRelationVerify;

    @Resource
    private PermissionDomainService permissionDomainService;

    @Resource
    private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addFinanceEntrustRelation(FinanceEntrustRelationBean reqParam) {
        reqParam.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
        reqParam.setCreatedDate(new Date());

        cascadeCreateFinanceEntrustRelEvent.addIfNotExist(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateFinanceEntrustRelation(FinanceEntrustRelationBean reqParam) {
        reqParam.setModifiedDate(new Date());
        StateBean bean = commonEntrustRelationMapper.queryEntrustRelationStateByIdLock(reqParam.getId());
        if (!ObjectUtils.isEmpty(bean)) {
            financeEntrustRelationMapper.updateFinanceEntrustRelation(reqParam);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableFinanceEntrustRelation(EnableOperateParam reqParam) {
        // 财务委托关系本身就是重复的，不进行状态校验
        entrustRelationVerify.enableFinanceEntrustRelationVerify(reqParam);

        commonEntrustRelationMapper.enableEntrustRelation(reqParam, new Date());
    }

    @Override
    public PageInfo<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationList(FinanceEntrustRelationReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<FinanceEntrustRelationInfoBean> financeEntrustRelationInfoBeans = financeEntrustRelationMapper.queryFinanceEntrustRelationList(reqParam);
        return new PageInfo<>(financeEntrustRelationInfoBeans);
    }

    @Override
    public List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelation(QueryFinanceEntrustRelationParam reqParam) {
        return financeEntrustRelationMapper.queryFinanceEntrustRelation(reqParam);
    }

    @Override
    public List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationByUserPermiss(QueryFinanceEntrustRelationByUserPermissionParam reqParam) {
        final Long userId = reqParam.getUserId();
        final Long groupId = reqParam.getGroupId();

        // 获取用户组织权限ID集合
        Set<Long> permissionOrgIds = permissionDomainService.getOrgsInPermission(userId, groupId, OrgTypeEnum.ORGANIZATION);
        if (CollectionUtils.isEmpty(permissionOrgIds)) {
            LOGGER.warn("user hase not permission query finance entrust relation,reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.user.permission.insufficient"));
        }

        // 过滤具有财务组织职能的业务单元
        final List<Long> financeOrgIds = bizUnitMapper.fillHasFinanceOrgFuncBU(new ArrayList<>(permissionOrgIds));
        if (CollectionUtils.isEmpty(financeOrgIds)) {
            LOGGER.warn("in user permission range does not have finance org,reqParam:{}.", financeOrgIds);
            throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.finance.user.permission.no.finance.org"));
        }

        final Integer orgRole = reqParam.getOrgRole();
        return financeEntrustRelationMapper.queryFinanceEntrustRelationByOrgRole(financeOrgIds, orgRole);
    }

    @Override
    public Map<Long, List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelationBatch(List<Long> orgIds) {
        List<FinanceEntrustRelationInfoBean> resultData = financeEntrustRelationMapper.queryFinanceEntrustRelationBatch(orgIds);
        List<FinanceEntrustRelationInfoDto> resultDataresultDataresultData = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resultData)) {
            try {
                resultDataresultDataresultData = HuToolUtil.exchange(resultData, FinanceEntrustRelationInfoDto.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<Long, List<FinanceEntrustRelationInfoDto>> map = resultDataresultDataresultData.stream().collect(Collectors.groupingBy(FinanceEntrustRelationInfoDto::getBizOrgId));
        return map;
    }
}
