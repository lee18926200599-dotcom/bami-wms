package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.AddBizUnitReqParam;
import com.org.permission.server.org.dto.param.OrgFunctionReqParam;
import com.org.permission.server.utils.NumericUtil;
import com.common.base.enums.BooleanEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 组织职能参数校验器
 */
@Component(value = "organizationFunctionReqParamValidator")
public class OrganizationFunctionReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationFunctionReqParamValidator.class);

    public void validate(OrgFunctionReqParam orgFuncReqParam, AddBizUnitReqParam buReqParam) {

        if (orgFuncReqParam == null) {
            LOGGER.warn("add organization function of biz unit req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Integer functionType = orgFuncReqParam.getFunctionType();
        FunctionTypeEnum functionTypeEnum = FunctionTypeEnum.getEnum(functionType);
        if (functionTypeEnum == null) {
            LOGGER.warn("add biz unit illegal req param 【functionType】= {}.", functionType);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.functionType.error"));
        }

        switch (functionTypeEnum) {
            case CORPORATION:
                validateCorporation(orgFuncReqParam);
                break;
            case FINANCE:
                validateFinance(orgFuncReqParam, buReqParam.hasCorporationFunc());
                break;
            case PURCHASE:
                validatePurchase(orgFuncReqParam);
                break;
            case SALE:
                validateSale(orgFuncReqParam);
                break;
            case STORAGE:
                validateStorage(orgFuncReqParam);
                break;
            case LOGISTICS:
                validateLogistics(orgFuncReqParam);
                break;
            default:
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.functionType.error"));
        }
    }

    /**
     * 法人公司组织职能参数验证
     */
    private void validateCorporation(final OrgFunctionReqParam reqParam) {

        final String taxpayerName = reqParam.getTaxpayerName();

        if (StringUtils.isEmpty(taxpayerName)) {
            LOGGER.warn("add corporation , illegal req param 【taxpayerName】= {}.", taxpayerName);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.taxpayerName.error"));
        }
    }

    /**
     * 财务组织职能参数验证
     */
    private void validateFinance(final OrgFunctionReqParam reqParam, boolean haveCorporationFunction) {

        final Integer aloneFlag = reqParam.getAloneFlag();
        if (aloneFlag == null) {
            LOGGER.warn("add finance , illegal req param 【aloneFlag】= null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.aloneFlag.error"));
        }

        if (haveCorporationFunction) {
            if (BooleanEnum.FALSE.getCode().equals(aloneFlag)) {
                LOGGER.warn("add finance , illegal req param 【aloneFlag】= {}.", aloneFlag);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.aloneFlag.must.error"));
            }
        }

        final String taxRegistrationNumber = reqParam.getTaxRegistrationNumber();
        if (BooleanEnum.TRUE.getCode().equals(aloneFlag)) {
            if (StringUtils.isEmpty(taxRegistrationNumber)) {
                LOGGER.warn("add finance , illegal req param 【taxRegistrationNumber】= {}.", taxRegistrationNumber);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,
                        I18nUtils.getMessage("org.validator.orgfunction.taxRegistrationNumber.error"));
            }
        }
    }

    /**
     * 采购组织职能参数验证
     */
    private void validatePurchase(final OrgFunctionReqParam reqParam) {

        final Long defaultPayOrgId = reqParam.getDefaultPayOrgId();
        if (NumericUtil.lessThanZero(defaultPayOrgId)) {
            LOGGER.warn("add purchase , illegal req param 【defaultPayOrgId】= {}.", defaultPayOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.defaultPayOrgId.error"));
        }
        final Long defaultSettlementOrgId = reqParam.getDefaultSettlementOrgId();
        if (NumericUtil.lessThanZero(defaultSettlementOrgId)) {
            LOGGER.warn("add purchase , illegal req param 【defaultSettlementOrgId】= {}.", defaultSettlementOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.defaultSettlementOrgId.error"));
        }
    }

    /**
     * 销售组织职能参数验证
     */
    private void validateSale(final OrgFunctionReqParam reqParam) {

        final Long defaultPayOrgId = reqParam.getDefaultPayOrgId();
        if (NumericUtil.lessThanZero(defaultPayOrgId)) {
            LOGGER.warn("add sale , illegal req param 【defaultPayOrgId】= {}.", defaultPayOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.sale.defaultPayOrgId.error"));
        }

        final Long defaultSettlementOrgId = reqParam.getDefaultSettlementOrgId();
        if (NumericUtil.lessThanZero(defaultSettlementOrgId)) {
            LOGGER.warn("add sale , illegal req param 【defaultSettlementOrgId】= {}.", defaultSettlementOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.sale.defaultSettlementOrgId.error"));
        }
    }

    /**
     * 仓储组织职能参数验证
     */
    private void validateStorage(final OrgFunctionReqParam reqParam) {

        final Long defaultAccountOrgId = reqParam.getDefaultAccountOrgId();
        if (NumericUtil.lessThanZero(defaultAccountOrgId)) {
            LOGGER.warn("add sale , illegal req param 【defaultAccountOrgId】= {}.", defaultAccountOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.warehouse.defaultAccountOrgId.error"));
        }

        final Long defaultSettlementOrgId = reqParam.getDefaultSettlementOrgId();
        if (NumericUtil.lessThanZero(defaultSettlementOrgId)) {
            LOGGER.warn("add sale , illegal req param 【defaultSettlementOrgId】= {}.", defaultSettlementOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.warehouse.defaultSettlementOrgId.error"));
        }
    }

    /**
     * 物流组织职能参数验证
     */
    private void validateLogistics(final OrgFunctionReqParam reqParam) {

        final Long defaultAccountOrgId = reqParam.getDefaultAccountOrgId();
        if (NumericUtil.lessThanZero(defaultAccountOrgId)) {
            LOGGER.warn("add logistics , illegal req param 【defaultAdjustOrg】= {}.", defaultAccountOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.logistics.defaultAdjustOrg.error"));
        }

        final Long defaultSettlementOrgId = reqParam.getDefaultSettlementOrgId();
        if (NumericUtil.lessThanZero(defaultSettlementOrgId)) {
            LOGGER.warn("add logistics , illegal req param 【defaultSettlementOrgId】= {}.", defaultSettlementOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.orgfunction.logistics.defaultSettlementOrgId.error"));
        }
    }
}
