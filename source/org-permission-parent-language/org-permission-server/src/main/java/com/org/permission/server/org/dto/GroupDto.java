package com.org.permission.server.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 集团返回对象
 */
@ApiModel(description = "集团返回对象 ")
@Data
public class GroupDto implements Serializable {

	private static final long serialVersionUID = 8489320919778127833L;
	@ApiModelProperty(value = "id")
	private Long id; //
	@ApiModelProperty(value = "集团编码")
	private String groupCode; //
	@ApiModelProperty(value = "集团名称")
	private String groupName; //
	@ApiModelProperty(value = "集团id")
	private Long groupId;
	@ApiModelProperty(value = "生效日期")
	private Date effectiveTime;
	@ApiModelProperty(value = "失效日期")
	private Date expireTime;
}
