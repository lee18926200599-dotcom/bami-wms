package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.param.QueryBizUnitListByFuncParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.common.base.enums.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 业务单元组织职能列表查询
 */
@Component("queryBizUnitListByFuncParamValidator")
public class QueryBizUnitListByFuncParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryGroupListReqParamValidator.class);

    public void validate(QueryBizUnitListByFuncParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("query biz unit list by func req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Integer funcType = reqParam.getFuncType();
        FunctionTypeEnum functionTypeEnum = FunctionTypeEnum.getEnum(funcType);
        if (functionTypeEnum == null) {
            LOGGER.warn("query biz unit list by func, illegal req param 【funcType】= {}.", funcType);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.querybizunit.funcType.error"));
        }


        final Integer state = reqParam.getState();
        if (state == null) {
            return;
        }
        StateEnum stateEnum = StateEnum.getEnum(state);
        if (stateEnum == null) {
            LOGGER.warn("query biz unit list by func, illegal req param 【status】= {}.", state);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.state.error"));
        }
    }
}
