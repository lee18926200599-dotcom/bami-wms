package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务委托关系列表请求参数
 */
@ApiModel(description = "业务委托关系列表请求参数",value = "QueryEntrustRelationshipListReqParam")
@Data
public class QueryEntrustRelationshipListReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "委托范围(1集团间；2 集团内")
	private Integer entrustRange;

	@ApiModelProperty(value = "委托关系(1采销；2采购；3销售；4仓储;5物流;6财务；")
	private Integer entrustType;

	@ApiModelProperty(value = "委托关系来源(1 手工;2 合同;3 调拨单")
	private Integer entrustSource;

	@ApiModelProperty(value = "状态(1 未启用；2 启用；3 停用；)")
	private Integer state;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;
}

