package com.org.permission.common.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息数据基础实体，定义共有属性
 */
@Data
public class BaseIntegerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务自增主键
	 */
	private Long id;
	/**
	 * 状态
	 */
	private Integer state = 1;
	/**
	 * 创建人id
	 */
	private Long createdBy;
	/**
	 *创建人
	 */
	private String createdName;
	/**
	 *创建日期
	 */
	private Date createdDate;
	/**
	 *修改人id
	 */
	private Long modifiedBy;
	/**
	 *修改人
	 */
	private String modifiedName;
	/**
	 *修改时间
	 */
	private Date modifiedDate;

	public BaseIntegerBean() {
	}

	public BaseIntegerBean(Integer state, Date createTime) {
		this.state = state;
		this.createdDate=createTime;
	}

}
