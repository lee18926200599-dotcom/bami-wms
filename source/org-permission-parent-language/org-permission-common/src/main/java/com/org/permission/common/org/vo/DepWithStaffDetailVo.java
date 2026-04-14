package com.org.permission.common.org.vo;

import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 部门及人员信息
 */
@ApiModel(description = "部门及人员信息")
@Data
public class DepWithStaffDetailVo extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "部门编码")
	private String orgCode;

	@ApiModelProperty(value = "部门名称")
	private String depName;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "集团名称")
	private String groupName;

	@ApiModelProperty(value = "上级部门ID")
	private Long parentDepId;

	@ApiModelProperty(value = "上级部门名")
	private String parentDepName;

	@ApiModelProperty(value = "上级业务单元Id")
	private Long parentBUId;

	@ApiModelProperty(value = "上级业务单元名")
	private String parentBUName;

	@ApiModelProperty(value = "成立时间")
	private Date establishTime;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty(value = "")
	private List<StaffInfoDto> staffs;

	@ApiModelProperty(value = "状态")
	private Integer state;


	@ApiModelProperty(value = "部门负责人")
	private Long depDutyStaff;

    @ApiModelProperty("注册来源")
    private Integer registSource;

    @ApiModelProperty("注册来源Id")
    private String registSourceId;

}
