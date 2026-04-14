package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息表
 */
@Data
@TableName("base_cust_info")
public class BaseCustInfo implements Serializable {

	@ApiModelProperty(value = "客户ID，主键，ID生成器生成")
	
	@TableId(value = "id")

	private Long id;

	@ApiModelProperty(value = "客户编码")
	@TableField(value = "cust_code")
	private String custCode;

	@ApiModelProperty(value = "客户姓名")
	@TableField(value = "cust_name")
	private String custName;

	@ApiModelProperty(value = "客户拼音")
	@TableField(value = "cust_py")
	private String custPy;

	@ApiModelProperty(value = "客户简称")
	@TableField(value = "short_name")
	private String shortName;

	@ApiModelProperty(value = "归属于全局")
	@TableField(value = "belong_org")
	private Long belongOrg;

	@ApiModelProperty(value = "信用代码")
	@TableField(value = "credit_code")
	private String creditCode;

	@ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
	@TableField(value = "business_type")
	private String businessType;

	@ApiModelProperty(value = "是否工商登记 0:否，1:是")
	@TableField(value = "saic_flag")
	private Integer saicFlag;

	@ApiModelProperty(value = "是否已经入驻 0:否，1:是")
	@TableField(value = "presence_flag")
	private Integer presenceFlag;

	@ApiModelProperty(value = "对应集团")
	@TableField(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "对应业务单元 全局客户与业务单元关联后回写")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "企业类型：个体，连锁，分公司等")
	@TableField(value = "enterprise_type")
	private Integer enterpriseType;

	@ApiModelProperty(value = "渠道类型 字典数据")
	@TableField(value = "channel_type")
	private String channelType;

	@ApiModelProperty(value = "上级客户 不允许互为上下级")
	@TableField(value = "parent_id")
	private Long parentId;

	@ApiModelProperty(value = "行业编码。 字典数据。")
	@TableField(value = "trade_code")
	private String tradeCode;

	@ApiModelProperty(value = "经营状态 字典数据：在业、存续、吊销、注销、迁出")
	@TableField(value = "operate_state")
	private Integer operateState;

	@ApiModelProperty(value = "国家三位编码")
	@TableField(value = "gb_code")
	private String gbCode;

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

	@ApiModelProperty(value = "身份证姓名")
	@TableField(value = "identity_name")
	private String identityName;

	@ApiModelProperty(value = "身份证号码")
	@TableField(value = "identity_number")
	private String identityNumber;

	@ApiModelProperty(value = "手持身份证照片")
	@TableField(value = "hand_identity_photo")
	private String handIdentityPhoto;

	@ApiModelProperty(value = "营业执照照片")
	@TableField(value = "license_photo")
	private String licensePhoto;

	@ApiModelProperty(value = "备注")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "冻结标识 1:已冻结，0:未冻结")
	@TableField(value = "freeze_flag")
	private Integer freezeFlag;

	@ApiModelProperty(value = "启停状态 0:未启用，1:已启用，2:已停用")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "核准状态 0:未核准，1:已核准；非全局档手工添加默认为0，由全局节点更新为1")
	@TableField(value = "check_state")
	private Integer checkState;

	@ApiModelProperty(value = "来源，记录数据的来源系统编码，通用的系统编码")
	@TableField(value = "source")
	private Integer source;

	@ApiModelProperty(value = "删除标识  0=未删除  1=删除")
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
