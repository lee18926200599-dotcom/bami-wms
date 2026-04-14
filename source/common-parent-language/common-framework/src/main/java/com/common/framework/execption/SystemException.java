package com.common.framework.execption;

/**
 * 系统异常
 */
public class SystemException extends RuntimeException {
    private int errorCode;
    private String errorReason;

    public SystemException(int errorCode, String errorReason) {
        super(errorReason);
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }


    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorReason() {
        return this.errorReason;
    }

    public SystemException setCause(Throwable t) {
        super.initCause(t);
        return this;
    }
    public SystemException(String errorReason){super(errorReason);}
    public SystemException(String errorReason,Throwable throwable){super(errorReason,throwable);}
}
