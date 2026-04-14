package com.org.permission.server.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织登陆后响应参数
 */
@Data
public class OrgLogolDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 集团LOGO图片URL
	 */
	private String logoUrl;

	public OrgLogolDto() {
	}

	public OrgLogolDto(Long custId, String logoUrl) {
		this.custId = custId;
		this.logoUrl = logoUrl;
	}
}
