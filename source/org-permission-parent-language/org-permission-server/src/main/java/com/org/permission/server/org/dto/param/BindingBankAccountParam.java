package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 绑定银行账号请求参数
 */
@ApiModel(description = "绑定银行账号请求参数")
@Data
public class BindingBankAccountParam extends BaseParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("资金账号关系id")
	private Integer id;

	@ApiModelProperty(value = "集团ID", required = true)
	private Long groupId;

	@ApiModelProperty(value = "业务单元ID", required = true)
	private Long buId;

	@ApiModelProperty(value = "账户分类：1,银行；2,微信；3,支付宝；4,京东钱包；5,现金", required = true)
	private String accountCategory;

	@ApiModelProperty(value = "账户性质:包括：1：对公；2：对私", required = true)
	private String accountType;

	@ApiModelProperty(value = "账户号:账号，账户分类为银行账户时：对公为户号（一般20位）、对私为卡号（16、19位），其他情况下（如微信、支付宝）依据账户归属平台制定的规则，加密", required = true)
	private String accountSn;

	@ApiModelProperty(value = "户名:即开户名称，一般使用实名。个人用姓名、企业使用营业执照上的公司名称。  ——必填", required = true)
	private String accountName;

	@ApiModelProperty("开户行简码B:账户分类为银行账户时必填，参考《联动优势商户接入规范_快捷支付API直连_V4.0.8》等接口文档中的字典。")
	private String bankCode;

	@ApiModelProperty("开户行名B:开户行全名（到支行），与联行号一一对应。")
	private String bankName;

	@ApiModelProperty(value = "是否允许上级组织使用：1：是；0：否。是否允许拥有者的上级组织使用。默认为“否”。  ——必填", required = true)
	private String isUseHigherLevel;

	@ApiModelProperty(value = "是否允许下级组织使用：1：是；0：否。是否允许拥有者的下级组织使用。默认为“否”。  ——必填", required = true)
	private String isUseLowerLevel;

	@ApiModelProperty("是否默认账户：是否默认的账户。1：是；0：否。默认为否。每一个组织下的实体只有一个")
	private Integer defaultFlag;

	@ApiModelProperty("增值税票信息。1：是；0：否")
	private Integer addedValueTax;

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("状态")
	private Integer state;
}
