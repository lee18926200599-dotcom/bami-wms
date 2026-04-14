package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团列表信息
 */
@Data
public class OrgListBean extends BaseTreeBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 组织类型
	 * 1 全局,
	 * 2 集团,
	 * 3 业务单元，
	 * 4 部门。
	 */
	private Integer orgType;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 根业务单元
	 */
	private Integer mainOrgFlag;

	private Long parentId;
}
