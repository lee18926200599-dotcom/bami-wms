package com.org.permission.server.org.enums;

import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.springframework.util.StringUtils;

/**
 * 实体属性枚举
 */
public enum EntityAttrEnum {
	/**
	 * 1 公司
	 * 2 分公司
	 * 3 办事处
	 * 4 事业部
	 * 5 部门
	 * 6 工厂
	 * 7 其他
	 */
	COMPANY("1", "公司"), CONTROLLED_COMPANY("2", "分公司"), OFFICE("3", "办事处"), BUSINESS("4", "事业部"),
	DEPARTMENT("5", "部门"), FACTORY("6", "工厂"), OTHER("7", "其他");

	private String attrCode;

	private String attrName;

	EntityAttrEnum(String attrCode, String attrName) {
		this.attrCode = attrCode;
		this.attrName = attrName;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public String getAttrName() {
		return attrName;
	}

	public  static  EntityAttrEnum getEntityAttrEnum(String attrCode){
		if (StringUtils.isEmpty(attrCode)) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,"实体属性不存在");
		}
		for (EntityAttrEnum entityAttrEnum : EntityAttrEnum.values()) {
			if (entityAttrEnum.getAttrCode().equals(attrCode)) {
				return entityAttrEnum;
			}
		}

		throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE,"实体属性不存在");
	}
}
