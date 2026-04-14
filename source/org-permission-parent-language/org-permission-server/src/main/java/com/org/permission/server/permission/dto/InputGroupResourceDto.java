package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(description = "集团下功能资源入参")
public class InputGroupResourceDto implements Serializable {


	private static final long serialVersionUID = 7965764053263987658L;
	@ApiModelProperty(value = "用户id")
	private Long userId;
	@ApiModelProperty(value = "集团id")
	private Long groupId;
	@ApiModelProperty(value = "功能权限id")
	private List<Long> funcList;

}
