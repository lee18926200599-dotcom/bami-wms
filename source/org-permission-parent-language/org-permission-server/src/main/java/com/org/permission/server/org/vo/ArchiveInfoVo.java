package com.org.permission.server.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 档案信息
 */
@ApiModel
@Data
public class ArchiveInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("档案ID")
	private Long id;

	@ApiModelProperty("档案名")
	private String name;

	@ApiModelProperty("用户id")
	private String userId;

}
