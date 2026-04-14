package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 业务单元开票信息
 */
@Data
public class BUInvoiceInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 取至哪个业务单元数据
	 */
	private Long buId;
	/**
	 * 信用代码
	 */
	private String creditCode;
	/**
	 * 纳税人类型码
	 */
	private String taxpayerCode;
	/**
	 * 纳税人类型名
	 */
	private String taxpayerName;
	/**
	 * 组织机构代码
	 */
	private String orgInstitutionCode;
	/**
	 * 工商注册号
	 */
	private String bizRegistNumber;
	/**
	 * 税务登记号
	 */
	private String taxRegistrationNumber;
}
