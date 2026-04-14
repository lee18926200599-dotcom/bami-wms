package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息
 */
@Data
public class DepInfoDto extends OrgInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 集团名
	 */
	private String groupName;

	/**
	 * 上级业务单元
	 */
	private String parentBuName;
	/**
	 * 上级部门名称
	 */
	private String parentDepName;


	private BaseAddressDto baseAddressDto;
}
