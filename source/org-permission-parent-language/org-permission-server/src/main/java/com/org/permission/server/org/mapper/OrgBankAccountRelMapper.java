package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.OrgBankAccountBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 资金账号管理
 */
@Mapper
public interface OrgBankAccountRelMapper {

    /**
     * 添加资金账号
     *
     * @param orgBankAccountBean
     */
    void insert(@Param("params") OrgBankAccountBean orgBankAccountBean);


    /**
     * 根据主键修改
     *
     * @param orgBankAccountBean
     */
    void update(@Param("params") OrgBankAccountBean orgBankAccountBean);

    /**
     * 根据参数查找返回值
     *
     * @param orgBankAccountBean
     * @return
     */
    List<OrgBankAccountBean> selectList(@Param("params") OrgBankAccountBean orgBankAccountBean);


    /**
     * 根据查询参数返回一个 资金账户
     *
     * @param id
     * @return
     */
    OrgBankAccountBean select(@Param("id") Long id);

    /**
     * 根据主键查找集合
     *
     * @param ids
     * @return
     */
    List<OrgBankAccountBean> selectListByIds(@Param("ids") Set<Integer> ids);

    /**
     * 根据账户ID查找集合
     *
     * @param accountIds
     * @return
     */
    List<OrgBankAccountBean> selectListByAccountIds(@Param("accountIds") Set<Long> accountIds);

    /**
     * 批量添加
     *
     * @param addBeans
     */
    void insertBatch(@Param("params") List<OrgBankAccountBean> addBeans);

    /**
     * 根据ID 更新状态
     *
     * @param updateIds
     * @param status
     */
    void updateStateByIds(@Param("ids") List<Long> updateIds, @Param("status") Integer status);
    /**
     * 根据ID 更新状态
     *
     * @param deleteIds
     */
    void deleteByIds(@Param("ids") List<Long> deleteIds);
}
