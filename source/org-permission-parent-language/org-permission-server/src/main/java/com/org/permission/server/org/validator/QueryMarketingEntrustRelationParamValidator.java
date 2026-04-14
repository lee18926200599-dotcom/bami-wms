package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.MarketEntrustRelationQueryParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 查询采销委托关系查询请求参数校验器
 */
@Component(value = "queryMarketingEntrustRelationParamValidator")
public class QueryMarketingEntrustRelationParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryMarketingEntrustRelationParamValidator.class);

	public void validate(final MarketEntrustRelationQueryParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query marketing entrust relation req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		if (NumericUtil.greterThanZero(reqParam.getOwnerId()) ||
				NumericUtil.greterThanZero(reqParam.getMarketingOrgId()) ||
				NumericUtil.greterThanZero(reqParam.getWarehouseProviderId()) ||
				NumericUtil.greterThanZero(reqParam.getStockOrgId()) ||
				NumericUtil.greterThanZero(reqParam.getWarehouseId()) ||
				StringUtils.hasLength(reqParam.getOriAccCode()) ||
				Objects.isNull(reqParam.getDefaultFlag())) {
			return;
		}

		LOGGER.warn("query marketing entrust relation illegal req param.");
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
	}
}
