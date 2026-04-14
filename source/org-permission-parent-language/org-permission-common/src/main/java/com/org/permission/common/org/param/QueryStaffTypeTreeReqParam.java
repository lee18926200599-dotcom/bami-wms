package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询人员类别树请求参数
 */
@ApiModel(description = "查询人员类别树请求参数")
@Data
public class QueryStaffTypeTreeReqParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID(可空）（控制权限预留字段)")
	private Long userId;

	@ApiModelProperty("所属组织ID(必填)")
	private Long belongOrg;

	@ApiModelProperty("类别状态（选填）（0创建；1启用；2停用；null所有）")
	private Integer state;
}
