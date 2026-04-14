package com.org.permission.common.org.dto.orgfunc;

import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 物流组织职能数据实体
 */
@ApiModel(description = "物流组织职能")
@Data
public class LogisticsOrgFuncInfoDto extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	@ApiModelProperty("组织ID")
	private Long orgId;
	/**
	 * 组织名
	 */
	@ApiModelProperty("组织名")
	private String orgName;
	/**
	 * 功能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 * 7 金融
	 * 8 劳务
	 * 9 平台
	 */
	@ApiModelProperty("功能类别")
	private Integer funcType = 6;
	/**
	 * 结算组织ID
	 */
	@ApiModelProperty("结算组织ID")
	private Long settleOrgId;
	/**
	 * 核算组织ID
	 */
	@ApiModelProperty("核算组织ID")
	private Long accountOrgId;
	/**
	 * 结算组织名
	 */
	@ApiModelProperty("结算组织名")
	private String settleOrgName;
	/**
	 * 核算组织名
	 */
	@ApiModelProperty("核算组织名")
	private String accountOrgName;
	/**
	 * 详细地址
	 */
	@ApiModelProperty("详细地址")
	private BaseAddressDto addressDetail;
	/**
	 * 经度
	 */
	@ApiModelProperty("经度")
	private double longitude;
	/**
	 * 纬度
	 */
	@ApiModelProperty("经度")
	private double latitude;

	@ApiModelProperty("物流职能网点类型：0业务网点，1职能网点")
	private Integer logisticsFunctionType=0;

	@ApiModelProperty("物流职能，网点代码")
	private String logisticsFunctionCode;
}

