package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ID查询请求参数校验器
 */
@Component(value = "queryByIdReqParamValidator")
public class QueryByIdReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryByIdReqParamValidator.class);

	public void validate(final QueryByIdReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query by id req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
	}
}
