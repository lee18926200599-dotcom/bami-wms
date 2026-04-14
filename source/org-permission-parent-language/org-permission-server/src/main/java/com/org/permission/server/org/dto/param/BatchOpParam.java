package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量操作请求参数
 */
@ApiModel(description = "批量操作请求参数")
@Data
public class BatchOpParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID（可空）（预留权限控制）")
	private Long userId;

	@ApiModelProperty("ID 集合（非空）")
	private List<Integer> ids;

	public BatchOpParam() {
	}

	@Override
	public String toString() {
		return "BatchQueryParam{" +
				"userId=" + userId +
				", ids=" + ids +
				'}';
	}
}
