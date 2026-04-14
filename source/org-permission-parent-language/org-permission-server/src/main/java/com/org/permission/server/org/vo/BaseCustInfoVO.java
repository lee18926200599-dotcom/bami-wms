package com.org.permission.server.org.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;



@Data
@Accessors(chain = true)
public class BaseCustInfoVO extends BaseVO{

	@ApiModelProperty(value = "客户ID，主键，ID生成器生成")
	private Long id;

	@ApiModelProperty(value = "客户编码")
	private String custCode;

	@ApiModelProperty(value = "客户姓名")
	private String custName;

	@ApiModelProperty(value = "客户拼音")
	private String custPy;

	@ApiModelProperty(value = "客户简称")
	private String shortName;

	@ApiModelProperty(value = "归属于全局")
	private Long belongOrg;

	@ApiModelProperty(value = "信用代码")
	@TableField(value = "credit_code")
	private String creditCode;

	@ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
	private String businessType;

	@ApiModelProperty(value = "业务类型 业务类型，可多选，英文逗号隔开")
	private List<String> businessTypeList;

	@ApiModelProperty(value = "是否工商登记 0:否，1:是")
	private Integer saicFlag;

	@ApiModelProperty(value = "是否已经入驻 0:否，1:是")
	private Integer presenceFlag;

	@ApiModelProperty(value = "对应集团")
	private Long groupId;

	@ApiModelProperty(value = "对应业务单元 全局客户与业务单元关联后回写")
	private Long orgId;

	@ApiModelProperty(value = "企业类型：个体，连锁，分公司等")
	private Integer enterpriseType;

	@ApiModelProperty(value = "渠道类型 字典数据")
	private String channelType;

	@ApiModelProperty(value = "上级客户 不允许互为上下级")
	private Long parentId;

	@ApiModelProperty(value = "行业编码。 字典数据。")
	private String tradeCode;

	@ApiModelProperty(value = "经营状态 字典数据：在业、存续、吊销、注销、迁出")
	private Integer operateState;

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

	@ApiModelProperty(value = "身份证姓名")
	private String identityName;

	@ApiModelProperty(value = "身份证号码")
	private String identityNumber;

	@ApiModelProperty(value = "手持身份证照片")
	private String handIdentityPhoto;

	@ApiModelProperty(value = "营业执照照片")
	private String licensePhoto;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "冻结标识 1:已冻结，0:未冻结")
	private Integer freezeFlag;

	@ApiModelProperty(value = "启停状态 0:未启用，1:已启用，2:已停用")
	private Integer state;

	@ApiModelProperty(value = "核准状态 0:未核准，1:已核准；非全局档手工添加默认为0，由全局节点更新为1")
	private Integer checkState;

	@ApiModelProperty(value = "来源，记录数据的来源系统编码，通用的系统编码")
	private Integer source;

	@ApiModelProperty(value = "删除标识  0=未删除  1=删除")
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
