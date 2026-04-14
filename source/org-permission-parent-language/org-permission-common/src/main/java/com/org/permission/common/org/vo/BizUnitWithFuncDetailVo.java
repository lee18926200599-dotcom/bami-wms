package com.org.permission.common.org.vo;

import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.func.BankingOrgFuncBean;
import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务单元信息,前端展示
 */
@ApiModel
@Data
public class BizUnitWithFuncDetailVo extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态（1未启用；2启用；3停用; 4删除）")
	private Integer state;

	@ApiModelProperty(value = "初始化（1是；0否）")
	private Integer initFlag;

	@ApiModelProperty(value = "启用时间")
	private Date startTime;

	@ApiModelProperty(value = "业务单元编码")
	private String orgCode;

	@ApiModelProperty(value = "业务单元名")
	private String orgName;

	@ApiModelProperty(value = "业务单元简称")
	private String orgShortName;

	@ApiModelProperty(value = "上级业务单元Id")
	private Long parentId;

	@ApiModelProperty(value = "上级业务单元")
	private String parentName;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "客商ID")
	private Long custId;

	@ApiModelProperty(value = "内部客商ID")
	private Long innerCustId;

	@ApiModelProperty(value = "集团名称")
	private String groupName;

	@ApiModelProperty("根业务单元(1是；0否")
	private Integer mainOrgFlag;

	@ApiModelProperty(value = "实体属性码")
	private String entityCode;

	@ApiModelProperty(value = "实体属性名")
	private String entityName;

	@ApiModelProperty(value = "信用代码")
	private String creditCode;

	@ApiModelProperty(value = "所属公司ID")
	private Long companyId;

	@ApiModelProperty(value = "所属公司名")
	private String companyName;

	@ApiModelProperty(value = "所属行业")
	private String industryCode;

	@ApiModelProperty(value = "所属行业")
	private String industryName;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = " 本位币")
	private String currency;

	@ApiModelProperty(value = "说明")
	private String remark;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "详细地址")
	private BaseAddressDto addressDetail;

	@ApiModelProperty(value = "法人公司(true 有；false 无)")
	private Boolean hasCorporationFunc;
	@ApiModelProperty(value = "法人组织职能")
	private com.org.permission.common.org.dto.func.CorporateOrgFuncBean corporate;

	@ApiModelProperty(value = "财务(true 有；false 无)")
	private Boolean hasFinanceFunc;
	@ApiModelProperty(value = "财务组织职能")
	private com.org.permission.common.org.dto.func.FinanceOrgFuncBean finance;

	@ApiModelProperty(value = "采购(true 有；false 无)")
	private Boolean hasPurchaseFunc;
	@ApiModelProperty(value = "采购组织职能")
	private com.org.permission.common.org.dto.func.PurchaseOrgFuncBean purchase;

	@ApiModelProperty(value = "销售(true 有；false 无)")
	private Boolean hasSaleFunc;
	@ApiModelProperty(value = "销售组织职能")
	private com.org.permission.common.org.dto.func.SaleOrgFuncBean sale;

	@ApiModelProperty(value = "仓储(true 有；false 无)")
	private Boolean hasStorageFunc;
	@ApiModelProperty(value = "仓储组织职能")
	private com.org.permission.common.org.dto.func.StorageOrgFuncBean storage;

	@ApiModelProperty(value = "物流(true 有；false 无)")
	private Boolean hasLogisticsFunc;
	@ApiModelProperty(value = "物流组织职能")
	private com.org.permission.common.org.dto.func.LogisticsOrgFuncBean logistics;

	@ApiModelProperty("金融（true 有；false 无）")
	private Boolean hasBankFunc;

	@ApiModelProperty("金融组织职能")
	private BankingOrgFuncBean banking;

	@ApiModelProperty("业务线ID集合")
	private List<Long> lineIdList;

	public BizUnitWithFuncDetailVo() {
	}

	/**
	 * 是否具有法人组织职能
	 *
	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasCorporationFunc() {
		return corporate != null;
	}

	/**
	 * 是否具有财务组织职能
	 *
	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasFinanceFunc() {
		return finance != null;
	}

	/**
	 * 是否具有采购组织职能
	 *
	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasPurchaseFunc() {
		return purchase != null;
	}

	/**
	 * 是否具有销售组织职能
	 *
	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasSaleFunc() {
		return sale != null;
	}

	/**
	 * 是否具有仓储组织职能
	 *

	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasStorageFunc() {
		return storage != null;
	}

	/**
	 * 是否具有物流组织职能
	 *
	 * @return <code>true</code>有;<code>false</code>无;
	 */
	public Boolean getHasLogisticsFunc() {
		return logistics != null;
	}

	/**
	 * 是否有金融职能
	 * @return
	 */
	public Boolean getHasBankFunc(){return banking !=null;}
}
