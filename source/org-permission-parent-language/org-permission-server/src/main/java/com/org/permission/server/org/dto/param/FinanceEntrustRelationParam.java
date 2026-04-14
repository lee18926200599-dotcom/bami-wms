package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 财务委托关系
 */
@ApiModel
@Data
public class FinanceEntrustRelationParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("业务组织ID")
	private Long bizOrgId;

	@ApiModelProperty("核算组织ID")
	private Long accountOrgId;

	@ApiModelProperty("结算组织ID")
	private Long settleOrgId;

	@ApiModelProperty("备注")
	private String remark;
}
