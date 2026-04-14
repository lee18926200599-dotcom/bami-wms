package com.org.permission.server.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团logo管理请求参数
 */
@ApiModel(description = "集团logo管理请求参数", value = "ManageGroupLogo")
@Data
public class ManageGroupLogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "操作人ID")
	private Long userId;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "LOGO URL")
	private String logoUrl;

}
