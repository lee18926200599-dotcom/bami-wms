package com.org.permission.server.org.dto.param;

import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.func.*;
import com.org.permission.server.org.bean.LabourOrgFuncBean;
import com.org.permission.server.org.bean.PlatformOrgFuncBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新业务单元请求参数
 */
@ApiModel(description = "更新业务单元请求参数")
@Data
public class UpdateBizUnitReqParam extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "业务单元ID")
	private Long id;

	@ApiModelProperty(value = "业务单元名称")
	private String orgName;

	@ApiModelProperty(value = "业务单元简称")
	private String orgShortName;

	@ApiModelProperty(value = "上级组织ID")
	private Long parentId;

	@ApiModelProperty(value = "实体属性字典码")
	private String entityCode;

	@ApiModelProperty(value = "实体属性字典名")
	private String entityName;

	@ApiModelProperty(value = "信用代码")
	private String creditCode;

	@ApiModelProperty(value = "所属公司")
	private Long companyId;

	@ApiModelProperty(value = "所属行业字典码")
	private String industryCode;

	@ApiModelProperty(value = "所属行业字典名")
	private String industryName;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "简介")
	private String remark;

	@ApiModelProperty(value = "本位币")
	private String currency;

	@ApiModelProperty("详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty("法人组织职能")
	private CorporateOrgFuncBean corporate;

	@ApiModelProperty("财务组织职能")
	private FinanceOrgFuncBean finance;

	@ApiModelProperty("采购组织职能")
	private PurchaseOrgFuncBean purchase;

	@ApiModelProperty("销售组织职能")
	private SaleOrgFuncBean sale;

	@ApiModelProperty("仓储组织职能")
	private StorageOrgFuncBean storage;

	@ApiModelProperty("物流组织职能")
	private LogisticsOrgFuncBean logistics;

	@ApiModelProperty("平台组织职能")
	private PlatformOrgFuncBean platform;

	@ApiModelProperty("劳务组织职能")
	private LabourOrgFuncBean labour;

	@ApiModelProperty("金融组织职能")
	private BankingOrgFuncBean banking;
	@ApiModelProperty("业务线ID集合")
	private List<Long> lineIdList;

	/**
	 * 是否具有法人公司组织职能
	 *
	 * @return <code>true</code>有；<code>false</code>无；
	 */
	public boolean hasCorporationFunc() {
		return corporate != null;
	}

	public boolean hasFinanceFunc() {
		return finance != null;
	}

	public boolean hasPurchaseFunc() {
		return purchase != null;
	}

	public boolean hasSaleFunc() {
		return sale != null;
	}

	public boolean hasLogisticFunc() {
		return logistics != null;
	}

	public boolean hasStorageFunc() {
		return storage != null;
	}
	public boolean hasPlatformFunc() {
		return platform != null;
	}
	public boolean hasLabourFunc() {
		return labour != null;
	}
	public boolean hasBankingFunc() {
		return banking != null;
	}


}
