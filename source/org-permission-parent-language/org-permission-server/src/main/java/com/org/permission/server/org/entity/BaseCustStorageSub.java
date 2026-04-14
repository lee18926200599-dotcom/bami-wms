package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓储信息分配子表
 */
@Data
@TableName("base_cust_storage_sub")
public class BaseCustStorageSub implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "物流类型。1-快递，2-物流")
	@TableField(value = "logistics_type")
	private Integer logisticsType;

	@ApiModelProperty(value = "快递简码")
	@TableField(value = "express_code")
	private String expressCode;

	@ApiModelProperty(value = "基础数据-打印模板 ；物流类型-快递时，必填")
	@TableField(value = "print_template")
	private Integer printTemplate;

	@ApiModelProperty(value = "劳务合作类型 字典取值 1:长期、2:短期")
	@TableField(value = "labor_type")
	private Integer laborType;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	@TableField(value = "pay_agreement")
	private Integer payAgreement;

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
