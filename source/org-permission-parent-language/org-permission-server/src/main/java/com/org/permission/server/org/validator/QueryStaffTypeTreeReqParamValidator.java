package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询人员类别树请求参数校验器
 */
@Component(value = "queryStaffTypeTreeReqParamValidator")
public class QueryStaffTypeTreeReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddStaffReqParamValidator.class);

	public void validate(final QueryStaffTypeTreeReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query staff type tree request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long belongOrg = reqParam.getBelongOrg();
		if (NumericUtil.lessZeroNotNull(belongOrg)) {
			LOGGER.warn("query staff type tree illegal req param 【belongOrg】= {}.", belongOrg);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.querystafftypetree.belongOrg.error"));
		}
	}
}
