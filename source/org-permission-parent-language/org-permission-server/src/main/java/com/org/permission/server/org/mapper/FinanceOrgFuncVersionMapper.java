package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.FinanceOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 财务组织职能写 mapper
 */
@Mapper
public interface FinanceOrgFuncVersionMapper {

	/**
	 * 新增法人组织职能版本
	 *
	 * @param bean 法人组织职能数据实体
	 */
	int addFinanceFunc(@Param("bean") FinanceOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

