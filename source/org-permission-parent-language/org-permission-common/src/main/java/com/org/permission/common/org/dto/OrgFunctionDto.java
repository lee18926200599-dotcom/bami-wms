package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织职能简要信息
 */
@Data
public class OrgFunctionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 功能类别
	 */
	private Integer functionType;

	/**
	 * 状态（0 未启用；1 启用；2 停用）
	 */
	private Integer state;
}
