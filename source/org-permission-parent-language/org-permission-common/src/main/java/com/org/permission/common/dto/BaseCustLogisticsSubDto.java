package com.org.permission.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Accessors(chain = true)
public class BaseCustLogisticsSubDto implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	private Long orgId;

	@ApiModelProperty(value = "收发货单位类型,1-发货单位；2-收货单位")
	private Integer deliveryType;

	@ApiModelProperty(value = "当单位类型为：2-收货单位时，此ID为其对应的发货单位物流子表ID")
	private Integer deliveryDepartment;

	@ApiModelProperty(value = "托运类型 字典数据 1:普通客户, 2:vip客户, 3:三方客户")
	private Integer consignType;

	@ApiModelProperty(value = "信用额度")
	private BigDecimal creditAmount;

	@ApiModelProperty(value = "是否黑名单 0:否，1:是")
	private Integer blackFlag;

	@ApiModelProperty(value = "是否月结 0-否，1-是")
	private Integer monthlyFlag;

	@ApiModelProperty(value = "承运商属性 字典数据 1:个人承运商，2:车队承运商，3:外发承运商")
	private Integer carrierType;

	@ApiModelProperty(value = "劳务合作类型 字典取值 1:长期、2:短期")
	private Integer laborType;

	@ApiModelProperty(value = "是否开票 0:否，1:是")
	private Integer invoiceFlag;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	private Integer payAgreement;

	@ApiModelProperty(value = "退货款方式。字典数据：tms-thkfs。1-现金，2-汇款，3-支票")
	private Integer refundType;

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
