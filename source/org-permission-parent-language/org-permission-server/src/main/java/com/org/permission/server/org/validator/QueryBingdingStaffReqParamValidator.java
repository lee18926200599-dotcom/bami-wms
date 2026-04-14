package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.QueryBingdingStaffReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询绑定人员请求参数校验器
 */
@Component("queryBingdingStaffReqParamValidator")
public class QueryBingdingStaffReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryBingdingStaffReqParamValidator.class);

	public void validate(QueryBingdingStaffReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query bingding staffs request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long bingdingId = reqParam.getBingdingId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(bingdingId)) {
			LOGGER.warn("query bingding staffs request,illegal param 【bingdingId】= {}.", bingdingId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.querybindstaff.bingdingId.error"));
		}

	}
}
