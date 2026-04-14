package com.org.permission.common.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行账号展示数据
 */
@ApiModel(description = "银行账号展示数据")
@Data
public class BankAccountVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = " 账号ID")
	private Long accountId;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "集团名")
	private String groupName;

	@ApiModelProperty(value = "业务单元ID")
	private Long buId;

	@ApiModelProperty(value = "业务单元名")
	private String buName;

	@ApiModelProperty(value = "状态（1未启用；2启用；3停用；4删除）")
	private Integer state;

	@ApiModelProperty(value = "增值税票信息。1：是；0：否")
	private Boolean addedValueTax;

	@ApiModelProperty(value = "账户分类：1,银行；2,微信；3,支付宝；4,京东钱包;5,现金")
	private String accountCategory;

	@ApiModelProperty(value = "账户性质:包括：1：对公；2：对私")
	private String accountType;

	@ApiModelProperty(value = "账户号:账号，账户分类为银行账户时：对公为户号（一般20位）、对私为卡号（16、19位），其他情况下（如微信、支付宝）依据账户归属平台制定的规则，加密")
	private String accountSn;

	@ApiModelProperty(value = "户名:即开户名称，一般使用实名。个人用姓名、企业使用营业执照上的公司名称。  ——必填")
	private String accountName;

	@ApiModelProperty("开户行简码B:账户分类为银行账户时必填，参考《联动优势商户接入规范_快捷支付API直连_V4.0.8》等接口文档中的字典。")
	private String bankCode;

	@ApiModelProperty("开户行简码名 ：开户行简码对应的银行简码名")
	private String bankSimpleCodeName;

	@ApiModelProperty("开户行名B:开户行全名（到支行），与联行号一一对应。")
	private String bankName;

	@ApiModelProperty(value = "是否允许上级组织使用：1：是；0：否。是否允许拥有者的上级组织使用。默认为“否”。  ——必填")
	private String isUseHigherLevel;

	@ApiModelProperty(value = "是否允许下级组织使用：1：是；0：否。是否允许拥有者的下级组织使用。默认为“否”。  ——必填")
	private String isUseLowerLevel;

	@ApiModelProperty("是否默认账户：是否默认的账户，各业务线可以根据自己的情况灵活使用这个字段，每一个主体只能有一个默认账户，如果有冲突则覆盖之前的默认账户。1：是；0：否。默认为否。每一个组织下的实体只有一个")
	private Integer defaultFlag;

	@ApiModelProperty(value = "生效时间、格式：yyyyMMddHHmmss")
	private Long effectiveTime;

	@ApiModelProperty(value = "签名")
	private String sign;

	@ApiModelProperty("账户描述:文字说明。说明绑定的原因、账户的用途等。")
	private String remark;

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("真实姓名")
	private String realName;
}
