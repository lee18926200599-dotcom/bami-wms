package com.org.permission.server.org.mapper;


import com.org.permission.server.org.bean.LogisticsEntrustRelationBean;
import com.org.permission.server.org.bean.LogisticsEntrustRelationInfoBean;
import com.org.permission.server.org.dto.LogisticEntrustRelationDto;
import com.org.permission.server.org.dto.param.LogisticEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.LogisticsEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流业务委托关系写
 */
@Mapper
public interface LogisticsEntrustRelationMapper {

    /**
     * 新增物流业务委托关系
     *
     * @param bean 物流业务委托关系数据实体
     */
    void addLogisticsEntrustRelation(@Param(value = "bean") LogisticsEntrustRelationBean bean);

    /**
     * 新增物流业务委托关系
     *
     * @param bean 物流业务委托关系数据实体
     */
    void updateLogisticsEntrustRelation(@Param(value = "bean") LogisticsEntrustRelationBean bean);

    /**
     * 根据物流服务商，查询集团间物流业务委托关系
     *
     * @param logisticProviderId 物流服务商ID
     * @return 物流业务委托关系集合
     */
    List<LogisticsEntrustRelationBean> queryLogisticEntrustRelationByLogisticProviderLock(@Param(value = "logisticProviderId") Long logisticProviderId);

    /**
     * 根据委托关系ID,查询物流业务委托关系
     *
     * @param entrustId 委托关系 ID
     * @return 物流业务委托关系
     */
    LogisticsEntrustRelationBean queryEntrustRelationByIdLock(@Param(value = "entrustId") Long entrustId);

    /**
     * 统计物流委托关系
     *
     * @param reqParam 分页查询物流委托关系查询请求参数
     * @return 数量
     */
    int countLogisticsEntrustRelation(@Param(value = "queryParam") LogisticsEntrustRelationReqParam reqParam);

    /**
     * 查询物流委托关系列表
     *
     * @param queryParam 分页查询物流委托关系查询请求参数
     * @return 物流委托关系集合
     */
    List<LogisticsEntrustRelationInfoBean> queryLogisticsEntrustRelationList(@Param(value = "queryParam") LogisticsEntrustRelationReqParam queryParam);

    /**
     * 查询物流委托关系
     *
     * @param queryParam 物流委托关系查询请求参数
     * @return 物流委托关系集合
     */
    List<LogisticEntrustRelationDto> queryLogisticsEntrustRelation(@Param(value = "queryParam") LogisticEntrustRelationQueryParam queryParam);
}
