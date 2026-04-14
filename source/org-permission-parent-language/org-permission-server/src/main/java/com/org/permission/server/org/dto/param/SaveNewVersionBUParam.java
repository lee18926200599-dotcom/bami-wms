package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增业务单元版本请求参数
 */
@ApiModel(description = "新增业务单元版本请求参数", value = "SaveNewVersionBUParam")
@Data
public class SaveNewVersionBUParam extends AddBizUnitReqParam implements Serializable {
	private static final long serialVersionUID = 1;

	@ApiModelProperty(value = "根业务单元(1是；0否)")
	private Integer mainOrgFlag;

	@ApiModelProperty(value = "BU编码")
	private String orgCode;

	@ApiModelProperty(value = "状态（1未启用;2启用;3停用）")
	private Integer state;

	@ApiModelProperty(value = "初始化（true是；false否）")
	private Integer initFlag;

	@ApiModelProperty(value = "启用时间")
	private Date startTime;

	@ApiModelProperty(value = "版本状态(1 未启用；2 启用；3 停用)")
	private Integer versionFlag;

	@ApiModelProperty(value = "版本开始时间")
	private Date versionStartTime;

	@ApiModelProperty(value = "版本结束时间")
	private Date versionEndTime;
}
