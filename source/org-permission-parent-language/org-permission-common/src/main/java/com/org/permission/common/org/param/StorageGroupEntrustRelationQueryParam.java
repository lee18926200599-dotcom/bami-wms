package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系查询参数
 */
@ApiModel
@Data
public class StorageGroupEntrustRelationQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认（1是，0否）
	 */
	@ApiModelProperty("默认")
	private Integer defaultFlag;
	/**
	 * 库存组织ID
	 */
	@ApiModelProperty("库存组织ID")
	private Long stockOrgId;
	/**
	 * 物流组织ID
	 */
	@ApiModelProperty("物流组织ID")
	private Long logisticsOrgId;
	/**
	 * 核算组织ID
	 */
	@ApiModelProperty("核算组织ID")
	private Long accountOrgId;
	/**
	 * 结算组织ID
	 */
	@ApiModelProperty("结算组织ID")
	private Long settmentOrgId;
	/**
	 * 关系主键Id
	 */
	@ApiModelProperty("关系主键Id")
	private Integer id;

	/**
	 * false取当前业务单元的客户id，true取根业务单元的客户id
	 */
	@ApiModelProperty("flag:false取当前业务单元的客户id，true取根业务单元的客户id")
	private Boolean flag =Boolean.FALSE;
}
