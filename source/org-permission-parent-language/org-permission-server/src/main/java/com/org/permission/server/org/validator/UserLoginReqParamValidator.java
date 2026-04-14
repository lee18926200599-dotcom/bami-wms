package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.UserLoginReqParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户登陆请求参数校验器
 */
@Component(value = "userLoginReqParamValidator")
public class UserLoginReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginReqParamValidator.class);

	public void validate(UserLoginReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("user login request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
	}
}
