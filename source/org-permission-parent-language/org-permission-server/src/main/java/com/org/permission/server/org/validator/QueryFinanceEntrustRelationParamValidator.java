package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 财务委托关系查询请求参数校验器
 */
@Component(value = "queryFinanceEntrustRelationParamValidator")
public class QueryFinanceEntrustRelationParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFinanceEntrustRelationParamValidator.class);


	public void validate(final QueryFinanceEntrustRelationParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query finance entrust relation req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		if (NumericUtil.greterThanZero(reqParam.getBizOrgId()) ||
				NumericUtil.greterThanZero(reqParam.getAccountOrgId()) ||
				NumericUtil.greterThanZero(reqParam.getSettleOrgId())) {
			return;
		}
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryentrustrelation.all.null"));
	}
}
