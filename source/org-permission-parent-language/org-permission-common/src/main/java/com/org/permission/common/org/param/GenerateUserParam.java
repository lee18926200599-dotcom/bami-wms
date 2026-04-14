package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 生成用户请求参数
 */
@ApiModel(description = "生成用户请求参数")
@Data
public class GenerateUserParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("操作人ID")
	private Long operaterId;

	@ApiModelProperty("人员ID")
	private List<Long> staffIds;
}
