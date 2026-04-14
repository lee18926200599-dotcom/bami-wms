package com.org.permission.server.org.dto.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 业务单元仓库查询请求参数
 */
@Data
public class BUWarehouseReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务单元ID
	 */
	private Long buId;
}
