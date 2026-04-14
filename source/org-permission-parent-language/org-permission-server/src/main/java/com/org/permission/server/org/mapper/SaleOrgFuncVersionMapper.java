package com.org.permission.server.org.mapper;


import com.org.permission.common.org.dto.func.SaleOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 销售组织职能写 mapper
 */
@Mapper
public interface SaleOrgFuncVersionMapper {

	/**
	 * 新版本法人组织职能
	 *
	 * @param bean     法人组织职能数据实体
	 * @param reqParam 版本参数
	 */
	int addSaleFunc(@Param("bean") SaleOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

