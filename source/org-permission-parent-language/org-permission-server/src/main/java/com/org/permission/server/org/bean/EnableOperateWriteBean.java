package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 启用组织数据实体
 */
@Data
public class EnableOperateWriteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 启用 | 停用
	 * <p>
	 * 2 启用；
	 * 3 停用
	 */
	private Integer enableState;

	/**
	 * 操作时间
	 */
	private Date enableTime;

	/**
	 * 操作人
	 */
	private Integer enableUser;
}
