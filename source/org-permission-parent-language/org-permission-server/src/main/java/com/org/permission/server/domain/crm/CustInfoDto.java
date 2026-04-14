package com.org.permission.server.domain.crm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客商简要信息
 */
@ApiModel
@Data
public class CustInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "客商ID")
	private Long custId;

	@ApiModelProperty(value = "客商名称")
	private String custName;

	@ApiModelProperty(value = "客商业务类型")
	private String bizTypeId;

	@ApiModelProperty(value = "客商业务类型名")
	private String bizTypeName;

	public CustInfoDto() {
	}

}
