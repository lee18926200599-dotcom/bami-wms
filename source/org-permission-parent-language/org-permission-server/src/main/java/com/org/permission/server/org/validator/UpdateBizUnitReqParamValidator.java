package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.UpdateBizUnitReqParam;
import com.org.permission.common.org.dto.func.CorporateOrgFuncBean;
import com.org.permission.common.org.dto.func.FinanceOrgFuncBean;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 更新业务单元请求参数
 */
@Component(value = "updateBizUnitReqParamValidator")
public class UpdateBizUnitReqParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBizUnitReqParamValidator.class);

	/**
	 * 更新业务单元请求参数校验器
	 *
	 * @param reqParam 更新业务单元请求参数
	 */
	public void validate(UpdateBizUnitReqParam reqParam) {
		if (reqParam == null) {
			LOGGER.warn("update biz unit req param is null.");
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
		}

		/*final Long modifiedBy = reqParam.getModifiedBy();
		if (NumericUtil.lessThanZero(modifiedBy)) {
			LOGGER.warn("update biz unit ,illegal req param 【modifiedBy】= {}.", modifiedBy);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "更新业务单元请求参数【modifiedBy】不符合要求");
		}*/

		final Long id = reqParam.getId();
		if (NumericUtil.lessThanZero(id)) {
			LOGGER.warn("update biz unit ,illegal req param 【id】= {}.", id);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
		}

		final CorporateOrgFuncBean corporate = reqParam.getCorporate();
		final FinanceOrgFuncBean finance = reqParam.getFinance();
		if (corporate != null) {
			if (finance == null) {
				LOGGER.warn("update biz unit,illegal param corporate:{},finance:{}.", corporate, finance);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.bizunit.update.corporate.finance.error"));
			}
		}
	}
}
