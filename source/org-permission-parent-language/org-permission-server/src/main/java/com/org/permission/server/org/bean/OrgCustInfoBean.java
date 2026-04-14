package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团信息数据实体
 */
@Data
public class OrgCustInfoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	private Long orgId;
	/**
	 * 组织类型
	 */
	private Integer orgType;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 业务类型
	 */
	private String businessType;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer state;
	/**
	 * 是否初始化
	 * <p>
	 * 0 未完成；1 完成
	 */
	private Integer initFlag;
}
