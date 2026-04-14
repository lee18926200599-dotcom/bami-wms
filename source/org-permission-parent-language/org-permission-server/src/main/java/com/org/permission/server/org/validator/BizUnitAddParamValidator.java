package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.builder.BizUnitOrgFunctionBuilder;
import com.org.permission.server.org.dto.param.AddBizUnitReqParam;
import com.org.permission.server.org.enums.EntityAttrEnum;
import com.org.permission.common.org.dto.func.*;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 新增业务单元请求参数校验器
 */
@Component(value = "addBizUnitRepParamValidator")
public class BizUnitAddParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BizUnitAddParamValidator.class);

	@Resource
	private OrganizationFunctionReqParamValidator organizationFunctionReqParamValidator;

	@Resource
	private BizUnitOrgFunctionBuilder bizUnitOrgFunctionBuilder;

	public void validate(AddBizUnitReqParam reqParam) {
		Assert.notNull(reqParam, I18nUtils.getMessage("org.common.param.cannot.null"));
		final Long groupId = reqParam.getGroupId();
		Assert.notNull(groupId, I18nUtils.getMessage("org.validator.bizunit.add.group.error"));
		final Long parentId = reqParam.getParentId();
		if (NumericUtil.lessThanZero(parentId)) {
			LOGGER.warn("add biz unit illegal req param 【parentId】= {}.", parentId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.parent.error"));
		}

		final String currency = reqParam.getCurrency();
		if (StringUtils.isEmpty(currency)) {
			LOGGER.warn("add biz unit illegal req param 【currency】= {}.", currency);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.currency.error"));
		}

		//  法人公司=T or 实体属性包含公司、分公司时，必填
		final String entityCode = reqParam.getEntityCode();
		final String creditCode = reqParam.getCreditCode();
		if (!StringUtils.isEmpty(entityCode)) {
			final EntityAttrEnum entityAttrEnum = EntityAttrEnum.getEntityAttrEnum(entityCode);
			if (EntityAttrEnum.COMPANY == entityAttrEnum || EntityAttrEnum.CONTROLLED_COMPANY == entityAttrEnum) {
				if (StringUtils.isEmpty(creditCode)) {
					LOGGER.warn("add biz unit illegal req param 【creditCode】= {}.", creditCode);
					throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.creditCode.error"));
				}
			}
		}
		final CorporateOrgFuncBean corporate = reqParam.getCorporate();
		if (corporate != null) {
			if (StringUtils.isEmpty(creditCode)) {
				LOGGER.warn("add biz unit illegal req param 【creditCode】= {}.", creditCode);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.creditCode.error"));
			}
		}

		final Long companyId = reqParam.getCompanyId();
		if (corporate == null) {
			if (NumericUtil.lessThanZero(companyId)) {
				LOGGER.warn("add biz unit illegal req param 【companyId】= {}.", companyId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.company.error"));
			}
		} else {
			if (companyId != null) {
				LOGGER.warn("add biz unit illegal req param 【companyId】= {}.", companyId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.company.error"));
			}
		}
		// 法人组织职能验证
		corporateOrgFuncvalidate(reqParam);
		// 财务组织职能验证
		financeOrgFuncvalidate(reqParam);
		// 采购组织职能验证
		purchaseOrgFuncvalidate(reqParam);
		// 销售组织职能验证
		saleOrgFuncvalidate(reqParam);
		// 仓储组织职能验证
		storageOrgFuncvalidate(reqParam);
		// 物流组织职能验证
		logisticsOrgFuncvalidate(reqParam);
	}

	private void corporateOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasCorporationFunc()) {
			return;
		}

		CorporateOrgFuncBean corporationOrgFunc = reqParam.getCorporate();
		final Long parentOrgId = corporationOrgFunc.getParentOrgId();
		if (NumericUtil.lessThanZero(parentOrgId)) {
			LOGGER.warn("add corporation illegal req param 【parentOrgId】= {}.", parentOrgId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.parentorgid.error"));
		}

		final String taxpayerCode = corporationOrgFunc.getTaxpayerCode();
		if (StringUtils.isEmpty(taxpayerCode)) {
			LOGGER.warn("add corporation , illegal req param 【taxpayerCode】= {}.", taxpayerCode);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.rateType.error"));
		}
	}

	private void financeOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasFinanceFunc()) {
			return;
		}
		final FinanceOrgFuncBean financeOrgFunc = reqParam.getFinance();

		final Boolean aloneFlag = financeOrgFunc.getAloneFlag();
		if (aloneFlag == null) {
			LOGGER.warn("add finance , illegal req param 【aloneFlag】= null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.alone.error"));
		}
		final CorporateOrgFuncBean corporate = reqParam.getCorporate();
		if (corporate != null) {
			if (!aloneFlag) {
				LOGGER.warn("add finance , illegal req param 【aloneFlag】= {}.", aloneFlag);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.alone.must.error"));
			}
		}
		final String creditCode = reqParam.getCreditCode();
		if (StringUtils.isEmpty(creditCode)) {
			final String taxRegistrationNumber = financeOrgFunc.getTaxRegistrationNumber();
			if (StringUtils.isEmpty(taxRegistrationNumber)) {
				if(aloneFlag==Boolean.TRUE){
					LOGGER.warn("add finance , illegal req param 【taxRegistrationNumber】= {}.", taxRegistrationNumber);
					throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,
							I18nUtils.getMessage("org.validator.bizunit.add.taxRegistrationNumber.error"));
				}
			}
		}
	}

	private void purchaseOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasPurchaseFunc()) {
			return;
		}
		final PurchaseOrgFuncBean purchaseOrgFunc = reqParam.getPurchase();

		final FinanceOrgFuncBean finance = reqParam.getFinance();
		if (finance == null) {
			final Long payOrgId = purchaseOrgFunc.getPayOrgId();
			if (NumericUtil.lessThanZero(payOrgId)) {
				LOGGER.warn("add purchase , illegal req param 【payOrgId】= {}.", payOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.payorg.error"));
			}
			final Long settleOrgId = purchaseOrgFunc.getSettleOrgId();
			if (NumericUtil.lessThanZero(settleOrgId)) {
				LOGGER.warn("add purchase , illegal req param 【settleOrgId】= {}.", settleOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.settleorg.error"));
			}
		}
	}

	private void saleOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasSaleFunc()) {
			return;
		}
		final SaleOrgFuncBean saleOrgFunc = reqParam.getSale();
		if (!reqParam.hasFinanceFunc()) {
			final Long receiveOrgId = saleOrgFunc.getReceiveOrgId();
			if (NumericUtil.lessThanZero(receiveOrgId)) {
				LOGGER.warn("add sale , illegal req param 【receiveOrgId】= {}.", receiveOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.receiveorg.error"));
			}

			final Long settleOrgId = saleOrgFunc.getSettleOrgId();
			if (NumericUtil.lessThanZero(settleOrgId)) {
				LOGGER.warn("add sale , illegal req param 【settleOrgId】= {}.", settleOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.settleorg.error"));
			}
		}
	}

	private void storageOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasStorageFunc()) {
			return;
		}
		final StorageOrgFuncBean storageOrgFunc = reqParam.getStorage();

		if (!reqParam.hasFinanceFunc()) {
			final Long accountOrgId = storageOrgFunc.getAccountOrgId();
			if (NumericUtil.lessThanZero(accountOrgId)) {
				LOGGER.warn("add storage , illegal req param 【accountOrgId】= {}.", accountOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.accountorg.error"));
			}

			final Long settleOrgId = storageOrgFunc.getSettleOrgId();
			if (NumericUtil.lessThanZero(settleOrgId)) {
				LOGGER.warn("add storage , illegal req param 【settleOrgId】= {}.", settleOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.warehouse.settleorg.error"));
			}
		}
	}

	private void logisticsOrgFuncvalidate(AddBizUnitReqParam reqParam) {
		if (!reqParam.hasLogisticFunc()) {
			return;
		}
		final LogisticsOrgFuncBean logisticsOrgFunc = reqParam.getLogistics();
		if (!reqParam.hasFinanceFunc()) {
			final Long accountOrgId = logisticsOrgFunc.getAccountOrgId();
			if (NumericUtil.lessThanZero(accountOrgId)) {
				LOGGER.warn("add logistics , illegal req param 【accountOrgId】= {}.", accountOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.logistics.accountorg.error"));
			}
			final Long settleOrgId = logisticsOrgFunc.getSettleOrgId();
			if (NumericUtil.lessThanZero(settleOrgId)) {
				LOGGER.warn("add logistics , illegal req param 【settleOrgId】= {}.", settleOrgId);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.add.logistics.settleorg.error"));
			}
		}
	}
}
