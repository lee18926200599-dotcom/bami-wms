package com.org.permission.server.org.mapper;


import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.org.bean.BaseEntrustRelationBean;
import com.org.permission.server.org.bean.EntrustRelationshipInfoBean;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.StateBean;
import com.org.permission.server.org.dto.StorageInfoDto;
import com.org.permission.server.org.dto.param.MarketEntrustRelationReqParam;
import com.org.permission.server.org.dto.param.QueryEntrustRelationshipListReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 业务委托关系
 */
@Mapper
public interface CommonEntrustRelationMapper {
    /**
     * 删除业务委托关系
     *
     * @param entrustRelationshipId 委托关系ID
     */
    void deleteEntrustRelation(@Param(value = "entrustRelationshipId") Long entrustRelationshipId);

    /**
     * 启用 | 停用 业务委托关系
     *
     * @param bean 启停实体数据
     */
    void enableEntrustRelation(@Param(value = "bean") EnableOperateParam bean, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 根据业务单元ID,修改委托关系状态
     *
     * @param buId         业务单元ID
     * @param userId       用户ID
     * @param modifyStatus 修改状态
     * @param sourceStatus 原有状态
     * @param modifiedDate   更新时间
     */
    void modifyEntrustRelationByOrgId(@Param(value = "buId") Long buId, @Param(value = "userId") Long userId, @Param(value = "modifiedDate") Date modifiedDate, @Param(value = "modifyStatus") Integer modifyStatus, @Param(value = "sourceStatus") Integer sourceStatus);
    void delEntrustRelationByOrgId(@Param(value = "buId") Long buId, @Param(value = "userId") Long userId, @Param(value = "modifiedDate") Date modifiedDate, @Param(value = "deletedFlag") Integer deletedFlag, @Param(value = "sourceStatus") Integer sourceStatus);

    /**
     * 置业务委托关系为非默认状态
     *
     * @param entrustId 委托关系 ID
     */
    void disdefaultEntrustRelationById(@Param(value = "entrustId") Long entrustId, @Param(value = "userId") Integer userId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 根据采购委托关系查询对应的 ID
     *
     * @param bean 采购委托关系
     * @return 委托关系 ID
     */
    Long queryBUProductEntrustRelationId(@Param(value = "bean") BaseEntrustRelationBean bean);

    /**
     * 根据 ID 查询委托关系状态
     *
     * @param entrustId 委托关系ID
     * @return 委托关系实体状态
     */
    StateBean queryEntrustRelationStateByIdLock(@Param(value = "entrustId") Long entrustId);

    /**
     * 根据ID查询业务委托关系
     *
     * @param entrustRelationshipId 业务委托关系ID
     * @return 数据库对应的业务委托关系数据实体
     */
    EntrustRelationshipInfoBean queryEntrustRelationshipById(@Param(value = "entrustRelationshipId") Long entrustRelationshipId);

    /**
     * 根据参数统计业务委托关系数量
     *
     * @param queryParam
     * @return
     */
    int countEntrustRelationship(@Param(value = "queryParam") QueryEntrustRelationshipListReqParam queryParam);

    /**
     * 根据参数查询业务委托关系列表
     *
     * @param queryParam 查询业务委托关系列表请求参数
     * @return 数据库对应的业务委托关系数据实体列表
     */
    List<EntrustRelationshipInfoBean> queryEntrustRelationshipList(@Param(value = "queryParam") QueryEntrustRelationshipListReqParam queryParam);

    /**
     * 查询货主可用仓储委托关系
     *
     * @param orgId 货主ID
     * @return 所有可用委托关系
     */
    List<StorageInfoDto> queryOwnerEnableWarehouse(@Param(value = "orgId") Integer orgId);

    /**
     * 查询财务委托关系
     *
     * @param queryParam 财务委托关系查询请求参数
     * @return 财务委托关系集合
     */
    List<FinanceEntrustRelationBean> queryFinanceEntrustRelation(@Param(value = "queryParam") QueryFinanceEntrustRelationParam queryParam);

    /**
     * 统计采销委托关系
     *
     * @param reqParam 分页查询采销委托关系查询请求参数
     * @return 数量
     */
    int countMarketEntrustRelation(@Param(value = "queryParam") MarketEntrustRelationReqParam reqParam);

    Long queryEntrustRelationWithCondition(@Param(value = "entrustRange") Integer entrustRange, @Param(value = "entrustType") Integer entrustType, @Param(value = "warehourseId") Long warehourseId, @Param(value = "purchaseSaleOrg") Long purchaseSaleOrg, @Param(value = "stockOrg") Long stockOrg, @Param(value = "logisticsOrg") Long logisticsOrg, @Param(value = "settmentOrg") Long settmentOrg, @Param(value = "accountOrg") Long accountOrg, @Param(value = "businessOrg") Long businessOrg);

    List<EntrustRelationOrgInfoDto> queryPurchaseEntrustRelation(@Param(value = "purchaseSaleOrgId") Long purchaseSaleOrgId);

}

