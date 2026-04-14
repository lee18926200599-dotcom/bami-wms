package com.org.permission.server.org.vo;


import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团简要信息
 */
@ApiModel(description = "集团简要信息")
@Data
public class GroupInfoVo extends BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团编码")
	private String orgCode;

	@ApiModelProperty("集团名称")
	private String orgName;

	@ApiModelProperty("集团简称")
	private String orgShortName;

	@ApiModelProperty(value = "行业字典编码")
	private String industryCode;

	@ApiModelProperty(value = "所属行业")
	private String industryName;

	@ApiModelProperty("电话")
	private String phone;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("网址")
	private String netAddress;

	@ApiModelProperty("成立时间")
	private Long establishTime;

	@ApiModelProperty("信用代码")
	private String creditCode;

	@ApiModelProperty("本位币")
	private String currency;

	@ApiModelProperty("业务类型")
	private String businessType;

	@ApiModelProperty("状态(1 未启用；2 启用；3 停用; 4删除)")
	private Integer state;

	@ApiModelProperty("是否初始化(0 未完成；1 完成)")
	private Boolean init;

	@ApiModelProperty("简介")
	private String note;

	@ApiModelProperty("主营业务")
	private String mainBusiness;

	@ApiModelProperty("详细地址")
	private BaseAddressDto addressDetail;

	public GroupInfoVo() {
	}

}
