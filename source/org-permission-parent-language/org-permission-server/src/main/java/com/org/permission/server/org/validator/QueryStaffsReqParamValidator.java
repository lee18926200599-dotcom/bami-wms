package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryStaffsReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 人员查询请求参数校验器
 */
@Component(value = "queryStaffsReqParamValidator")
public class QueryStaffsReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryStaffsReqParamValidator.class);

	public void validate(QueryStaffsReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query staffs req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long orgId = reqParam.getOrgId();
		if (NumericUtil.lessThanZero(orgId)) {
			LOGGER.warn("query staffs , illegal req param 【orgId】= {}.", orgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.orgid.error"));
		}
	}
}
