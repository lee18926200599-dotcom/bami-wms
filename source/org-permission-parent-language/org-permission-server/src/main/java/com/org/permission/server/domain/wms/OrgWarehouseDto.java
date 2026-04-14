package com.org.permission.server.domain.wms;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织仓库
 */
@Data
public class OrgWarehouseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 仓库ID
	 */
	private Long warehouseId;
	/**
	 * 仓库名称
	 */
	private String warehouseName;
	/**
	 * 仓库名称
	 */
	private String warehouseCode;
}
