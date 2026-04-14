package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新集团数据实体
 */
@Data
public class UpdateGroupWriteBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 集团名称
	 */
	private String orgName;
	/**
	 * 集团简称
	 */
	private String orgShortName;
	/**
	 * 所属行业字典码
	 */
	private String industryCode;
	/**
	 * 所属行业
	 */
	private String industryName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 网址
	 */
	private String netAddress;
	/**
	 * 成立时间
	 */
	private Long establishTime;
	/**
	 * 信用代码
	 */
	private String creditCode;
	/**
	 * 本位币
	 */
	private String currency;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 业务类型
	 */
	private String bizTypeId;
	/**
	 * 简介
	 */
	private String remark;
	/**
	 * 主营业务
	 */
	private String mainBusiness;
	/**
	 * 地址信息
	 */
	private BaseAddressDto addressDetail;
}
