package com.org.permission.server.org.controller;

import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.LogisticsEntrustRelationBean;
import com.org.permission.server.org.bean.LogisticsEntrustRelationInfoBean;
import com.org.permission.server.org.dto.LogisticEntrustRelationDto;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationParam;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.LogisticsEntrustRelationReqParam;
import com.org.permission.server.org.service.LogisticsEntrustRelationService;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.EnableOperateReqParamValidator;
import com.org.permission.server.org.validator.LogisticsEntrustRelationReqParamValidator;
import com.org.permission.server.org.vo.LogisticsEntrustRelationInfoVo;
import com.org.permission.server.utils.NumericUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物流委托关系控制器
 */
@RestController
@Api(tags = "0物流委托关系接口文档")
@RequestMapping("logistics-entrust")
public class LogisticsEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsEntrustRelationController.class);

    @Resource
    private LogisticsEntrustRelationReqParamValidator logisticsEntrustRelationReqParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private LogisticsEntrustRelationService logisticsEntrustRelationService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private UserDomainService userDomainService;

    @ApiOperation(value = "新增业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addLogisticsEntrustRelation")
    public RestMessage<Boolean> addLogisticsEntrustRelation(@RequestBody LogisticEntrustRelationParam reqParam) {
        LOGGER.info("add logistics entrust relation req param :{}.", reqParam);
        try {
            logisticsEntrustRelationReqParamValidator.validate(reqParam);
            LogisticsEntrustRelationBean logisticsEntrustRelationBean = new LogisticsEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, logisticsEntrustRelationBean);
            LOGGER.info("add logistics entrust relation bean:{}.", logisticsEntrustRelationBean);

            logisticsEntrustRelationService.addLogisticsEntrustRelation(logisticsEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add logistics entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add logistics entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新物流业务委托关系", httpMethod = "POST")
    @PostMapping(value = "updateLogisticsEntrustRelation")
    public RestMessage<Boolean> updateLogisticsEntrustRelation(@RequestBody LogisticEntrustRelationParam reqParam) {
        LOGGER.info("update logistics entrust relation req param :{}.", reqParam);
        try {
            LogisticsEntrustRelationBean logisticsEntrustRelationBean = new LogisticsEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, logisticsEntrustRelationBean);

            LOGGER.debug("update logistics entrust relation bean:{}.", logisticsEntrustRelationBean);
            logisticsEntrustRelationService.updateLogisticsEntrustRelation(logisticsEntrustRelationBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add logistics entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add logistics entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停物流业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enableLogisticsEntrustRelation")
    public RestMessage<Boolean> enableLogisticsEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable Logistics entrust relation req param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);

            logisticsEntrustRelationService.enableLogisticsEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable Logistics entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable Logistics entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询物流委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryLogisticsEntrustRelationList")
    public RestMessage<PageInfo<LogisticsEntrustRelationInfoVo>> queryLogisticsEntrustRelationList(@RequestBody LogisticsEntrustRelationReqParam reqParam) {
        LOGGER.info("page query logistics entrust relation req param :{}.", reqParam);
        try {
            List<Long> ids = new ArrayList<>();
            if (!ObjectUtils.isEmpty(reqParam.getConditionName())) {
                List<Long> result = custDomainService.getCustInfoListByName(reqParam.getConditionName());
                if (!ObjectUtils.isEmpty(result)) {
                    ids.addAll(result);
                    reqParam.setIds(ids);
                }
            }
            final PageInfo<LogisticsEntrustRelationInfoBean> logisticsList = logisticsEntrustRelationService.queryLogisticsEntrustRelationList(reqParam);
            return wrapPagedList(logisticsList);
        } catch (OrgException oex) {
            LOGGER.info("page query logistics entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query logistics entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询物流委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryLogisticsEntrustRelation")
    public RestMessage<List<LogisticEntrustRelationDto>> queryLogisticsEntrustRelation(@RequestBody LogisticEntrustRelationQueryParam reqParam) {
        LOGGER.info("query logistics entrust relation req param :{}.", reqParam);
        try {
            final List<LogisticEntrustRelationDto> entrustRelationDtos = logisticsEntrustRelationService.queryLogisticsEntrustRelation(reqParam);
            return RestMessage.doSuccess(entrustRelationDtos);
        } catch (OrgException oex) {
            LOGGER.info("query logistics entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query logistics entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装分页查询结果
     *
     * @param logisticsPage 分页查询结果
     * @return 物流委托关系视图实体集合
     */
    private RestMessage<PageInfo<LogisticsEntrustRelationInfoVo>> wrapPagedList(final PageInfo<LogisticsEntrustRelationInfoBean> logisticsPage) {
        PageInfo<LogisticsEntrustRelationInfoVo> pageInfo = PageUtil.convert(logisticsPage, item -> {
            LogisticsEntrustRelationInfoVo vo = new LogisticsEntrustRelationInfoVo();
            BeanUtil.copyProperties(item, vo);
            return vo;
        });
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            fillName(pageInfo.getList());
            userDomainService.batchFillUserName(pageInfo.getList());
        }
        return RestMessage.doSuccess(pageInfo);
    }

    /**
     * 填充名字
     *
     * @param logisticsInfoVos 完整信息的采销委托关系
     */
    private void fillName(final List<LogisticsEntrustRelationInfoVo> logisticsInfoVos) {

        Set<Long> logisticsProviderIds = new HashSet<>(logisticsInfoVos.size());
        for (LogisticsEntrustRelationInfoVo logisticsInfoVo : logisticsInfoVos) {
            final Long logisticsProviderId = logisticsInfoVo.getLogisticsProviderId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(logisticsProviderId)) {
                logisticsProviderIds.add(logisticsProviderId);
            }
            final Long relevanceLogisticsProviderId = logisticsInfoVo.getRelevanceLogisticsProviderId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(relevanceLogisticsProviderId)) {
                logisticsProviderIds.add(relevanceLogisticsProviderId);
            }
        }

        final Map<Long, String> cunstNameMap = custDomainService.batchQueryCustInfoByIds(logisticsProviderIds);
        for (LogisticsEntrustRelationInfoVo logisticsInfoVo : logisticsInfoVos) {
            logisticsInfoVo.setLogisticsProviderName(cunstNameMap.get(logisticsInfoVo.getLogisticsProviderId()));
            final String relevanceLogisticsProviderName = cunstNameMap.get(logisticsInfoVo.getRelevanceLogisticsProviderId());
            logisticsInfoVo.setRelevanceLogisticsProviderName(relevanceLogisticsProviderName);
        }
    }
}
