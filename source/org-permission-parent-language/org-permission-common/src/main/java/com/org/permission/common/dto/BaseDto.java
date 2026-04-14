package com.org.permission.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息数据基础实体，定义共有属性
 */
@ApiModel(description = "基础新增数据实体")
@Data
public class BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("业务自增主键")
	private Long id;

	@ApiModelProperty(value = "创建人id")
	private Long createdBy;
	@ApiModelProperty(value = "创建人")
	private String createdName;
	@ApiModelProperty(value = "创建日期")
	private Date createdDate;
	@ApiModelProperty(value = "修改人id")
	private Long modifiedBy;
	@ApiModelProperty(value = "修改人")
	private String modifiedName;
	@ApiModelProperty(value = "修改时间")
	private Date modifiedDate;
}
