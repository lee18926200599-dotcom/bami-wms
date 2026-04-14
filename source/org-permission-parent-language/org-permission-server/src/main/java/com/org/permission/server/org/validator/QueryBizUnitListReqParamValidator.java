package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryBizUnitListReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 业务单元列表查询请求参数校验器
 */
@Component(value = "queryBizUnitListReqParamValidator")
public class QueryBizUnitListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryGroupListReqParamValidator.class);

	public void validate(QueryBizUnitListReqParam reqParam) {

		if (reqParam == null) {
			LOGGER.warn("query biz unit list req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.lessThanZero(userId)) {
			LOGGER.warn("query biz unit list , illegal req param 【userId】= {}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
		}
	}
}
