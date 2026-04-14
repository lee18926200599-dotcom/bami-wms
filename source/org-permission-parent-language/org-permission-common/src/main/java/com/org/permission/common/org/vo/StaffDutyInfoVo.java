package com.org.permission.common.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 人员任职详细信息(前端展示)
 */
@ApiModel(description = "人员任职信息(前端展示)", value = "StaffDutyInfoVo")
@Data
public class StaffDutyInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "集团名")
	private String groupName;

	@ApiModelProperty(value = " 业务单元ID")
	private Long orgId;

	@ApiModelProperty(value = " 业务单元名")
	private String orgName;

	@ApiModelProperty(value = " 部门ID")
	private Long depId;

	@ApiModelProperty(value = " 部门名")
	private String depName;

	@ApiModelProperty(value = "人员类别ID(逗号分隔)")
	private String staffTypeId;

	@ApiModelProperty(value = "人员类别名（逗号分隔）")
	private String staffTypeName;

	@ApiModelProperty(value = "任职开始时间")
	private LocalDate startDate;

	@ApiModelProperty(value = "任职结束时间")
	private LocalDate endDate;

	public StaffDutyInfoVo() {
	}
}
