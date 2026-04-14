package com.org.permission.common.dto;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BaseCustSubDto implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "客户编码")
	private String custCode;

	@ApiModelProperty(value = "客户ID，对应客户信息表id")
	private Long custId;

	@ApiModelProperty(value = "归属集团")
	private Long belongOrg;

    @ApiModelProperty(value = "归属集团")
    private String belongOrgName;

	@ApiModelProperty(value = "集团/组织编码")
	private String orgCode;

	@ApiModelProperty(value = "组织类型（1 平台;2 集团;3 业务单元(组织);4部门）")
	private Integer orgType;

	@ApiModelProperty(value = "对应客户上下游关系 1-客户（下游），2-供应商（上游）")
	private Integer custType;

	@ApiModelProperty(value = "客户名称")
	private String custName;

	@ApiModelProperty(value = "客户拼音")
	private String custPy;

	@ApiModelProperty(value = "简称")
	private String shortName;

	@ApiModelProperty(value = "信用代码")
	private String creditCode;

	@ApiModelProperty(value = "基本分类 基础数据，客户分类，供应商分类")
	private Integer category;

	@ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
	private String businessType;

    @ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
    private String businessTypeName;

	@ApiModelProperty(value = "渠道类型 字典数据")
	private String channelType;

	@ApiModelProperty(value = "企业类型：个体，连锁，分公司等")
	private Integer enterpriseType;

	@ApiModelProperty(value = "经营状态 字典数据：在业、存续、吊销、注销、迁出")
	private Integer operateStatus;

	@ApiModelProperty(value = "上级客户 不允许互为上下级")
	private Long parentId;

	@ApiModelProperty(value = "行业编码。 字典数据")
	private String tradeCode;

	@ApiModelProperty(value = "行业 字典数据")
	private Integer trade;

	@ApiModelProperty(value = "单位类型 1:外部单位，2:内部单位")
	private Integer departmentType;

	@ApiModelProperty(value = "对应业务单元 当单位类型为内部单位时启用")
	private Long orgId;

	@ApiModelProperty(value = "可为空。同一集团与同一客商，同时发生上下游两种关系时，此值为对应的另一条记录的cust_sub表的ID")
	private Long sameCustSub;

	@ApiModelProperty(value = "是否散户 0:否，1:是")
	private Integer retailsFlag;

	@ApiModelProperty(value = "是否工商注册。如果只有集团档时，此字段只能为否。如果有全局档时，与全局档保持一致。两个集团档升级全局档时，如果一方为是，则全局档为是并更新为否的集档")
	private Integer saicFlag;

	@ApiModelProperty(value = "客商税类")
	private Integer taxType;

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

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "固定电话或手机。固定电话要有区号，分机号可有可无。")
	private String phone;

	@ApiModelProperty(value = "身份证姓名")
	private String identityName;

	@ApiModelProperty(value = "身份证号码")
	private String identityNumber;

	@ApiModelProperty(value = "手持身份证照片")
	private String handIdentityPhoto;

	@ApiModelProperty(value = "创建组织")
	private Long createOrg;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "冻结标识 1:已冻结，0:未冻结")
	private Integer deletedFlag;

	@ApiModelProperty(value = "来源系统。对应数据字典：crm-lyxt")
	private Integer source;

	@ApiModelProperty(value = "启停状态 0:未启用，1:已启用，2:已停用")
	private Integer state;

	@ApiModelProperty(value = "来源渠道集团客户ID")
	private Long sourceChannelCustId;

	@ApiModelProperty(value = "来源渠道集团客户名称")
	private String sourceChannelCustName;

	@ApiModelProperty(value = "营业执照照片")
	private String licensePhoto;

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

	private List<BaseCustAddressDto> custAddressList;

	private List<BaseCustContactsDto> custContactsList;

}
