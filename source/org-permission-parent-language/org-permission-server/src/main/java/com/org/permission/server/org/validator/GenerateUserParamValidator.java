package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.GenerateUserParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 生成用户请求参数
 */
@Component("generateUserParamValidator")
public class GenerateUserParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateUserParamValidator.class);

	public void validate(GenerateUserParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("generate user  req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
		final Long operaterId = reqParam.getOperaterId();
		if (NumericUtil.lessThanZero(operaterId)) {
			LOGGER.warn("generate user,illegal req param 【operaterId】= {}.", operaterId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.generate.user.operaterId.error"));
		}

		final List<Long> staffIds = reqParam.getStaffIds();
		if (CollectionUtils.isEmpty(staffIds)) {
			LOGGER.warn("generate user,illegal req param 【staffIds】= {}.", staffIds);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.generate.user.staffIds.error"));
		}
	}
}
