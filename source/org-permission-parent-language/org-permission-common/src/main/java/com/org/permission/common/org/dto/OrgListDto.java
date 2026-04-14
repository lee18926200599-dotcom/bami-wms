package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团列表信息
 */
@Data
public class OrgListDto extends TreeDto<OrgListDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orgName;
}
