package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.QueryGroupListReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 集团信息列表查询请求参数
 */
@Component(value = "queryGroupListReqParamValidator")
public class QueryGroupListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryGroupListReqParamValidator.class);

	public void validate(QueryGroupListReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query group list req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
	}
}
