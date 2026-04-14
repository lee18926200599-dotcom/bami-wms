package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员任职详细信息
 */
@ApiModel(description = "新增人员任职信息", value = "AddPersonDutyParam")
@Data
public class AddPersonDutyParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = " 业务单元ID")
	private Long buId;

	@ApiModelProperty(value = " 部门ID")
	private Long depId;

	@ApiModelProperty(value = "人员类别ID")
	private Integer staffTypeId;

	@ApiModelProperty(value = "人员类别业务编码")
	private String staffTypeBizCode;

	@ApiModelProperty(value = "任职开始时间")
	private Date startDate;

	@ApiModelProperty(value = "任职结束时间")
	private Date endDate;

	@ApiModelProperty(value = "创建人")
	private Long createdBy;
}
