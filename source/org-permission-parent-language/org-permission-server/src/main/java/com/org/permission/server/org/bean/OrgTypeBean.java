package com.org.permission.server.org.bean;

import com.org.permission.server.exception.OrgException;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织信息
 */
@Data
public class OrgTypeBean implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 集团ID
	 */
	private Long groupId;
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

	public OrgTypeBean() {
	}

	@Override
	public OrgTypeBean clone() {
		try {
			return (OrgTypeBean) super.clone();
		} catch (CloneNotSupportedException ignored) {
			throw new OrgException(1000, "系统错误");
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OrgTypeBean{");
		sb.append("id=").append(id);
		sb.append(", groupId=").append(groupId);
		sb.append(", orgType=").append(orgType);
		sb.append(", state=").append(state);
		sb.append('}');
		return sb.toString();
	}
}
