package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售业务委托关系数据实体
 */
@ApiModel
@Data
public class SaleEntrustRelationParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("默认（1是，0否）")
	private Integer defaultFlag;

	@ApiModelProperty("销售组织")
	private Long saleOrgId;

	@ApiModelProperty("库存组织")
	private Long stockOrgId;

	@ApiModelProperty("应收组织")
	private Long receiveOrgId;

	@ApiModelProperty("结算组织")
	private Long settleOrgId;

	@ApiModelProperty("备注")
	private String remark;
}

