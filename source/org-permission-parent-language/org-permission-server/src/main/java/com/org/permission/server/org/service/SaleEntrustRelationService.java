package com.org.permission.server.org.service;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.SaleEntrustRelationQueryParam;
import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.SaleEntrustRelationReqParam;
import com.common.base.enums.StateEnum;

import java.util.List;

/**
 * 销售委托关系服务
 */
public interface SaleEntrustRelationService {

	/**
	 * 新增销售委托关系
	 *
	 * @param reqParam 销售委托关系
	 */
	void addSaleEntrustRelation(SaleEntrustRelationBean reqParam);

	/**
	 * 更新销售委托关系
	 *
	 * @param reqParam 销售委托关系
	 */
	void updateSaleEntrustRelation(SaleEntrustRelationBean reqParam);

	/**
	 * 业务单元更新级联更新销售托关系
	 *
	 * @param reqParam 更新参数
	 */
	void buFuncCascadeUpdateSaleEntrustRelation(SaleEntrustRelationBean reqParam, StateEnum stateEnum, Long groupId);

	/**
	 * 启停销售委托关系
	 *
	 * @param reqParam 启停销售委托关系
	 */
	void enableSaleEntrustRelation(EnableOperateParam reqParam);

	/**
	 * 查询销售委托关系列表
	 *
	 * @param reqParam 分页查询销售委托关系查询请求参数
	 * @return 销售委托关系集合
	 */
	PageInfo<SaleEntrustRelationInfoBean> querySaleEntrustRelationList(SaleEntrustRelationReqParam reqParam);

	/**
	 * 查询销售委托关系
	 *
	 * @param reqParam 查询销售委托关系请求参数
	 * @return 销售委托关系集合
	 */
	List<SaleEntrustRelationInfoBean> querySaleEntrustRelation(SaleEntrustRelationQueryParam reqParam);
}
