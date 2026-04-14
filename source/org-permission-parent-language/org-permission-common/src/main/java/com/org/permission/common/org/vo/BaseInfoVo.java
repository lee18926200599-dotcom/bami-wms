package com.org.permission.common.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据实体基础信息
 */
@Data
@ApiModel(description = "基础信息", value = "BaseInfoVo")
public class BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
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

	@ApiModelProperty(value = "客商ID")
	private Long custId;

	@ApiModelProperty(value = "客商名称")
	private String custName;

	public BaseInfoVo() {
	}

}
