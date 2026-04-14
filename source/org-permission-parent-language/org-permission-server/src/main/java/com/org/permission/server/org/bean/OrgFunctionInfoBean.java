package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织职能数据实体
 */
@Data
public class OrgFunctionInfoBean implements Serializable {
	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	private Integer functionType;
	/**
	 * 业务单元ID
	 */
	private Long orgId;
	/**
	 * 状态
	 */
	private Integer state;
}

