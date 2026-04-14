package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务委托关系表
 */
@Data
@TableName("base_entrust_relationship")
public class BaseEntrustRelationship implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	
	private Long id;

	@ApiModelProperty(value = "委托范围（1 集团间; 2 集团内）")
	@TableField(value = "entrust_range")
	private Integer entrustRange;

	@ApiModelProperty(value = "委托类型（1 采销;2采购;3销售;4仓储;5物流;6财务）")
	@TableField(value = "entrust_type")
	private Integer entrustType;

	@ApiModelProperty(value = "委托来源（0级联;1手工;2合同;3调拨单;4业务单元产生）")
	@TableField(value = "entrust_source")
	private Integer entrustSource;

	@ApiModelProperty(value = "委托来源ID")
	@TableField(value = "entrust_source_id")
	private Long entrustSourceId;

	@ApiModelProperty(value = "默认（0 否;1 是）")
	@TableField(value = "default_flag")
	private Integer defaultFlag;

	@ApiModelProperty(value = "状态（1 未启用; 2 启用;3 停用）")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "停用时间")
	@TableField(value = "stop_time")
	private Long stopTime;

	@ApiModelProperty(value = "启用时间")
	@TableField(value = "start_time")
	private Long startTime;

	@ApiModelProperty(value = "集团ID")
	@TableField(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "货主")
	@TableField(value = "owner_id")
	private Long ownerId;

	@ApiModelProperty(value = "货主名称")
	@TableField(value = "owner_name")
	private String ownerName;

	@ApiModelProperty(value = "采销组织")
	@TableField(value = "purchase_sale_org_id")
	private Long purchaseSaleOrgId;

	@ApiModelProperty(value = "仓储服务商")
	@TableField(value = "warehouse_provider_id")
	private Long warehouseProviderId;

	@ApiModelProperty(value = "仓储服务商名称")
	@TableField(value = "warehouse_provider_name")
	private String warehouseProviderName;

	@ApiModelProperty(value = "库存组织")
	@TableField(value = "stock_org_id")
	private Long stockOrgId;

	@ApiModelProperty(value = "仓库")
	@TableField(value = "warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "仓库编号")
	@TableField(value = "warehouse_code")
	private String warehouseCode;

	@ApiModelProperty(value = "仓库名称")
	@TableField(value = "warehouse_name")
	private String warehouseName;

	@ApiModelProperty(value = "物流服务商")
	@TableField(value = "logistics_provider_id")
	private Long logisticsProviderId;

	@ApiModelProperty(value = "物流服务商组织id")
	@TableField(value = "logistics_org_id")
	private Long logisticsOrgId;

	@ApiModelProperty(value = "应收应付组织")
	@TableField(value = "pay_receive_org_id")
	private Long payReceiveOrgId;

	@ApiModelProperty(value = "结算组织")
	@TableField(value = "settment_org_id")
	private Long settmentOrgId;

	@ApiModelProperty(value = "核算组织")
	@TableField(value = "account_org_id")
	private Long accountOrgId;

	@ApiModelProperty(value = "业务组织")
	@TableField(value = "business_org_id")
	private Long businessOrgId;

	@ApiModelProperty(value = "合同号")
	@TableField(value = "ori_acc_code")
	private String oriAccCode;

	@ApiModelProperty(value = "关联物流服务商")
	@TableField(value = "relevance_logistics_provider_id")
	private Long relevanceLogisticsProviderId;

	@ApiModelProperty(value = "关联物流组织")
	@TableField(value = "relevance_logistics_org_id")
	private Long relevanceLogisticsOrgId;

	@ApiModelProperty(value = "是否普罗格签约")
	@TableField(value = "sign_flag")
	private Integer signFlag;

	@ApiModelProperty(value = "简介说明")
	@TableField(value = "remark")
	private String remark;

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
