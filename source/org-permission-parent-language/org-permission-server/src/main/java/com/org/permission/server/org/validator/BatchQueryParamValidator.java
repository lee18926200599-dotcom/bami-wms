package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.common.util.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 批量查询请求参数校验器
 */
@Component("batchQueryParamValidator")
public class BatchQueryParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchQueryParamValidator.class);

	public void validate(BatchQueryParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("batch query org info req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
		if (!CollectionUtils.isEmpty(reqParam.getCodes()) && !CollectionUtils.isEmpty(reqParam.getNames())) {
			AssertUtils.isTrue(false, I18nUtils.getMessage("org.validator.batchquery.code.name.error"));
		}
	}
}
