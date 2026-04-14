package com.org.permission.common.org.dto.func;

import com.org.permission.common.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 财务组织职能数据实体
 */
@ApiModel("财务组织职能")
@Data
public class FinanceOrgFuncBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 组织ID
	 */
	@ApiModelProperty("组织ID")
	private Long orgId;

	/**
	 * 组织名称
	 */
	@ApiModelProperty("组织名称")
	private String orgName;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	@ApiModelProperty("功能类别")
	private Integer funcType = 2;
	/**
	 * 是否独立缴纳增值税（false否；true是）
	 */
	@ApiModelProperty("是否独立缴纳增值税（false否；true是）")
	private Boolean aloneFlag;
	/**
	 * 税务登记号
	 */
	@ApiModelProperty("税务登记号")
	private String taxRegistrationNumber;

	public FinanceOrgFuncBean() {
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("FinanceOrgFuncBean{");
		sb.append("orgId=").append(orgId);
		sb.append(", funcType=").append(funcType);
		sb.append(", aloneFlag=").append(aloneFlag);
		sb.append(", taxRegistrationNumber='").append(taxRegistrationNumber).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

