package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.org.param.QueryPermissionOrgInfoParam;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "queryPermissionOrgInfoParamValidator")
public class QueryPermissionOrgInfoParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryPermissionOrgInfoParamValidator.class);

    public void validate(QueryPermissionOrgInfoParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("query org permission list request, param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long orgId = reqParam.getOrgId();
        if (NumericUtil.lessThanZero(orgId)) {
            LOGGER.warn("query org  departmentInfo request param 【orgId】= {}.",orgId );
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.orgid.error"));
        }
        final Long userId = reqParam.getUserId();
        if (NumericUtil.lessThanZero(userId)) {
            LOGGER.warn("query org departmentInfo request param 【userId】= {}.", userId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
        }
    }
}
