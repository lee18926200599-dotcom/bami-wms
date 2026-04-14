package com.org.permission.common.org.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 修改人员任职详细信息
 */
@ApiModel(description = "修改人员任职信息", value = "StaffDutyInfoVo")
@Data
public class ModifyDutyInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("所属集团ID")
	private Long groupId;

	@ApiModelProperty(value = " 业务单元ID")
	private Long orgId;

	@ApiModelProperty(value = " 部门ID")
	private Long depId;

	@ApiModelProperty(value = "人员类别ID，逗号分隔")
	private String staffTypeId;

	@ApiModelProperty(value = "人员类别业务编码，逗号分隔")
	private String staffTypeBizCode;

	@ApiModelProperty(value = "任职开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate startDate;

	@ApiModelProperty(value = "任职结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate endDate;
}
