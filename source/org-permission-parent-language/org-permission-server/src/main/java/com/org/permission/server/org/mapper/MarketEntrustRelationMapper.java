package com.org.permission.server.org.mapper;


import com.org.permission.server.org.bean.MarketEntrustRelationBean;
import com.org.permission.server.org.bean.MarketEntrustRelationInfoBean;
import com.org.permission.common.org.dto.MarketEntrustRelationDto;
import com.org.permission.common.org.dto.WarehouseEnterOwnerRankingListDto;
import com.org.permission.common.org.param.MarketEntrustRelationQueryParam;
import com.org.permission.common.org.param.WarehouseEnterOwnerRankingListParam;
import com.org.permission.server.org.dto.param.MarketEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采销业务委托关系写
 */
@Mapper
public interface MarketEntrustRelationMapper {

    /**
     * 新增采销业务委托关系
     *
     * @param bean 采销业务委托关系数据实体
     */
    void addMarketEntrustRelation(@Param(value = "bean") MarketEntrustRelationBean bean);

    /**
     * 更新采销业务委托关系
     *
     * @param bean 采销业务委托关系数据实体
     */
    void updateMarketEntrustRelation(@Param(value = "bean") MarketEntrustRelationBean bean);

    /**
     * 根据货主查询采销委托关系
     *
     * @param owerId 货主ID
     * @return 采销委托关系集合
     */
    List<MarketEntrustRelationBean> queryMarketEntrustRelationByOwerLock(@Param(value = "owerId") Long owerId);

    /**
     * 根据 ID 查询采销委托关系（锁）
     *
     * @param entrustId 委托关系ID
     * @return 采销委托关系集合
     */
    MarketEntrustRelationBean queryEntrustRelationById(@Param(value = "entrustId") Long entrustId);

    /**
     * 统计采销委托关系
     *
     * @param reqParam 分页查询采销委托关系查询请求参数
     * @return 数量
     */
    int countMarketEntrustRelation(@Param(value = "queryParam") MarketEntrustRelationReqParam reqParam);

    /**
     * 查询采销委托关系列表
     *
     * @param reqParam 分页查询采销委托关系查询请求参数
     * @return 采销委托关系集合
     */
    List<MarketEntrustRelationInfoBean> queryMarketEntrustRelationList(@Param(value = "queryParam") MarketEntrustRelationReqParam reqParam);

    /**
     * 查询采销委托关系
     *
     * @param queryParam 采销委托管关系询请求参数
     * @return 采销委托关系集合
     */
    List<MarketEntrustRelationDto> queryMarketEntrustRelation(@Param(value = "queryParam") MarketEntrustRelationQueryParam queryParam);

    /**
     * 仓库入驻货主排行榜
     *
     * @return 排行榜列表
     */
    List<WarehouseEnterOwnerRankingListDto> warehouseEnterOwnerRankingList(@Param(value = "reqParam") WarehouseEnterOwnerRankingListParam reqParam);
}

