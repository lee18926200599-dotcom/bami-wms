package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.MarketEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 新增业务委托关系请求参数校验器
 */
@Component(value = "marketEntrustRelationReqParamValidator")
public class MarketEntrustRelationReqParamValidator {
	@Resource
	private BaseEntrustRelationValidator baseEntrustRelationValidator;
	private static final Logger LOGGER = LoggerFactory.getLogger(MarketEntrustRelationReqParamValidator.class);

	public void validate(final MarketEntrustRelationParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add market entrust relation req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}
		final Integer defaultFlag = reqParam.getDefaultFlag();
		if (defaultFlag == null) {
			LOGGER.warn("add market entrust relationship , illegal req param 【defaultFlag】= null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.defaultFlag.error"));
		}

		final Long ownerId = reqParam.getOwnerId();
		if (NumericUtil.lessThanZero(ownerId)) {
			LOGGER.warn("add market entrust relationship , illegal req param 【ownerId】= {}.", ownerId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.select.owner.error"));
		}

		final Long marketOrgId = reqParam.getMarketOrgId();
		if (NumericUtil.lessThanZero(marketOrgId)) {
			LOGGER.warn("add market entrust relation ,illegal req param 【marketOrgId】= {}.", marketOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.select.purchaseOrg.error"));
		}

		final Long warehouseProviderId = reqParam.getWarehouseProviderId();
		if (NumericUtil.lessThanZero(warehouseProviderId)) {
			LOGGER.warn("add market entrust relation ,illegal req param 【warehouseProviderId】= {}.", warehouseProviderId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.select.warehouseorg.error"));
		}

		final Long stockOrgId = reqParam.getStockOrgId();
		if (NumericUtil.lessThanZero(stockOrgId)) {
			LOGGER.warn("add market entrust relation ,illegal req param 【stockOrgId】= {}.", stockOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.select.stockorg.error"));
		}

		final Long warehouseId = reqParam.getWarehouseId();
		if (NumericUtil.lessThanZero(warehouseId)) {
			LOGGER.warn("add market entrust relation ,illegal req param 【warehouseId】= {}.", warehouseId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.select.warehouse.error"));
		}

		Boolean flag = baseEntrustRelationValidator.groupIdValidate(ownerId, warehouseProviderId);
		if(flag){
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.market.owner.warehouseorg.same.group.error"));
		}
	}
}