package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询仓储委托关系请求参数
 */
@Data
public class QueryStorageEntrustRelationLogisticsOrgParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托范围（必填）
	 * <p>
	 * 1 集团间（外部）；
	 * 2 集团内（自由）；
	 */
	private Integer entrustRange;
	/**
	 * 库存组织（必填）
	 */
	private Long stockOrg;
	/**
	 * 城市数据字典码（选填）（集团内必填）
	 */
	private Long cityCode;
	/**
	 * 仓库ID（选填）（集团间必填）
	 */
	private Long warehouseId;

	public QueryStorageEntrustRelationLogisticsOrgParam() {
	}

}
