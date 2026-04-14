package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryStaffListReqParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 人员列表查询请求参数
 */
@Component("queryStaffListReqParamValidator")
public class QueryStaffListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryStaffListReqParamValidator.class);

	public void validate(QueryStaffListReqParam reqParam) {
		if (null == reqParam) {
			LOGGER.warn("query staff list req param is null");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
	}
}
