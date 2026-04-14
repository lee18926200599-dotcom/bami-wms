package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.BingdingStaffReqParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 绑定人员请求参数校验器
 */
@Component("bingdingStaffReqParamValidator")
public class BingdingStaffReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BingdingStaffReqParamValidator.class);

	public void validate(BingdingStaffReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("bingding staffs request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final List<StaffInfoDto> staffIds = reqParam.getStaffs();
		if (CollectionUtils.isEmpty(staffIds)) {
			LOGGER.warn("bingding staffs request,illegal param 【staffIds】= {}.", staffIds);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bind.staff.ids.error"));
		}
	}
}
