package com.org.permission.server.org.enums;


import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;

/**
 * 档案类别枚举
 */
public enum ArchiveTypeEnum {

	STAFF(0), CUST(1), SUPPORT(2);

	private int type;

	ArchiveTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static final ArchiveTypeEnum getArchiveTypeEnum(Integer type) {
		if (type == null) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "档案类别参数错误");
		}

		for (ArchiveTypeEnum archiveTypeEnum : ArchiveTypeEnum.values()) {
			if (archiveTypeEnum.getType() == type) {
				return archiveTypeEnum;
			}
		}
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "档案类别参数错误");
	}
}
