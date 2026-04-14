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
 * 物流信息分配子表实体类型
 */
@Data
@TableName("base_cust_logistics_sub")
public class BaseCustLogisticsSub implements Serializable {

	@ApiModelProperty(value = "主键ID")
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "收发货单位类型,1-发货单位；2-收货单位")
	@TableField(value = "delivery_type")
	private Integer deliveryType;

	@ApiModelProperty(value = "当单位类型为：2-收货单位时，此ID为其对应的发货单位物流子表ID")
	@TableField(value = "delivery_department")
	private Integer deliveryDepartment;

	@ApiModelProperty(value = "托运类型 字典数据 1:普通客户, 2:vip客户, 3:三方客户")
	@TableField(value = "consign_type")
	private Integer consignType;

	@ApiModelProperty(value = "信用额度")
	@TableField(value = "credit_amount")
	private BigDecimal creditAmount;

	@ApiModelProperty(value = "是否黑名单 0:否，1:是")
	@TableField(value = "black_flag")
	private Integer blackFlag;

	@ApiModelProperty(value = "是否月结 0-否，1-是")
	@TableField(value = "monthly_flag")
	private Integer monthlyFlag;

	@ApiModelProperty(value = "承运商属性 字典数据 1:个人承运商，2:车队承运商，3:外发承运商")
	@TableField(value = "carrier_type")
	private Integer carrierType;

	@ApiModelProperty(value = "劳务合作类型 字典取值 1:长期、2:短期")
	@TableField(value = "labor_type")
	private Integer laborType;

	@ApiModelProperty(value = "是否开票 0:否，1:是")
	@TableField(value = "invoice_flag")
	private Integer invoiceFlag;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	@TableField(value = "pay_agreement")
	private Integer payAgreement;

	@ApiModelProperty(value = "退货款方式。字典数据：tms-thkfs。1-现金，2-汇款，3-支票")
	@TableField(value = "refund_type")
	private Integer refundType;

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
