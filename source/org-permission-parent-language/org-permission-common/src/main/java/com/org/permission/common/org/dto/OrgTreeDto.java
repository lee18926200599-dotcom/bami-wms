package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织树 信息
 */
@ApiModel
@Data
public class OrgTreeDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织 id
	 */
	@ApiModelProperty(value = "组织ID")
	private Long id;
	/**
	 * 组织类别
	 * <p>
	 * 2 集团
	 * 3 业务单元
	 * 4 部门
	 */
	private Integer orgType;
	/**
	 * 根业务单元
	 */
	private Integer mainOrgFlag;
	/**
	 * 组织编码
	 */
	private String orgCode;

	/**
	 * 组织名称
	 */
	@ApiModelProperty(value = "组织名称")
	private String orgName;
	/**
	 * 集团简称
	 */
	private String orgShortName;
	/**
	 * 同级业务单元（可多个）
	 */
	private List<OrgTreeDto> brotherOrgs = new ArrayList<>(2);
	/**
	 * 同级部门（可多个）
	 */
	private List<OrgTreeDto> brotherDeps = new ArrayList<>(2);
	/**
	 * 下级业务单元（可多个）
	 */
	private List<OrgTreeDto> childeBUs = new ArrayList<>(2);

	/**
	 * 下级部门（可多个）
	 */
	private List<OrgTreeDto> childeDeps = new ArrayList<>(2);

	/**
	 * 子组织
	 * {@link OrgTreeDto#childeBUs} 和{@link OrgTreeDto#childeDeps} 合集
	 */
	@ApiModelProperty(value = "下级组织合集")
	private List<OrgTreeDto> children;

	/**
	 * 上级业务单元（只有一个）
	 */
	private OrgTreeDto parentBU;

	/**
	 * 上级业务单元 或 部门 ID
	 */
	private Long parentId;

	/**
	 * 上级部门（只有一个）
	 */
	private OrgTreeDto parentDep;

	/**
	 * 状态（1未启用；2启用；3停用；4删除）
	 */
    private Integer state;

	public OrgTreeDto() {
	}


	public List<OrgTreeDto> getChildren() {
		List<OrgTreeDto> joinChildren = new ArrayList<>();
		if (!CollectionUtils.isEmpty(childeBUs)) {
			joinChildren.addAll(childeBUs);
		}

		if (!CollectionUtils.isEmpty(childeDeps)) {
			joinChildren.addAll(childeDeps);
		}

		return joinChildren;
	}
}
