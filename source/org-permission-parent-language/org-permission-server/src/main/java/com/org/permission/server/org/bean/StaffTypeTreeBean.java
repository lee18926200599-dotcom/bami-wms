package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
/**
 * 人员类别实体数据模型
 */
@Data
public class StaffTypeTreeBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 类别级别
	 * 1 全局；
	 * 2 集团。
	 */
	private Integer typeLevel;
	/**
	 * 所属组织
	 */
	private Long belongOrg;
	/**
	 * 类别编码
	 */
	private String typeCode;
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 类别业务编码
	 */
	private String bizCode;
	/**
	 * 上级类别
	 */
	private Long parentId;
	/**
	 * 类别状态（1未启用；2启用；3停用）
	 */
	private Integer state;
}
