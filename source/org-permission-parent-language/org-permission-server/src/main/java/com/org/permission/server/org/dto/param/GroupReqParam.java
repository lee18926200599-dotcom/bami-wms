package com.org.permission.server.org.dto.param;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改集团请求参数
 */
@ApiModel(description = "修改集团请求参数", value = "GroupReqParam")
@Data
public class GroupReqParam extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团名称")
	private String orgName;

	@ApiModelProperty(value = "集团简称")
	private String orgShortName;

	@ApiModelProperty(value = "行业字典编码")
	private String industryCode;

	@ApiModelProperty(value = "所属行业")
	private String industryName;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "网址")
	private String netAddress;

	@ApiModelProperty(value = "成立时间")
	private Long establishTime;

	@ApiModelProperty(value = "信用代码")
	private String creditCode;

	@ApiModelProperty(value = "本位币")
	private String currency;

	@ApiModelProperty(value = "客户ID")
	private Long custId;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "主营业务")
	private String mainBusiness;

	@ApiModelProperty("详细地址")
	private BaseAddressDto addressDetail;
}
