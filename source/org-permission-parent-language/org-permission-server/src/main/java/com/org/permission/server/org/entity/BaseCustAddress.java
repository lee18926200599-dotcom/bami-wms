package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址信息
 */
@Data
@TableName("base_cust_address")
public class BaseCustAddress implements Serializable {

	@ApiModelProperty(value = "主键ID")
    private Long id;

	@ApiModelProperty(value = "客户信息表ID")
	private Long custId;

	@ApiModelProperty(value = "客户ID")
	private Long custSubId;

	@ApiModelProperty(value = "所属组织")
	private Long belongOrg;

	@ApiModelProperty(value = "归属的业务领域，业务类型：1:采销，2:仓储，3:物流，4:财务")
	private Integer businessType;

	@ApiModelProperty(value = "所属实体类型")
	private String entityType;

	@ApiModelProperty(value = "所属实体id")
	@TableField(value = "entity_id")
	private Integer entityId;

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

	@ApiModelProperty(value = "详细地址 手工输入")
	@TableField(value = "address")
	private String address;

	@ApiModelProperty(value = "地址全称")
	@TableField(value = "all_address")
	private String allAddress;

	@ApiModelProperty(value = "地址类型 1:收发货地址，2:退货地址，3:收件方地址")
	@TableField(value = "address_type")
	private Integer addressType;

	@ApiModelProperty(value = "邮编")
	@TableField(value = "postcode")
	private String postcode;

	@ApiModelProperty(value = "姓名")
	@TableField(value = "contacts_name")
	private String contactsName;

	@ApiModelProperty(value = "电话")
	@TableField(value = "telephone")
	private String telephone;

	@ApiModelProperty(value = "手机")
	@TableField(value = "mobile_phone")
	private String mobilePhone;

	@ApiModelProperty(value = "邮箱")
	@TableField(value = "email")
	private String email;

	@ApiModelProperty(value = "公司名称")
	@TableField(value = "company_name")
	private String companyName;

	@ApiModelProperty(value = "收货客户D")
	@TableField(value = "receipt_cust")
	private Long receiptCust;

	@ApiModelProperty(value = "是否默认 0:否，1是")
	@TableField(value = "default_flag")
	private Integer defaultFlag;

	@ApiModelProperty(value = "删除标识 0=末删除 1=删除")
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
