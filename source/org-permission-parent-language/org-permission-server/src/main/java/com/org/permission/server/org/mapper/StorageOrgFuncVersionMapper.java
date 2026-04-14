package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.StorageOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 仓储组织职能写 mapper
 */
@Mapper
public interface StorageOrgFuncVersionMapper {

	/**
	 * 新版本仓储组织职能
	 *
	 * @param bean     仓储组织职能数据实体
	 * @param reqParam 版本参数
	 */
	int addStorageFunc(@Param("bean") StorageOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

