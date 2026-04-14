package com.org.permission.common.org.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 人员类别树
 */
@Data
public class StaffTypeTreeVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 类别 id
	 */
	private Long id;
	/**
	 * 类别级别
	 * 1 全局；
	 * 2 集团。
	 */
	private Integer typeLevel;
	/**
	 * 所属集团
	 */
	private Long belongOrg;
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 类别业务编码
	 */
	private String bizCode;
	/**
	 * 类别编码
	 */
	private String typeCode;
	/**
	 * 子类别
	 */
	private List<StaffTypeTreeVo> childTypes = new ArrayList<>(0);

	public StaffTypeTreeVo() {
	}
}
