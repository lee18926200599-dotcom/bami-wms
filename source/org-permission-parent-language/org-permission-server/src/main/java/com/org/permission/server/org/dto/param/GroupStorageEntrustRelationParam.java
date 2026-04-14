package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团内仓储业务委托关系数据实体
 */
@ApiModel(description = "集团间仓储委托关系")
@Data
public class GroupStorageEntrustRelationParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("默认（true是;false否）（必填）")
	private Integer defaultFlag;

	@ApiModelProperty("库存组织")
	private Long stockOrgId;

	@ApiModelProperty("物流组织")
	private Long logisticsOrgId;

	@ApiModelProperty("核算组织")
	private Long accountOrgId;

	@ApiModelProperty("结算组织")
	private Long settleOrgId;

	@ApiModelProperty("备注")
	private String remark;
}

