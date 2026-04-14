package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.AddBizUnitReqParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 新增业务单元版本请求参数校验器
 */
@Component(value = "saveNewVersionBizUnitRepParamValidator")
public class SaveNewVersionBizUnitRepParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveNewVersionBizUnitRepParamValidator.class);

	@Resource
	private BizUnitAddParamValidator bizUnitAddParamValidator;

	public void validate(AddBizUnitReqParam reqParam) {

		bizUnitAddParamValidator.validate(reqParam);

		final String version = reqParam.getVersion();
		if (StringUtils.isEmpty(version)) {
			LOGGER.warn("save biz unit new version request param 【version】= {}.", version);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.savenewversionbinzunit.version.error"));
		}
	}
}
