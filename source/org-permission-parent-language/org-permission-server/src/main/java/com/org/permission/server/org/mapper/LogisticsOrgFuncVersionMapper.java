package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.LogisticsOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流组织职能
 */
@Mapper
public interface LogisticsOrgFuncVersionMapper {

	/**
	 * 新版本物流组织职能
	 *
	 * @param bean     物流组织职能数据实体
	 * @param reqParam 版本参数
	 */
	void addLogisticsFunc(@Param("bean") LogisticsOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

