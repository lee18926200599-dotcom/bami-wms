package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.SaleEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 新增销售委托关系请求参数校验器
 */
@Component(value = "saleEntrustRelationReqParamValidator")
public class SaleEntrustRelationReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaleEntrustRelationReqParamValidator.class);

	public void validate(final SaleEntrustRelationParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add sale entrust relation req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long createdBy = reqParam.getCreatedBy();
		if (NumericUtil.lessThanZero(createdBy)) {
			LOGGER.warn("add sale entrust relation, illegal req param 【createdBy】= {}.", createdBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
		}

		final Integer defaultFlag = reqParam.getDefaultFlag();
		if (null == defaultFlag) {
			LOGGER.warn("add sale entrust relation , illegal req param 【defaultFlag】= null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.defaultFlag.error"));
		}

		final Long saleOrg = reqParam.getSaleOrgId();
		if (NumericUtil.lessThanZero(saleOrg)) {
			LOGGER.warn("add sale entrust relationship , illegal req param 【saleOrg】= {}.", saleOrg);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.saleOrg.error"));
		}

		final Long stockOrg = reqParam.getStockOrgId();
		if (NumericUtil.lessThanZero(stockOrg)) {
			LOGGER.warn("add sale entrust relationship , illegal req param 【stockOrg】= {}.", stockOrg);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.stockOrg.error"));
		}

		final Long receiveOrgId = reqParam.getReceiveOrgId();
		if (NumericUtil.lessThanZero(receiveOrgId)) {
			LOGGER.warn("add sale entrust relationship , illegal req param 【receiveOrgId】= {}.", receiveOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.receiveOrgId.error"));
		}

		final Long settleOrgId = reqParam.getSettleOrgId();
		if (NumericUtil.lessThanZero(settleOrgId)) {
			LOGGER.warn("add sale entrust relationship , illegal req param 【settleOrgId】= {}.", settleOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.settleOrgId.error"));
		}

		final String remark = reqParam.getRemark();
		if (!StringUtils.isEmpty(remark) && remark.length() > 50) {
			LOGGER.warn("add finance entrust relation, illegal req param 【remark】= {}.", remark);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.remark.error"));
		}
	}
}
