package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.StorageGroupEntrustRelationExtendDto;
import com.org.permission.common.org.param.EntrustRelationQueryParam;
import com.org.permission.common.org.param.QueryStorageEntrustRelationLogisticsOrgParam;
import com.org.permission.common.org.param.StorageGroupEntrustRelationQueryParam;
import com.org.permission.common.org.param.StoragePlatformEntrustRelationQueryParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 仓储委托关系
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface StorageEntrustRelationFeign {

    /**
     * 查询集团间仓储委托关系
     *
     * @param reqParam 集团间仓储委托关系查询参数
     * @return 仓储委托关系实体数据
     */
    @PostMapping(value = "/storage-entrust/queryPlatformStorageEntrustRelation")
    RestMessage<List<com.org.permission.common.org.dto.StoragePlatformEntrustRelationDto>> queryPlatformStorageEntrustRelation(@RequestBody StoragePlatformEntrustRelationQueryParam reqParam);

    /**
     * 查询集团内仓储委托关系
     *
     * @param reqParam 集团内仓储委托关系查询参数
     * @return 仓储委托关系实体数据
     */
    @PostMapping(value = "/storage-entrust/queryGroupStorageEntrustRelation")
    RestMessage<List<com.org.permission.common.org.dto.StorageGroupEntrustRelationDto>> queryGroupStorageEntrustRelation(@RequestBody StorageGroupEntrustRelationQueryParam reqParam);

    /**
     * 查询集团内仓储委托关系(扩展版)
     * TMS 查询物流组织对应的全局客商 ID
     *
     * @param reqParam 集团内仓储委托关系查询参数
     * @return 仓储委托关系实体数据
     */
    @PostMapping(value = "/storage-entrust/extendQueryGroupStorageEntrustRelation")
    RestMessage<List<StorageGroupEntrustRelationExtendDto>> extendQueryGroupStorageEntrustRelation(@RequestBody StorageGroupEntrustRelationQueryParam reqParam);

    /**
     * 查询集团间仓储委托关系(扩展版)
     *
     * @param reqParam 集团间仓储委托关系查询参数
     * @return 集团间仓储委托关系实体数据
     */
    @PostMapping(value = "/storage-entrust/extendQueryPlatformStorageEntrustRelation")
    RestMessage<List<com.org.permission.common.org.dto.StoragePlatformEntrustRelationExtendDto>> extendQueryPlatformStorageEntrustRelation(@RequestBody StoragePlatformEntrustRelationQueryParam reqParam);

    /**
     * 查询所有具有委托关系的物流组织
     *
     * @param reqParam 查询请求参数
     * @return 物流组织信息
     */
    @PostMapping(value = "/storage-entrust/queryAllLogisticOrgHasEntrustRelation")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryAllLogisticOrgHasEntrustRelation(@RequestBody EntrustRelationQueryParam reqParam);

    /**
     * 查询具有仓储委托关系的物流组织及其下具有结算能力的子业务单元
     *
     * @param reqParam 查询请求参数
     * @return 组织列表信息
     */
    @PostMapping(value = "/storage-entrust/queryHasStorageEntrustRelationLogisticsOrgSettleableMembers")
    RestMessage<List<com.org.permission.common.org.dto.OrgListDto>> queryHasStorageEntrustRelationLogisticsOrgSettleableMembers(@RequestBody QueryStorageEntrustRelationLogisticsOrgParam reqParam);

    /**
     * 查询有仓储委托关系的物流组织信息
     *
     * @param reqParam 查询请求参数
     * @return 物流组织信息
     */
    @PostMapping(value = "/storage-entrust/queryHasStorageEntrustRelationLogisticsOrg")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryHasStorageEntrustRelationLogisticsOrg(@RequestBody EntrustRelationQueryParam reqParam);


    /**
     * 查询有仓储委托关系的物流组织信息根据仓库信息
     *
     * @param
     * @return
     */
    @PostMapping(value = "/storage-entrust/queryStorageEntrustRelationLogisticsOrgByWarehouse")
    RestMessage<List<com.org.permission.common.org.dto.EntrustRelationOrgInfoDto>> queryStorageEntrustRelationLogisticsOrgByWarehouse(@RequestParam("stockOrg") Long stockOrg, @RequestParam("warehouseId") Long warehouseId);
}
