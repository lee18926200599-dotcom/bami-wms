package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据用户权限查询财务委托关系参数
 */
@Data
public class QueryFinanceEntrustRelationByUserPermissionParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID（必填）
	 */
	private Long userId;
	/**
	 * 集团ID（必填）
	 */
	private Long groupId;
	/**
	 * 组织角色（必填）
	 * 1 业务组织；
	 * 2 核算组织；
	 * 3 结算组织；
	 */
	private Integer orgRole;

}
