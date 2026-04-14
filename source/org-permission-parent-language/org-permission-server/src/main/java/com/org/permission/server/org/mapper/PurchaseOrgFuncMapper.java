package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.PurchaseOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface PurchaseOrgFuncMapper {

	/**
	 * 新增采购组织职能
	 *
	 * @param bean 采购组织职能数据实体
	 */
	int addPurchaseFunc(@Param("bean") PurchaseOrgFuncBean bean);

	/**
	 * 更新采购组织职能
	 *
	 * @param bean 采购组织职能数据实体
	 */
	void updatePurchaseFunc(@Param("bean") PurchaseOrgFuncBean bean);

	/**
	 * 业务委托关系更新采购组织职能
	 *
	 * @param bean 采购组织职能数据实体
	 */
	void entrustCascadeUpdatePurchaseFunc(@Param("bean") PurchaseOrgFuncBean bean);
}

