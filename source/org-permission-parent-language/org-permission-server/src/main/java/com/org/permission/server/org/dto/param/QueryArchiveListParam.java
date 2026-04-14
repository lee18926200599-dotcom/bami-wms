package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询档案列表请求参数
 */
@ApiModel
@Data
public class QueryArchiveListParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("档案名")
	private String archiveName;

	@ApiModelProperty("档案类别(0人员；1客户；2供应商)")
	private Integer archiveType;

	@ApiModelProperty("是否已经生成用户：true已经生成用户 false或者不传未生成用户")
	private Boolean hasUser;

	@ApiModelProperty("绑定了用户的人员id")
	private List<Long> boundUserStaffIds;

	@ApiModelProperty("包括禁用的数据")
	private Boolean withDisabled;

}
