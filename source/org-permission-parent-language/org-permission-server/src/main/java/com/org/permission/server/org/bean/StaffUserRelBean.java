package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 人员用户关系实体
 */
@Data
public class StaffUserRelBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 人员ID
	 */
	private Long staffId;
	/**
	 * 用户ID
	 */
	private Long userId;
}
