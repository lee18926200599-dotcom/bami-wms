package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息分配子表
 */
@Data
@TableName("base_cust_sub")
public class BaseCustSub implements Serializable {

	@ApiModelProperty(value = "主键ID")
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "客户编码")
	@TableField(value = "cust_code")
	private String custCode;

	@ApiModelProperty(value = "客户ID，对应客户信息表id")
	@TableField(value = "cust_id")
	private Long custId;

	@ApiModelProperty(value = "归属集团")
	@TableField(value = "belong_org")
	private Long belongOrg;

	@ApiModelProperty(value = "集团/组织编码")
	@TableField(value = "org_code")
	private String orgCode;

    @ApiModelProperty(value = "组织类型（1 平台;2 集团;3 业务单元(组织);4部门）")
    @TableField(value = "org_type")
    private Integer orgType;

	@ApiModelProperty(value = "对应客户上下游关系 1-客户（下游），2-供应商（上游）")
	@TableField(value = "cust_type")
	private Integer custType;

	@ApiModelProperty(value = "客户名称")
	@TableField(value = "cust_name")
	private String custName;

	@ApiModelProperty(value = "客户拼音")
	@TableField(value = "cust_py")
	private String custPy;

	@ApiModelProperty(value = "简称")
	@TableField(value = "short_name")
	private String shortName;

    @ApiModelProperty(value = "信用代码")
    @TableField(value = "credit_code")
    private String creditCode;

	@ApiModelProperty(value = "基本分类 基础数据，客户分类，供应商分类")
	@TableField(value = "category")
	private Integer category;

	@ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
	@TableField(value = "business_type")
	private String businessType;

	@ApiModelProperty(value = "渠道类型 字典数据")
	@TableField(value = "channel_type")
	private String channelType;

	@ApiModelProperty(value = "企业类型：个体，连锁，分公司等")
	@TableField(value = "enterprise_type")
	private Integer enterpriseType;

	@ApiModelProperty(value = "经营状态 字典数据：在业、存续、吊销、注销、迁出")
	@TableField(value = "operate_status")
	private Integer operateStatus;

	@ApiModelProperty(value = "上级客户 不允许互为上下级")
	@TableField(value = "parent_id")
	private Long parentId;

	@ApiModelProperty(value = "行业编码。 字典数据")
	@TableField(value = "trade_code")
	private String tradeCode;

	@ApiModelProperty(value = "行业 字典数据")
	@TableField(value = "trade")
	private Integer trade;

	@ApiModelProperty(value = "单位类型 1:外部单位，2:内部单位")
	@TableField(value = "department_type")
	private Integer departmentType;

	@ApiModelProperty(value = "对应业务单元 当单位类型为内部单位时启用")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "可为空。同一集团与同一客商，同时发生上下游两种关系时，此值为对应的另一条记录的cust_sub表的ID")
	@TableField(value = "same_cust_sub")
	private Long sameCustSub;

	@ApiModelProperty(value = "是否散户 0:否，1:是")
	@TableField(value = "retails_flag")
	private Integer retailsFlag;

	@ApiModelProperty(value = "是否工商注册。如果只有集团档时，此字段只能为否。如果有全局档时，与全局档保持一致。两个集团档升级全局档时，如果一方为是，则全局档为是并更新为否的集档")
	@TableField(value = "saic_flag")
	private Integer saicFlag;

	@ApiModelProperty(value = "客商税类")
	@TableField(value = "tax_type")
	private Integer taxType;

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

	@ApiModelProperty(value = "固定电话或手机。固定电话要有区号，分机号可有可无。")
	@TableField(value = "phone")
	private String phone;

	@ApiModelProperty(value = "身份证姓名")
	@TableField(value = "identity_name")
	private String identityName;

	@ApiModelProperty(value = "身份证号码")
	@TableField(value = "identity_number")
	private String identityNumber;

	@ApiModelProperty(value = "手持身份证照片")
	@TableField(value = "hand_identity_photo")
	private String handIdentityPhoto;

	@ApiModelProperty(value = "创建组织")
	@TableField(value = "create_org")
	private Long createOrg;

	@ApiModelProperty(value = "备注")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "冻结标识 1:已冻结，0:未冻结")
	@TableField(value = "deleted_flag")
	private Integer deletedFlag;

	@ApiModelProperty(value = "来源系统。对应数据字典：crm-lyxt")
	@TableField(value = "source")
	private Integer source;

	@ApiModelProperty(value = "启停状态 0:未启用，1:已启用，2:已停用")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "来源渠道集团客户ID")
	@TableField(value = "source_channel_cust_id")
	private Long sourceChannelCustId;

	@ApiModelProperty(value = "来源渠道集团客户名称")
	@TableField(value = "source_channel_cust_name")
	private String sourceChannelCustName;

	@ApiModelProperty(value = "营业执照照片")
	@TableField(value = "license_photo")
	private String licensePhoto;

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
