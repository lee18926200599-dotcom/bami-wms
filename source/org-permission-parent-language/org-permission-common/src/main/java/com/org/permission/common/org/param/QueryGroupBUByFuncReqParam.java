package com.org.permission.common.org.param;

import com.org.permission.common.enums.BooleanEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 根据组织职能查询集团下业务单元列表请求参数
 */
@ApiModel(description = "根据组织职能查询集团下业务单元列表请求参数", value = "QueryGroupBUByFuncReqParam")
@Data
public class QueryGroupBUByFuncReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID(需要权限控制时必填)")
	private Long userId;

	@ApiModelProperty(value = "集团ID(必填)")
	private Long groupId;

	@ApiModelProperty(value = "职能类型(选填)")
	private Integer functionType;

	@ApiModelProperty(value = "职能类型(必填)")
	private List<Integer> funcTypes;

	@ApiModelProperty(value = "物流 网点代码")
	private String logisticsFunctionCode;

	@ApiModelProperty(value = "无职能类型(选填)")
	private Integer noFuncType;

	@ApiModelProperty(value = "状态（选填）(1未启用；2启用；3停用； null所有)")
	private Integer state;

	@ApiModelProperty(value = "权限控制（true:是 false:否）")
	private Boolean flag = Boolean.FALSE;

	@ApiModelProperty(value="组织类型（1平台；2集团；3业务单元；4部门）")
	private Integer orgType;

	@ApiModelProperty(value = "根业务单元（true:是 false:否）")
	private Integer mainOrgFlag= BooleanEnum.FALSE.getCode();

	@ApiModelProperty("前端过滤所需（业务单元. 采购or销售职能.核算组织=货主（全局客户）对应的业务单元）")
	private Boolean bizMaketFunctionAccountOrg;

	@ApiModelProperty("前端过滤所需（业务单元.仓储职能.核算组织.所属公司=仓储服务商对应的业务单元）")
	private Boolean bizStorageFunctionAccountOrg;

	@ApiModelProperty("前端过滤所需（业务单元.物流职能.核算组织.所属公司=仓储服务商对应的业务单元）")
	private Boolean bizLogisticsFunctionAccountOrg;

	@ApiModelProperty("前端从客商查的全局客商上的货主的业务单元")
	private Long ownerOrgId;

	@ApiModelProperty("前端从客商查的全局客商上的物流服务商的业务单元")
	private Long logisticsProviderOrgId;

	@ApiModelProperty("前端从客商查的全局客商上的仓储服务商的业务单元")
	private Long warehouseProviderOrgId;

	private List<Long> permissionOrgIds;

	public QueryGroupBUByFuncReqParam() {
	}

}
