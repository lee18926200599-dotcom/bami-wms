package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织树查询参数
 */
@ApiModel
@Data
public class QueryOrgTreeReqParam extends QueryByIdReqParam {
	private static final long serialVersionUID = 1L;

	/**
	 * 过滤级别
	 * <p>
	 * 1 上级和下级（含平级）
	 * 2 所有下级(含业务单元级部门)
	 * 3 所有下级业务单元
	 */
	@ApiModelProperty("过滤级别(1 上级和下级（含平级）;2 所有下级(含业务单元级部门);3 所有下级业务单元)")
	private Integer Level;

	/**
	 * 集团ID
	 */
	@ApiModelProperty("集团ID")
	private Long groupId;
}
