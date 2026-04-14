package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.server.org.dto.param.OrgFunctionReqParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新业务单元请求参数
 */
@Data
public class UpdateBizUnitWriteBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务单元名称
	 */
	private String orgName;
	/**
	 * 业务单元简称
	 */
	private String orgShortName;
	/**
	 * 上级组织ID
	 */
	private Long parentId;
	/**
	 * 实体属性码
	 */
	private String entityCode;
	/**
	 * 实体属性名
	 */
	private String entityName;
	/**
	 * 信用代码
	 */
	private String creditCode;
	/**
	 * 所属公司
	 */
	private Long companyId;
	/**
	 * 所属行业码
	 */
	private String industryCode;
	/**
	 * 所属行业名
	 */
	private String industryName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 简介
	 */
	private String remark;

	/**
	 * 本位币
	 */
	private String currency;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;

	private List<OrgFunctionReqParam> organizationFunctions;
}
