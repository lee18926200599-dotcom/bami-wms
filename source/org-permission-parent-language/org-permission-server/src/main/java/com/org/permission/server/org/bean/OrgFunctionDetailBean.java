package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 组织职能数据实体
 */
@Data
public class OrgFunctionDetailBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	private Long orgId;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	private Integer functionType;
	/**
	 * 状态（0 未启用；1 启用；2 停用）
	 */
	private Integer state;
	/**
	 * 上级公司ID
	 */
	private Long parentOrgId;
	/**
	 * 企业类型字典码
	 */
	private String enterpriseCode;
	/**
	 * 企业类型字典名
	 */
	private String enterpriseName;
	/**
	 * 纳税人类型码
	 */
	private String taxpayerCode;
	/**
	 * 纳税人类型名
	 */
	private String taxpayerName;
	/**
	 * 组织机构代码
	 */
	private String orgInstitutionCode;
	/**
	 * 工商注册号
	 */
	private String bizRegistNumber;
	/**
	 * 注册资本
	 */
	private String registeredCapital;
	/**
	 * 成立日期
	 */
	private Date establishTime;
	/**
	 * 业务开始日期
	 */
	private Date businessStartTime;
	/**
	 * 业务结束日期
	 */
	private Date businessEndTime;
	/**
	 * 网址
	 */
	private String netAddress;
	/**
	 * 简介
	 */
	private String remark;
	/**
	 * 联系人
	 */
	private String linker1;
	/**
	 * 电话
	 */
	private String phone1;
	/**
	 * 电子邮件
	 */
	private String email1;

	/**
	 * 联系人
	 */
	private String linker2;
	/**
	 * 电话
	 */
	private String phone2;
	/**
	 * 电子邮件
	 */
	private String email2;

	/**
	 * 是否独立缴纳增值税
	 */
	private Integer aloneFlag;
	/**
	 * 税务登记号
	 */
	private String taxRegistrationNumber;
	/**
	 * 默认应付组织
	 */
	private Long defaultPayOrgId;
	/**
	 * 默认结算财务组织
	 */
	private Long defaultSettlementOrgId;
	/**
	 * 默认库存组织
	 */
	private Long defaultStockOrgId;
	/**
	 * 上级销售组织
	 */
	private Long parentSaleOrgId;
	/**
	 * 默认应收组织
	 */
	private Long defaultReceiveOrgId;
	/**
	 * 默认核算组织
	 */
	private Long defaultAccountOrgId;
	/**
	 * 默认物流组织
	 */
	private Long defaultLogisticsOrgId;
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;
}

