package com.org.permission.server.org.bean;

import lombok.Data;

import java.util.Date;

/**
 * 集团LOGO管理写bean
 */
@Data
public class GroupLogoWriteBean {
	/**
	 * 操作人ID
	 */
	private Long userId;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 操作类型
	 * 1 新增；
	 * 2 删除；
	 * 3 更新；
	 */
	private Integer operateType;
	/**
	 * LOGO URL
	 */
	private String logoUrl;
	/**
	 * 更新时间
	 */
	private Date modifiedDate;
}
