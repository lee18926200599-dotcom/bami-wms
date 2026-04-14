package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.EntrustRelationQueryParam;
import com.org.permission.common.org.param.QueryStorageEntrustRelationLogisticsOrgParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务委托关系读 mapper
 */
@Mapper
public interface StorageEntrustRelationMapper {

	/**
	 * 统计有仓储委托关系的物流组织信息
	 *
	 * @param queryParam 委托关系查询请求参数
	 * @return 数量
	 */
	int countHasStorageEntrustRelationLogisticsOrg(@Param(value = "queryParam") EntrustRelationQueryParam queryParam);

	/**
	 * 查询有仓储委托关系的物流组织信息
	 *
	 * @param queryParam 委托关系查询请求参数
	 * @return 委托组织信息
	 */
	List<OrgInfoDto> queryHasStorageEntrustRelationLogisticsOrg(
			@Param(value = "queryParam") EntrustRelationQueryParam queryParam);

	List<EntrustRelationOrgInfoDto> queryStorageEntrustRelationLogisticsOrgByWarehouse(@Param("queryParam") QueryStorageEntrustRelationLogisticsOrgParam queryParam);
}

