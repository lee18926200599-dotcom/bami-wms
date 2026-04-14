package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.MarketEntrustRelationDto;
import com.org.permission.common.org.dto.WarehouseEnterOwnerRankingListDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.MarketEntrustRelationQueryParam;
import com.org.permission.common.org.param.WarehouseEnterOwnerRankingListParam;
import com.org.permission.server.org.bean.MarketEntrustRelationBean;
import com.org.permission.server.org.bean.MarketEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.MarketEntrustRelationReqParam;

import java.util.List;

/**
 * 采销委托关系服务
 */
public interface MarketEntrustRelationshipService {

	/**
	 * 新增采销委托关系
	 *
	 * @param reqParam 采销委托关系
	 */
	void addMarketEntrustRelation(MarketEntrustRelationBean reqParam);

	/**
	 * 更新采销委托关系
	 *
	 * @param reqParam 采销委托关系
	 */
	void updateMarketEntrustRelation(MarketEntrustRelationBean reqParam);

	/**
	 * 启停采购委托关系
	 *
	 * @param reqParam 启停操作请求参数
	 */
	void enableMarketEntrustRelation(EnableOperateParam reqParam);

	/**
	 * 查询采销委托关系列表
	 *
	 * @param reqParam 分页查询采销委托关系查询请求参数
	 * @return 采销委托关系集合
	 */
	PageInfo<MarketEntrustRelationInfoBean> queryMarketEntrustRelationList(
			MarketEntrustRelationReqParam reqParam);

	/**
	 * 查询采销委托关系
	 *
	 * @param reqParam 采销委托关系查询请求参数
	 * @return 采销委托关系
	 */
	List<MarketEntrustRelationDto> queryMarketEntrustRelation(MarketEntrustRelationQueryParam reqParam);

	/**
	 * 仓库入驻货主排行榜
	 * <p>
	 * 数据已实现 Comparable 接口 业务方自行排序（默认按降序排列）
	 */
	List<WarehouseEnterOwnerRankingListDto> warehouseEnterOwnerRankingList(WarehouseEnterOwnerRankingListParam reqParam);
}
