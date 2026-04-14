package com.org.permission.server.org.bean;

import com.org.permission.common.bean.BaseIntegerBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台组织职能数据实体
 */
@Data
@ApiModel(description = "平台组织职能")
public class PlatformOrgFuncBean extends BaseIntegerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	@ApiModelProperty("组织ID")
	private Long orgId;
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
	private Integer funcType = 9;


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PlatformOrgFuncBean{");
		sb.append(super.toString());
		sb.append("orgId=").append(orgId);
		sb.append(", funcType=").append(funcType);
		sb.append('}');
		return sb.toString();
	}
}

