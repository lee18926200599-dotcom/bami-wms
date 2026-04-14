package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.StorageGroupEntrustRelationBean;
import com.org.permission.server.org.bean.StorageGroupEntrustRelationInfoBean;
import com.org.permission.server.org.bean.StoragePlatformEntrustRelationInfoBean;
import com.org.permission.common.org.dto.StorageGroupEntrustRelationDto;
import com.org.permission.common.org.param.QueryStorageEntrustRelationLogisticsOrgParam;
import com.org.permission.common.org.param.StorageGroupEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集团内仓储委托关系
 */
@Mapper
public interface StorageGroupEntrustRelationMapper {
    /**
     * 新增集内间仓储业务委托关系
     *
     * @param bean 集团内仓储业务委托关系数据实体
     */
    void addStorageEntrustRelation(@Param(value = "bean") StorageGroupEntrustRelationBean bean);

    /**
     * 更新集团内仓储业务委托关系
     *
     * @param bean 集团内仓储业务委托关系数据实体
     */
    void updateStorageEntrustRelation(@Param(value = "bean") StorageGroupEntrustRelationBean bean);

    /**
     * 更新默认集团内仓储业务委托关系
     *
     * @param bean 集团内仓储业务委托关系数据实体
     */
    void updateBUProductStorageEntrustRelation(@Param(value = "bean") StorageGroupEntrustRelationBean bean);

    /**
     * 根据 ID 查询集团内仓储委托关系(锁)
     *
     * @param entrustId 委托关系 ID
     * @return 集团内仓储委托关系
     */
    StorageGroupEntrustRelationBean queryGroupStorageEntrustRelationByIdLock(Long entrustId);

    /**
     * 根据库存组织，查询集团内仓储委托关系
     *
     * @param stockOrg 库存组织ID
     * @return 集团内仓储委托关系集合
     */
    List<StorageGroupEntrustRelationBean> queryGroupStorageEntrustRelationByStockOrgIdLock(@Param(value = "stockOrg") Long stockOrg);

    /**
     * 根据库存组织ID，删除集团内仓储委托关系
     *
     * @param stockOrgId 库存组织ID
     */
    void deleteGroupStorageEntrustRelationByStockOrgId(@Param(value = "stockOrgId") Long stockOrgId);

    /**
     * 统计仓储委托关系
     *
     * @param reqParam 分页查询仓储委托关系查询请求参数
     * @return 数量
     */
    int countStorageEntrustRelation(@Param(value = "queryParam") GroupStorageEntrustRelationReqParam reqParam);

    /**
     * 查询仓储委托关系列表
     *
     * @param reqParam 分页查询仓储委托关系查询请求参数
     * @return 仓储委托关系集合
     */
    List<StorageGroupEntrustRelationInfoBean> queryStorageEntrustRelationList(@Param(value = "queryParam") GroupStorageEntrustRelationReqParam reqParam);

    /**
     * 查询集团内具有委托关系的物流组织
     *
     * @return 物流组织列表信息
     */
    List<Long> queryInGroupLogisticOrg(@Param("queryParam") QueryStorageEntrustRelationLogisticsOrgParam queryParam);

    /**
     * 查询集团间仓储委托关系
     *
     * @param queryParam 集团间仓储委托关系查询参数
     * @return 集团间仓储委托关系实体数据
     */
    List<StorageGroupEntrustRelationDto> queryGroupStorageEntrustRelation(@Param("queryParam") StorageGroupEntrustRelationQueryParam queryParam);

    /**
     * 根据主键id查询仓储委托关系
     *
     * @param id
     * @return
     */
    StoragePlatformEntrustRelationInfoBean queryStorageEntrustRelationById(@Param("id") Integer id);
}
