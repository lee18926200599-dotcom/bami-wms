package com.common.excle.export;


public class ExportException extends RuntimeException {
    
    public ExportException() {
        super();
    }
    
    public ExportException(String message) {
        super(message);
    }
    
    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ExportException(Throwable cause) {
        super(cause);
    }
    
    protected ExportException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
