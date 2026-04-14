package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 仓库入驻货主排行榜
 */
@Data
public class WarehouseEnterOwnerRankingListDto implements Serializable, Comparable<WarehouseEnterOwnerRankingListDto> {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库 ID
	 */
	private Long warehouseId;

	/**
	 * 货主统计数量
	 */
	private Integer ownerCount;

	@Override
	public int compareTo(WarehouseEnterOwnerRankingListDto target) {
		return this.ownerCount - target.getOwnerCount();
	}

}
