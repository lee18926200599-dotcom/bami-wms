package com.org.permission.common.org.param;

import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.ModifyDutyInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改人员详细信息（含任职及类别信息）
 */
@ApiModel("修改人员详细信息")
@Data
public class ModifyStaffInfoDto extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("所属部门ID")
	private Long depId;

	@ApiModelProperty("所属业务单元ID")
	private Long orgId;

	@ApiModelProperty("所属集团ID")
	private Long groupId;

	@ApiModelProperty("姓名")
	private String realname;

	@ApiModelProperty("直属上级")
	private Long directSupervisor;

	@ApiModelProperty("性别(0男;1女)")
	private Integer sex;

	@ApiModelProperty("证件类型 字典编码:FPL_CRM_ZJLX  1=二代身份证，2=港澳通行证，3=台湾通行证，4=护照")
	private Integer certificateType;

	@ApiModelProperty("证件号码")
	private String certificateNo;

	@ApiModelProperty("生日")
	private String birthday;

	@ApiModelProperty("电话")
	private String phone;

	@ApiModelProperty("邮件")
	private String email;

	@ApiModelProperty("用工形式 字典编码:FPL_CRM_YGXS 1=正式工 2=临时工")
	private Integer employmentType;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty("人员任职信息")
	private ModifyDutyInfoDto staffDuty;

    @ApiModelProperty("注册来源")
	private Integer registSource;

    @ApiModelProperty("注册来源Id")
    private String registSourceId;

}
