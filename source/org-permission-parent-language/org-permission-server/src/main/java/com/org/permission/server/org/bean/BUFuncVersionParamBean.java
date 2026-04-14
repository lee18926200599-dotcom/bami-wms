package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 业务单元版本职能参数
 */
@Data
public class BUFuncVersionParamBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务单元ID
	 */
	private Long buId;
	/**
	 * 组织版本
	 */
	private String version;

}
