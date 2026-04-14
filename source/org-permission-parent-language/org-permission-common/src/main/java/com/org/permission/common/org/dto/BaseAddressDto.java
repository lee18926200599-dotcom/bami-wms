package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 基础地址实体
 */
@ApiModel(description = "基础地址实体")
@Data
public class BaseAddressDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "国家地区ID")
	private String regionCode = "CHN";

	@ApiModelProperty(value = "国家地区名称")
	private String regionName = "中国";

	@ApiModelProperty(value = "省ID")
	private Long invoiceProvCode=0L;

	@ApiModelProperty(value = "省名称")
	private String invoiceProvName="";

	@ApiModelProperty(value = "城市ID")
	private Long invoiceCityCode=0L;

	@ApiModelProperty(value = "城市名称")
	private String invoiceCityName="";

	@ApiModelProperty(value = "区ID")
	private Long invoiceDistrictCode=0L;

	@ApiModelProperty(value = "区名称")
	private String invoiceDistrictName="";

	@ApiModelProperty(value = "街道ID")
	private Long invoiceStreetCode=0L;

	@ApiModelProperty(value = "街道名称")
	private String invoiceStreetName="";

	@ApiModelProperty(value = "地址")
	private String address="";

	public BaseAddressDto() {
	}

	/**
	 * 行政区划
	 * 省市区三级
	 *
	 * @return 详细地址
	 */
	public String getLinkedAddressDetail() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(getInvoiceProvName())) {
			sb.append(getInvoiceProvName());
		}
		if (!StringUtils.isEmpty(getInvoiceCityName())) {
			sb.append(getInvoiceCityName());
		}
		if (!StringUtils.isEmpty(getInvoiceDistrictName())) {
			sb.append(getInvoiceDistrictName());
		}
		if (!StringUtils.isEmpty(getInvoiceStreetName())) {
			sb.append(getInvoiceStreetName());
		}
		return sb.toString();
	}

}
