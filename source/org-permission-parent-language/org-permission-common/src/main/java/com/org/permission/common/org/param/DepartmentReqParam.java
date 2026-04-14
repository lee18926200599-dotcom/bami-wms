package com.org.permission.common.org.param;

import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门请求参数
 */
@ApiModel(description = "部门请求参数", value = "DepartmentReqParam")
@Data
public class DepartmentReqParam extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "部门名称")
	private String depName;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "上级部门")
	private Long parentId;

	@ApiModelProperty(value = "上级业务单元")
	private Long parentBUId;

	@ApiModelProperty(value = "成立时间")
	private Date establishTime;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty(value = "启用状态")
	private Integer state;

	@ApiModelProperty(value = "部门负责人")
	private Long depDutyStaff;

    @ApiModelProperty(value = "注册来源")
    private Integer registSource;

    @ApiModelProperty(value = "注册来源Id")
    private String registSourceId;
}
