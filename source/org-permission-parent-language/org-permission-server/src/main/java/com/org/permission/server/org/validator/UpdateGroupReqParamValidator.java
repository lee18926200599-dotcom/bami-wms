package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.GroupReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 更新集团请求参数
 */
@Component(value = "updateGroupReqParamValidator")
public class UpdateGroupReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateGroupReqParamValidator.class);

	public void validate(GroupReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("update group req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long modifiedBy = reqParam.getModifiedBy();
		if (NumericUtil.lessThanZero(modifiedBy)) {
			LOGGER.warn("update group , illegal req param 【modifiedBy】= {}.", modifiedBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.modifiedBy.error"));
		}
	}
}
