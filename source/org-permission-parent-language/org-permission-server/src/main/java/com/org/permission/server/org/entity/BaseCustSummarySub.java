package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 汇总信息分配子表
 */
@Data
@TableName("base_cust_summary_sub")
public class BaseCustSummarySub implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	@TableField(value = "org_id")
	private Long orgId;

	@ApiModelProperty(value = "已分配业务职能。逗号分隔")
	@TableField(value = "assigned_buz_funcs")
	private String assignedBuzFuncs;

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
