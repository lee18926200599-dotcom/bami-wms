package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.OrgTreeLevelEnum;
import com.org.permission.common.org.param.QueryOrgTreeReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 组织树查询请求参数校验器
 */
@Component(value = "queryOrgTreeReqParamValidator")
public class QueryOrgTreeReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryOrgTreeReqParamValidator.class);

	@Resource
	private QueryByIdReqParamValidator queryByIdReqParamValidator;

	public void validate(final QueryOrgTreeReqParam reqParam) {
		queryByIdReqParamValidator.validate(reqParam);

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.lessThanZero(groupId)) {
			LOGGER.warn("query org tree , illegal req param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}

		OrgTreeLevelEnum.getOrgTreeLevelEnum(reqParam.getLevel());
	}
}
