package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织职能表
 */
@Data
@TableName("base_organization_function")
public class BaseOrganizationFunction implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "功能类别（1 法人公司;2 财务;3 采购;4 销售;5 仓储;6 物流;7 金融;8 劳务; 9 平台）")
	@TableField(value = "function_type")
	private Integer functionType;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "上级公司ID")
	@TableField(value = "parent_org_id")
	private Long parentOrgId;

	@ApiModelProperty(value = "企业类型字典码")
	@TableField(value = "enterprise_code")
	private String enterpriseCode;

	@ApiModelProperty(value = "企业类型字典名")
	@TableField(value = "enterprise_name")
	private String enterpriseName;

	@ApiModelProperty(value = "纳税人类型字典码")
	@TableField(value = "taxpayer_code")
	private String taxpayerCode;

	@ApiModelProperty(value = "纳税人类型字典名")
	@TableField(value = "taxpayer_name")
	private String taxpayerName;

	@ApiModelProperty(value = "组织机构代码")
	@TableField(value = "org_institution_code")
	private String orgInstitutionCode;

	@ApiModelProperty(value = "工商注册号")
	@TableField(value = "biz_regist_number")
	private String bizRegistNumber;

	@ApiModelProperty(value = "状态（1 未启用;2 启用;3 停用;4 删除）")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "注册资本")
	@TableField(value = "registered_capital")
	private String registeredCapital;

	@ApiModelProperty(value = "成立日期")
	@TableField(value = "establish_time")
	private Long establishTime;

	@ApiModelProperty(value = "业务开始日期")
	@TableField(value = "business_start_time")
	private Long businessStartTime;

	@ApiModelProperty(value = "业务结束日期")
	@TableField(value = "business_end_time")
	private Long businessEndTime;

	@ApiModelProperty(value = "网址")
	@TableField(value = "net_address")
	private String netAddress;

	@ApiModelProperty(value = "简介")
	@TableField(value = "note")
	private String note;

	@ApiModelProperty(value = "税务登记号")
	@TableField(value = "tax_registration_number")
	private String taxRegistrationNumber;

	@ApiModelProperty(value = "默认应付组织")
	@TableField(value = "default_pay_org_id")
	private Long defaultPayOrgId;

	@ApiModelProperty(value = "默认结算财务组织")
	@TableField(value = "default_settlement_org_id")
	private Long defaultSettlementOrgId;

	@ApiModelProperty(value = "默认库存组织")
	@TableField(value = "default_stock_org_id")
	private Long defaultStockOrgId;

	@ApiModelProperty(value = "上级销售组织")
	@TableField(value = "parent_sale_org_id")
	private Long parentSaleOrgId;

	@ApiModelProperty(value = "默认应收组织")
	@TableField(value = "default_receive_org_id")
	private Long defaultReceiveOrgId;

	@ApiModelProperty(value = "默认核算组织")
	@TableField(value = "default_account_org_id")
	private Long defaultAccountOrgId;

	@ApiModelProperty(value = "默认物流组织")
	@TableField(value = "default_logistics_org_id")
	private Long defaultLogisticsOrgId;

	@ApiModelProperty(value = "物流，网点代码")
	@TableField(value = "logistics_function_code")
	private String logisticsFunctionCode;

	@ApiModelProperty(value = "运输职能类型（0.业务网点;1.职能网点）")
	@TableField(value = "logistics_function_type")
	private Integer logisticsFunctionType;

	@ApiModelProperty(value = "国家三位编码")
	@TableField(value = "region_code")
	private String regionCode;

	@ApiModelProperty(value = "国家")
	@TableField(value = "region_name")
	private String regionName;

	@ApiModelProperty(value = "省ID")
	@TableField(value = "province_code")
	private Long provinceCode;

	@ApiModelProperty(value = "省名称")
	@TableField(value = "province_name")
	private String provinceName;

	@ApiModelProperty(value = "市ID")
	@TableField(value = "city_code")
	private Long cityCode;

	@ApiModelProperty(value = "市名称")
	@TableField(value = "city_name")
	private String cityName;

	@ApiModelProperty(value = "区ID")
	@TableField(value = "district_code")
	private Long districtCode;

	@ApiModelProperty(value = "区名称")
	@TableField(value = "district_name")
	private String districtName;

	@ApiModelProperty(value = "街道ID")
	@TableField(value = "street_code")
	private Long streetCode;

	@ApiModelProperty(value = "街道名称")
	@TableField(value = "street_name")
	private String streetName;

	@ApiModelProperty(value = "详细地址")
	@TableField(value = "address")
	private String address;

	@ApiModelProperty(value = "经度")
	@TableField(value = "longitude")
	private Double longitude;

	@ApiModelProperty(value = "纬度")
	@TableField(value = "latitude")
	private Double latitude;

	@ApiModelProperty(value = "创建人id")
	@TableField(value = "created_by")
	private Long createdBy;

	@ApiModelProperty(value = "创建人")
	@TableField(value = "created_name")
	private String createdName;

	@ApiModelProperty(value = "创建日期")
	@TableField(value = "created_date")
	private Date createdDate;

	@ApiModelProperty(value = "修改人id")
	@TableField(value = "modified_by")
	private Long modifiedBy;

	@ApiModelProperty(value = "修改人")
	@TableField(value = "modified_name")
	private String modifiedName;

	@ApiModelProperty(value = "修改时间")
	@TableField(value = "modified_date")
	private Date modifiedDate;

}
