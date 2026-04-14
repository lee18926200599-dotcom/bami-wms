package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织简要信息
 */
@Data
public class OrgConciseInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务单元ID
	 */
	private Long id;
	/**
	 * 集团ID
	 */
	private Long groupId;

	/**
	 * 集团名
	 */
	private String groupName;

	/**
	 * 组织类别
	 * 2 集团
	 * 3 业务单元
	 * 4 部门
	 */
	private Integer orgType;
	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 状态
	 * 1未启用；2启用；3停用
	 */
	private Integer state;
}
