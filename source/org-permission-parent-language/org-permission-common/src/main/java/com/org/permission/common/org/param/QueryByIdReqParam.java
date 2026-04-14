package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ID查询请求参数
 */
@ApiModel
@Data
public class QueryByIdReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID（需要控制权限时必填）")
	private Long userId;

	@ApiModelProperty("数据ID（组织 ID，或其他数据 ID）")
	private Long id;

	@ApiModelProperty("数据ID（组织 ID，或其他数据 ID）集合")
	private List<Long> ids;


	@ApiModelProperty("状态")
	private Integer state;

	public QueryByIdReqParam() {
	}

	public QueryByIdReqParam(Long id) {
		this.id = id;
	}
}
