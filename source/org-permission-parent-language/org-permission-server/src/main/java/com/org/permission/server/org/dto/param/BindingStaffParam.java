package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 绑定人员请求参数
 */
@ApiModel(description = "绑定人员请求参数")
@Data
public class BindingStaffParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("人员 ID（必填）")
	private List<Long> staffIds;

	@ApiModelProperty("绑定关系ID（必填）")
	private Long relId;

	/**
	 * 操作时间(系统时间)
	 */
	@ApiModelProperty("操作时间(系统时间)")
	private Date opTime;
}
