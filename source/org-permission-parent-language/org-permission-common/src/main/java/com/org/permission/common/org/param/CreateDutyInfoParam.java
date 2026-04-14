package com.org.permission.common.org.param;

import com.org.permission.common.org.dto.ModifyDutyInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员任职详细信息
 */
@ApiModel(description = "人员任职信息", value = "StaffDutyInfoVo")
@Data
public class CreateDutyInfoParam extends ModifyDutyInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;
}
