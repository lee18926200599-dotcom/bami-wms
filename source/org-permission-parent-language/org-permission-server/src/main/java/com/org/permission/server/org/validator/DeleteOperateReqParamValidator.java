package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 删除操作请求参数校验器
 */
@Component(value = "deleteOperateReqParamValidator")
public class DeleteOperateReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteOperateReqParamValidator.class);

	public void validate(KeyOperateParam reqParam) {

		if (reqParam == null) {
			LOGGER.warn("delete operate req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long userId = reqParam.getUserId();
		if (NumericUtil.lessThanZero(userId)) {
			LOGGER.warn("delete operate , illegal req param 【userId】= {}.", userId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.delete.operate.userid.error"));
		}

		final Long id = reqParam.getId();
		if (NumericUtil.lessThanZero(id)) {
			LOGGER.warn("delete operate , illegal req param 【id】= {}.", id);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.delete.operate.id.error"));
		}
	}
}
