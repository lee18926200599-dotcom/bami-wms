package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织状态数据实体
 */
@Data
public class DepBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer state;
	/**
	 * 集团ID
	 */
	private Long groupId;

	/**
	 * 业务单元id
	 */
	private Long buId;
}
