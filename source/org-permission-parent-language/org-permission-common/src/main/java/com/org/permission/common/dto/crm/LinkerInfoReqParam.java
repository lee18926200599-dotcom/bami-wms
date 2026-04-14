package com.org.permission.common.dto.crm;

import java.io.Serializable;

/**
 * 联系信息
 */
public class LinkerInfoReqParam implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人
	 */
	private String linker;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 电子邮件
	 */
	private String email;

	public LinkerInfoReqParam() {
	}

	public String getLinker() {
		return linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LinkerInfoReqParam{");
		sb.append("linker='").append(linker).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
