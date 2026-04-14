package com.org.permission.server.org.bean;


import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.OrgFunctionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 业务单元及其组织职能
 */
@Data
public class SimpleBizUnitWithFuncBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织ID
	 */
	private Long id;
	/**
	 * 是否为根业务单元
	 */
	private Integer mainOrgFlag;
	/**
	 * 状态
	 * 1未启用；2启用；3停用；4删除
	 */
	private Integer state;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 集团名
	 */
	private String groupName;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 组织类别
	 * 2 集团
	 * 3 业务单元
	 * 4 部门
	 */
	private Integer orgType;
	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织简称
	 */
	private String orgShortName;
	/**
	 * 上级业务单元 ID
	 */
	private Long parentId;
	/**
	 * 上级业务单元名
	 */
	private String parentName;
	/**
	 * 行业字典编码
	 */
	private String industryCode;
	/**
	 * 所属行业
	 */
	private String industryName;
	/**
	 * 业务类型
	 */
	private String businessType;
	/**
	 * 所属公司id
	 */
	private Long companyId;
	/**
	 * 所属公司名称
	 */
	private String companyName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 部门负责人
	 */
	private Integer depDutyStaff;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;
	/**
	 * 组织职能
	 */
	private List<OrgFunctionDto> orgFuncs;

	/**
	 * 部门归属业务单元id
	 */
	private Long parentBuId;
}
