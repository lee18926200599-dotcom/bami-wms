package com.org.permission.server.org.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;



@Data
public class BaseEntrustRelationshipVO extends BaseVO {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "委托范围（1 集团间; 2 集团内）")
	private Integer entrustRange;

	@ApiModelProperty(value = "委托类型（1 采销;2采购;3销售;4仓储;5物流;6财务）")
	private Integer entrustType;

	@ApiModelProperty(value = "委托来源（0级联;1手工;2合同;3调拨单;4业务单元产生）")
	private Integer entrustSource;

	@ApiModelProperty(value = "委托来源ID")
	private Long entrustSourceId;

	@ApiModelProperty(value = "默认（0 否;1 是）")
	private Integer defaultFlag;

	@ApiModelProperty(value = "状态（1 未启用; 2 启用;3 停用）")
	private Integer state;

	@ApiModelProperty(value = "停用时间")
	private Long stopTime;

	@ApiModelProperty(value = "启用时间")
	private Long startTime;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "货主")
	private Long ownerId;

	@ApiModelProperty(value = "货主")
	private String ownerName;

	@ApiModelProperty(value = "采销组织")
	private Long purchaseSaleOrgId;

	@ApiModelProperty(value = "采销组织名称")
	private Long purchaseSaleOrgName;

	@ApiModelProperty(value = "仓储服务商")
	private Long warehouseProviderId;


	@ApiModelProperty(value = "仓储服务商名称")
	private String warehouseProviderName;

	@ApiModelProperty(value = "库存组织")
	private Long stockOrgId;

	@ApiModelProperty(value = "库存组织名称")
	private String stockOrgName;

	@ApiModelProperty(value = "仓库")
	private Long warehouseId;

	@ApiModelProperty(value = "仓库编号")
	private String warehouseCode;

	@ApiModelProperty(value = "仓库名称")
	private String warehouseName;

	@ApiModelProperty(value = "物流服务商")
	private Long logisticsProviderId;

	@ApiModelProperty(value = "物流服务商")
	private Long logisticsOrgId;

	@ApiModelProperty(value = "应收应付组织")
	private Long payReceiveOrgId;

	@ApiModelProperty(value = "结算组织")
	private Long settmentOrgId;

	@ApiModelProperty(value = "核算组织")
	private Long accountOrgId;

	@ApiModelProperty(value = "业务组织")
	private Long businessOrgId;

	@ApiModelProperty(value = "合同号")
	private String oriAccCode;

	@ApiModelProperty(value = "关联物流服务商")
	private Long relevanceLogisticsProviderId;

	@ApiModelProperty(value = "关联物流组织")
	private Long relevanceLogisticsOrgId;

	@ApiModelProperty(value = "是否普罗格签约")
	private Integer signFlag;

	@ApiModelProperty(value = "简介说明")
	private String remark;

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

	@ApiModelProperty(value = "搜索")
	private String search;

}
