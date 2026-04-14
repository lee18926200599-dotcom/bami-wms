package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 组织结构信息
 */
@Data
public class OrgTreeBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 客商ID
	 */
	private Long custId;
	/**
	 * 组织类型
	 * 1 全局,
	 * 2 集团,
	 * 3 业务单元，
	 * 4 部门。
	 */
	private Integer orgType;
	/**
	 * 根业务单元
	 */
	private Integer mainOrgFlag;
	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织命名
	 */
	private String orgName;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 上级业务单元 或 部门 ID
	 */
	private Long parentId;
	/**
	 * 部门 上级业务单元ID
	 */
	private Long parentBUId;
	/**
	 * 业务类型
	 */
	private String bizType;

	public OrgTreeBean parentOrg;

	public Set<OrgTreeBean> childeOrgs = new HashSet<>(4);

	public OrgTreeBean() {
	}
}
