package com.org.permission.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Accessors(chain = true)
public class BaseCustPurchaseSubDto implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	private Long orgId;

	@ApiModelProperty(value = "是否做起订限制 0:否，1:是")
	private Integer minimumFlag;

	@ApiModelProperty(value = "最小起订金额")
	private BigDecimal minimumAmount;

	@ApiModelProperty(value = "下单交货天数")
	private Integer deliveryDays;

	@ApiModelProperty(value = "供应商类型 可多选，逗号分隔 字典取值 1:采购、2:委派加工")
	private String providerType;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	private Integer payAgreement;

	@ApiModelProperty(value = "商户类型，0-个体；1-连锁")
	private Integer storeType;

	@ApiModelProperty(value = "是否开票 0:否，1:是")
	private Integer invoiceFlag;

	@ApiModelProperty(value = "字典-发票类型：增值税普票、增值税增票")
	private Integer invoiceType;

	@ApiModelProperty(value = "专管部门 参照已分配采销组织的部门档案")
	private Integer department;

	@ApiModelProperty(value = "专管业务员 与专管部门联动")
	private Integer worker;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态。0-未启用，1-已启用，2-已停用")
	private Integer state;

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
