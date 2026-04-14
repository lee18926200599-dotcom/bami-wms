package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GeneratorStaffDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 成功的人员生成用户
	 */
	private List<RegistStaffDto> succceedStaffList;
	/**
	 * 失败的人员生成用户
	 */
	private List<RegistStaffDto> failStaffList;
	/**
	 * 已经生成过用户的人员
	 */
	private List<RegistStaffDto> alreadGeneratorList;

}
