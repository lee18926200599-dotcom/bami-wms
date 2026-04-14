package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 集团返回对象
 */
@Data
@ApiModel(description = "集团返回对象 ")
public class GroupDto implements Serializable {

	private static final long serialVersionUID = -6937151891361441866L;
	@ApiModelProperty(value = "id")
	private Long id;
	@ApiModelProperty(value = "集团编码")
	private String groupCode;
	@ApiModelProperty(value = "集团名称")
	private String groupName;
	@ApiModelProperty(value = "集团id")
	private Long groupId;
	@ApiModelProperty(value = "生效日期")
	private Date effectiveTime;
	@ApiModelProperty(value = "失效日期")
	private Date expireTime;
}
