package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.FinanceEntrustRelationInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationByUserPermissionParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.FinanceEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationReqParam;

import java.util.List;
import java.util.Map;

/**
 * 财务委托关系服务
 */
public interface FinanceEntrustRelationService {

    /**
     * 新增财务委托关系
     *
     * @param reqParam 财务委托关系
     */
    void addFinanceEntrustRelation(FinanceEntrustRelationBean reqParam);

    /**
     * 更新财务委托关系
     *
     * @param reqParam 财务委托关系
     */
    void updateFinanceEntrustRelation(FinanceEntrustRelationBean reqParam);

    /**
     * 启停财务委托关系
     *
     * @param reqParam 启停操作请求参数
     */
    void enableFinanceEntrustRelation(EnableOperateParam reqParam);

    /**
     * 查询财务委托关系列表
     *
     * @param reqParam 分页查询财务委托关系查询请求参数
     * @return 财务委托关系集合
     */
    PageInfo<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationList(FinanceEntrustRelationReqParam reqParam);

    /**
     * 查询财务委托关系
     *
     * @param reqParam 财务委托关系查询请求参数
     * @return 财务委托关系
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelation(QueryFinanceEntrustRelationParam reqParam);

    /**
     * 根据用户权限查询财务委托关系
     *
     * @param reqParam 查询请求参数
     * @return 财务委托关系
     */
    List<FinanceEntrustRelationInfoBean> queryFinanceEntrustRelationByUserPermiss(QueryFinanceEntrustRelationByUserPermissionParam reqParam);

    /**
     * 批量查询财务委托关系
     *
     * @param orgIds 查询请求参数
     * @return 财务委托关系
     */
    Map<Long, List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelationBatch(List<Long> orgIds);
}
