package com.org.permission.server.org.vo;

import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.TreeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 集团业务类型决定的组织职能
 */
@ApiModel
public class OrgBizTypeVo extends TreeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "业务类型")
	private String bizType;

	@ApiModelProperty(value = "前端忽视")
	private List<Integer> simpleFunctions;

	@ApiModelProperty(value = "法人公司(true 有；false 无)")
	private Boolean corporate;

	@ApiModelProperty(value = "财务(true 有；false 无)")
	private Boolean finance;

	@ApiModelProperty(value = "采购(true 有；false 无)")
	private Boolean purchase;

	@ApiModelProperty(value = "销售(true 有；false 无)")
	private Boolean sale;

	@ApiModelProperty(value = "仓储(true 有；false 无)")
	private Boolean storage;

	@ApiModelProperty(value = "物流(true 有；false 无)")
	private Boolean logistics;

	@ApiModelProperty(value = "平台(true 有；false 无)")
	private Boolean platform;

	@ApiModelProperty(value = "劳务(true 有；false 无)")
	private Boolean labour;

	@ApiModelProperty(value = "金融(true 有；false 无)")
	private Boolean banking;

	public OrgBizTypeVo() {
	}

	/**
	 * 法人公司组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getCorporate() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.CORPORATION.getIndex());
	}

	/**
	 * 财务组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getFinance() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.FINANCE.getIndex());
	}

	/**
	 * 采购组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getPurchase() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.PURCHASE.getIndex());
	}

	/**
	 * 销售组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getSale() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.SALE.getIndex());
	}

	/**
	 * 仓储组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getStorage() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.STORAGE.getIndex());
	}

	/**
	 * 物流组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getLogistics() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.LOGISTICS.getIndex());
	}

	/**
	 * 平台组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getPlatform() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.PLATFORM.getIndex());
	}

	/**
	 * 劳务组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getLabour() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.LABOUR_SERVICE.getIndex());
	}

	/**
	 * 金融组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getBanking() {
		return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.BANKING.getIndex());
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public List<Integer> getSimpleFunctions() {
		return simpleFunctions;
	}

	public void setSimpleFunctions(List<Integer> simpleFunctions) {
		this.simpleFunctions = simpleFunctions;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OrgBizTypeVo{");
		sb.append("bizType='").append(bizType).append('\'');
		sb.append(", simpleFunctions=").append(simpleFunctions);
		sb.append(", corporate=").append(corporate);
		sb.append(", finance=").append(finance);
		sb.append(", purchase=").append(purchase);
		sb.append(", sale=").append(sale);
		sb.append(", storage=").append(storage);
		sb.append(", logistics=").append(logistics);
		sb.append(", platform=").append(platform);
		sb.append(", labour=").append(labour);
		sb.append(", banking=").append(banking);
		sb.append('}');
		return sb.toString();
	}
}
