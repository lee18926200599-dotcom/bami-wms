package com.org.permission.server.org.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员任职详细信息
 */
@ApiModel(description = "人员任职信息", value = "StaffDutyInfoBean")
@Data
public class StaffDutyInfoBean extends StaffDutyBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团名")
	private String groupName;

	@ApiModelProperty(value = " 业务单元名")
	private String orgName;

	@ApiModelProperty(value = " 部门名")
	private String depName;

	@ApiModelProperty(value = "人员类别名")
	private String staffTypeName;
}
