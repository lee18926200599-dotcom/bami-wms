package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.utils.NumericUtil;
import com.common.base.enums.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 启用操作请求参数校验器
 */
@Component(value = "enableOperateReqParamValidator")
public class EnableOperateReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableOperateReqParamValidator.class);

    public void validate(EnableOperateParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("enable org req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }


        final Long id = reqParam.getId();
        if (NumericUtil.lessThanZero(id)) {
            LOGGER.warn("enable org , illegal req param 【id】= {}.", id);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.enable.operate.id.error"));
        }

        final Integer enableState = reqParam.getState();
        if (StateEnum.getEnum(enableState) == null) {
            LOGGER.warn("enable org , illegal req param 【state】= {}.", enableState);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.enable.operate.state.error"));
        }
    }
}
