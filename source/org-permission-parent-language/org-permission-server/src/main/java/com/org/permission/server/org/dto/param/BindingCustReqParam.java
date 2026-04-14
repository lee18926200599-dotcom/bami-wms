package com.org.permission.server.org.dto.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 业务单元绑定客户请求参数
 */
@Data
public class BindingCustReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 组织ID
	 */
	private Long orgId;
	/**
	 * 客户ID
	 */
	private Long custId;
}
