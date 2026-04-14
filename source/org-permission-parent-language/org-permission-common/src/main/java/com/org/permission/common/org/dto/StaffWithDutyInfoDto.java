package com.org.permission.common.org.dto;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工及其任职信息
 */
@Data
public class StaffWithDutyInfoDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("所属部门ID")
	private Long depId;

	@ApiModelProperty("所属部门名")
	private String depName;

	@ApiModelProperty("所属业务单元ID")
	private Long orgId;

	@ApiModelProperty("所属集团")
	private Long groupId;

	@ApiModelProperty("所属业务单元名")
	private String orgName;

	@ApiModelProperty("人员编码")
	private String staffCode;

	@ApiModelProperty("姓名")
	private String realname;

	@ApiModelProperty("性别(0=男;1女)")
	private Integer sex;

	@ApiModelProperty("证件类型")
	private Integer certificateType;

	@ApiModelProperty("证件号码")
	private String certificateNo;

	@ApiModelProperty("生日")
	private String birthday;

	@ApiModelProperty("电话")
	private String phone;

	@ApiModelProperty("邮件")
	private String email;

	@ApiModelProperty("用工性质（0 正式工;1 临时工）")
	private Integer employmentType;

	@ApiModelProperty(value = "状态(1未启用;2启用;3停用;4删除")
	private Integer state;

	@ApiModelProperty(value = "生成用户的用户id")
	private Long userId;

	@ApiModelProperty("关联用户（true是;false否）")
	private Boolean relatedUser;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty("人员任职信息")
	private StaffDutyInfoDvo staffDuty;

	@ApiModelProperty("类别业务编码（供业务线调用）")
	private String bizCode;

	@ApiModelProperty("直属上级，选填")
	private Long directSupervisor;

    @ApiModelProperty("注册来源")
    private Integer registSource;

    @ApiModelProperty("注册来源Id")
    private String registSourceId;
}
