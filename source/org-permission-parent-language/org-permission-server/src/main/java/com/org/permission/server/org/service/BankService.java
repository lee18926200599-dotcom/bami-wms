package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.BankAccountQueryParam;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.bean.OrgBankAccountInfoBean;



/**
 * 银行账号服务
 */
public interface BankService {
	/**
	 * 查询账号列表
	 *
	 * @param reqParam 请求参数
	 * @return 银行账号列表
	 */
	PageInfo<OrgBankAccountInfoBean> list(BankAccountQueryParam reqParam);

	/**
	 * 绑定银行账号
	 *
	 * @param reqParam 请求参数
	 */
	void binding(OrgBankAccountBean reqParam);

	/**
	 * 更新银行账号
	 *
	 * @param reqParam 请求参数
	 */
	Integer update(OrgBankAccountBean reqParam);

	/**
	 * 解绑银行账号
	 *
	 * @param reqParam 请求参数
	 */
	void unbinding(KeyOperateParam reqParam);

	/**
	 * 启停银行账号
	 *
	 * @param reqParam 请求参数
	 */
	void enable(EnableOperateParam reqParam);

	Integer queryAccountIdById(Integer id);
}
