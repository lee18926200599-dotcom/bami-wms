package com.org.permission.server.org.mapper;


import com.org.permission.common.org.dto.func.SaleOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface SaleOrgFuncMapper {

	/**
	 * 新增法人组织职能
	 *
	 * @param bean 法人组织职能数据实体
	 */
	int addSaleFunc(@Param("bean") SaleOrgFuncBean bean);

	/**
	 * 更新法人组织职能
	 *
	 * @param bean 法人组织职能数据实体
	 */
	void updateSaleFunc(@Param("bean") SaleOrgFuncBean bean);

	/**
	 * 业务委托关系更新销售组织职能
	 *
	 * @param bean 销售组织职能数据实体
	 */
	void entrustCascadeUpdateSaleFunc(@Param("bean") SaleOrgFuncBean bean);
}

