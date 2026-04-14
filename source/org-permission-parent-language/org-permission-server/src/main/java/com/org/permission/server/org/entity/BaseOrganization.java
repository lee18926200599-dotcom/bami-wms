package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织(集团、业务单元、部门)表
 */
@Data
@TableName("base_organization")
public class BaseOrganization implements Serializable {

	@ApiModelProperty(value = "主键")

	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "组织类型（1 平台;2 集团;3 业务单元(组织);4部门）")
	@TableField(value = "org_type")
	private Integer orgType;

	@ApiModelProperty(value = "组织编码")
	@TableField(value = "org_code")
	private String orgCode;

	@ApiModelProperty(value = "组织名称")
	@TableField(value = "org_name")
	private String orgName;

	@ApiModelProperty(value = "主组织（0否;1是）")
	@TableField(value = "main_org_flag")
	private Integer mainOrgFlag;

	@ApiModelProperty(value = "集团ID")
	@TableField(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "上级组织")
	@TableField(value = "parent_id")
	private Long parentId;

	@ApiModelProperty(value = "上级业务单元")
	@TableField(value = "parent_bu_id")
	private Long parentBuId;

	@ApiModelProperty(value = "组织简称")
	@TableField(value = "org_short_name")
	private String orgShortName;

	@ApiModelProperty(value = "实体属性字典编码")
	@TableField(value = "entity_code")
	private String entityCode;

	@ApiModelProperty(value = "实体属性")
	@TableField(value = "entity_name")
	private String entityName;

	@ApiModelProperty(value = "行业字典码")
	@TableField(value = "industry_code")
	private String industryCode;

	@ApiModelProperty(value = "行业字典名")
	@TableField(value = "industry_name")
	private String industryName;

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

	@ApiModelProperty(value = "地址")
	@TableField(value = "address")
	private String address;

	@ApiModelProperty(value = "电话")
	@TableField(value = "phone")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	@TableField(value = "email")
	private String email;

	@ApiModelProperty(value = "网址")
	@TableField(value = "net_address")
	private String netAddress;

	@ApiModelProperty(value = "信用代码")
	@TableField(value = "credit_code")
	private String creditCode;

	@ApiModelProperty(value = "所属公司")
	@TableField(value = "company_id")
	private Long companyId;

	@ApiModelProperty(value = "成立时间")
	@TableField(value = "establish_time")
	private Long establishTime;

	@ApiModelProperty(value = "本位币")
	@TableField(value = "currency")
	private String currency;

	@ApiModelProperty(value = "全局客户ID")
	@TableField(value = "cust_id")
	private Long custId;

	@ApiModelProperty(value = "部门负责人")
	@TableField(value = "dep_duty_staff")
	private Integer depDutyStaff;

	@ApiModelProperty(value = "内部客户ID")
	@TableField(value = "inner_cust_id")
	private Long innerCustId;

	@ApiModelProperty(value = "客户业务类型")
	@TableField(value = "business_type")
	private String businessType;

	@ApiModelProperty(value = "状态（0 未启用;1 启用;2 停用;3删除）")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "启用时间")
	@TableField(value = "start_time")
	private Date startTime;

	@ApiModelProperty(value = "停用时间")
	@TableField(value = "stop_time")
	private Date stopTime;

	@ApiModelProperty(value = "初始化状态（0 未完成;1 完成）")
	@TableField(value = "init_flag")
	private Integer initFlag;

	@ApiModelProperty(value = "引用状态（0 未引用;1 已引用）")
	@TableField(value = "quoted_flag")
	private Integer quotedFlag;

	@ApiModelProperty(value = "简介")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "主营业务")
	@TableField(value = "main_business")
	private String mainBusiness;

	@ApiModelProperty(value = "版本号")
	@TableField(value = "version")
	private String version;

	@ApiModelProperty(value = "LOGO URL")
	@TableField(value = "logo_url")
	private String logoUrl;

	@ApiModelProperty(value = "数据来源")
	@TableField(value = "regist_source")
	private Integer registSource;

	@ApiModelProperty(value = "数据来源Id")
	@TableField(value = "regist_source_id")
	private String registSourceId;
	@TableField(value = "deleted_flag")
	private Integer deletedFlag;

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
