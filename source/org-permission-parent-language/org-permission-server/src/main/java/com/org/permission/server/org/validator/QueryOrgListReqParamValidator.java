package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.OrgListFilterLevelEnum;
import com.org.permission.common.org.param.QueryOrgListReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询组织列表请求参数校验器
 */
@Component(value = "queryOrgListReqParamValidator")
public class QueryOrgListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryOrgListReqParamValidator.class);

	public void validate(QueryOrgListReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query org list request, param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long orgId = reqParam.getOrgId();
		if (NumericUtil.lessThanZero(orgId)) {
			LOGGER.warn("query org list request param 【orgId】= {}.", orgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.orgid.error"));
		}
		final Integer filterLevel = reqParam.getFilterLevel();
		try {
			OrgListFilterLevelEnum.getOrgListFilterLevelEnum(filterLevel);
		} catch (Exception e) {
			LOGGER.warn("query org list request param 【filterLevel】= {}.", filterLevel);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryorglist.filterLevel.error"));
		}
	}
}

