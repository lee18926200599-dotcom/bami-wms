package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
/**
 * 组织银行账号实体数据
 */
@Data
public class OrgBankAccountInfoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -233120765586750431L;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 集团名
	 */
	private String groupName;
	/**
	 * 业务单元ID
	 */
	private Long buId;
	/**
	 * 业务单元名
	 */
	private String buName;
	/**
	 * 账号ID
	 */
	private Long accountId;
	/**
	 * 增值税票信息。1：是；0：否
	 */
	private Integer addedValueTax;

	/**
	 * 状态（1未启用；2启用；3停用；4删除）
	 */
	private Integer state;

	/**
	 * 用户id(账户管理者)
	 */
	private Long userId;
}
