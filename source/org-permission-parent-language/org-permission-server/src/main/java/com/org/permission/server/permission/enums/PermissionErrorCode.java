package com.org.permission.server.permission.enums;


import com.common.framework.execption.RestDefinition;
import com.common.language.util.I18nUtils;

/**
 * 系统公共状态码定义范围
 */
public enum PermissionErrorCode implements RestDefinition {
    FAIL_UPDATE(5001, I18nUtils.getMessage("permission.enums.errorcode.updatefail")),
    DEL_ROLE_ERROR(5100, I18nUtils.getMessage("permission.enums.errorcode.delroleerror")),

    INSERT_UPDATE_ROLE_ERROR(5101, I18nUtils.getMessage("permission.enums.errorcode.addrolefail")),
    ROLE_CODE_ERROR(5102, I18nUtils.getMessage("permission.enums.errorcode.rolecodeexist")),
    ROLE_NAME_ERROR(5103, I18nUtils.getMessage("permission.enums.errorcode.rolenameexist")),
    BUILD_ROLE_TREE_ERROR(5104, I18nUtils.getMessage("permission.enums.errorcode.buildroletreeerror")),
    BUILD_ROLE_ORG_TREE_ERROR(5105, I18nUtils.getMessage("permission.enums.errorcode.buildroleorgtreeerror")),
    ROLE_BATCH_USER_ERROR(5106, I18nUtils.getMessage("permission.enums.errorcode.batch.user.role.error")),
    GROUP_INIT_ERROR(5107, I18nUtils.getMessage("permission.enums.errorcode.group.init.error")),
    REGISTER_ERROR(5108, I18nUtils.getMessage("permission.enums.errorcode.register.user.error")),
    UPDATE_USER_ERROR(5109, I18nUtils.getMessage("permission.enums.errorcode.update.user.error")),
    LOGIN_PARAM_ERROR(5110, I18nUtils.getMessage("permission.enums.errorcode.user.cannot.null")),
    STRATEGY_ADMIN_ERROR(5111, I18nUtils.getMessage("permission.enums.errorcode.disable.admin.groupid.null")),
    STRATEGY_USER_ERROR(5112, I18nUtils.getMessage("permission.enums.errorcode.disable.user.groupid.null")),
    SEARCH_PARAM_ERROR(5113, I18nUtils.getMessage("permission.enums.errorcode.param.error")),;
    private int errorCode;
    private String errorReason;

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorReason() {
        return this.errorReason;
    }

    private PermissionErrorCode(int errorCode, String errorReason) {
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }

}
