package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.org.dto.BaseAddressDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务单元组织职能请求参数
 */
@ApiModel(description = "业务单元组织职能请求参数", value = "OrgFunctionReqParam")
@Data
public class OrgFunctionReqParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "功能类别(1 法人公司;2 财务;3 采购;4 销售;5 仓储;6 物流;7 金融;8 劳务;9 平台)")
	private Integer functionType;

	@ApiModelProperty(value = "状态")
	private Integer state;

	@ApiModelProperty(value = "上级公司ID")
	private Long parentOrgId;

	@ApiModelProperty(value = "企业类型字典码")
	private String enterpriseCode;

	@ApiModelProperty(value = "企业类型字典名")
	private String enterpriseName;

	@ApiModelProperty(value = "纳税人类型字典码")
	private String taxpayerCode;

	@ApiModelProperty(value = "纳税人类型字典名")
	private String taxpayerName;

	@ApiModelProperty(value = "组织机构代码")
	private String orgCode;

	@ApiModelProperty(value = "工商注册号")
	private String bizRegistNumber;

	@ApiModelProperty(value = "注册资本")
	private String registeredCapital;

	@ApiModelProperty(value = "成立日期")
	private Date establishTime;

	@ApiModelProperty(value = "业务起止时间")
	private Date[] bizTime;

	@ApiModelProperty(value = "网址")
	private String netAddress;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "联系人信息")
	private List<LinkerInfoReqParam> linkerInfos;

	@ApiModelProperty(value = "是否独立缴纳增值税")
	private Integer aloneFlag;

	@ApiModelProperty(value = "税务登记号")
	private String taxRegistrationNumber;

	@ApiModelProperty(value = "默认应付组织")
	private Long defaultPayOrgId;

	@ApiModelProperty(value = "默认结算财务组织")
	private Long defaultSettlementOrgId;

	@ApiModelProperty(value = "默认库存组织")
	private Long defaultStockOrgId;

	@ApiModelProperty(value = "上级销售组织")
	private Long parentSaleOrgId;

	@ApiModelProperty(value = "默认应收组织")
	private Long defaultReceiveOrgId;

	@ApiModelProperty(value = "默认核算组织")
	private Long defaultAccountOrgId;

	@ApiModelProperty(value = " 默认物流组织")
	private Long defaultLogisticsOrgId;

	@ApiModelProperty(value = "经度")
	private double longitude;

	@ApiModelProperty(value = "纬度")
	private double latitude;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;
}

