package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 组织职能数据实体
 */
@Data
public class OrgFunctionVersionDetailBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本状态
	 * 1 未启用；2 启用；3 停用
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

