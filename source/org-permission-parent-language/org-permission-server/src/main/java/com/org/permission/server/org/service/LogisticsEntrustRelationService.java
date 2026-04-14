package com.org.permission.server.org.service;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.server.org.bean.LogisticsEntrustRelationBean;
import com.org.permission.server.org.bean.LogisticsEntrustRelationInfoBean;
import com.org.permission.server.org.dto.LogisticEntrustRelationDto;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.LogisticsEntrustRelationReqParam;

import java.util.List;

/**
 * 物流委托关系服务
 */
public interface LogisticsEntrustRelationService {

	/**
	 * 新增物流委托关系
	 *
	 * @param reqParam 物流委托关系
	 */
	void addLogisticsEntrustRelation(LogisticsEntrustRelationBean reqParam);

	/**
	 * 更新物流委托关系
	 *
	 * @param reqParam 物流委托关系
	 */
	void updateLogisticsEntrustRelation(LogisticsEntrustRelationBean reqParam);

	/**
	 * 启停物流委托关系
	 *
	 * @param reqParam 启停操作请求参数
	 */
	void enableLogisticsEntrustRelation(EnableOperateParam reqParam);

	/**
	 * 查询物流委托关系列表
	 *
	 * @param reqParam 分页查询物流委托关系查询请求参数
	 * @return 物流委托关系集合
	 */
	PageInfo<LogisticsEntrustRelationInfoBean> queryLogisticsEntrustRelationList(
			LogisticsEntrustRelationReqParam reqParam);

	/**
	 * 查询物流委托关系
	 *
	 * @param reqParam 物流委托关系查询请求参数
	 * @return 物流委托关系集合
	 */
	List<LogisticEntrustRelationDto> queryLogisticsEntrustRelation(final LogisticEntrustRelationQueryParam reqParam);

}
