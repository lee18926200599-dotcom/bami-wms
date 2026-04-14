package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体状态数据
 */
@Data
public class StateBean implements Serializable {
	private static final long serialVersionUID = 1;

	private Long id;

	private Integer state;

	private Long userId;

	private Integer source;

	private Integer defaultFlag;
	private Integer deletedFlag;
}
