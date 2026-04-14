package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物流委托关系分页查询请求参数
 */
@ApiModel
@Data
public class LogisticsEntrustRelationReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("物流服务商名字")
	private String conditionName;

	@ApiModelProperty("前端条件：是否默认，1是，0否")
	private Integer defaultFlag;

	@ApiModelProperty("前端条件：状态")
	private Integer state;

	private List<Long> ids;
}
