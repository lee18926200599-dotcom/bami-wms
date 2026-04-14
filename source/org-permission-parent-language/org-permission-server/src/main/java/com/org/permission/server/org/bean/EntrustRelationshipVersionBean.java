package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务委托关系数据实体
 */
@Data
public class EntrustRelationshipVersionBean extends EntrustRelationshipInfoBean implements Serializable {
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

