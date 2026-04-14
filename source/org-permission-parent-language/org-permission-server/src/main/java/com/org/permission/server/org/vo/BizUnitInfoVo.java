package com.org.permission.server.org.vo;

import java.io.Serializable;

/**
 * 精简业务单元信息,前端展示
 */
public class BizUnitInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务单元ID
	 */
	private Integer id;
	/**
	 * 业务单元编码
	 */
	private String orgCode;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer status;
	/**
	 * 业务单元名称
	 */
	private String orgName;
	/**
	 * 业务单元简称
	 */
	private String orgShortName;
	/**
	 * 版本号
	 */
	private String version;

	public BizUnitInfoVo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizUnitInfoVo{");
		sb.append("id='").append(id).append('\'');
		sb.append(", orgCode='").append(orgCode).append('\'');
		sb.append(", status=").append(status);
		sb.append(", orgName='").append(orgName).append('\'');
		sb.append(", orgShortName='").append(orgShortName).append('\'');
		sb.append(", version='").append(version).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
