package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步客商业务类型请求参数
 */
@Data
public class SyncCustBizTypeParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID(必填)
	 */
	private Long groupId;
	/**
	 * 业务单元ID
	 */
	private Long buId;
	/**
	 * 新业务类型（若为空，组删除所有业务类型）
	 */
	private String newBizType;
	/**
	 * 更新人ID（选填）
	 */
	private Long operaterId;
}
