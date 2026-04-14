package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增业务委托关系请求参数校验器
 */
@Component(value = "logisticsEntrustRelationReqParamValidator")
public class LogisticsEntrustRelationReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsEntrustRelationReqParamValidator.class);

    public void validate(final LogisticEntrustRelationParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("add logistics entrust relation req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }
        final Integer defaultFlag = reqParam.getDefaultFlag();
        if (defaultFlag == null) {
            LOGGER.warn("add logistics entrust relation, illegal req param 【defaultFlag】= {}.", defaultFlag);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.defaultFlag.error"));
        }

        final Long logisticsProvider = reqParam.getLogisticsProviderId();
        if (NumericUtil.lessThanZero(logisticsProvider)) {
            LOGGER.warn("add logistic entrust relation, illegal req param【logisticsProvider={}.", logisticsProvider);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.logisticsProvider.error"));
        }

        final Long logisticsOrg = reqParam.getLogisticsOrgId();
        if (NumericUtil.lessThanZero(logisticsOrg)) {
            LOGGER.warn("add logistic entrust relation, illegal req param 【logisticsOrg】={}.", logisticsOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.logisticsOrg.error"));
        }

        final Long relevanceLogisticsProvider = reqParam.getRelevanceLogisticsProviderId();
        if (NumericUtil.lessThanZero(relevanceLogisticsProvider)) {
            LOGGER.warn("add logistic entrust relation, illegal req param【relevanceLogisticsProvider={}.", relevanceLogisticsProvider);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.relevanceLogisticsProvider.error"));
        }

        final Long relevanceLogisticsOrg = reqParam.getRelevanceLogisticsOrgId();
        if (NumericUtil.lessThanZero(relevanceLogisticsOrg)) {
            LOGGER.warn("add logistic entrust relation, illegal req param 【relevanceLogisticsOrg】= {}.", relevanceLogisticsOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.relevanceLogisticsOrg.error"));
        }

        if (logisticsProvider.equals(relevanceLogisticsProvider)) {
            LOGGER.warn("add logistic entrust relation, illegal req param 【logisticsProvider】= {}," + "【relevanceLogisticsProvider】={}.", logisticsProvider, relevanceLogisticsProvider);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.relevance.error"));
        }

    }
}
