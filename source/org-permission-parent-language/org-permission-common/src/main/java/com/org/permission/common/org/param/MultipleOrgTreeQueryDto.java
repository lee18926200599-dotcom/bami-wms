package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 模糊查询组织树请求参数
 */
@Data
public class MultipleOrgTreeQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID，（选填）
	 */
	private Long userId;

	/**
	 * 组织名称（必填）
	 */
	private String orgName;
}
