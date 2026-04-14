package com.org.permission.server.exception;

/**
 * 异常类
 * 
 *
 */
public class PermissionException extends RuntimeException {

	// 错误码
	private int errorCode;

	public PermissionException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
