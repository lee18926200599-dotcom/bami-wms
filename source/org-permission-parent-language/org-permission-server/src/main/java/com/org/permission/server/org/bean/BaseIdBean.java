package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 主键BEAN
 */
@Data
public class BaseIdBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务自增主键
	 */
	private Integer id;

}
