package com.org.permission.server.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 人员简要信息
 */
@Data
public class StaffConciseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 人员ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 人员编码
	 */
	private String staffCode;
	/**
	 * 姓名
	 */
	private String realname;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 用工性质
	 * 0 正式工
	 * 1 临时工
	 */
	private Integer employmentType;
	/**
	 * 人员状态
	 * 1 未启用
	 * 2 启用
	 * 3 停用
	 * 4 删除
	 */
	private Integer state;

	private Long buId;

	private String typeName;

	private String bizCode;

	/**
	 * 人员ID
	 */
	private Long relId;

}
