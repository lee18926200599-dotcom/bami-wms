package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.CorporateOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface CorporateOrgFuncMapper {

	/**
	 * 新增法人组织职能
	 *
	 * @param bean 法人组织职能数据实体
	 */
	int addCorporateFunc(@Param("bean") CorporateOrgFuncBean bean);

	/**
	 * 更新法人组织职能
	 *
	 * @param bean 法人组织职能数据实体
	 */
	void updateCorporateFunc(@Param("bean") CorporateOrgFuncBean bean);
}

