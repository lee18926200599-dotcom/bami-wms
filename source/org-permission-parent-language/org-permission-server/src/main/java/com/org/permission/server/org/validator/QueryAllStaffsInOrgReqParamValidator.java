package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryAllStaffsInOrgReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 根据组织查询人员请求参数校验器
 */
@Component(value = "queryAllStaffsInOrgReqParamValidator")
public class QueryAllStaffsInOrgReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryAllStaffsInOrgReqParamValidator.class);

	public void validate(QueryAllStaffsInOrgReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query org staffs by org id request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long orgId = reqParam.getOrgId();
		if (NumericUtil.lessThanZero(orgId)) {
			LOGGER.warn("query org staffs by org id request,illegal param 【orgId】= {}.", orgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryallstaffinorg.orgId.error"));
		}

		final Integer staffstate = reqParam.getStaffstate();
		if (!(staffstate == null || staffstate == 2 || staffstate == 3)) {
			LOGGER.warn("query org staffs by org id request,illegal param 【staffstate】= {}.", staffstate);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryallstaffinorg.staffstate.error"));
		}

		final Integer associateUser = reqParam.getAssociateUser();
		if (!(associateUser == null || associateUser == 0 || associateUser == 1)) {
			LOGGER.warn("query org staffs by org id request,illegal param 【associateUser】= {}.", associateUser);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryallstaffinorg.associateUser.error"));
		}

		if (reqParam.isPermissionControl()) {
			final Long groupId = reqParam.getGroupId();
			if (NumericUtil.lessThanZero(groupId)) {
				LOGGER.warn("query org staffs request,illegal param 【groupId】= {}.", groupId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryallstaffinorg.groupId.error"));
			}

			final Long userId = reqParam.getUserId();
			if (NumericUtil.lessThanZero(userId)) {
				LOGGER.warn("query org staffs request,illegal param 【userId】= {}.", userId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryallstaffinorg.userid.error"));
			}
		}
	}
}
