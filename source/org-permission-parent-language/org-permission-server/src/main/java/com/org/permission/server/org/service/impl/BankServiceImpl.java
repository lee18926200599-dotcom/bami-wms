package com.org.permission.server.org.service.impl;


import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.BankAccountQueryParam;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.bean.OrgBankAccountInfoBean;
import com.org.permission.server.org.mapper.OrgBankMapper;
import com.org.permission.server.org.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行服务实现
 */
@Service("bankService")
public class BankServiceImpl implements BankService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankServiceImpl.class);

	@Resource
	private OrgBankMapper orgBankMapper;

	@Override
	public PageInfo<OrgBankAccountInfoBean> list(BankAccountQueryParam reqParam) {
		PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
		final List<OrgBankAccountInfoBean> accounts = orgBankMapper.queryOrgBankAccout(reqParam);
		PageInfo<OrgBankAccountInfoBean> pageInfo = new PageInfo<>(accounts);
		return pageInfo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void binding(OrgBankAccountBean reqParam) {
		orgBankMapper.binding(reqParam);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Integer update(OrgBankAccountBean reqParam){
		return orgBankMapper.update(reqParam);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void unbinding(KeyOperateParam reqParam) {
		final int relId = reqParam.getId().intValue();
		final OrgBankAccountBean orgAccount = orgBankMapper.getByIdLock(relId);
		if (orgAccount == null) {
			LOGGER.warn("unbinding account not exist,accountId:{}.", relId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bank.unbindrelation.not.exist"));
		}
		orgBankMapper.unbinding(reqParam);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void enable(EnableOperateParam reqParam) {
		final int relId = reqParam.getId().intValue();
		final OrgBankAccountBean orgAccount = orgBankMapper.getByIdLock(relId);
		if (orgAccount == null) {
			LOGGER.warn("enable account not exist,accountId:{}.", relId);
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bank.able.bindrelation.not.exist"));
		}
		orgBankMapper.enable(reqParam);
	}

	public Integer queryAccountIdById(Integer id){
		return orgBankMapper.queryAccountIdById(id);
	}
}
