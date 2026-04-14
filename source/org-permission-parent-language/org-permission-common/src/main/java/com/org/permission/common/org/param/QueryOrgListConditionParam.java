package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织条件查询请求参数
 */
@Data
public class QueryOrgListConditionParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 8677931937707095434L;

	/**
	 * 业务单元id
	 */
	private Long orgId;

	/**
	 * 组织名称（前后模糊查询）
	 */
	private String orgName;

	/**
	 * 组织编码
	 */
	private String orgCode;

	/**
	 * 关键字
	 */
    private String keyWord;

}
