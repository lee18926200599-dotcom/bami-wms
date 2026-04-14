package com.org.permission.server.org.bean;


import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务单元信息数据实体
 */
@Data
public class BizUnitWithFuncInfoBean extends BaseTreeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化 0否；1是
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
	 * 状态
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
	 * 所属公司ID
	 */
	private Long companyId;
	/**
	 * 所属公司名
	 */
	private String companyName;
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
	 * 本位币
	 */
	private String currency;
	/**
	 * 全局客户ID
	 */
	private Long custId;

	/**
	 * 集团客户id
	 */
	private Long innerCustId;

	/**
	 * 全局客户名字
	 */
	private String custName;

	/**
	 * 集团客户名字
	 */
	private String innerCustName;
	/**
	 * 供应商名字
	 */
	private String supplierName;
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

}
