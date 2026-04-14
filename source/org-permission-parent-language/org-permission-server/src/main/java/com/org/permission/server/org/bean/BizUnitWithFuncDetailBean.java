package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.func.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务单元信息数据实体
 */
@Data
public class BizUnitWithFuncDetailBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化（true是；false否）
	 */
	private Integer initFlag;
	/**
	 * 组织类型
	 */
	private Integer orgType;
	/**
	 * 是否是根业务单元
	 */
	private Integer mainOrgFlag;
	/**
	 * 状态（1未启用；2启用；3停用; 4删除）
	 */
	private Integer state;
	/**
	 * 业务单元编码
	 */
	private String orgCode;
	/**
	 * 业务单元名
	 */
	private String orgName;
	/**
	 * 业务单元简称
	 */
	private String orgShortName;
	/**
	 * 上级业务单元
	 */
	private Long parentId;
	/**
	 * 上级业务单元
	 */
	private String parentName;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 集团名称
	 */
	private String groupName;
	/**
	 * 实体属性字典码
	 */
	private String entityCode;
	/**
	 * 实体属性字典名
	 */
	private String entityName;
	/**
	 * 信用代码
	 */
	private String creditCode;
	/**
	 * 所属公司ID
	 */
	private Long companyId;
	/**
	 * 所属公司名
	 */
	private String companyName;
	/**
	 * 所属行业字典码
	 */
	private String industryCode;
	/**
	 * 所属行业字典名
	 */
	private String industryName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 本位币
	 */
	private String currency;
	/**
	 * 客商ID
	 */
	private Long custId;
	/**
	 * 内部客商ID
	 */
	private Long innerCustId;
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;
	/**
	 * 法人组织职能
	 */
	private CorporateOrgFuncBean corporate;
	/**
	 * 财务组织职能
	 */
	private FinanceOrgFuncBean finance;
	/**
	 * 采购组织职能
	 */
	private PurchaseOrgFuncBean purchase;
	/**
	 * 销售组织职能
	 */
	private SaleOrgFuncBean sale;
	/**
	 * 仓储组织职能
	 */
	private StorageOrgFuncBean storage;
	/**
	 * 物流组织职能
	 */
	private LogisticsOrgFuncBean logistics;

	/**
	 * 金融组织职能
	 */
	private BankingOrgFuncBean banking;

	private List<Long> lineIdList;

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

	public boolean hasBankingFunc(){return banking !=null;}

}
