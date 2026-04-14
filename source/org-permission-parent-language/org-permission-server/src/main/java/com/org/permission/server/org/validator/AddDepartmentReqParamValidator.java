package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.DepartmentReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增部门请求参数校验器
 */
@Component(value = "addDepartmentReqParamValidator")
public class AddDepartmentReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddDepartmentReqParamValidator.class);

	public void validate(DepartmentReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add department request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long createdBy = reqParam.getCreatedBy();
		if (NumericUtil.lessThanZero(createdBy)) {
			LOGGER.warn("add department illegal req param 【createUser】= {}.", createdBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.department.createdby.error"));
		}

		final String depName = reqParam.getDepName();
		if (StringUtils.isEmpty(depName)) {
			LOGGER.warn("add department illegal req param 【depName】= {}.", depName);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.department.name.error"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.lessThanZero(groupId)) {
			LOGGER.warn("add department illegal req param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.department.groupid.error"));
		}

		final Long parentBUId = reqParam.getParentBUId();
		final Long parentId = reqParam.getParentId();
		if (NumericUtil.lessThanZero(parentBUId) && NumericUtil.lessThanZero(parentId)) {
			LOGGER.warn("add department illegal req param 【parentBUId】= {},【parentId】= {}.", parentBUId, parentId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.department.parentid.error"));
		}
	}
}
