package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务委托关系详细信息
 */
@Data
public class EntrustRelationshipDetailVo extends BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("货主ID")
	private Long ownerId;

	@ApiModelProperty("组织ID")
	private Long orgId;

	@ApiModelProperty("委托关系类型(1 集团间；2 集团内")
	private Integer entrustType;

	@ApiModelProperty("委托关系(1 采销;2 采购；3 销售；4 仓储；5 物流；6 财务；)")
	private Integer entrustBusinessType;

	@ApiModelProperty("是否默认(true是；false否")
	private Boolean defaultFlag;

	@ApiModelProperty("状态(1 未启用;2 启用;3 停用)")
	private Integer state;

	@ApiModelProperty("来源合同号")
	private String oriAccCode;

	@ApiModelProperty("仓储服务商")
	private Long warehouseProviderId;

	@ApiModelProperty("仓库")
	private Long warehouseId;

	@ApiModelProperty("库存组织")
	private Long stockOrgId;

	@ApiModelProperty("应收应付组织")
	private Long payReceiveOrgId;

	@ApiModelProperty("核算组织")
	private Long accountOrgId;

	@ApiModelProperty("物流服务商")
	private Long logisticsProviderId;

	@ApiModelProperty("物流组织")
	private Long logisticsOrgId;

	@ApiModelProperty("采购/销售组织")
	private Long purchaseSaleOrgId;

	@ApiModelProperty("结算组织")
	private Long settmentOrgId;

	@ApiModelProperty("业务组织")
	private Long businessOrgId;

	@ApiModelProperty("采销类型")
	private Integer purchaseSaleType;

	@ApiModelProperty("关联物流服务商")
	private Long relevanceLogisticsProviderId;

	@ApiModelProperty("关联物流组织")
	private Long relevanceLogisticsOrgId;
}

