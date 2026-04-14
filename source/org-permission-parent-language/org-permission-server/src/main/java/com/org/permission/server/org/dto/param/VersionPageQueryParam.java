package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 版本分页查询请求参数
 */
@ApiModel(description = "版本分页查询请求参数", value = "VersionPageQueryParam")
@Data
public class VersionPageQueryParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "业务单元ID")
	private Long buId;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "版本状态(1 未启用；2 启用；3 停用)")
	private Integer versionFlag;

	@ApiModelProperty(value = "版本开始时间")
	private Date versionStartTime;

	@ApiModelProperty(value = "版本结束时间")
	private Date versionEndTime;
}
