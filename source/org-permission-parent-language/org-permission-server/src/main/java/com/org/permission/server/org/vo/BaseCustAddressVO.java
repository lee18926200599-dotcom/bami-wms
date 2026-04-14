package com.org.permission.server.org.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;



@Data
@Accessors(chain = true)
public class BaseCustAddressVO extends BaseVO{

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
	private Integer entityId;

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

	@ApiModelProperty(value = "详细地址 手工输入")
	private String address;

	@ApiModelProperty(value = "地址全称")
	private String allAddress;

	@ApiModelProperty(value = "地址类型 1:收发货地址，2:退货地址，3:收件方地址")
	private Integer addressType;

	@ApiModelProperty(value = "邮编")
	private String postcode;

	@ApiModelProperty(value = "姓名")
	private String contactsName;

	@ApiModelProperty(value = "电话")
	private String telephone;

	@ApiModelProperty(value = "手机")
	private String mobilePhone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "收货客户D")
	private Long receiptCust;

	@ApiModelProperty(value = "是否默认 0:否，1是")
	private Integer defaultFlag;

	@ApiModelProperty(value = "删除标识 0=末删除 1=删除")
	private Integer deletedFlag;

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

}
