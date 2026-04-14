package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员类别请求参数
 */
@ApiModel(description = "人员类别请求参数", value = "StaffTypeParam")
@Data
public class StaffTypeParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "类别级别(1 全局；2 集团)")
	private Integer typeLevel;

	@ApiModelProperty(value = "所属组织（集团ID或全局1）（必填）")
	private Long belongOrg;

	@ApiModelProperty(value = "类别名称")
	private String typeName;

	@ApiModelProperty(value = "类别业务编码")
	private String bizCode;

	@ApiModelProperty(value = "上级类别")
	private Long parentId;

	@ApiModelProperty(value = "简介")
	private String remark;
}
