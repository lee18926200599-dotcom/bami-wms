package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员任职详细信息
 */
@ApiModel(description = "人员任职信息", value = "StaffDutyInfoVo")
@Data
public class StaffDutyInfoDvo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "集团名")
	private String groupName;

	@ApiModelProperty(value = " 业务单元ID")
	private Long buId;

	@ApiModelProperty(value = " 业务单元名")
	private String buName;

	@ApiModelProperty(value = " 部门ID")
	private Long depId;

	@ApiModelProperty(value = " 部门名")
	private String depName;

	@ApiModelProperty(value = "人员类别ID")
	private Integer staffTypeId;

	@ApiModelProperty(value = "人员类别名")
	private String staffTypeName;

	@ApiModelProperty(value = "任职开始时间")
	private Date startDate;

	@ApiModelProperty(value = "任职结束时间")
	private Date endDate;
}
