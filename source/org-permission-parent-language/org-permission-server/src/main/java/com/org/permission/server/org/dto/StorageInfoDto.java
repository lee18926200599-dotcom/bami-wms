package com.org.permission.server.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 仓储信息
 */
@Data
public class StorageInfoDto implements Serializable {
	private static final long serialVersionUID = 5934970981060204517L;

	/**
	 * 委托关系ID
	 */
	private Integer relationId;
	/**
	 * 仓储服务商ID
	 */
	private Long storageProviderId;
	/**
	 * 库存组织ID
	 */
	private Long stockOrgId;
	/**
	 * 仓库ID
	 */
	private Long warehouseId;
	/**
	 * 默认
	 * <p>
	 * 0 否；
	 * 1 是。
	 */
	private Integer defaultFlag;
}
