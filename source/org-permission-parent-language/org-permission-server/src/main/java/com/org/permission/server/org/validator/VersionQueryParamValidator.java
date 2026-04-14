package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.VersionQueryParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 版本查询参数校验器
 */
@Component("versionQueryParamValidator")
public class VersionQueryParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(VersionQueryParamValidator.class);

	public void validate(VersionQueryParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("version query request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long buId = reqParam.getBuId();
		if (NumericUtil.lessThanZero(buId)) {
			LOGGER.warn("version query request param, illegal req param 【buId】= {}.", buId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.buId.error"));
		}

		final String version = reqParam.getVersion();
		if (StringUtils.isEmpty(version)) {
			LOGGER.warn("version query request param, illegal req param 【version】= {}.", version);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.savenewversionbinzunit.version.error"));
		}
	}
}
