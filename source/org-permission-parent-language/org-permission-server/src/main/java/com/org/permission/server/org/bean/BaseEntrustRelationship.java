package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
/**
 * 业务委托关系数据实体
 */
@Data
public class BaseEntrustRelationship extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 货主ID
	 */
	private Long ownerId;

	/**
	 * 组织ID
	 */
	private Long orgId;
	/**
	 * 委托关系类型
	 * 1 集团间；
	 * 2 集团内
	 */
	private Integer entrustType;

	/**
	 * 委托关系
	 * 1 采购业务委托关系；
	 * 2 销售业务委托关系；
	 * 3 仓储业务委托关系；
	 * 4 物流业务委托关系；
	 * 5 财务委托关系；
	 */
	private Integer entrustBusinessType;
	/**
	 * 是否默认
	 * 1 是；
	 * 0 否；
	 */
	private Integer defaultFlag;
	/**
	 * 状态
	 * 1 未启用；
	 * 2 启用；
	 * 3 停用；
	 */
	private Integer state;

	/**
	 * 来源合同号
	 */
	private String oriAccCode;
	/**
	 * 仓储服务商
	 */
	private Long warehouseProviderId;
	/**
	 * 仓库
	 */
	private Long warehouseId;
	/**
	 * 库存组织
	 */
	private Long stockOrgId;
	/**
	 * 应收应付组织
	 */
	private Long payReceiveOrgId;
	/**
	 * 核算组织
	 */
	private Long accountOrgId;
	/**
	 * 物流服务商
	 */
	private Long logisticsProviderId;
	/**
	 * 物流组织
	 */
	private Long logisticsOrgId;
	/**
	 * 采购/销售组织
	 */
	private Long purchaseSaleOrgId;
	/**
	 * 结算组织
	 */
	private Long settmentOrgId;
	/**
	 * 业务组织
	 */
	private Long businessOrgId;

}

