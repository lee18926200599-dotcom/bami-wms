package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 集团信息数据实体
 */
@Data
public class GroupInfoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 组织类型
	 */
	private Integer orgType;
	/**
	 * 集团编码
	 */
	private String orgCode;
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
	private Date establishTime;
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
	private String businessType;
	/**
	 * 状态
	 * 1 未启用；2 启用；3 停用; 4删除
	 */
	private Integer state;
	/**
	 * 是否初始化
	 * <p>
	 * 0 未完成；1 完成
	 */
	private Integer initFlag;
	/**
	 * 简介
	 */
	private String remark;
	/**
	 * 主营业务
	 */
	private String mainBusiness;

	private String logoUrl;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;
}
