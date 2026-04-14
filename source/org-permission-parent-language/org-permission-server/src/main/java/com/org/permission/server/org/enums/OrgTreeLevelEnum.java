package com.org.permission.server.org.enums;


import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;

/**
 * 组织树查询级别
 */
public enum OrgTreeLevelEnum {
	/**
	 * 过滤级别
	 *
	 * 1 上级和下级（含平级）
	 * 2 所有下级（含业务单元级部门）
	 * 3 所有下级业务单元
	 */
	UP_CONTAIN_DOWN(1), ALL_DOWN(2),ALL_BUS(3);

	private int level;

	OrgTreeLevelEnum(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public static OrgTreeLevelEnum getOrgTreeLevelEnum(Integer level) {
		if (level == null) {
			throw new IllegalStateException("非法组织树级别");
		}
		for (OrgTreeLevelEnum orgTreeLevelEnum : OrgTreeLevelEnum.values()) {
			if (orgTreeLevelEnum.getLevel() == level) {
				return orgTreeLevelEnum;
			}
		}
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,"组织树过滤级别错误");
	}
}
