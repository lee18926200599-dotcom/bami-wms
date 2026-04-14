package com.org.permission.server.org.dto.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求参数
 */
@Data
public abstract class BaseParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 操作人ID
	 */
	private Long operaterId;

	public BaseParam() {
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BaseParam{");
		sb.append("operaterId=").append(operaterId);
		sb.append('}');
		return sb.toString();
	}
}
