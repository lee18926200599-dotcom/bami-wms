package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.common.org.param.QueryStorageEntrustRelationLogisticsOrgParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 查询具有仓储委托关系的物流组织及其下具有结算能力的子业务单元参数校验器
 */
@Component("queryStorageEntrustRelationLogisticsOrgParamValidator")
public class QueryStorageEntrustRelationLogisticsOrgParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryStorageEntrustRelationLogisticsOrgParamValidator.class);

    public void validate(QueryStorageEntrustRelationLogisticsOrgParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("query storage entrust relation settleable members req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Integer entrustRange = reqParam.getEntrustRange();
        EntrustRangeEnum entrustRangeEnum = EntrustRangeEnum.getEnum(entrustRange);
        if (entrustRangeEnum == null) {
            LOGGER.warn("query storage entrust relation settleable members, illegal req param 【entrustRange】= {}.",
                    entrustRange);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryentrustrelation.entrustRange.error"));
        }


        final Long stockOrg = reqParam.getStockOrg();
        if (NumericUtil.nullOrlessThanOrEqualToZero(stockOrg)) {
            LOGGER.warn("query storage entrust relation settleable members, illegal req param 【stockOrg】= {}.", stockOrg);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryentrustrelation.stockOrg.error"));
        }

        if (EntrustRangeEnum.INTER_GROUP.getIndex() == entrustRange) {
            final Long warehouseId = reqParam.getWarehouseId();
            if (NumericUtil.nullOrlessThanOrEqualToZero(warehouseId)) {
                LOGGER.warn("query storage entrust relation settleable members, illegal req param 【warehouseId】= {}.",
                        warehouseId);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.queryentrustrelation.warehouseId.error"));
            }
        }

        if (EntrustRangeEnum.WITHIN_GROUP.getIndex() == entrustRange) {
            final Long cityCode = reqParam.getCityCode();
            if (NumericUtil.nullOrlessThanOrEqualToZero(cityCode)) {
                LOGGER.warn("query storage entrust relation settleable members, illegal req param【cityCode】= {}.", cityCode);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,I18nUtils.getMessage("org.validator.queryentrustrelation.cityDicCode.error"));
            }
        }
    }
}
