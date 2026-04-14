package com.org.permission.common.dto;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseOrganizationDto implements Serializable {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "组织类型（1 平台;2 集团;3 业务单元(组织);4部门）")
	private Integer orgType;

	@ApiModelProperty(value = "组织编码")
	private String orgCode;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "主组织（0否;1是）")
	private Integer mainOrgFlag;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

    @ApiModelProperty(value = "集团名称")
    private String groupName;

	@ApiModelProperty(value = "上级组织")
	private Long parentId;

    @ApiModelProperty(value = "上级组织名称")
    private String parentName;

	@ApiModelProperty(value = "上级业务单元")
	private Long parentBuId;

	@ApiModelProperty(value = "组织简称")
	private String orgShortName;

	@ApiModelProperty(value = "实体属性字典编码")
	private String entityCode;

	@ApiModelProperty(value = "实体属性")
	private String entityName;

	@ApiModelProperty(value = "行业字典码")
	private String industryCode;

	@ApiModelProperty(value = "行业字典名")
	private String industryName;

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

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "网址")
	private String netAddress;

	@ApiModelProperty(value = "信用代码")
	private String creditCode;

	@ApiModelProperty(value = "所属公司")
	private Long companyId;

	@ApiModelProperty(value = "成立时间")
	private Long establishTime;

	@ApiModelProperty(value = "本位币")
	private String currency;

	@ApiModelProperty(value = "全局客户ID")
	private Long custId;

	@ApiModelProperty(value = "全局客户名称")
	private String custName;

	@ApiModelProperty(value = "部门负责人")
	private Integer depDutyStaff;

	@ApiModelProperty(value = "内部客户ID")
	private Long innerCustId;

	@ApiModelProperty(value = "客户业务类型")
	private String businessType;

	@ApiModelProperty(value = "状态（0 未启用;1 启用;2 停用;3删除）")
	private Integer state;

	@ApiModelProperty(value = "启用时间")
	private Date startTime;

	@ApiModelProperty(value = "停用时间")
	private Date stopTime;

	@ApiModelProperty(value = "初始化状态（0 未完成;1 完成）")
	private Integer initFlag;

	@ApiModelProperty(value = "引用状态（0 未引用;1 已引用）")
	private Integer quotedFlag;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "主营业务")
	private String mainBusiness;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "LOGO URL")
	private String logoUrl;

	@ApiModelProperty(value = "数据来源")
	private Integer registSource;

	@ApiModelProperty(value = "数据来源Id")
	private String registSourceId;

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

	private List<BaseOrganizationFunctionDto> bofDtoList;

	private List<BaseOrganizationDto> orgList;

}
