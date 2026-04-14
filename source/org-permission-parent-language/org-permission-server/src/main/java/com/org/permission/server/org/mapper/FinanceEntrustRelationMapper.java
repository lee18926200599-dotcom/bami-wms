package com.org.permission.server.org.mapper;


import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.FinanceEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 财务委托关系 写 mapper
 */
@Mapper
public interface FinanceEntrustRelationMapper {
    /**
     * 新增财务业务委托关系
     *
     * @param bean 财务业务委托关系数据实体
     */
    void addFinanceEntrustRelation(@Param(value = "bean") FinanceEntrustRelationBean bean);

    /**
     * 新增财务业务委托关系
     *
     * @param beans 财务业务委托关系数据实体
     */
    void batchAddFinanceEntrustRelation(@Param(value = "beans") List<FinanceEntrustRelationBean> beans);

    /**
     * 更新财务业务委托关系
     *
     * @param bean 财务业务委托关系数据实体
     */
    void updateFinanceEntrustRelation(@Param(value = "bean") FinanceEntrustRelationBean bean);

    /**
     * 更新默认财务业务委托关系
     *
     * @param bean 财务业务委托关系数据实体
     */
    void updateBUProductFinanceEntrustRelation(@Param(value = "bean") FinanceEntrustRelationBean bean);

    /**
     * 更新级联的财务业务委托关
     *
     * @param bean 财务业务委托关系数据实体
     */
    void cascadeUpdateFinanceEntrustRelation(@Param(value = "bean") FinanceEntrustRelationBean bean);

    /**
     * 根据业务组织，查询集团内财务委托关系
     *
     * @param businuessOrgId 业务组织ID
     * @return 集团内财务委托关系集合
     */
    List<FinanceEntrustRelationBean> queryFinanceEntrustRelationByOrgIdLock(@Param(value = "businuessOrgId") Long businuessOrgId);

    /**
     * 根据委托关系 ID，查询集团内某业务组织的所有财务委托关系
     *
     * @param entrustId 委托关系ID
     * @return 集团内财务委托关系集合
     */
    List<FinanceEntrustRelationBean> queryBizOrgFinanceEntrustRelByEntrustIdLock(@Param(value = "entrustId") Long entrustId);

    /**
     * 级联启停用委托关系
     *
     * @param bean 启停请求参数
     */
    void cascadeEnableEntrustRelation(@Param(value = "bean") EnableOperateParam bean, @Param("modifiedDate") Date modifiedDate);

    /**
     * 根据财务委托关系判断是否存在同样的数据
     *
     * @param newBean 新财务委托关系
     * @return 财务委托关系ID集合
     */
    Long queryFinanceEntrustRelByBean(@Param(value = "newBean") FinanceEntrustRelationBean newBean);

    /**
     * 根据财务组织id，清空核算结算组织 ID
     *
     * @param financeOrgId 业务组织ID
     */
    void cleanFinanceEntrustRelationByFinanceOrgId(@Param(value = "financeOrgId") Long financeOrgId);

    /**
     * 统计采销委托关系
     *
     * @param reqParam 分页查询采销委托关系查询请求参数
     * @return 数量
     */
    int countFinanceEntrustRelation(@Param(value = "queryParam") FinanceEntrustRelationReqParam reqParam);

    /**
     * 查询采销委托关系列表
     *
     * @param reqParam 分页查询采销委托关系查询请求参数
     * @return 采销委托关系集合
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationList(@Param(value = "queryParam") FinanceEntrustRelationReqParam reqParam);

    /**
     * 查询采销委托关系
     *
     * @param queryParam 采销委托管关系询请求参数
     * @return 采销委托关系集合
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelation(@Param(value = "queryParam") QueryFinanceEntrustRelationParam queryParam);

    /**
     * 根据组织角色查询采销委托关系
     *
     * @param orgIds  组织ID
     * @param orgRole 组织角色
     * @return 采销委托关系集合
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationByOrgRole(@Param(value = "orgIds") List<Long> orgIds, @Param(value = "orgRole") Integer orgRole);

    /**
     * 批量查询财务委托关系
     *
     * @param orgIds 组织ID
     * @return 采销委托关系集合
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationBatch(@Param(value = "orgIds") List<Long> orgIds);

    /**
     * 根据业务组织id,结算组织id，核算组织id查询财务委托关系
     *
     * @param queryParam
     * @return
     */
    List<Integer> queryFinanceEntrustByOrgIdAndSettleOrgAndAccountOrg(@Param(value = "queryParam") QueryFinanceEntrustRelationParam queryParam);
}
