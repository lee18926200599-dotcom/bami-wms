package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门相关状态数据
 */
@Data
public class DepartmentStateBean implements Serializable {
	private static final long serialVersionUID = 1;

	private Long id;

	private Integer state;

	private Integer quotedFlag;
	private Integer deletedFlag;
}
