package com.org.permission.server.org.mapper;


import com.org.permission.common.org.param.BankAccountQueryParam;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.bean.OrgBankAccountInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OrgBankMapper {
    /**
     * 绑定
     *
     * @param bean 绑定参数
     */
    void binding(@Param("bean") OrgBankAccountBean bean);

    Integer update(@Param("bean") OrgBankAccountBean bean);

    /**
     * 解绑
     *
     * @param bean 解绑参数
     */
    void unbinding(@Param("bean") KeyOperateParam bean);

    /**
     * 启停用
     *
     * @param bean 启停参数
     */
    void enable(@Param("bean") EnableOperateParam bean);

    /**
     * 根据主键查询绑定关系
     *
     * @param id 主键
     */
    OrgBankAccountBean getByIdLock(@Param("id") Integer id);

    /**
     * 统计银行账号数量
     *
     * @param queryParam 查询参数
     * @return 数量
     */
    int countOrgBankAccout(@Param("queryParam") BankAccountQueryParam queryParam);

    /**
     * 银行账号分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    List<OrgBankAccountInfoBean> queryOrgBankAccout(@Param("queryParam") BankAccountQueryParam queryParam);

    Integer queryAccountIdById(@Param("id") Integer id);
}
