package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.QueryCustBUByFuncReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 依据组织职能查询客商下组织简要信息请求参数
 */
@Component(value = "queryCustBUByFuncReqParamValidator")
public class QueryCustBUByFuncReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCustBUByFuncReqParamValidator.class);

	public void validate(QueryCustBUByFuncReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query crm biz unit by function req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long custId = reqParam.getCustId();
		if (NumericUtil.lessThanZero(custId)) {
			LOGGER.warn("query crm biz unit by function,illegal req param 【custId】= {}.", custId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.custId.error"));
		}
	}
}
