package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采销信息分配子表
 */
@Data
@TableName("base_cust_purchase_sub")
public class BaseCustPurchaseSub implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "是否做起订限制 0:否，1:是")
	@TableField(value = "minimum_flag")
	private Integer minimumFlag;

	@ApiModelProperty(value = "最小起订金额")
	@TableField(value = "minimum_amount")
	private BigDecimal minimumAmount;

	@ApiModelProperty(value = "下单交货天数")
	@TableField(value = "delivery_days")
	private Integer deliveryDays;

	@ApiModelProperty(value = "供应商类型 可多选，逗号分隔 字典取值 1:采购、2:委派加工")
	@TableField(value = "provider_type")
	private String providerType;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	@TableField(value = "pay_agreement")
	private Integer payAgreement;

	@ApiModelProperty(value = "商户类型，0-个体；1-连锁")
	@TableField(value = "store_type")
	private Integer storeType;

	@ApiModelProperty(value = "是否开票 0:否，1:是")
	@TableField(value = "invoice_flag")
	private Integer invoiceFlag;

	@ApiModelProperty(value = "字典-发票类型：增值税普票、增值税增票")
	@TableField(value = "invoice_type")
	private Integer invoiceType;

	@ApiModelProperty(value = "专管部门 参照已分配采销组织的部门档案")
	@TableField(value = "department")
	private Integer department;

	@ApiModelProperty(value = "专管业务员 与专管部门联动")
	@TableField(value = "worker")
	private Integer worker;

	@ApiModelProperty(value = "备注")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "状态。0-未启用，1-已启用，2-已停用")
	@TableField(value = "state")
	private Integer state;

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
