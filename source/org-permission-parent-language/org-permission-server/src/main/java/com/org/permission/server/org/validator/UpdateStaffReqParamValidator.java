package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.UpdateStaffReqParam;
import com.org.permission.server.org.enums.EmploymentTypeEnum;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增人员请求参数校验器
 */
@Component(value = "updateStaffReqParamValidator")
public class UpdateStaffReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStaffReqParamValidator.class);

    public void validate(UpdateStaffReqParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("update staff request param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long createdBy = reqParam.getCreatedBy();
        if (NumericUtil.lessThanZero(createdBy)) {
            LOGGER.warn("update staff illegal req param 【createdBy】= {}.", createdBy);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.common.userid.error"));
        }

        final Long depId = reqParam.getDepId();
        if (NumericUtil.lessThanZero(depId)) {
            LOGGER.warn("update staff illegal req param 【depId】= {}.", depId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.depid.error"));
        }

        final Long buId = reqParam.getBuId();
        if (NumericUtil.lessThanZero(buId)) {
            LOGGER.warn("update staff illegal req param 【buId】= {}.", buId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.buid.error"));
        }

        final Long groupId = reqParam.getGroupId();
        if (NumericUtil.lessThanZero(groupId)) {
            LOGGER.warn("update staff illegal req param 【groupId】= {}.", groupId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.groupid.error"));
        }

        final Integer employmentType = reqParam.getEmploymentType();
        EmploymentTypeEnum employmentTypeEnum = EmploymentTypeEnum.getEnum(employmentType);
        if (employmentTypeEnum == null) {
            LOGGER.warn("update staff illegal req param 【employmentType】= {}.", employmentType);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.employmentType.error"));
        }

        final String realname = reqParam.getRealname();
        if (StringUtils.isEmpty(realname)) {
            LOGGER.warn("update staff illegal req param 【name】= {}.", realname);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.realname.error"));
        }
        final String phone = reqParam.getPhone();
        if (StringUtils.isEmpty(phone)) {
            LOGGER.warn("update staff illegal req param 【phone】= {}.", phone);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.phone.error"));
        }

        //如果更新人员是正式人员时候，证件类型和证件号的判断
        if (employmentTypeEnum.getCode() == EmploymentTypeEnum.FORMAL.getCode()) {
            final Integer certificateType = reqParam.getCertificateType();
            if (NumericUtil.lessThanZero(certificateType)) {
                LOGGER.warn("update staff illegal req param 【certificateType】= {}.", certificateType);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.certificateType.error"));
            }

            final String certificateNo = reqParam.getCertificateNo();
            if (StringUtils.isEmpty(certificateNo)) {
                LOGGER.warn("update staff illegal req param 【certificateNo】= {}.", certificateNo);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.certificateNo.error"));
            }
        }
    }
}
