package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.CustGenerateGroupParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 客商生成集团请求参数校验
 */
@Component("custGenerateGroupParamValidator")
public class CustGenerateGroupParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustGenerateGroupParamValidator.class);

	public void validate(CustGenerateGroupParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("客商生成集团请求参数为空");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(userId)) {
			LOGGER.warn("客商生成集团请求参数非法，userId：{}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.cust.generate.group.userid.error"));
		}

		final Long custId = reqParam.getCustId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(custId)) {
			LOGGER.warn("客商生成集团请求参数非法，custId：{}.", custId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.cust.generate.group.custid.error"));
		}
	}
}
