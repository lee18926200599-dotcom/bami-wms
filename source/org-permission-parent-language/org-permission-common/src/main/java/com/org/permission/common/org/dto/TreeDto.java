package com.org.permission.common.org.dto;

import lombok.Data;

import java.util.List;

/**
 * 基本树形结构
 */
@Data
public class TreeDto<T extends TreeDto> {

	private Long id;

	private List<T> children;

	public TreeDto() {
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TreeDto{");
		sb.append("id=").append(id);
		sb.append(", children=").append(children);
		sb.append('}');
		return sb.toString();
	}
}
