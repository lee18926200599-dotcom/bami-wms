package com.org.permission.server.org.service.verify;

import com.common.language.util.I18nUtils;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.UpdateBizUnitReqParam;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 业务单元启用状态下组织组织职能修改验证器
 */
@Component("buEnableOrgFuncUpdateVerify")
public class BUEnableOrgFuncUpdateVerify {

    /**
     * 业务单元启用后,组织职能不能减少
     *
     * @param orgFuncTypes 原业务单元组织职能
     * @param reqParam     更新请求参数
     */
    public void verify(final List<Integer> orgFuncTypes, UpdateBizUnitReqParam reqParam) {
        if (CollectionUtils.isEmpty(orgFuncTypes)) {
            return;// 原业务单元组织职能为空，爱咋地咋地吧！
        }

        for (Integer orgFuncType : orgFuncTypes) {
            switch (FunctionTypeEnum.getEnum(orgFuncType)) {
                case CORPORATION://具有法人职能
                    if (reqParam.getCorporate() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.corporate.cannot.reduce"));
                    }
                    break;
                case FINANCE://具有财务职能
                    if (reqParam.getFinance() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.finance.cannot.reduce"));
                    }
                    break;
                case PURCHASE://具有采购职能
                    if (reqParam.getPurchase() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.purchase.cannot.reduce"));
                    }
                    break;
                case SALE://具有销售职能
                    if (reqParam.getSale() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.sale.cannot.reduce"));
                    }
                    break;
                case STORAGE://具有仓储职能
                    if (reqParam.getStorage() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.warehouse.cannot.reduce"));
                    }
                    break;
                case LOGISTICS://具有物流职能
                    if (reqParam.getLogistics() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.logistics.cannot.reduce"));
                    }
                    break;
                case BANKING:
                    if (reqParam.getBanking() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.bank.cannot.reduce"));
                    }
                    break;
                case LABOUR_SERVICE:
                    if (reqParam.getLabour() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.labour.cannot.reduce"));
                    }
                    break;
                case PLATFORM:
                    if (reqParam.getPlatform() == null) {
                        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.init.platform.cannot.reduce"));
                    }
                    break;
                default:
                    // nothing to do!
            }
        }
    }
}
