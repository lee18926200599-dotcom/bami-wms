package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.QueryStaffPageListReqParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueryStaffPageListParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryStaffPageListParamValidator.class);

    public void validate(QueryStaffPageListReqParam reqParam) {
        if (null == reqParam) {
            LOGGER.warn("query staff list req param is null");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }
    }
}
