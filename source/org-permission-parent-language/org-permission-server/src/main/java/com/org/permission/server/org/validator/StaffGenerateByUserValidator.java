package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "staffGenerateByUserValidator")
public class StaffGenerateByUserValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffGenerateByUserValidator.class);

    public void validator(StaffInfoDto reqParam) {
        if (reqParam.getId() == null) {
            LOGGER.warn("query staff param :【id】" + reqParam.getId());
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }
        if (reqParam.getUserId() == null) {
            LOGGER.warn("query staff param :【userId】" + reqParam.getUserId());
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
        }
    }
}
