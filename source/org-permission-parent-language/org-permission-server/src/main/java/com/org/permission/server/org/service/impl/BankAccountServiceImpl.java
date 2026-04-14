package com.org.permission.server.org.service.impl;

import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.mapper.OrgBankAccountRelMapper;
import com.org.permission.server.org.service.BankAccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public  class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private OrgBankAccountRelMapper orgBankAccountRelMapper;

    @Override
    public List<OrgBankAccountBean> getList(OrgBankAccountBean params) {
        return orgBankAccountRelMapper.selectList(params);
    }

    @Override
    public void update(OrgBankAccountBean update) {
        orgBankAccountRelMapper.update(update);
    }

    @Override
    public void save(OrgBankAccountBean orgBankAccountBean) {
        orgBankAccountRelMapper.insert(orgBankAccountBean);
    }

    @Override
    public OrgBankAccountBean getOne(Long id) {
        return orgBankAccountRelMapper.select(id);
    }

    @Override
    public List<OrgBankAccountBean> getListByIds(Set<Integer> ids) {
        return orgBankAccountRelMapper.selectListByIds(ids);
    }

    @Override
    public List<OrgBankAccountBean> getListByAccountIds(Set<Long> accountIds) {
        return orgBankAccountRelMapper.selectListByAccountIds(accountIds);
    }

    @Override
    public void saveList(List<OrgBankAccountBean> addBeans) {
        if(CollectionUtils.isNotEmpty(addBeans)) {
            orgBankAccountRelMapper.insertBatch(addBeans);
        }
    }

    @Override
    public void updateStatusByIds(List<Long> updateIds, Integer status) {
        if(CollectionUtils.isNotEmpty(updateIds)) {
            orgBankAccountRelMapper.updateStateByIds(updateIds, status);
        }
    }
    @Override
    public void deleteByIds(List<Long> deleteIds) {
        if(CollectionUtils.isNotEmpty(deleteIds)) {
            orgBankAccountRelMapper.deleteByIds(deleteIds);
        }
    }
}
