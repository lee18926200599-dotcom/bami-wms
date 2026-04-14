package com.org.permission.server.org.service;


import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;

import java.util.List;

/**
 * 通用业务委托关系管理服务
 */
public interface CommonEntrustRelationService {
	/**
	 * 删除业务委托关系
	 *
	 * @param reqParam 删除业务委托关系请求参数
	 */
	void deleteEntrustRelation(KeyOperateParam reqParam);

	/**
	 * 启用业务委托关系
	 *
	 * @param reqParam 启用业务委托关系请求参数
	 */
	void enableEntrustRelation(EnableOperateParam reqParam);

	/**
	 * 多条件唯一性查询
	 * @param wareHourseId
	 * @param
	 * @return
	 */
	Long queryEntrustRelationWithCondition(Integer entrustRange, Integer entrustType, Long wareHourseId, Long purchaseSaleOrg, Long stockOrg,
											  Long logisticsOrg, Long settmentOrg, Long accountOrg,Long businessOrg);

	/**
	 * 通过采销组织获取委托关系信息
	 * @param purchaseSaleOrgId
	 * @return
	 */
	List<EntrustRelationOrgInfoDto> queryPurchaseEntrustRelation(Long purchaseSaleOrgId);

}

