package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryOrgPermissionListReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 组织权限树查询请求参数
 */
@Component(value = "queryOrgPermissionListReqParamValidator")
public class QueryOrgPermissionListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryOrgPermissionListReqParamValidator.class);

	public void validate(QueryOrgPermissionListReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query org permission list request, param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.lessThanZero(groupId)) {
			LOGGER.warn("query org permission list request param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}
		final Long userId = reqParam.getUserId();
		if (NumericUtil.lessThanZero(userId)) {
			LOGGER.warn("query org permission list request param 【userId】= {}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
		}
	}
}
