package com.org.permission.server.org.validator;


import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.StaffTypeParam;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 更新人员类别请求参数校验器
 */
@Component(value = "updateStaffTypeReqParamValidator")
public class UpdateStaffTypeParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStaffTypeParamValidator.class);

    public void validate(StaffTypeParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("update statff type req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long modifiedBy = reqParam.getModifiedBy();
        if (NumericUtil.lessThanZero(modifiedBy)) {
            LOGGER.warn("update statff type , illegal req param 【modifiedBy】= {}.", modifiedBy);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.modifiedBy.error"));
        }

        final Long id = reqParam.getId();
        if (NumericUtil.lessThanZero(id)) {
            LOGGER.warn("update statff type , illegal req param 【id】= {}.", id);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
        }

        //TODO 有业务引用不允许更改

        if (id == 1) {
            LOGGER.warn("update operate , illegal req param 【id】= {}.", id);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.top.error"));
        }

        final String typeName = reqParam.getTypeName();
        if (!StringUtils.isEmpty(typeName)) {
            return;
        }

        final Long parentId = reqParam.getParentId();
        if (NumericUtil.lessZeroNotNull(parentId)) {
            LOGGER.warn("update statff type , illegal req param 【parentId】= {}.", parentId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.stafftype.parent.error"));
        }
        if (parentId != null) {
            return;
        }

        final String remark = reqParam.getRemark();
        if (!StringUtils.isEmpty(remark)) {
            return;
        }

        LOGGER.warn("update statff type , illegal req param:{}.", reqParam);
        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
    }
}
