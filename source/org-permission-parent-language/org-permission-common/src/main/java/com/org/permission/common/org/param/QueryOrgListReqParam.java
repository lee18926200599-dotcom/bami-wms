package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询组织列表请求参数
 */
@Data
public class QueryOrgListReqParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID（选填）
	 */
	private Long userId;
	/**
	 *  类型ID（例：组织ID）
	 */
	private Long orgId;
	/**
	 * 过滤级别
	 * <li>1 所有上级业务单元+集团</li>
	 * <li>2 所有下级业务单元</li>
	 * <li>3 集团所有组织</li>
	 */
	private Integer filterLevel;
}
