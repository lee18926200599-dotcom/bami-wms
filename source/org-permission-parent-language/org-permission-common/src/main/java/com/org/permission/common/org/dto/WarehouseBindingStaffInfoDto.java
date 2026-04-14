package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓库绑定人员信息
 */
@ApiModel
@Data
public class WarehouseBindingStaffInfoDto extends StaffInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 关系表 ID
	 */
	private Integer relationId;
	/**
	 * 关联数据 ID
	 */
	private Long relId;
	/**
	 * 关联数据名
	 */
	private String relName;

	/**
	 * 人员类别名
	 */
	private String staffTypeName;
}
