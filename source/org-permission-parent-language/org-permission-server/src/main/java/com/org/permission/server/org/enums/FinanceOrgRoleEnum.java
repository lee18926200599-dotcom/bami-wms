package com.org.permission.server.org.enums;


import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;

/**
 * 财务用户角色枚举
 */
public enum FinanceOrgRoleEnum {
	BIZ_ORG(1), ACCOUNT_ORG(2), SETTLE_ORG(3);

	private int userRole;

	FinanceOrgRoleEnum(int userRole) {
		this.userRole = userRole;
	}

	public int getUserRole() {
		return userRole;
	}

	public static FinanceOrgRoleEnum getFinanceUserRoleEnum(Integer userRole) {
		if (null == userRole) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "用户组织角色类型错误");
		}

		for (FinanceOrgRoleEnum financeOrgRoleEnum : FinanceOrgRoleEnum.values()) {
			if (financeOrgRoleEnum.getUserRole() == userRole) {
				return financeOrgRoleEnum;
			}
		}
		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "用户组织角色类型错误");
	}
}
