package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.QueryEntrustRelationshipListReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询业务委托关系列表请求参数校验器
 */
@Component(value = "queryEntrustRelationshipListReqParamValidator")
public class QueryEntrustRelationshipListReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryEntrustRelationshipListReqParamValidator.class);

	public void validate(QueryEntrustRelationshipListReqParam reqParam) {

		if (reqParam == null) {
			LOGGER.warn("query entrust relationship list req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.lessThanZero(userId)) {
			LOGGER.warn("query entrust relationship list , illegal req param 【userId】= {}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
		}
	}
}
