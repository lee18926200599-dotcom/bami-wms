package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 财务委托关系展示数据
 */
@ApiModel(description = "财务委托关系展示数据")
@Data
public class FinanceEntrustRelationInfoVo extends BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态(0创建;1启用;2停用;")
	private Integer state;

	@ApiModelProperty(value = "状态(0创建;1启用;2停用;")
	private String stateName;

	@ApiModelProperty("业务组织ID")
	private Integer bizOrgId;

	@ApiModelProperty("业务组织名")
	private String bizOrgName;

	@ApiModelProperty("核算组织ID")
	private Integer accountOrgId;

	@ApiModelProperty("核算组织名")
	private String accountOrgName;

	@ApiModelProperty("结算组织ID")
	private Integer settleOrgId;

	@ApiModelProperty("结算组织名")
	private String settleOrgName;

	@ApiModelProperty("备注")
	private String note;

	public String getStateName() {
		if (this.state != null) {
			StateEnum stateEnum = StateEnum.getEnum(this.state);
			if (stateEnum != null) {
				return stateEnum.getName();
			}
		}
		return this.stateName;
	}
}
