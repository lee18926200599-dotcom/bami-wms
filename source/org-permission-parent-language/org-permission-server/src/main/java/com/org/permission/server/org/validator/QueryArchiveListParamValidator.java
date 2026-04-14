package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.enums.ArchiveTypeEnum;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询档案请求参数
 */
@Component("queryArchiveListParamValidator")
public class QueryArchiveListParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryArchiveListParamValidator.class);

	public void validate(QueryArchiveListParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query archive request param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long groupId = reqParam.getGroupId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(groupId)) {
			LOGGER.warn("query archive request,illegal param 【groupId】= {}.", groupId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.groupid.error"));
		}

		final Integer archiveType = reqParam.getArchiveType();
		try {
			ArchiveTypeEnum.getArchiveTypeEnum(archiveType);
		} catch (Exception e) {
			LOGGER.warn("query archive request,illegal param 【archiveType】= {}.", archiveType);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryarchivelist.archiveType.error"));
		}
	}
}
