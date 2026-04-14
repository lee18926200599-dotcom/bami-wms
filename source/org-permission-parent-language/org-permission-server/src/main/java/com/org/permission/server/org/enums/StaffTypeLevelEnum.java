package com.org.permission.server.org.enums;

import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;

/**
 * 类别级别
 */
public enum StaffTypeLevelEnum {
	/**
	 * 类别级别
	 * 1 全局；
	 * 2 集团。
	 */
	GLOBAL(1),GROUP(2);
	private int level;

	StaffTypeLevelEnum(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
	public static final StaffTypeLevelEnum getStaffTypeLevelEnum(Integer level) {
		if (level == null) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,"人员类别错误");
		}

		for (StaffTypeLevelEnum staffTypeLevelEnum : StaffTypeLevelEnum.values()) {
			if (staffTypeLevelEnum.getLevel() == level) {
				return staffTypeLevelEnum;
			}
		}
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,"人员类别错误");
	}
}
