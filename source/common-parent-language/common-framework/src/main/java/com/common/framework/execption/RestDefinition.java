package com.common.framework.execption;


import com.common.util.message.RestMessage;
import org.apache.commons.lang3.StringUtils;
public interface RestDefinition {
    int getErrorCode();

    String getErrorReason();

    default SystemException throwError() {
        throw new SystemException(this.getErrorCode(), this.getErrorReason());
    }

    default SystemException throwError(Throwable throwable) {
        throw (new SystemException(this.getErrorCode(), this.getErrorReason())).setCause(throwable);
    }

    default SystemException throwError(String msg) {
        throw new SystemException(this.getErrorCode(), msg);
    }

    default SystemException throwError(String msg, Throwable throwable) {
        throw (new SystemException(this.getErrorCode(), msg)).setCause(throwable);
    }

    default SystemException throwErrorAndAppendDetail(String detail) {
        throw new SystemException(this.getErrorCode(), this.getErrorReason() + "(" + detail + ")");
    }

    default SystemException throwErrorAndAppendDetail(String detail, Throwable throwable) {
        throw (new SystemException(this.getErrorCode(), this.getErrorReason() + "(" + detail + ")")).setCause(throwable);
    }

    default RestMessage newRestMessage() {
        return RestMessage.error(String.valueOf(this.getErrorCode()), this.getErrorReason());
    }

    default RestMessage newRestMessage(String msg) {
        return RestMessage.error(String.valueOf(this.getErrorCode()), msg);
    }

    default RestMessage asRestMessage(String errorDetail) {
        return RestMessage.error(String.valueOf(this.getErrorCode()), this.getErrorReason(errorDetail));
    }

    default String getErrorReason(String errorDetails) {
        return StringUtils.isNotBlank(errorDetails) ? this.getErrorReason() + "(" + errorDetails + ")" : this.getErrorReason();
    }
}
