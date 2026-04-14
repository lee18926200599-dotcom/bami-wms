package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.SyncCustBizTypeParam;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 同步客商请求参数校验器
 */
@Component("syncCustBizTypeParamValidator")
public class SyncCustBizTypeParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustGenerateGroupParamValidator.class);

	public void validate(SyncCustBizTypeParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("sync cust biz type req param is null");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(groupId)) {
			LOGGER.warn("sync cust biz type illegal param，groupId：{}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}

		final String newBizType = reqParam.getNewBizType();
		if (StringUtils.isEmpty(newBizType)) {
			LOGGER.warn("sync cust biz type illegal param，newBizType：{}.", newBizType);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sync.bizType.new.error"));
		}
	}
}
