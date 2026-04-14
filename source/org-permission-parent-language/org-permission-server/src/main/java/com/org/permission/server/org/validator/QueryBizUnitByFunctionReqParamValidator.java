package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 依据组织职能查询集团下组织简要信息请求参数
 */
@Component(value = "queryBizUnitByFunctionReqParamValidator")
public class QueryBizUnitByFunctionReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryBizUnitByFunctionReqParamValidator.class);

	public void validate(QueryGroupBUByFuncReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query biz unit by function req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.lessThanZero(groupId)) {
			LOGGER.warn("query biz unit by function,illegal req param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}
	}
}
