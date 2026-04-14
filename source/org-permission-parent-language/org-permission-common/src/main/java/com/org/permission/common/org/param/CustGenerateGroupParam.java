package com.org.permission.common.org.param;

import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 客商生成集团请求参数
 */
@Data
public class CustGenerateGroupParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;
	private String userName;
	/**
	 * 集团名
	 */
	private String groupName;
	/**
	 * 信用代码
	 */
	private String creditCode;
	/**
	 * 组织机构代码
	 */
	private String orgInstitutionCode;
	/**
	 * 客商ID
	 */
	private Long custId;
	/**
	 * 税务登记号
	 */
	private String taxRegistrationNumber;
	/**
	 * 纳税人类型字典码
	 */
	private String taxpayerCode;
	/**
	 * 纳税人类型字典名
	 */
	private String taxpayerName;

	/**
	 * 集团简称
	 */
	private String orgShortName;

	/**
	 * 集团地址
	 */
	private BaseAddressDto addressDto;

	/**
	 * 行业代码
	 */
	private String industryCode;

	/**
	 * 行业名称
	 */
	private String industryName;
}
