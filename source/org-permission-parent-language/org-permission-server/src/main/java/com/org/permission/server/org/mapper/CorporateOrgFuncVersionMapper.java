package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.CorporateOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 法人组织职能版本写 mapper
 */
@Mapper
public interface CorporateOrgFuncVersionMapper {

	/**
	 * 新增法人组织职能
	 *
	 * @param bean 法人组织职能数据实体
	 */
	void addCorporateFunc(@Param("bean") CorporateOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

