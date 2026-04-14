package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.StorageOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface StorageOrgFuncMapper {

	/**
	 * 新增仓储组织职能
	 *
	 * @param bean 仓储组织职能数据实体
	 */
	int addStorageFunc(@Param("bean") StorageOrgFuncBean bean);

	/**
	 * 更新仓储组织职能
	 *
	 * @param bean 仓储组织职能数据实体
	 */
	void updateStorageFunc(@Param("bean") StorageOrgFuncBean bean);

	/**
	 * 业务委托关系更新仓储组织职能
	 *
	 * @param bean 仓储组织职能数据实体
	 */
	void entrustCascadeUpdateStorageFunc(@Param("bean") StorageOrgFuncBean bean);
}

