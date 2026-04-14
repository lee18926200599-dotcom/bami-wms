package com.org.permission.server.org.service;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.PurchaseEntrustRelationQueryParam;
import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationReqParam;
import com.common.base.enums.StateEnum;

import java.util.List;

/**
 * 采购委托关系服务
 */
public interface PurchaseEntrustRelationService {

	/**
	 * 新增采购委托关系
	 *
	 * @param reqParam 采购委托关系
	 */
	void addPurchaseEntrustRelation(PurchaseEntrustRelationBean reqParam);

	/**
	 * 更新采购委托关系
	 *
	 * @param reqParam 采购委托关系
	 */
	void updatePurchaseEntrustRelation(PurchaseEntrustRelationBean reqParam);

	/**
	 * 业务单元组织能更新级联更新业务委托关系
	 *
	 * @param bean 采购委托关系
	 */
	void buFuncCascadeUpdatePurchaseEntrustRelation(PurchaseEntrustRelationBean bean, StateEnum stateEnum, Long groupId);

	/**
	 * 启停采购委托关系
	 *
	 * @param reqParam 启停操作请求参数
	 */
	void enablePurchaseEntrustRelation(EnableOperateParam reqParam);

	/**
	 * 查询采购委托关系列表
	 *
	 * @param reqParam 分页查询采购委托关系查询请求参数
	 * @return 采购委托关系集合
	 */
	PageInfo<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelationList(
			PurchaseEntrustRelationReqParam reqParam);

	/**
	 * 查询采购委托关系
	 *
	 * @param reqParam 查询采购委托关系请求参数
	 * @return 采购委托关系集合
	 */
	List<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelation(PurchaseEntrustRelationQueryParam reqParam);
}
