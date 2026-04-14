package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.StoragePlatformEntrustRelationBean;
import com.org.permission.server.org.bean.StoragePlatformEntrustRelationInfoBean;
import com.org.permission.common.org.dto.StoragePlatformEntrustRelationDto;
import com.org.permission.common.org.param.QueryStorageEntrustRelationLogisticsOrgParam;
import com.org.permission.common.org.param.StoragePlatformEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集团间仓储委托关系
 */
@Mapper
public interface StoragePlatformEntrustRelationMapper {

    /**
     * 新增集团间仓储业务委托关系
     *
     * @param bean 集团间仓储业务委托关系数据实体
     */
    void addStorageEntrustRelation(@Param(value = "bean") StoragePlatformEntrustRelationBean bean);

    /**
     * 新增集团间仓储业务委托关系
     *
     * @param bean 集团间仓储业务委托关系数据实体
     */
    void updateStorageEntrustRelation(@Param(value = "bean") StoragePlatformEntrustRelationBean bean);

    /**
     * 根据仓储服务商，查询集团间仓储业务委托关系
     *
     * @param warehouseProviderId 仓储服务商ID
     * @return 集团间仓储业务委托关系集合
     */
    List<StoragePlatformEntrustRelationBean> queryStorageEntrustRelationByWarehouseProviderLock(@Param(value = "warehouseProviderId") Long warehouseProviderId);

    /**
     * 根据ID,查询集团间仓储业务委托关系
     *
     * @param entrustId 委托关系ID
     * @return 集团间仓储业务委托关系
     */
    StoragePlatformEntrustRelationBean queryEntrustRelationByIdLock(@Param(value = "entrustId") Long entrustId);

    /**
     * 统计仓储委托关系
     *
     * @param reqParam 分页查询仓储委托关系查询请求参数
     * @return 数量
     */
    int countStorageEntrustRelation(@Param(value = "queryParam") PlatformStorageEntrustRelationReqParam reqParam);

    /**
     * 查询仓储委托关系列表
     *
     * @param reqParam 分页查询仓储委托关系查询请求参数
     * @return 仓储委托关系集合
     */
    List<StoragePlatformEntrustRelationInfoBean> queryStorageEntrustRelationList(@Param(value = "queryParam") PlatformStorageEntrustRelationReqParam reqParam);

    /**
     * 查询集团间仓储委托关系
     *
     * @param queryParam 集团间仓储委托关系查询参数
     * @return 集团间仓储委托关系实体数据
     */
    List<StoragePlatformEntrustRelationDto> queryPlatformStorageEntrustRelation(@Param("queryParam") StoragePlatformEntrustRelationQueryParam queryParam);

    /**
     * 查询集团间具有委托关系的物流组织
     *
     * @return 物流组织列表信息
     */
    List<Long> queryOutGroupLogisticOrg(@Param("queryParam") QueryStorageEntrustRelationLogisticsOrgParam queryParam);
}
