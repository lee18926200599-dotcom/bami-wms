package com.org.permission.server.org.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 集团组织树(含部门)
 */
public class OrgTreeVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织 id
	 */
	private Integer id;
	/**
	 * 组织类别
	 *
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
	 * 组织简称
	 */
	private String orgShortName;
	/**
	 * 下级业务单元
	 */
	private List<OrgTreeVo> childBUs;
	/**
	 * 下级部门
	 */
	private List<OrgTreeVo> childeDeps;


	public OrgTreeVo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public List<OrgTreeVo> getChildBUs() {
		return childBUs;
	}

	public void setChildBUs(List<OrgTreeVo> childBUs) {
		this.childBUs = childBUs;
	}

	public List<OrgTreeVo> getChildeDeps() {
		return childeDeps;
	}

	public void setChildeDeps(List<OrgTreeVo> childeDeps) {
		this.childeDeps = childeDeps;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OrgTreeDto{");
		sb.append("id=").append(id);
		sb.append(", orgType=").append(orgType);
		sb.append(", orgCode='").append(orgCode).append('\'');
		sb.append(", orgName='").append(orgName).append('\'');
		sb.append(", orgShortName='").append(orgShortName).append('\'');
		sb.append(", childBUs=").append(childBUs);
		sb.append(", childeDeps=").append(childeDeps);
		sb.append('}');
		return sb.toString();
	}
}
