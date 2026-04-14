package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.GroupReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增集团请求参数校验
 */
@Component(value = "addGroupRepParamValidator")
public class AddGroupRepParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddGroupRepParamValidator.class);

	public void validate(GroupReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add group req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long createdBy = reqParam.getCreatedBy();
		if (NumericUtil.lessThanZero(createdBy)) {
			LOGGER.warn("add group , illegal req param 【createUser】= {}.", createdBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.createdby.error"));
		}

		final String orgName = reqParam.getOrgName();
		if (StringUtils.isEmpty(orgName)) {
			LOGGER.warn("add group , illegal req param 【orgName】= {}.", orgName);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.name.error"));
		}

		final String currency = reqParam.getCurrency();
		if (StringUtils.isEmpty(currency)) {
			LOGGER.warn("add group , illegal req param 【currency】= {}.", currency);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.currency.error"));
		}

		final Long custId = reqParam.getCustId();
		if (null != custId && custId.intValue() < 0) {
			LOGGER.warn("add group , illegal req param 【custId】= {}.", custId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.custid.error"));
		}

	}
}
