package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 根据组织功能查询客户下业务单元列表请求参数
 */
@ApiModel(description = "根据组织功能查询客商下业务单元列表请求参数", value = "QueryCustBUByFuncReqParam")
@Data
public class QueryCustBUByFuncReqParam implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID(可空)")
	private Long userId;

	@ApiModelProperty(value = "客商ID(必填)")
	private Long custId;

	@ApiModelProperty(value = "职能类型(必填)(1法人公司；2财务；3采购；4销售；5仓储；6物流；7金融；8劳务；9平台)")
	private Integer functionType;

	@ApiModelProperty(value = "职能类型(必填)(1法人公司；2财务；3采购；4销售；5仓储；6物流；7金融；8劳务；9平台)")
	private List<Integer> funcTypes;

	@ApiModelProperty("业务单元所属公司")
	private Long companyId;

	@ApiModelProperty(value = "状态(1未启用；2启用；3停用； null所有)")
	private Integer state;

	@ApiModelProperty("集团id")
	private Long groupId;

	@ApiModelProperty("前端过滤所需（业务单元. 采购or销售职能.核算组织=货主（全局客户）对应的业务单元）")
	private Boolean bizMaketFunctionAccountOrg;

	@ApiModelProperty("前端过滤所需（业务单元.仓储职能.核算组织.所属公司=仓储服务商对应的业务单元）")
	private Boolean bizStorageFunctionAccountOrg;

	@ApiModelProperty("前端过滤所需（业务单元.物流职能.核算组织.所属公司=物流服务商对应的业务单元）")
	private Boolean bizLogisticsFunctionAccountOrg;

	@ApiModelProperty("前端从客商查的全局客商上的货主的业务单元")
	private Long ownerOrgId;

	@ApiModelProperty("前端从客商查的全局客商上的物流服务商的业务单元")
	private Long logisticsProviderOrgId;

	@ApiModelProperty("前端从客商查的全局客商上的仓储服务商的业务单元")
	private Long warehouseProviderOrgId;
}
