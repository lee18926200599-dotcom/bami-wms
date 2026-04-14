package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.BindingCustReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 绑定客户请求参数
 */
@Component(value = "bindingCustReqParamValidator")
public class BindingCustReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BindingCustReqParamValidator.class);

	public void validate(BindingCustReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("binding crm req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long orgId = reqParam.getOrgId();
		if (NumericUtil.lessThanZero(orgId)) {
			LOGGER.warn("binding crm,illegal req param 【orgId】= {}.", orgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bind.cust.orgid.error"));
		}
	}
}
