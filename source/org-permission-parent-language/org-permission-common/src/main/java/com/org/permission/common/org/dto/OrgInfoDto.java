package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 组织列表简要信息
 */
@Data
@ApiModel
public class OrgInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("组织ID")
	private Long id;

	@ApiModelProperty("集团ID")
	private Long groupId;

	@ApiModelProperty("上级组织ID")
	private Long parentId;

	@ApiModelProperty("归属业务单元id")
	private Long parentBuId;

	@ApiModelProperty("组织类别（2集团;3业务单元;4部门）")
	private Integer orgType;

	@ApiModelProperty("组织编码")
	private String orgCode;

	@ApiModelProperty("组织名称")
	private String orgName;

	@ApiModelProperty("父组织名称")
	private String parentOrgName;

	@ApiModelProperty("组织简称")
	private String orgShortName;

	@ApiModelProperty("状态（1为启用；2启用；3停用）")
	private Integer state;

	@ApiModelProperty("全局客户id")
	private Long custId;

	/**
	 * 职能类别
	 * 1 法人公司
	 * 2 财务
	 * 3 采购
	 * 4 销售
	 * 5 仓储
	 * 6 物流
	 */
	@ApiModelProperty("组织职能(只有业务单元级才有组织职能(0-6)")
	private List<Integer> orgFuncs;

	@ApiModelProperty("业务类型，该属性只有集团才有")
	private String businessType;

	@ApiModelProperty("创建时间")
	private Date createdDate;

}
