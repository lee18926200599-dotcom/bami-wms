package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织状态数据实体
 */
@Data
public class OrgBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer state;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 上级ID
	 */
	private Long parentId;
	/**
	 * 是否初始化
	 * <p>
	 * 0 未完成；1 完成
	 */
	private Integer initFlag;
	/**
	 * 根业务单元
	 * 0否；
	 * 1是；
	 */
	private Integer mainOrgFlag;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 内部客户ID
	 */
	private Long innerCustId;
	/**
	 * 本位币
	 */
	private String currency;
	/**
	 * 业务类型
	 */
	private String bizTypeId;

	private String orgName;

	private Integer deletedFlag;
}
