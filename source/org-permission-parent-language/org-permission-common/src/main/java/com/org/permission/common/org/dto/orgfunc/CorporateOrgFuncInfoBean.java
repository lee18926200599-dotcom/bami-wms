package com.org.permission.common.org.dto.orgfunc;

import com.org.permission.common.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 法人公司组织职能数据实体
 */
@ApiModel(description = "法人组织职能")
@Data
public class CorporateOrgFuncInfoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	@ApiModelProperty("组织ID")
	private Long orgId;
	/**
	 * 组织名
	 */
	@ApiModelProperty("组织名")
	private String orgName;
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
	@ApiModelProperty("功能类别")
	private Integer funcType = 1;
	/**
	 * 上级公司ID
	 */
	@ApiModelProperty("上级公司ID")
	private Long parentOrgId;
	/**
	 * 上级公司名
	 */
	@ApiModelProperty("上级公司名")
	private String parentOrgName;
	/**
	 * 企业类型字典码
	 */
	@ApiModelProperty("企业类型字典码")
	private String enterpriseCode;
	/**
	 * 企业类型字典名
	 */
	@ApiModelProperty("企业类型字典名")
	private String enterpriseName;
	/**
	 * 纳税人类型码
	 */
	@ApiModelProperty("纳税人类型码")
	private String taxpayerCode;
	/**
	 * 纳税人类型名
	 */
	@ApiModelProperty("纳税人类型名")
	private String taxpayerName;
	/**
	 * 组织机构代码
	 */
	@ApiModelProperty("组织机构代码")
	private String orgInstitutionCode;
	/**
	 * 工商注册号
	 */
	@ApiModelProperty("工商注册号")
	private String bizRegistNumber;
	/**
	 * 注册资本
	 */
	@ApiModelProperty("注册资本")
	private String registeredCapital;
	/**
	 * 成立日期
	 */
	@ApiModelProperty("成立日期")
	private Date establishTime;
	/**
	 * 业务开始日期
	 */
	@ApiModelProperty("业务开始日期")
	private Date bizStartTime;
	/**
	 * 业务结束日期
	 */
	@ApiModelProperty("业务结束日期")
	private Date bizEndTime;

	/**
	 * 网址
	 */
	@ApiModelProperty("网址")
	private String netAddress;
	/**
	 * 联系人
	 */
	@ApiModelProperty("联系人")
	private String linker;
	/**
	 * 电话
	 */
	@ApiModelProperty("电话")
	private String phone;
	/**
	 * 电子邮件
	 */
	@ApiModelProperty("电子邮件")
	private String email;
	/**
	 * 简介
	 */
	@ApiModelProperty("简介")
	private String remark;

}

