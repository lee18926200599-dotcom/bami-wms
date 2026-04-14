package com.org.permission.server.exception;

/**
 * 组织系统异常
 */
public class OrgException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/**
	 * 错误码
	 */
	private Integer errorCode;

	public OrgException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
