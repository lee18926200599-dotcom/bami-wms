package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织职能业务单元列表查询请求参数
 */
@ApiModel(description = "组织职能业务单元列表查询请求参数", value = "QueryBizUnitListByFuncParam")
@Data
public class QueryBizUnitListByFuncParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "业务单元名称（选填）")
	private String orgName;

	@ApiModelProperty(value = "职能类型(必填)（1法人公司；2财务；3采购；4销售；5仓储；6物流;7金融;8劳务;9平台)")
	private Integer funcType;

	@ApiModelProperty(value = "状态(选填)（1未启用；2启用；3停用；null查所有）（默认为2）")
	private Integer state = 2;
}
