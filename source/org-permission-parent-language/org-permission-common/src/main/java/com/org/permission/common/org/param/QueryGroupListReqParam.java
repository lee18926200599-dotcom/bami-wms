package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询集团请求参数
 */
@ApiModel(description = "查询集团请求参数", value = "QueryGroupListReqParam")
@Data
public class QueryGroupListReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "集团编码")
	private String orgCode;

	@ApiModelProperty(value = "集团名称")
	private String orgName;

	@ApiModelProperty(value = "集团简称")
	private String orgShortName;

	@ApiModelProperty(value = "客户ID")
	private Long custId;

	@ApiModelProperty(value = "业务类型（参照数据字典），目前只支持单个过滤")
	private String bizType;

	@ApiModelProperty(value = "状态（1未启用；2启用；3停用;null 所有）")
	private Integer state;

	@ApiModelProperty("集团id集合")
	private List<Long> ids;

	private String creditCode;
}
