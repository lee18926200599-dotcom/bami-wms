package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息分配子表实体类型
 */
@Data
@TableName("base_cust_finance_sub")
public class BaseCustFinanceSub implements Serializable {

	@ApiModelProperty(value = "客户ID，主键，ID生成器生成")
	
	@TableId(value = "id")
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "默认币种")
	@TableField(value = "currency")
	private Integer currency;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	@TableField(value = "pay_agreement")
	private Integer payAgreement;

	@ApiModelProperty(value = "专管部门 参照已分配采销组织的部门档案")
	@TableField(value = "department")
	private Long department;

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
