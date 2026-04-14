package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 组织银行账号实体数据
 */
@Data
public class OrgBankAccountBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -233120765586750431L;
	/**
	 * 集团ID
	 */
	private Long groupId;
	/**
	 * 业务单元ID
	 */
	private Long buId;


	/**
	 * 使用组织ID
	 */
	private Long useOrgId;
	/**
	 * 账号ID
	 */
	private Long accountId;
	/**
	 * 增值税票信息。1：是；0：否
	 */
	private Integer addedValueTax;

	private Long userId;

	/**
	 * 账户号
	 */
	private String accountSn;

	private Integer queryAccountSnLike;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 账户分类
	 */
	private String accountCategory;

	private Integer state;


	private Set<Long> accountIds;

	public OrgBankAccountBean() {
	}

	public OrgBankAccountBean setQueryAccountSnLike(Integer queryAccountSnLike) {
		this.queryAccountSnLike = queryAccountSnLike;
		return this;
	}
	public OrgBankAccountBean setAccountIds(Set<Long> accountIds) {
		this.accountIds = accountIds;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrgBankAccountBean bean = (OrgBankAccountBean) o;
		return Objects.equals(groupId, bean.groupId) &&
				Objects.equals(buId, bean.buId) &&
				Objects.equals(useOrgId, bean.useOrgId) &&
				Objects.equals(accountId, bean.accountId) &&
				Objects.equals(addedValueTax, bean.addedValueTax) &&
				Objects.equals(userId, bean.userId) &&
				Objects.equals(accountSn, bean.accountSn) &&
				Objects.equals(accountType, bean.accountType) &&
				Objects.equals(accountCategory, bean.accountCategory) &&
				Objects.equals(state, bean.state);
	}

	@Override
	public int hashCode() {

		return Objects.hash(groupId, buId, useOrgId, accountId, addedValueTax, userId, accountSn, accountType, accountCategory, state);
	}

	@Override
	public String toString() {
		return "OrgBankAccountBean{" +
				"groupId=" + groupId +
				", buId=" + buId +
				", accountId=" + accountId +
				", addedValueTax=" + addedValueTax +
				", userId=" + userId +
				", state=" + state +
				'}';
	}
}
