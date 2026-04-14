package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 新增仓储业务委托关系请求参数校验器
 */
@Component(value = "storageEntrustRelationReqParamValidator")
public class StorageEntrustRelationReqParamValidator {
    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageEntrustRelationReqParamValidator.class);

    public void validatePlatform(final PlatformStorageEntrustRelationParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("add platform storage entrust relation req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

//        final Long createdBy = reqParam.getCreatedBy();
//        if (NumericUtil.lessThanZero(createdBy)) {
//            LOGGER.warn("add platform storage entrust relation, illegal req param 【createdBy】= {}.", createdBy);
//            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "新增集团间仓储委托关系请求参数【createdBy】不符合要求");
//        }

        final Integer defaultFlag = reqParam.getDefaultFlag();
        if (null == defaultFlag) {
            LOGGER.warn("add platform storage entrust relation , illegal req param 【defaultFlag】= null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.defaultFlag.error"));
        }

        final Long warehouseProviderId = reqParam.getWarehouseProviderId();
        if (NumericUtil.lessThanZero(warehouseProviderId)) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【warehouseProviderId】= {}.", warehouseProviderId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.storage.warehouseProvider.error"));
        }

        final Long stockOrg = reqParam.getStockOrgId();
        if (NumericUtil.lessThanZero(stockOrg)) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【stockOrg】= {}.", stockOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.stockOrg.error"));
        }

        final Long warehouseId = reqParam.getWarehouseId();
        if (NumericUtil.lessThanZero(warehouseId)) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【warehouseId】= {}.", warehouseId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.storage.warehouseid.error"));
        }

        final Long logisticsProviderId = reqParam.getLogisticsProviderId();
        if (NumericUtil.lessThanZero(logisticsProviderId)) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【logisticsProviderId】= {}.", logisticsProviderId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.logisticsProvider.error"));
        }

        final Long logisticsOrg = reqParam.getLogisticsOrgId();
        if (NumericUtil.lessThanZero(logisticsOrg)) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【logisticsOrg】= {}.", logisticsOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.logisticsOrg.error"));
        }

        if (logisticsProviderId.compareTo(warehouseProviderId) == 0) {
            LOGGER.warn("add platform storage entrust relation, illegal req param 【logisticsProvider】= {}," + "【warehouseProvider】={}.", logisticsProviderId, warehouseProviderId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.storage.Provider.error"));
        }

        Boolean flag = baseEntrustRelationValidator.groupIdValidate(logisticsProviderId, warehouseProviderId);
        if (flag) {
            LOGGER.info("仓储服务商和物流服务商不能属于同一集团" + logisticsProviderId + "," + warehouseProviderId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.storage.Provider.same.group.error"));
        }

    }

    public void validateGroup(final GroupStorageEntrustRelationParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("add group storage entrust relation req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long createdBy = reqParam.getCreatedBy();
        /*if (NumericUtil.lessThanZero(createdBy)) {
            LOGGER.warn("add group storage entrust relation, illegal req param 【createdBy】= {}.", createdBy);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "新增集团内仓储委托关系请求参数【createdBy】不符合要求");
        }*/

        final Integer defaultFlag = reqParam.getDefaultFlag();
        if (null == defaultFlag) {
            LOGGER.warn("add group storage entrust relation , illegal req param 【defaultFlag】= null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.defaultFlag.error"));
        }

        final Long logisticsOrgId = reqParam.getLogisticsOrgId();
        if (NumericUtil.lessThanZero(logisticsOrgId)) {
            LOGGER.warn("add group storage entrust relationship , illegal req param 【logisticsOrgId】= {}.", logisticsOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.logistics.logisticsOrg.error"));
        }

        final Long stockOrg = reqParam.getStockOrgId();
        if (NumericUtil.lessThanZero(stockOrg)) {
            LOGGER.warn("add group storage entrust relationship , illegal req param 【stockOrg】= {}.", stockOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.sale.stockOrg.error"));
        }

        final Long accountOrgId = reqParam.getAccountOrgId();
        if (NumericUtil.lessThanZero(accountOrgId)) {
            LOGGER.warn("add group storage entrust relationship , illegal req param 【accountOrgId】= {}.", accountOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.accountOrg.error"));
        }

        final Long settleOrgId = reqParam.getSettleOrgId();
        if (NumericUtil.lessThanZero(settleOrgId)) {
            LOGGER.warn("add group storage entrust relationship , illegal req param 【settleOrgId】= {}.", settleOrgId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.settleOrgId.error"));
        }

        final String remark = reqParam.getRemark();
        if (!StringUtils.isEmpty(remark) && remark.length() > 50) {
            LOGGER.warn("add finance entrust relation, illegal req param 【remark】= {}.", remark);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.finance.remark.error"));
        }
    }
}
