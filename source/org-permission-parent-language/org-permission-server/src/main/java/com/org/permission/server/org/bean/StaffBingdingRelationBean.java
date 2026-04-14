package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
/**
 * 人员绑定关系数据实体
 */
@Data
public class StaffBingdingRelationBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关联类型业务编码
	 */
	private String bizCode;
	/**
	 * 关联ID
	 */
	private Long relId;
	/**
	 * 人员ID
	 */
	private Long staffId;
}
