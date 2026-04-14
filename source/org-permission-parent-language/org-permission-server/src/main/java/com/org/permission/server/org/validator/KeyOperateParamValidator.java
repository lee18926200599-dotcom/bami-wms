package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "keyOperateParamValidator")
public class KeyOperateParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyOperateParamValidator.class);

    public void validate(KeyOperateParam reqParam) {
        if (reqParam.getId() == null) {
            LOGGER.warn("query staff param :【id】" + reqParam.getId());
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }
        if (reqParam.getUserId() == null) {
            LOGGER.warn("query staff param :【userId】" + reqParam.getUserId());
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.userid.cannot.null"));
        }
    }

}
