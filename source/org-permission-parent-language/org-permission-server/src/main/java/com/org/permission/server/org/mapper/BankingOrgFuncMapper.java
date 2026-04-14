package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.BankingOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface BankingOrgFuncMapper {

	/**
	 * 新增物流组织职能
	 *
	 * @param bean 物流组织职能数据实体
	 */
	int addBankingFunc(@Param("bean") BankingOrgFuncBean bean);

	/**
	 * 更新金融组织职能
	 * @param bean
	 */
	void updateBankFunc(@Param("bean") BankingOrgFuncBean bean);
}

