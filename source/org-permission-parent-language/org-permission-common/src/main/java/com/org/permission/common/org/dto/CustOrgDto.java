package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客商集团信息
 */
@Data
public class CustOrgDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 客商ID
	 */
	private Long custId;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 业务单元 ID
	 */
	private Long buId;
	/**
	 * 绑定类型（2集团；3业务单元）
	 */
	private Integer mainOrgFlag;

	public CustOrgDto() {
	}

	/**
	 * 判断绑定是否为集团
	 *
	 * @return <code>true</code>绑定集团；<code>false</code>绑定业务单元；
	 */
	public boolean bindingGroup() {
		return mainOrgFlag == 1;
	}

	public CustOrgDto(Long custId, Long groupId, Long buId, Integer mainOrgFlag) {
		this.custId = custId;
		this.groupId = groupId;
		this.buId = buId;
		this.mainOrgFlag = mainOrgFlag;
	}
}
