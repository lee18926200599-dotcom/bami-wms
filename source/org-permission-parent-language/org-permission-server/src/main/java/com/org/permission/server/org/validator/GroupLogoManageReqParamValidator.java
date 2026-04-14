package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.ManageGroupLogo;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 集团logo管理请求参数校验器
 */
@Component(value = "groupLogoManageReqParamValidator")
public class GroupLogoManageReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupLogoManageReqParamValidator.class);

	public void validate(ManageGroupLogo reqParam) {
		if (reqParam == null) {
			LOGGER.warn("group logo manage request, param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.lessThanZero(userId)) {
			LOGGER.warn("group logo manage illegal request param 【userId】= {}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.grouplogo.userid.error"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.lessThanZero(groupId)) {
			LOGGER.warn("group logo manage illegal request param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.grouplogo.groupid.error"));
		}
	}
}
