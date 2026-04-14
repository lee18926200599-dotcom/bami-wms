package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 人员实体状态数据
 */
@Data
public class StaffStateBean implements Serializable {
	private static final long serialVersionUID = 1;
	/**
	 * 主键
	 */
	private Long id;

	private Integer state;
	/**
	 * 用户 ID
 	 */
	private Long userId;
}
