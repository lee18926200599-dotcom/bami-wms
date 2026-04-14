package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 新增财务业务委托关系请求参数校验器
 */
@Component(value = "financeEntrustRelationReqParamValidator")
public class FinanceEntrustRelationReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceEntrustRelationReqParamValidator.class);

	public void validate(final FinanceEntrustRelationParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add finance entrust relation req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long createdBy = reqParam.getCreatedBy();
		if (NumericUtil.lessThanZero(createdBy)) {
			LOGGER.warn("add finance entrust relation ,illegal req param 【createdBy】= {}.", createdBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.createdBy.error"));
		}

		final Long bizOrgId = reqParam.getBizOrgId();
		if (NumericUtil.lessThanZero(bizOrgId)) {
			LOGGER.warn("add finance entrust relation, illegal req param 【bizOrgId】= {}.", bizOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.bizOrgId.error"));
		}

		final Long accountOrg = reqParam.getAccountOrgId();
		if (NumericUtil.lessThanZero(accountOrg)) {
			LOGGER.warn("add finance entrust relation, illegal req param 【accountOrg】= {}.", accountOrg);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.accountOrg.error"));
		}

		final Long settleOrgId = reqParam.getSettleOrgId();
		if (NumericUtil.lessThanZero(settleOrgId)) {
			LOGGER.warn("add finance entrust relation, illegal req param 【settleOrgId】= {}.", settleOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.settleOrgId.error"));
		}

		final String remark = reqParam.getRemark();
		if (!StringUtils.isEmpty(remark) && remark.length() > 50) {
			LOGGER.warn("add finance entrust relation, illegal req param 【remark】= {}.", remark);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.remark.error"));
		}
	}
}
