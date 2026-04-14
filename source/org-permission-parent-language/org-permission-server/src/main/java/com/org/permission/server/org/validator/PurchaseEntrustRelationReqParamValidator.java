package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 新增采购委托关系请求参数校验器
 */
@Component(value = "purchaseEntrustRelationReqParamValidator")
public class PurchaseEntrustRelationReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseEntrustRelationReqParamValidator.class);

    public void validate(final PurchaseEntrustRelationParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("add purchase entrust relation req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long createdBy = reqParam.getCreatedBy();
        if (NumericUtil.lessThanZero(createdBy)) {
            LOGGER.warn("add purchase entrust relation, illegal req param 【createdBy】= {}.", createdBy);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.createdBy.error"));
        }

        final Integer defaultFlag = reqParam.getDefaultFlag();
        if (null == defaultFlag) {
            LOGGER.warn("add purchase entrust relation , illegal req param 【defaultFlag】= null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.defaultFlag.error"));
        }

        final Long purchaseOrg = reqParam.getPurchaseOrgId();
        if (NumericUtil.lessThanZero(purchaseOrg)) {
            LOGGER.warn("add purchase entrust relation, illegal req param 【purchaseOrg】= {}.", purchaseOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.purchaseOrg.error"));
        }

        final Long stockOrg = reqParam.getStockOrgId();
        if (NumericUtil.lessThanZero(stockOrg)) {
            LOGGER.warn("add purchase entrust relation, illegal req param 【stockOrg】= {}.", stockOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.stockOrg.error"));
        }

        final Long payOrgId = reqParam.getPayOrgId();
        if (NumericUtil.lessThanZero(payOrgId)) {
            LOGGER.warn("add purchase entrust relationship , illegal req param 【payOrgId】= {}.", payOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.payOrgId.error"));
        }

        final Long settmentOrgId = reqParam.getSettleOrgId();
        if (NumericUtil.lessThanZero(settmentOrgId)) {
            LOGGER.warn("add purchase entrust relationship , illegal req param 【settleOrgId】= {}.", settmentOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.settleOrgId.error"));
        }

        final String remark = reqParam.getRemark();
        if (!StringUtils.isEmpty(remark) && remark.length() > 50) {
            LOGGER.warn("add finance entrust relation, illegal req param 【remark】= {}.", remark);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.purchase.remark.error"));
        }
    }
}
