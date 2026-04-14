package com.org.permission.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Accessors(chain = true)
public class BaseOrganizationFunctionDto implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "功能类别（1 法人公司;2 财务;3 采购;4 销售;5 仓储;6 物流;7 金融;8 劳务; 9 平台）")
	private Integer functionType;

	@ApiModelProperty(value = "业务单元ID")
	private Long orgId;

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
	private String orgInstitutionCode;

	@ApiModelProperty(value = "工商注册号")
	private String bizRegistNumber;

	@ApiModelProperty(value = "状态（1 未启用;2 启用;3 停用;4 删除）")
	private Integer state;

	@ApiModelProperty(value = "注册资本")
	private String registeredCapital;

	@ApiModelProperty(value = "成立日期")
	private Long establishTime;

	@ApiModelProperty(value = "业务开始日期")
	private Long businessStartTime;

	@ApiModelProperty(value = "业务结束日期")
	private Long businessEndTime;

	@ApiModelProperty(value = "网址")
	private String netAddress;

	@ApiModelProperty(value = "简介")
	private String note;

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

	@ApiModelProperty(value = "默认物流组织")
	private Long defaultLogisticsOrgId;

	@ApiModelProperty(value = "物流，网点代码")
	private String logisticsFunctionCode;

	@ApiModelProperty(value = "运输职能类型（0.业务网点;1.职能网点）")
	private Integer logisticsFunctionType;

	@ApiModelProperty(value = "国家三位编码")
	private String regionCode;

	@ApiModelProperty(value = "国家")
	private String regionName;

	@ApiModelProperty(value = "省ID")
	private Long provinceCode;

	@ApiModelProperty(value = "省名称")
	private String provinceName;

	@ApiModelProperty(value = "市ID")
	private Long cityCode;

	@ApiModelProperty(value = "市名称")
	private String cityName;

	@ApiModelProperty(value = "区ID")
	private Long districtCode;

	@ApiModelProperty(value = "区名称")
	private String districtName;

	@ApiModelProperty(value = "街道ID")
	private Long streetCode;

	@ApiModelProperty(value = "街道名称")
	private String streetName;

	@ApiModelProperty(value = "详细地址")
	private String address;

	@ApiModelProperty(value = "经度")
	private Double longitude;

	@ApiModelProperty(value = "纬度")
	private Double latitude;

	@ApiModelProperty(value = "创建人id")
	private Long createdBy;

	@ApiModelProperty(value = "创建人")
	private String createdName;

	@ApiModelProperty(value = "创建日期")
	private Date createdDate;

	@ApiModelProperty(value = "修改人id")
	private Long modifiedBy;

	@ApiModelProperty(value = "修改人")
	private String modifiedName;

	@ApiModelProperty(value = "修改时间")
	private Date modifiedDate;

	private List<BaseEntrustRelationshipDto> berDtoList;

}
