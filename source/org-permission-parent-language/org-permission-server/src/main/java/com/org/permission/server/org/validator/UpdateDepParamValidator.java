package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.DepartmentReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 更新部门参数校验器
 */
@Component(value = "updateDepParamValidator")
public class UpdateDepParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDepParamValidator.class);

	public void validate(DepartmentReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("update department request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long modifiedBy = reqParam.getModifiedBy();
		if (NumericUtil.lessThanZero(modifiedBy)) {
			LOGGER.warn("update department illegal req param 【modifiedBy】= {}.", modifiedBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.modifiedBy.error"));
		}

		final Long id = reqParam.getId();
		if (NumericUtil.lessThanZero(id)) {
			LOGGER.warn("update department illegal req param 【id】= {}.", id);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
		}
	}
}
