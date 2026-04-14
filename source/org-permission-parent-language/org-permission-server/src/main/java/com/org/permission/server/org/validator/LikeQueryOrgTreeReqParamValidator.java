package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.MultipleOrgTreeQueryDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 模糊查询请求参数校验器
 */
@Component(value = "likeQueryOrgTreeReqParamValidator")
public class LikeQueryOrgTreeReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupLogoManageReqParamValidator.class);

	public void validate(MultipleOrgTreeQueryDto reqParam) {
		if (reqParam == null) {
			LOGGER.warn("like query org tree request, param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final String orgName = reqParam.getOrgName();
		if (StringUtils.isEmpty(orgName)) {
			LOGGER.warn("like query org tree request param 【orgName】= {}.", orgName);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.likequeryorgtree.orgname.error"));
		}
	}
}

