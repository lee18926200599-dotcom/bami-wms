package com.org.permission.server.org.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系展示数据
 */
@Data
public class StoragePlatformEntrustRelationInfoBean extends StoragePlatformEntrustRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存组织
	 */
	private String stockOrgName;

	/**
	 * 物流组织
	 */
	private String logisticsOrgName;
}
