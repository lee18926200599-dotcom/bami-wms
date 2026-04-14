package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询组织下所有人员信息
 */
@ApiModel
@Data
public class QueryAllStaffsInOrgReqParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 权限控制（查看当前用户权限范围内组织的人员）（选填）
	 * 若为 true ：级联查询用户权限范围内业务单元下部门的人员信息；
	 * 默认为 false：只返回当前组织初级部门的人员信息
	 */
	@ApiModelProperty(value = "人员查询是否级联")
	private boolean permissionControl = true;
	/**
	 * 用户ID（选填，需要级联查询权限范围组织人员时必填）
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 集团ID（选填，需要级联查询权限范围组织人员时必填）
	 */
	@ApiModelProperty(value = "集团id,级联查询权限范围组织人员时必填")
	private Long groupId;
	/**
	 * 组织ID（必填），
	 * 可为集团，业务单元，部门ID
	 * 若为集团：返回集团下根业务单元所挂部门的人员信息；
	 * 若为业务：返回该业务单元下部门的人员信息；
	 * 若为部门：返回该部门下人员信息
	 */
	@ApiModelProperty(value = "组织ID（必填），可为集团，业务单元，部门ID若为集团：返回集团下根业务单元所挂部门的人员信息；若为业务：返回该业务单元下部门的人员信息；若为部门：返回该部门下人员信息")
	private Long orgId;
	/**
	 * 级联下级
	 * 若为 true ：级联查询下级部门的人员信息；
	 * 默认为 false：只返回当前组织初级部门的人员信息
	 */
	@ApiModelProperty(value = "是否级联")
	private boolean cascaded = false;
	/**
	 * 人员状态
	 * 2 启用
	 * 3 停用
	 * （默认）null 查询所有
	 */
	@ApiModelProperty("人员状态：2启用状态，3停用状态")
	private Integer staffstate;
	/**
	 * 人员姓名（模糊）（选填）
	 */
	@ApiModelProperty(value = "人员姓名，选填")
	private String staffName;
	/**
	 * 人员编码（模糊）（选填）
	 */
	@ApiModelProperty(value = "人员编码，选填")
	private String staffCode;
	/**
	 * 手机号（模糊）（选填）
	 */
	@ApiModelProperty(value = "手机号，选填")
	private String phone;
	/**
	 * 人员类别（选填）
	 */
	@ApiModelProperty(value = "人员类别，选填")
	private Integer staffType;
	/**
	 * 关联用户（选填）
	 * 0 未关联；
	 * 1 已关联；
	 * （默认）null 查询所有
	 */
	@ApiModelProperty("关联的用户，选填")
	private Integer associateUser;

	@ApiModelProperty(value = "人员类别编码")
	private String staffTypeBizCode;
}
