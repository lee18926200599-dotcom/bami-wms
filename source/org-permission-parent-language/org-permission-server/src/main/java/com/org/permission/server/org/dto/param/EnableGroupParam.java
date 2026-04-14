package com.org.permission.server.org.dto.param;

import com.org.permission.common.org.param.EnableOperateParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 启停操作请求参数
 */
@ApiModel(description = "启停操作请求参数", value = "EnableOperateParam")
@Data
public class EnableGroupParam extends EnableOperateParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "纳税人类型字典码（初次启用集团时填写）")
	private String taxpayerCode;

	@ApiModelProperty(value = "纳税人类型字典名（初次启用集团时填写）")
	private String taxpayerName;

	@ApiModelProperty(value = "组织机构代码")
	private String orgInstitutionCode;

	@ApiModelProperty(value = "工商注册号")
	private String taxRegistrationNumber;

	public EnableGroupParam() {
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EnableOperateParam{");
		sb.append(super.toString());
		sb.append(", taxpayerCode='").append(taxpayerCode).append('\'');
		sb.append(", taxpayerName='").append(taxpayerName).append('\'');
		sb.append(", orgInstitutionCode='").append(orgInstitutionCode).append('\'');
		sb.append(", taxRegistrationNumber='").append(taxRegistrationNumber).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
