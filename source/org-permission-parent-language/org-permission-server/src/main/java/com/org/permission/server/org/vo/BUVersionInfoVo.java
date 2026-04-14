package com.org.permission.server.org.vo;


import com.org.permission.common.enums.org.FunctionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 业务单元版本简要信息
 */
@ApiModel
public class BUVersionInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "版本ID")
	private Integer versionId;

	@ApiModelProperty(value = "业务单元ID")
	private Integer buId;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "版本状态(1 未启用；2 启用；3 停用)")
	private Integer versionFlag;

	@ApiModelProperty(value = "版本开始时间")
	private Long versionStartTime;

	@ApiModelProperty(value = "版本结束时间")
	private Long versionEndTime;

	@ApiModelProperty(value = "法人公司(true 有；false 无)")
	private Boolean corporation;

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

	private List<Integer> simpleFuncs;

	public BUVersionInfoVo() {
	}

	/**
	 * 法人公司组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getCorporation() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.CORPORATION.getIndex());
	}

	/**
	 * 财务组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getFinance() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.FINANCE.getIndex());
	}

	/**
	 * 采购组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getPurchase() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.PURCHASE.getIndex());
	}

	/**
	 * 销售组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getSale() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.SALE.getIndex());
	}

	/**
	 * 仓储组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getStorage() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.STORAGE.getIndex());
	}

	/**
	 * 物流组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public Boolean getLogistics() {
		return !CollectionUtils.isEmpty(simpleFuncs) && simpleFuncs.contains(FunctionTypeEnum.LOGISTICS.getIndex());
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getBuId() {
		return buId;
	}

	public void setBuId(Integer buId) {
		this.buId = buId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getVersionFlag() {
		return versionFlag;
	}

	public void setVersionFlag(Integer versionFlag) {
		this.versionFlag = versionFlag;
	}

	public Long getVersionStartTime() {
		return versionStartTime;
	}

	public void setVersionStartTime(Long versionStartTime) {
		this.versionStartTime = versionStartTime;
	}

	public Long getVersionEndTime() {
		return versionEndTime;
	}

	public void setVersionEndTime(Long versionEndTime) {
		this.versionEndTime = versionEndTime;
	}

	public List<Integer> getSimpleFuncs() {
		return simpleFuncs;
	}

	public void setSimpleFuncs(List<Integer> simpleFuncs) {
		this.simpleFuncs = simpleFuncs;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BUVersionInfoVo{");
		sb.append("versionId=").append(versionId);
		sb.append(", buId=").append(buId);
		sb.append(", version='").append(version).append('\'');
		sb.append(", versionFlag=").append(versionFlag);
		sb.append(", versionStartTime=").append(versionStartTime);
		sb.append(", versionEndTime=").append(versionEndTime);
		sb.append(", corporation=").append(corporation);
		sb.append(", finance=").append(finance);
		sb.append(", purchase=").append(purchase);
		sb.append(", sale=").append(sale);
		sb.append(", storage=").append(storage);
		sb.append(", logistics=").append(logistics);
		sb.append(", simpleFuncs=").append(simpleFuncs);
		sb.append('}');
		return sb.toString();
	}
}
