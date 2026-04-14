package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.BindingBankAccountParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 绑定银行账号请求参数校验器
 */
@Component("bindingBankAccountParamValidator")
public class BindingBankAccountParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BindingBankAccountParamValidator.class);

	public void validate(BindingBankAccountParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("binding bank account req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		final Long operaterId = reqParam.getOperaterId();
		if (NumericUtil.lessThanZero(operaterId)) {
			LOGGER.warn("binding bank account,illegal req param 【operaterId】= {}.", operaterId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.operatorid.error"));
		}

		final String accountCategory = reqParam.getAccountCategory();
		boolean illegalAccountCategory = "1".equals(accountCategory) ||
				"2".equals(accountCategory) ||
				"3".equals(accountCategory) ||
				"4".equals(accountCategory)||
				"5".equals(accountCategory);
		if (!(illegalAccountCategory)) {
			LOGGER.warn("binding bank account,illegal req param 【accountCategory】= {}.", accountCategory);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.accountCategory.error"));
		}

		final String accountType = reqParam.getAccountType();
		boolean illegalAccountType = "1".equals(accountType) || "2".equals(accountType);
		if (!(illegalAccountType)) {
			LOGGER.warn("binding bank account,illegal req param 【accountType】= {}.", accountType);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.accountType.error"));
		}

		final String accountSn = reqParam.getAccountSn();
		if (StringUtils.isEmpty(accountSn)) {
			LOGGER.warn("binding bank account,illegal req param 【accountSn】= {}.", accountSn);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.accountSn.error"));
		}

		final String accountName = reqParam.getAccountName();
		if (StringUtils.isEmpty(accountName)) {
			LOGGER.warn("binding bank account,illegal req param 【accountName】= {}.", accountName);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.accountName.error"));
		}
		//当账户分类是银行的时候，需要检验开户行和银行名称
		if("1".equals(reqParam.getAccountCategory())){
			final String bankCode = reqParam.getBankCode();
			if (StringUtils.isEmpty(bankCode)) {
				LOGGER.warn("binding bank account,illegal req param 【bankCode】= {}.", bankCode);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.bankCode.error"));
			}

			final String bankName = reqParam.getBankName();
			if (StringUtils.isEmpty(bankName)) {
				LOGGER.warn("binding bank account,illegal req param 【bankName】= {}.", bankName);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.bankName.error"));
			}
		}
		final String isUseHigherLevel = reqParam.getIsUseHigherLevel();
		if (!("1".equals(isUseHigherLevel) || "0".equals(isUseHigherLevel))) {
			LOGGER.warn("binding bank account,illegal req param 【isUseHigherLevel】= {}.", isUseHigherLevel);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.isUseHigherLevel.error"));
		}

		final String isUseLowerLevel = reqParam.getIsUseLowerLevel();
		if (!("1".equals(isUseLowerLevel) || "0".equals(isUseLowerLevel))) {
			LOGGER.warn("binding bank account,illegal req param 【isUseLowerLevel】= {}.", isUseLowerLevel);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.isUseLowerLevel.error"));
		}

		final Integer defaultFlag = reqParam.getDefaultFlag();
		if (defaultFlag==null) {
			LOGGER.warn("binding bank account,illegal req param 【defaultFlag】= {}.", defaultFlag);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.binding.bank.account.defaultFlag.error"));
		}
	}

}
