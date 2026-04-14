package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.FinanceOrgRoleEnum;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationByUserPermissionParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 *根据用户权限查询财务委托关系参数校验器
 */
@Component("queryFinanceEntrustRelationByUserPermissionParamValidator")
public class QueryFinanceEntrustRelationByUserPermissionParamValidator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryFinanceEntrustRelationByUserPermissionParamValidator.class);

	public void validate(QueryFinanceEntrustRelationByUserPermissionParam reqParam) {
		if (Objects.isNull(reqParam)) {
			LOGGER.warn("query finance entrust relation by user permission req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(userId)) {
			LOGGER.warn("query finance entrust relation by user permission,illegal param userId:{}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(groupId)) {
			LOGGER.warn("query finance entrust relation by user permission,illegal param groupId :{}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}

		try {
			FinanceOrgRoleEnum.getFinanceUserRoleEnum(reqParam.getOrgRole());
		} catch (Exception ignored) {
			LOGGER.warn("query finance entrust relation by user permission,illegal param :{}.", reqParam.getOrgRole());
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryentrustrelation.orgrole.error"));
		}

	}
}
