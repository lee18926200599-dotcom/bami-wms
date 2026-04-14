package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 精简业务单元信息,数据实体
 */
@Data
public class BizUnitInfoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务单元ID
	 */
	private Long id;
	/**
	 * 业务单元编码
	 */
	private String orgCode;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer state;
	/**
	 * 业务单元名称
	 */
	private String orgName;
	/**
	 * 业务单元简称
	 */
	private String orgShortName;
	/**
	 * 版本号
	 */
	private String version;

	private List<OrgFunctionInfoBean> orgFuncs;
}
