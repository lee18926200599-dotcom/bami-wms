package com.org.permission.server.org.enums;

/**
 * 客商委托类型枚举
 */
public enum CustEntrustTypeEnum {
	MARKET(1),// 采销
	STORAGE(2),// 仓配
	LOGISTICS(3);// 配配

	private int type;

	CustEntrustTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
