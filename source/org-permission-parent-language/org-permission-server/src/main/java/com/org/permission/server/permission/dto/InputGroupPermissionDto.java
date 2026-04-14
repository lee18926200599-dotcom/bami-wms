package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团功能权限修改入参
 */
@Data
@ApiModel(description = "集团功能权限修改入参")
public class InputGroupPermissionDto implements Serializable {

	private static final long serialVersionUID = 2088395775565515201L;
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "状态 1：启用  0：未启用")
	private Integer state;

}
