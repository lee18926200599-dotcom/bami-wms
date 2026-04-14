package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 版本查询请求参数
 */
@ApiModel(description = "版本请求参数", value = "VersionQueryParam")
@Data
public class VersionQueryParam implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "业务单元ID")
	private Long buId;

	@ApiModelProperty(value = "版本号")
	private String version;
}
