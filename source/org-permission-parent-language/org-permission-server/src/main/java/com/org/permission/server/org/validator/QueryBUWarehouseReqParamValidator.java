package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.BUWarehouseReqParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询业务单元仓库请求参数校验器
 */
@Component(value = "queryBUWarehouseReqParamValidator")
public class QueryBUWarehouseReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryBUWarehouseReqParamValidator.class);

	public void validate(BUWarehouseReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query biz unit warehouseId req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long buId = reqParam.getBuId();
		if (NumericUtil.nullOrlessThanOrEqualToZero(buId)) {
			LOGGER.warn("query biz unit warehouseId,illegal req param 【buId】= {}.", buId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.buId.error"));
		}
	}
}
