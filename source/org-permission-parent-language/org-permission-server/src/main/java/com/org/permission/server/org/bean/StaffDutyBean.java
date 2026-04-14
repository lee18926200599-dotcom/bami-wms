package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 人员任职详细信息
 */
@Data
public class StaffDutyBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 人员ID
	 */
	private Long staffId;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 业务单元ID
	 */
	private Long orgId;
	/**
	 * 部门ID
	 */
	private Long depId;
	/**
	 * 人员类别ID
	 */
	private Integer staffTypeId;
	/**
	 * 人员类别业务编码
	 */
	private String staffTypeBizCode;

	@ApiModelProperty(value = "任职开始时间")
	private LocalDate startDate;

	@ApiModelProperty(value = "任职结束时间")
	private LocalDate endDate;
}
