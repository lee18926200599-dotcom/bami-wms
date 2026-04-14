package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增物流委托关系数据
 */
@ApiModel(description = "新增物流委托关系数据")
@Data
public class LogisticEntrustRelationParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("默认（1是;0否）（必填）")
	private Integer defaultFlag;

	@ApiModelProperty("来源合同号（选填）")
	private String oriAccCode;

	@ApiModelProperty("物流服务商ID")
	private Long logisticsProviderId;

	@ApiModelProperty("物流组织ID")
	private Long logisticsOrgId;

	@ApiModelProperty("关联物流服务商ID")
	private Long relevanceLogisticsProviderId;

	@ApiModelProperty("关联物流组织ID")
	private Long relevanceLogisticsOrgId;

	@ApiModelProperty("备注")
	private String remark;
}
