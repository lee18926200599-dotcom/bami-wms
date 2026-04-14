package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.EmploymentTypeEnum;
import com.org.permission.common.org.param.AddStaffParam;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 新增人员请求参数校验器
 *
 * @since JDK 1.8
 */
@Component(value = "addStaffReqParamValidator")
public class AddStaffReqParamValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddStaffReqParamValidator.class);

    public void validate(AddStaffParam reqParam) {
        if (reqParam == null) {
            LOGGER.warn("add staff request param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
        }

        final Long createdBy = reqParam.getCreatedBy();
        if (NumericUtil.lessThanZero(createdBy)) {
            LOGGER.warn("add staff illegal req param 【createUser】= {}.", createdBy);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.createdby.error"));
        }

        final Long depId = reqParam.getDepId();
        if (NumericUtil.lessThanZero(depId)) {
            LOGGER.warn("add staff illegal req param 【depId】= {}.", depId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.depid.error"));
        }

        final Long buId = reqParam.getBuId();
        if (NumericUtil.lessThanZero(buId)) {
            LOGGER.warn("add staff illegal req param 【buId】= {}.", buId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.buid.error"));
        }

        final Long groupId = reqParam.getGroupId();
        if (NumericUtil.lessThanZero(groupId)) {
            LOGGER.warn("add staff illegal req param 【groupId】= {}.", groupId);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.groupid.error"));
        }

        final Integer employmentType = reqParam.getEmploymentType();
        LOGGER.warn("add staff illegal req param 【employmentType】= {}.", employmentType);
        EmploymentTypeEnum employmentTypeEnum = EmploymentTypeEnum.getEnum(employmentType);
        if (employmentTypeEnum == null) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.employmentType.error"));
        }

        final String realname = reqParam.getRealname();
        if (StringUtils.isEmpty(realname)) {
            LOGGER.warn("add staff illegal req param 【name】= {}.", realname);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.realname.error"));
        }
        final String phone = reqParam.getPhone();
        if (StringUtils.isEmpty(phone)) {
            LOGGER.warn("add staff illegal req param 【phone】= {}.", phone);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.phone.error"));
        }

        if (EmploymentTypeEnum.FORMAL.getCode() == employmentType) {
            final Integer certificateType = reqParam.getCertificateType();
            if (NumericUtil.lessThanZero(certificateType)) {
                LOGGER.warn("add staff illegal req param 【certificateType】= {}.", certificateType);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.certificateType.error"));
            }

            final String certificateNo = reqParam.getCertificateNo();
            if (StringUtils.isEmpty(certificateNo)) {
                LOGGER.warn("add staff illegal req param 【certificateNo】= {}.", certificateNo);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.staff.certificateNo.error"));
            }
        }
    }
}
