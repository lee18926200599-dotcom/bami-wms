package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系分页查询请求参数
 */
@ApiModel(description = "分页查询集团内仓储委托关系参数")
@Data
public class GroupStorageEntrustRelationReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty("前端条件：是否默认，1是，0否")
	private Integer defaultFlag;

	@ApiModelProperty("前端条件：状态")
	private Integer state;

	@ApiModelProperty("前端条件：模糊匹配业务单元id")
	private Integer conditionId;
}
