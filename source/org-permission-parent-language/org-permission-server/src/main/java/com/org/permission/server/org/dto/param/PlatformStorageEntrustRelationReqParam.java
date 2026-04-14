package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 集团间仓储委托关系分页查询请求参数
 */
@Data
public class PlatformStorageEntrustRelationReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private String conditionName;
	private List<Long> ids;
	@ApiModelProperty("前端条件：是否默认，1是，0否")
	private Integer defaultFlag;

	@ApiModelProperty("前端条件：状态")
	private Integer state;
}
