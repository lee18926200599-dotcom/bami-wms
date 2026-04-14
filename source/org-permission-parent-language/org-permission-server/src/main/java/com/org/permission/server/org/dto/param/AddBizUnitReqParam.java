package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增业务单元请求参数
 */
@ApiModel(description = "新增业务单元")
@Data
public class AddBizUnitReqParam extends UpdateBizUnitReqParam implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "本位币")
	private String currency="CNY";

	@ApiModelProperty(value = "版本号")
	private String version;

	public AddBizUnitReqParam() {
	}
}
