package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.PurchaseOrgFuncBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 采购组织职能写 mapper
 */
@Mapper
public interface PurchaseOrgFuncVersionMapper {

	/**
	 * 新版本采购组织职能
	 *
	 * @param bean     采购组织职能数据实体
	 * @param reqParam 版本参数
	 */
	int addPurchaseFunc(@Param("bean") PurchaseOrgFuncBean bean, @Param("reqParam") SaveNewVersionBUParam reqParam);
}

