package com.org.permission.server.org.service;


import com.org.permission.server.org.bean.OrgBankAccountBean;

import java.util.List;
import java.util.Set;

/**
 * 资金账号管理
 */
public interface BankAccountService {


    /**
     * 获取列表
     * @param params
     * @return
     */
    List<OrgBankAccountBean> getList(OrgBankAccountBean params);


    /**
     * 修改对象
     * @param update
     */
    void update(OrgBankAccountBean update);


    /**
     * 添加对象
     * @param orgBankAccountBean
     * @return
     */
    void save(OrgBankAccountBean orgBankAccountBean);


    /**
     * 根据主键获取唯一一条数据
     * @param id
     * @return
     */
    OrgBankAccountBean getOne(Long id);


    /**
     * 根据IDs查找数据
     * @param ids
     * @return
     */
    List<OrgBankAccountBean> getListByIds(Set<Integer> ids);

    /**
     * 根据账户ID 获取数据
     * @param accountIds
     * @return
     */
    List<OrgBankAccountBean> getListByAccountIds(Set<Long> accountIds);


    /**
     * 批量保存
     * @param addBeans
     */
    void saveList(List<OrgBankAccountBean> addBeans);

    /**
     * 批量更新
     * @param updateIds
     * @param status
     */
    void updateStatusByIds(List<Long> updateIds, Integer state);

    void deleteByIds(List<Long> deleteIds);
}
