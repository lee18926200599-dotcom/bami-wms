package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.LogisticsOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface LogisticsOrgFuncMapper {

	/**
	 * 新增物流组织职能
	 *
	 * @param bean 物流组织职能数据实体
	 */
	int addLogisticsFunc(@Param("bean") LogisticsOrgFuncBean bean);

	/**
	 * 更新物流组织职能
	 *
	 * @param bean 物流组织职能数据实体
	 */
	void updateLogisticsFunc(@Param("bean") LogisticsOrgFuncBean bean);
}

