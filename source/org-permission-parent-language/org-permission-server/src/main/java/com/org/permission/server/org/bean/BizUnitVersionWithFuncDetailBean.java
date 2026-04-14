package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务单元信息数据实体
 */
@Data
public class BizUnitVersionWithFuncDetailBean extends BizUnitWithFuncDetailBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本状态
	 */
	private Integer versionFlag;

	/**
	 * 版本开始时间
	 */
	private Date versionStartTime;

	/**
	 * 版本结束时间
	 */
	private Date versionEndTime;
}
