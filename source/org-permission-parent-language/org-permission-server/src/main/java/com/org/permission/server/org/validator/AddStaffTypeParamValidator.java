package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.StaffTypeParam;
import com.org.permission.server.org.enums.StaffTypeLevelEnum;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增人员类别请求参数校验器
 */
@Component(value = "addStaffTypeReqParamValidator")
public class AddStaffTypeParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddStaffTypeParamValidator.class);

	public void validate(final StaffTypeParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add staff type request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long createdBy = reqParam.getCreatedBy();
		if (NumericUtil.lessThanZero(createdBy)) {
			LOGGER.warn("add staff type illegal req param 【createUser】= {}.", createdBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.createdby.error"));
		}

		final Integer typeLevel = reqParam.getTypeLevel();
		try {
			StaffTypeLevelEnum.getStaffTypeLevelEnum(typeLevel);
		} catch (Exception e) {
			LOGGER.warn("add staff type illegal req param 【typeLevel】= {}.", typeLevel);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.level.error"));
		}

		final Long parentId = reqParam.getParentId();
		if (NumericUtil.lessThanZero(parentId)) {
			LOGGER.warn("add staff type illegal req param 【parentId】= {}.", parentId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.parent.error"));
		}

		final String typeName = reqParam.getTypeName();
		if (StringUtils.isEmpty(typeName)) {
			LOGGER.warn("add staff type illegal req param 【typeName】= {}.", typeName);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.name.error"));
		}
	}
}
