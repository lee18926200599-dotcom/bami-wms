package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 采销委托关系查询请求参数
 */
@ApiModel
@Data
public class MarketEntrustRelationReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("搜索条件（货主名字，仓储服务商，仓库名字）")
	private String conditionName;

	private List<Long> ids;
	@ApiModelProperty("前端条件：是否默认，1是，0否")
	private Integer defaultFlag;

	@ApiModelProperty("前端条件：状态")
	private Integer state;

	@ApiModelProperty("前端条件：是否签约 1=是")
	private Integer signFlag;
}
