package com.org.permission.server.org.vo;

import java.io.Serializable;

/**
 * 组织职能数据实体
 */
public class OrganizationFunctionInfoVo implements Serializable {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	private Integer functionType;
	/**
	 * 业务单元ID
	 */
	private Integer orgId;
	/**
	 * 状态（0 未启用；1 启用；2 停用）
	 */
	private Integer status;

	public OrganizationFunctionInfoVo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OrganizationFunctionInfoBean{");
		sb.append("id=").append(id);
		sb.append(", functionType=").append(functionType);
		sb.append(", orgId=").append(orgId);
		sb.append(", status=").append(status);
		sb.append('}');
		return sb.toString();
	}
}

